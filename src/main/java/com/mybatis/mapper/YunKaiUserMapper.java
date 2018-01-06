package com.mybatis.mapper;

import com.mybatis.domain.YunKaiUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yunkai on 2017/12/22.
 */
@Mapper
@Component
public interface YunKaiUserMapper {

    /**
     * 获取用户信息
     *
     * @return
     */
    List<YunKaiUserModel> queryAll();

}
