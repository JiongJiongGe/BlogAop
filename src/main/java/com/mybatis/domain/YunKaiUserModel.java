package com.mybatis.domain;

import java.io.Serializable;

/**
 * 用户对象model
 *
 * Created by yunkai on 2017/12/21.
 */
public class YunKaiUserModel implements Serializable{

    private static final long serialVersionUID = 362869943123767241L;

    private Integer id;

    private String userName;  //用户名

    private String userPhone;  //用户联系方式

    private Integer isDeleted;  //是否删除;0、否;1、是

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
