package com.youzan.ad.controller;

import com.alibaba.fastjson.JSON;
import com.youzan.ad.entity.AdUser;
import com.youzan.ad.exception.AdException;
import com.youzan.ad.service.IUserService;
import com.youzan.ad.vo.CreateUserResponse;
import com.youzan.ad.vo.CreateUserResquest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * 创建user对象    post  restful接口设计规则    @PostMapping()
     */

    @RequestMapping(value ="",method =RequestMethod.POST)
    public CreateUserResponse saveUser(@RequestBody CreateUserResquest createUserResquest)throws AdException {

        log.info("youzan-ad-sponsor:saveUser->{}", JSON.toJSONString(createUserResquest));
        return  userService.createUser(createUserResquest);

    }
}
