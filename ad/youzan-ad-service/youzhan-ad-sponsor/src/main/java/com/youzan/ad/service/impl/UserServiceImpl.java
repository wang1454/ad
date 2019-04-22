package com.youzan.ad.service.impl;

import com.youzan.ad.constant.Constants;
import com.youzan.ad.dao.AdUserRepository;
import com.youzan.ad.entity.AdUser;
import com.youzan.ad.exception.AdException;
import com.youzan.ad.service.IUserService;
import com.youzan.ad.util.CommonUtils;
import com.youzan.ad.vo.CreateUserResponse;
import com.youzan.ad.vo.CreateUserResquest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private AdUserRepository adUserRepository;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserResquest request) throws  AdException{

        //验证用户名是否为空
        if (!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAMETERS_ERROR);
        }

        //验证用户名是否唯一
        AdUser adUser=adUserRepository.findByUsername(request.getUsername());

        if (null!=adUser){
            throw new AdException(Constants.ErrorMsg.USER_REPETITION_ERROR);
        }

        //进行保存
        AdUser newUser=adUserRepository.save(new AdUser(request.getUsername(),CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(),newUser.getUsername(),newUser.getToken(),newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
