package com.plabon.rannaghar.interfaces;

import com.plabon.rannaghar.api.clients.RestClient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SendOTPApiInterface {

  String JSONURL = RestClient.BASE_URL + "api/v1/";

  // @GET("json_parsing.php")
  @GET("otp.php")
  Call<String> getString(@Query("otp") String n,
                         @Query("m") String m
                         );

}
