package com.example.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.ecommerce.R;
import com.example.ecommerce.model.Product;
import com.squareup.picasso.Picasso;

public class ProductDetailsScreen extends AppCompatActivity {

    public static Product product;
    AppCompatTextView tv_title,tv_price,tv_des,tv_title2;
    AppCompatImageView img_pic;
    LinearLayout ll_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_screen);

        Init();


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(product.getName()!=null){
            tv_title.setText(product.getName());
        }

        if(product.getName()!=null){
            tv_title2.setText(product.getName());
        }

        if (product.getDescription() != null) {
            tv_des.setText(product.getDescription());
        }

        if (product.getSpecial() != null) {
            tv_price.setText(product.getSpecial());
        }

        if (product.getImage()!=null){
            String imgFile = product.getImage();
            Picasso.get().load(imgFile)
                    .error(R.drawable.brokenimages)
                    .placeholder(R.drawable.brokenimages)
                    .into(img_pic);
        }
    }

    public void Init(){
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        img_pic = findViewById(R.id.img_pic);
        tv_des = findViewById(R.id.tv_description);
        tv_title2 = findViewById(R.id.tv_title2);
        ll_back = findViewById(R.id.ll_back);
    }

}