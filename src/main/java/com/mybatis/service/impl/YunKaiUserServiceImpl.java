package com.mybatis.service.impl;

import com.mybatis.aop.AuthChecker;
import com.mybatis.domain.YunKaiUserModel;
import com.mybatis.mapper.YunKaiUserMapper;
import com.mybatis.response.ErrorCode;
import com.mybatis.response.RpcResult;
import com.mybatis.service.YunKaiUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yunkai on 2017/12/22.
 */
@Service("yunKaiUserService")
public class YunKaiUserServiceImpl implements YunKaiUserService {

    private static Logger logger = LoggerFactory.getLogger(YunKaiUserServiceImpl.class);

    @Autowired
    private YunKaiUserMapper yunKaiUserMapper;

    /**
     * 获取所有用户的Id
     *
     * @return
     */
    @Override
    @AuthChecker
    public RpcResult<List<YunKaiUserModel>> findAll(){
        List<YunKaiUserModel> users = yunKaiUserMapper.queryAll();
        if(users != null && users.size() > 0){
            return RpcResult.ofSuccess(users);
        }
        return RpcResult.ofError(ErrorCode.BIZ_ERROR.getCode(), ErrorCode.BIZ_ERROR.getMsg("获取用户信息失败"));
    }

    @Override
    public RpcResult<List<YunKaiUserModel>> findAllUser(){
        List<YunKaiUserModel> users = yunKaiUserMapper.queryAll();
        if(users != null && users.size() > 0){
            return RpcResult.ofSuccess(users);
        }
        return RpcResult.ofError(ErrorCode.BIZ_ERROR.getCode(), ErrorCode.BIZ_ERROR.getMsg("获取用户信息失败"));
    }

}
