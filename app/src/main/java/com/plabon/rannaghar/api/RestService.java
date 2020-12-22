package com.plabon.rannaghar.api;


import com.plabon.rannaghar.model.Category;
import com.plabon.rannaghar.model.CategoryResult;
import com.plabon.rannaghar.model.FirstOrder;
import com.plabon.rannaghar.model.FirstOrdersResult;
import com.plabon.rannaghar.model.Order;
import com.plabon.rannaghar.model.OrdersResult;
import com.plabon.rannaghar.model.PlaceOrder;
import com.plabon.rannaghar.model.ProductResult;
import com.plabon.rannaghar.model.Token;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.model.UserResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RestService {

    @POST("api/v1/register")
    Call<UserResult> register(@Body User user);

    @POST("api/v1/login")
    Call<UserResult> login(@Body User user);

    @POST("api/v1/loginotp")
    Call<UserResult> loginotp(@Body User user);

    @POST("api/v1/userupdate")
    Call<UserResult> userupdate(@Body User user);

    @POST("api/v1/allcategory")
    Call<CategoryResult> allCategory(@Body Token token);

    @POST("api/v1/newProduct")
    Call<ProductResult> newProducts(@Body Token token);

    @POST("api/v1/homepage")
    Call<ProductResult> popularProducts(@Body Token token);

    @POST("api/v1/getlist")
    Call<ProductResult> getCategoryProduct(@Body Category category);

    @POST("api/v1/placeorder")
    Call<OrdersResult> confirmPlaceOrder(@Body PlaceOrder placeOrder);

    @POST("api/v1/orderDetails")
    Call<OrdersResult> orderDetails(@Body Order order);

    @POST("api/v1/getfirst")
    Call<FirstOrdersResult> firstOrderDetails(@Body FirstOrder order);

    @POST("api/v1/updateUser")
    Call<UserResult> updateUser(@Body User user);

    @GET("api/v1/product/search")
    Call<ProductResult> searchProduct(@Query("s") String search);


}
