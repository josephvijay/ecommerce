package com.example.ecommerce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CartListAdapter;
import com.example.ecommerce.model.Note;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.NoteRepository;
import com.example.ecommerce.utility.AppPreference;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CartScreen extends AppCompatActivity {

    private RecyclerView rv_cart;
    CartListAdapter cartListAdapter;
    AppPreference appPreference;
    private NoteRepository noteRepository;
    LinearLayout ll_noitem,ll_back,bottom_sheet;
    TextView tv_service,tv_total,tv_subtotal,tv_tax;
    Button tv_checkout;
    long subtotal = 0;
    long tax =60;
    long service = 80;
    long total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        Init();


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteRepository.deleteall();
                appPreference.setCartId("0");
                Toast.makeText(CartScreen.this, "Thanks For Shopping..!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartScreen.this,ProductListScreen.class);
                startActivity(intent);
                finish();

            }
        });

        updateTaskList();
    }



    public void Init(){
        rv_cart = findViewById(R.id.rv_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_cart.setLayoutManager(linearLayoutManager);
        ll_noitem =findViewById(R.id.ll_noitem);
        noteRepository = new NoteRepository(getApplicationContext());
        appPreference = new AppPreference(this);
        ll_back = findViewById(R.id.ll_back);
        tv_tax = findViewById(R.id.tv_tax);
        tv_subtotal = findViewById(R.id.tv_subtotal);
        tv_total = findViewById(R.id.tv_total);
        tv_service = findViewById(R.id.tv_service);
        tv_checkout = findViewById(R.id.tv_checkout);
        bottom_sheet = findViewById(R.id.bottom_sheet);
    }


    private void updateTaskList() {
        noteRepository.getTasks().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(notes.size() > 0) {
                    ll_noitem.setVisibility(View.GONE);
                    rv_cart.setVisibility(View.VISIBLE);
                    if (cartListAdapter == null) {
                        cartListAdapter = new CartListAdapter(notes);
                        rv_cart.setAdapter(cartListAdapter);
                        if (notes!=null){
                            for (int i =0; i< notes.size(); i++){
                                    Integer spl = Integer.valueOf(notes.get(i).getSpecial().replaceAll("[₹,]",""));
                                    Integer qty = Integer.valueOf(notes.get(i).getCount());
                                    Integer price =spl*qty;
                                    subtotal = subtotal + price;
                            }
                        }

                        tv_subtotal.setText("₹"+subtotal);

                      /*  tax = subtotal - subtotal/(1+5/100);*/

                        tv_tax.setText("₹"+tax);

                        tv_service.setText("₹"+service);

                        total = subtotal + tax + service;

                        tv_total.setText("₹"+total);

                    } else cartListAdapter.addTasks(notes);
                } else updateEmptyView();
            }
        });
    }

    private void updateEmptyView() {
        ll_noitem.setVisibility(View.VISIBLE);
        rv_cart.setVisibility(View.GONE);
        bottom_sheet.setVisibility(View.GONE);
    }

}