package com.intent.wx.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/7 下午2:19
 * @since 1.0
 */
public class Config implements Serializable {
    private static final long serialVersionUID = -3413305675170831566L;

    private String cookie;
    private String signature;
    private boolean debug;
    private int pid;
    private int hid;
    private String date;
    private User user;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Integer convertHpvType(String hpvTypeStr) {
        if ("4价".equals(hpvTypeStr)) {
            return 2;
        } else if ("进口2价".equals(hpvTypeStr)) {
            return 3;
        } else if ("国产2价".equals(hpvTypeStr)) {
            return 54;
        }
        return 1;
    }

    public static int getHpvStrByPosition(Integer hpvType) {
        if (hpvType != null) {
            if (hpvType == 2) {
                return 1;
            } else if (hpvType == 3) {
                return 2;
            } else if (hpvType == 54) {
                return 3;
            }
        }
        return 0;
    }
}
