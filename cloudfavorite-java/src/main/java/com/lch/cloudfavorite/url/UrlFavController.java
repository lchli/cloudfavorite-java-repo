package com.lch.cloudfavorite.url;

import com.alibaba.fastjson.JSON;
import com.lch.cloudfavorite.base.FavBaseResponse;
import com.lch.cloudfavorite.msg.RabbitmqConfig;
import com.lch.cloudfavorite.util.SingleInstances;
import com.lch.cloudfavorite.util.TokenUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fav")
public class UrlFavController {

    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UrlMapper urlMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public FavBaseResponse add(@RequestParam("title") String title, @RequestParam("url") String url,
                               @RequestParam("userId") String userId, @RequestParam("token") String token) {
        FavBaseResponse response = new FavBaseResponse();

        try {
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(url) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(token)) {
                response.markErrorCode();
                response.errmsg = "参数不能为空";
                return response;
            }

            if (!tokenUtil.isTokenValid(userId, token)) {
                response.markErrorCode();
                response.errmsg = "token无效";
                return response;
            }

            UrlEntity entity = new UrlEntity();
            entity.url = url;
            entity.title = title;
            entity.userId = userId;
            entity.createDate = System.currentTimeMillis();
            entity.uid = SingleInstances.snowFlake.nextId() + "";

            urlMapper.save(entity);


            //创建消息对象
            Map<String, String> msg = new HashMap<>();
            msg.put("action", "write");
            msg.put("url", url);
            msg.put("title", title);
            msg.put("userId", userId);
            msg.put("uid", entity.uid);
            msg.put("createDate", entity.createDate + "");
            //转成json串
            String jsonString = JSON.toJSONString(msg);
            //发送给mq
            //站点id
            //String siteId = cmsPage.getSiteId();
            rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, "5b30cba5f58b4411fc6cb1e5", jsonString);

        } catch (Throwable e) {
            e.printStackTrace();
            response.markErrorCode();
            response.errmsg = e.getMessage();
        }

        return response;

    }


    @GetMapping("/delete")
    public FavBaseResponse delete(@RequestParam("userId") String userId, @RequestParam("token") String token,
                                  @RequestParam("favId") String favId) {
        FavBaseResponse response = new FavBaseResponse();

        try {
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token) || StringUtils.isEmpty(favId)) {
                response.markErrorCode();
                response.errmsg = "参数不能为空";
                return response;
            }

            if (!tokenUtil.isTokenValid(userId, token)) {
                response.markErrorCode();
                response.errmsg = "token无效";
                return response;
            }

            UrlEntity old = urlMapper.findByUid(favId);
            if (old == null) {
                response.markErrorCode();
                response.errmsg = "数据不存在";
                return response;
            }

            if (!userId.equals(old.userId)) {//owner?
                response.markErrorCode();
                response.errmsg = "无权限";
                return response;
            }

            urlMapper.deleteByUid(old.uid);

            //创建消息对象
            Map<String, String> msg = new HashMap<>();
            msg.put("action", "delete");

            msg.put("uid", favId);
            //转成json串
            String jsonString = JSON.toJSONString(msg);
            //发送给mq
            //站点id
            //String siteId = cmsPage.getSiteId();
            rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, "5b30cba5f58b4411fc6cb1e5", jsonString);

        } catch (Throwable e) {
            e.printStackTrace();
            response.markErrorCode();
            response.errmsg = e.getMessage();
        }

        return response;
    }


    @GetMapping("/queryFavs")
    public FavBaseResponse queryFavs(@RequestParam("userId") String userId, @RequestParam("token") String token
    ) {
        UrlListResponse response = new UrlListResponse();

        try {
            if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(token)) {
                response.markErrorCode();
                response.errmsg = "参数不能为空";
                return response;
            }

            if (!tokenUtil.isTokenValid(userId, token)) {
                response.markErrorCode();
                response.errmsg = "token无效";
                return response;
            }

            response.items = urlMapper.findByUserId(userId);

        } catch (Throwable e) {
            e.printStackTrace();
            response.markErrorCode();
            response.errmsg = e.getMessage();
        }

        return response;
    }

}
