package com.youzan.ad.service;

import com.youzan.ad.exception.AdException;
import com.youzan.ad.vo.CreateUserResponse;
import com.youzan.ad.vo.CreateUserResquest;

public interface IUserService {

    /**
     * 创建广告主用户
     */
    CreateUserResponse createUser(CreateUserResquest resquest) throws AdException;
}
