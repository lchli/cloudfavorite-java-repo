package com.lch.cloudfavorite.user;

import com.lch.cloudfavorite.util.SingleInstances;
import com.lch.cloudfavorite.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public RegisterResponse register(@RequestParam("account") String account, @RequestParam("pwd") String pwd) {
        RegisterResponse response = new RegisterResponse();

        try {
            if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
                response.markErrorCode();
                response.errmsg = "参数不能为空";
                return response;
            }

            UserEntity old = userMapper.findByAccount(account);
            if (old != null) {
                response.markErrorCode();
                response.errmsg = "账号已经存在";
                return response;
            }

            UserEntity userEntity = new UserEntity();
            userEntity.account = account;
            userEntity.pwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
            userEntity.uid = SingleInstances.snowFlake.nextId() + "";

            int _id = userMapper.save(userEntity);

            response.account = userEntity.account;
            response.uid = userEntity.uid;
            response.token = SingleInstances.snowFlake.nextId() + "";
            tokenUtil.saveToken(response.uid, response.token);

        } catch (Throwable e) {
            e.printStackTrace();
            response.markErrorCode();
            response.errmsg = e.getMessage();
        }

        return response;

    }


    @GetMapping("/login")
    public RegisterResponse login(@RequestParam("account") String account, @RequestParam("pwd") String pwd) {
        RegisterResponse response = new RegisterResponse();

        try {
            if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
                response.markErrorCode();
                response.errmsg = "参数不能为空";
                return response;
            }

            UserEntity old = userMapper.findByAccount(account);
            if (old == null) {
                response.markErrorCode();
                response.errmsg = "账号不存在";
                return response;
            }

            String paramPwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());

            if (!paramPwdMd5.equals(old.pwdMd5)) {
                response.markErrorCode();
                response.errmsg = "密码错误";
                return response;
            }

            response.account = old.account;
            response.uid = old.uid;
            response.token = SingleInstances.snowFlake.nextId() + "";
            tokenUtil.saveToken(response.uid, response.token);

        } catch (Throwable e) {
            e.printStackTrace();
            response.markErrorCode();
            response.errmsg = e.getMessage();
        }

        return response;
    }


}
