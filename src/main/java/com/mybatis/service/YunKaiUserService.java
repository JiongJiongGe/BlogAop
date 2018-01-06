package com.mybatis.service;

import com.mybatis.domain.YunKaiUserModel;
import com.mybatis.response.RpcResult;

import java.util.List;

/**
 * Created by yunkai on 2017/12/22.
 */
public interface YunKaiUserService {

    /**
     * 获取所有用户
     *
     * @return
     */
    RpcResult<List<YunKaiUserModel>> findAll();

    RpcResult<List<YunKaiUserModel>> findAllUser();

}
