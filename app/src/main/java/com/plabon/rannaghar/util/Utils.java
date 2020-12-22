package com.plabon.rannaghar.util;

import com.plabon.rannaghar.api.clients.RestClient;


public class Utils {


    //Satya  9800970578
    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Fragments Tags
    public static final String Login_Fragment = "LoginFragment";
    public static final String SignUp_Fragment = "SignUpFragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String Home_Fragment = "HomeFragment";
    public static final String CategoryImage = RestClient.BASE_URL + "assets/images/ProductImage/category/";
    public static final String ProductImage = RestClient.BASE_URL + "assets/images/ProductImage/product/";


}
