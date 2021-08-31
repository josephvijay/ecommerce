package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.ProductListAdapter;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductListResponse;
import com.example.ecommerce.services.APIClient;
import com.example.ecommerce.services.APIInterface;
import com.example.ecommerce.utility.AppPreference;
import com.example.ecommerce.utility.Utilitys;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListScreen extends AppCompatActivity {

    ProductListAdapter productListAdapter;
    APIInterface mAPIService;
    AppPreference appPreference;
    RecyclerView rv_product;
    private Utilitys utility;
    ArrayList<Product> arr_product = new ArrayList<>();
    ProgressBar progress_circular;
    MaterialTextView tv_item;
    public static TextView badgecount_text;
    ImageView img_next;
    RelativeLayout rl_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_screen);

        Init();

        rl_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListScreen.this,CartScreen.class);
                startActivity(intent);
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Init(){
        mAPIService = APIClient.getClient().create(APIInterface.class);
        appPreference = new AppPreference(this);
        utility = new Utilitys(this);
        rv_product = findViewById(R.id.rv_product);
        progress_circular = findViewById(R.id.progress_circular);
        tv_item = findViewById(R.id.tv_itemcount);
        badgecount_text = findViewById(R.id.badgecount_text);
        img_next = findViewById(R.id.img_next);
        rl_cart = findViewById(R.id.rl_cart);
        int numberOfColumns = 2;
        rv_product.setLayoutManager(new GridLayoutManager(ProductListScreen.this, numberOfColumns));
        arr_product.clear();
        if (!appPreference.getCartId().equalsIgnoreCase("0")){
            badgecount_text.setVisibility(View.VISIBLE);
            badgecount_text.setText(appPreference.getCartId());
        }
        Get_ProductList();

    }

    private void Get_ProductList() {

        if (utility.isNetAvailable()) {

            progress_circular.setVisibility(View.VISIBLE);

            final Call<ProductListResponse> call = mAPIService.product_data();

            call.enqueue(new Callback<ProductListResponse>() {
                @Override
                public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {

                    progress_circular.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        ProductListResponse value = response.body();

                        tv_item.setText(value.getProducts().size()+" items");

                        for (int i = 0; i < value.getProducts().size(); i++) {
                            arr_product.add(value.getProducts().get(i));
                        }





                        productListAdapter = new ProductListAdapter(getApplicationContext(), arr_product);
                        rv_product.setAdapter(productListAdapter);
                        productListAdapter.notifyDataSetChanged();


                    }
                    else{
                        try {

                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ProductListScreen.this,  jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(ProductListScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ProductListResponse> call, Throwable t) {
                    progress_circular.setVisibility(View.GONE);
                    Toast.makeText(ProductListScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;


            }
        }, 2000);
    }
}