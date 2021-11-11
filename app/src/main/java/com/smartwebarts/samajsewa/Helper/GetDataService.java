package com.smartwebarts.samajsewa.Helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {
    @Headers("multipart: true")
    @POST("register_user")
    @Multipart
    Call<JsonObject> registration(
            @Part MultipartBody.Part image,
            @Part("name") RequestBody name,
            @Part("fname") RequestBody fname,
            @Part("age") RequestBody age,
            @Part("gender") RequestBody gender,
            @Part("email") RequestBody email,
            @Part("per_contact") RequestBody per_contact,
            @Part("contact") RequestBody contact,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("country") RequestBody country,
            @Part("pincode") RequestBody pincode,
            @Part("qualification") RequestBody qualification,
            @Part("occupation") RequestBody occupation,
            @Part("password") RequestBody password,
            @Part("refral") RequestBody refral
    );

    @POST("user_login")
    @FormUrlEncoded
    Call<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password

    );
    @POST("forgot_password")
    @FormUrlEncoded
    Call<JsonObject> forgotPassword(
            @Field("email") String email
    );

    @POST("updateForgetPassword")
    @FormUrlEncoded
    Call<JsonObject> updateForgetPassword(
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("cpassword") String cpassword
    );

    @POST("user_verification")
    @FormUrlEncoded
    Call<JsonObject> userverification(
            @Field("user_id") String user_id

    );

    @POST("user_details")
    @FormUrlEncoded
    Call<JsonObject> user_details(
            @Field("id") String id
    );

    @POST("showsMembers")
    @FormUrlEncoded
    Call<JsonArray> showsMembers(
            @Field("my_ref") String my_ref
    );
}
