package com.was.minemvc.bean;



/**
 * 省份model
 */
public class ProvinceBean {

    /**
     * code : 110000
     * name : 北京市
     */
    private String code;
    private String name;

    private boolean isCheck; // 是否选择
    private boolean isUnlimited; //是否替补不限

    public ProvinceBean() {
    }

    public ProvinceBean(String code, String name) {
        this.code = code;
        this.code = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isUnlimited() {
        return isUnlimited;
    }

    public void setUnlimited(boolean unlimited) {
        isUnlimited = unlimited;
    }
}
