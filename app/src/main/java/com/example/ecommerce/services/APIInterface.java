package com.example.ecommerce.services;

import com.example.ecommerce.model.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIInterface {

    @Headers({"Accept:application/json", "Content-Type:application/json"})
    @GET("v2/5def7b172f000063008e0aa2")
    Call<ProductListResponse> product_data();  //ProductList
}
