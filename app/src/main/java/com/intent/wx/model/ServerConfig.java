package com.intent.wx.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author intent zzy.main@gmail.com
 * @date 2021/11/13 上午11:50
 * @since 1.0
 */
public class ServerConfig implements Serializable {
    private static final long serialVersionUID = 1373163024658090408L;
    private String city;
    private Integer cityCode;
    private String lat;
    private String lng;
    private String subMonth;
    private String cookie;
    private String signature;
    private Integer pid;
    private List<String> filterKeyword;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hourOfDay;
    private Integer minute;
    private Integer second;
    private Integer hid;
    private String inoculationDate;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getSubMonth() {
        return subMonth;
    }

    public void setSubMonth(String subMonth) {
        this.subMonth = subMonth;
    }

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<String> getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(List<String> filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getInoculationDate() {
        return inoculationDate;
    }

    public void setInoculationDate(String inoculationDate) {
        this.inoculationDate = inoculationDate;
    }
}