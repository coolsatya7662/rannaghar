package com.plabon.rannaghar.interfaces;

import com.plabon.rannaghar.api.clients.RestClient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SendOTPLoginApiInterface {

  String JSONURL = RestClient.BASE_URL + "api/v1/";

  // @GET("json_parsing.php")
  @GET("otplogin.php")
  Call<String> getString(@Query("m") String m
  );

}
