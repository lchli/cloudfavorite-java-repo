package com.lch.cloudfavorite.search;


import com.lch.cloudfavorite.util.TokenUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/api/search")
public class EsController {

    @Value("${xuecheng.course.index}")
    private String index;

    @Value("${xuecheng.course.type}")
    private String type;

    @Value("${xuecheng.course.source_field}")
    private String source_field;


    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    TokenUtil tokenUtil;


    @GetMapping(value = "/list")
    public SearchFavResponse searchPost(@RequestParam("page") int page, @RequestParam("size") int size,
                                        @RequestParam("keyword") String keyword, @RequestParam("sort") String sort,
                                        @RequestParam(value = "userId",required = false) String userId,
                                        @RequestParam(value = "token",required = false) String token) {
        SearchFavResponse queryResult = new SearchFavResponse();
        try {


            if (!StringUtils.isEmpty(userId)) {
                if(StringUtils.isEmpty(token)||!tokenUtil.isTokenValid(userId, token)) {
                    queryResult.markErrorCode();
                    queryResult.errmsg = "token无效";
                    return queryResult;
                }
            }


            //创建搜索请求对象
            SearchRequest searchRequest = new SearchRequest(index);
            //设置搜索类型
            searchRequest.types(type);

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //过虑源字段
            String[] source_field_array = source_field.split(",");
            searchSourceBuilder.fetchSource(source_field_array, new String[]{});
            //创建布尔查询对象
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            //搜索条件
            //根据关键字搜索
            if (!StringUtils.isEmpty(keyword)) {
                MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "url")
                        .minimumShouldMatch("70%")
                        .field("title", 10);

                boolQueryBuilder.must(multiMatchQueryBuilder);
            }

            if (!StringUtils.isEmpty(userId)) {
                boolQueryBuilder.must(QueryBuilders.commonTermsQuery("userId", userId));/////
            }

            //设置boolQueryBuilder到searchSourceBuilder
            searchSourceBuilder.query(boolQueryBuilder);
            //设置分页参数
            if (page <= 0) {
                page = 1;
            }
            if (size <= 0) {
                size = 12;
            }
            //起始记录下标
            int from = (page - 1) * size;
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(size);

            //设置高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<font class='eslight'>");
            highlightBuilder.postTags("</font>");
            //设置高亮字段
            highlightBuilder.fields().add(new HighlightBuilder.Field("title"));
            searchSourceBuilder.highlighter(highlightBuilder);

            searchRequest.source(searchSourceBuilder);

            List<SearchFavResponse.Item> list = new ArrayList<>();
            try {
                //执行搜索
                SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
                //获取响应结果
                SearchHits hits = searchResponse.getHits();
                //匹配的总记录数
                long totalHits = hits.totalHits;
                // queryResult.setTotal(totalHits);
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    SearchFavResponse.Item coursePub = new SearchFavResponse.Item();
                    //源文档
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //取出id
                    String uid = (String) sourceAsMap.get("uid");
                    String title = (String) sourceAsMap.get("title");
                    coursePub.uid = uid;
                    coursePub.createDate = Long.parseLong((String) sourceAsMap.get("createDate"));
                    coursePub.url = (String) sourceAsMap.get("url");

                    //取出高亮字段name
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields != null) {
                        HighlightField highlightFieldName = highlightFields.get("title");
                        if (highlightFieldName != null) {
                            Text[] fragments = highlightFieldName.fragments();
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Text text : fragments) {
                                stringBuffer.append(text);
                            }
                            title = stringBuffer.toString();
                        }

                    }
                    coursePub.title = title;

                    //将coursePub对象放入list
                    list.add(coursePub);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            queryResult.items = list;

            return queryResult;

        } catch (Throwable e) {
            e.printStackTrace();
            queryResult.markErrorCode();
            queryResult.errmsg = e.getMessage();
        }

        return queryResult;
    }


}
