package com.example.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerce.R;
import com.example.ecommerce.activities.ProductDetailsScreen;
import com.example.ecommerce.model.Note;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.NoteRepository;
import com.example.ecommerce.utility.AppPreference;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.example.ecommerce.activities.ProductListScreen.badgecount_text;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    ArrayList<Product> arrdes;
    Context ctx;
    private NoteRepository noteRepository;
    AppPreference appPreference;
    final int[] cart = {0};

    public ProductListAdapter(Context context, ArrayList<Product> arrdes) {
        this.ctx=context;
        this.arrdes=arrdes;
        noteRepository = new NoteRepository(ctx);
        appPreference = new AppPreference(ctx);
        if (badgecount_text.getVisibility()==View.VISIBLE){
            cart[0] = Integer.parseInt(appPreference.getCartId());
        }
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        final Product datum = arrdes.get(position);

        final int[] i = {0};


        holder.img_heart.setEnabled(false);


            if(datum.getName()!=null){
                holder.tv_title.setText(datum.getName());
            }

        if (datum.getSpecial() != null && datum.getPrice()!=null) {
            if (datum.getPrice().equalsIgnoreCase(datum.getSpecial())){
                holder.tv_special.setVisibility(View.GONE);
                holder.tv_price.setText(datum.getPrice());
            }else {
                holder.tv_special.setVisibility(View.VISIBLE);
                holder.tv_price.setText(datum.getPrice());
                holder.tv_price.setTextColor(Color.parseColor("#999999"));
                holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_special.setText(datum.getSpecial());
            }
        }

            if (datum.getImage()!=null){
                String imgFile = datum.getImage();
                Picasso.get().load(imgFile)
                        .error(R.drawable.brokenimages)
                        .placeholder(R.drawable.brokenimages)
                        .into(holder.img_pic);
            }



            holder.ll_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( holder.img_heart.isEnabled() == true) {
                        holder.img_heart.setEnabled(false);
                    } else if ( holder.img_heart.isEnabled() == false) {
                        holder.img_heart.setEnabled(true);
                    }
                }
            });


        holder.ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i[0]++;
                holder.tv_count.setText(String.valueOf(i[0]));
                cart[0]++;
                appPreference.setCartId(String.valueOf(cart[0]));
                badgecount_text.setVisibility(View.VISIBLE);
                badgecount_text.setText(String.valueOf(cart[0]));
                holder.ll_add.setVisibility(View.GONE);
                holder.ll_count.setVisibility(View.VISIBLE);

                Integer id = position;
                String title = datum.getName();
                String desc = datum.getDescription();
                String price = datum.getPrice();
                String special = datum.getSpecial();
                String image = datum.getImage();
                String count = "1";

                noteRepository.insertTask(id,title, desc, price, special,image,count);

            }
        });

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i[0]++;
                noteRepository.update(i[0],position);
                holder.tv_count.setText(String.valueOf(i[0]));

            }
        });

        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    i[0]--;
                   noteRepository.update(i[0],position);
                    if (i[0] ==0){
                        holder.ll_add.setVisibility(View.VISIBLE);
                        holder.ll_count.setVisibility(View.GONE);
                        noteRepository.deleteById(position);
                        cart[0]--;
                        appPreference.setCartId(String.valueOf(cart[0]));
                        if (cart[0] ==0){
                            badgecount_text.setVisibility(View.GONE);
                        }
                        badgecount_text.setText(String.valueOf(cart[0]));
                    }
                    holder.tv_count.setText(String.valueOf(i[0]));
                }
        });


        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsScreen.product = datum;
                ctx.startActivity(new Intent(ctx, ProductDetailsScreen.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });






    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return arrdes.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tv_title,tv_price,tv_special;
        AppCompatImageView img_pic,img_heart;
        LinearLayout ll_add,ll_count,ll_item,ll_heart;
        AppCompatTextView tv_count;
        LinearLayout img_add,img_minus;

        public ViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_price = view.findViewById(R.id.tv_price);
            tv_special = view.findViewById(R.id.tv_special);
            img_pic = view.findViewById(R.id.img_pic);
            img_heart = view.findViewById(R.id.img_heart);
            ll_add = view.findViewById(R.id.ll_add);
            ll_count = view.findViewById(R.id.ll_count);
            tv_count = view.findViewById(R.id.tv_count);
            img_add = view.findViewById(R.id.img_add);
            img_minus = view.findViewById(R.id.img_minus);
            ll_item = view.findViewById(R.id.ll_item);
            ll_heart = view.findViewById(R.id.ll_heart);


        }
    }
}