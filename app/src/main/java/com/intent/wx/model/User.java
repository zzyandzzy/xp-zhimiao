package com.intent.wx.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午4:30
 * @since 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4392550166439709059L;

    private String birthday;
    private String tel;
    private String cname;
    private Integer sex;
    private String idcard;
    private Integer doctype;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getDoctype() {
        return doctype;
    }

    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
