package com.was.minemvc.data;

import com.was.minemvc.data.bean.ProvinceBean;
import com.was.minemvc.data.bean.SchoolUniformBean;
import com.was.minemvc.data.bean.UserBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * api 接口
 * Created by liukun on 16/3/9.
 */
public interface Api {
    /**
     * 登录
     *
     * @param phoneNumber
     * @param password
     * @return "content":{
     * "id":7,             //用户id
     * "type":3           //用户类型  1.家长 2.老师  3.普通用户
     * }
     */
    @FormUrlEncoded
    @POST("user/app_login.action")
    Observable<HttpResult<UserBean>>
    login(@Field("mobile") String phoneNumber, @Field("password") String password);


    @GET("nation/app_qryAllProvince.action")
    Observable<HttpResult<List<ProvinceBean>>> lookProvince();


    @GET("schoolUniform/app_qry.action")
    Observable<HttpResult<List<SchoolUniformBean>>> lookSchoolUniform(@Query("user_id") String userId,
                                                                      @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

}






