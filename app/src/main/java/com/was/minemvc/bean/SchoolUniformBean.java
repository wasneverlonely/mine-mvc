package com.was.minemvc.bean;

/**
 * 学校校服
 */
public class SchoolUniformBean {

    private int id;  //校服id
    private String img_url; //图片
    private String uniform_name;//校服名称
    private double price;//价格


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUniform_name() {
        return uniform_name;
    }

    public void setUniform_name(String uniform_name) {
        this.uniform_name = uniform_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
