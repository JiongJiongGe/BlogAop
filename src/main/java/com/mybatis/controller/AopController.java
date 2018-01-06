package com.mybatis.controller;

import com.mybatis.aop.AuthChecker;
import com.mybatis.domain.YunKaiUserModel;
import com.mybatis.mapper.YunKaiUserMapper;
import com.mybatis.response.RpcResult;
import com.mybatis.service.YunKaiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yunkai on 2018/1/2.
 */
@RestController
@RequestMapping("/aop")
public class AopController {

    @Autowired
    private YunKaiUserService yunKaiUserService;

    /**
     * controller方法需要AuthChecker
     *
     * @return
     */
    @AuthChecker
    @RequestMapping(value = "/need/check/auth")
    public String needCheckAuth(){
        return "need check auth is running ... ";
    }

    /**
     * service方法需要AuthChecker
     *
     * @return
     */
    @RequestMapping(value = "/not/need/check/auth")
    public RpcResult<List<YunKaiUserModel>> notNeedCheckAuth(){
        return yunKaiUserService.findAll();
    }

    /**
     * aop耗时统计
     *
     * @return
     */
    @RequestMapping(value = "/time/count")
    public RpcResult<List<YunKaiUserModel>> timeCount(){
        return yunKaiUserService.findAllUser();
    }
}
