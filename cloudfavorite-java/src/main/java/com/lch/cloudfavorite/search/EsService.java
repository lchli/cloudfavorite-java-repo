package com.lch.cloudfavorite.search;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EsService {

    @Value("${xuecheng.course.index}")
    private String index;

    @Value("${xuecheng.course.type}")
    private String type;

    @Value("${xuecheng.course.source_field}")
    private String source_field;


    @Autowired
    RestHighLevelClient restHighLevelClient;

    public void writePost(String url, String title,
                          String userId, String uid,String createDate) {

        try {

            Map<String, String> map = new HashMap<>();
            map.put("url", url);
            map.put("title", title);
            map.put("userId", userId);
            map.put("uid", uid);
            map.put("createDate", createDate);

            IndexRequest indexRequest = new IndexRequest(index, type).source(map);

            IndexResponse indexResp = restHighLevelClient.index(indexRequest);

            System.out.println("es code=" + indexResp.status().getStatus());

        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    public void updatePost(String content, String title,
                           String postId) {

//        try {
//
//            Map<String, String> map = new HashMap<>();
//            map.put("content", content);
//            map.put("title", title);
//            map.put("postId", postId);
//
//            UpdateRequest indexRequest = new UpdateRequest(index, type, postId).doc(map);
//
//            UpdateResponse indexResp = restHighLevelClient.update(indexRequest);
//            if (indexResp.status() == RestStatus.OK) {
//                return response;
//            }
//            response.markErrorCode();
//            response.errmsg = "code=" + indexResp.status().getStatus();
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//            response.markErrorCode();
//            response.errmsg = e.getMessage();
//        }
//
//        return response;

    }


    public void deletePost(String uid) {

        try {

            DeleteRequest indexRequest = new DeleteRequest(index, type, uid);

            DeleteResponse indexResp = restHighLevelClient.delete(indexRequest);

            System.out.println("es code=" + indexResp.status().getStatus());

        } catch (Throwable e) {
            e.printStackTrace();

        }


    }
}
