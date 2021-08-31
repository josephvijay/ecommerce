package com.example.ecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.model.Note;
import com.example.ecommerce.utility.AppUtils;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CustomViewHolder> {

    private List<Note> notes;
    public CartListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Note note = getItem(position);

        holder.tv_title.setText(note.getTitle());
        Integer spl = Integer.valueOf(note.getSpecial().replaceAll("[₹,]",""));
        Integer qty = Integer.valueOf(note.getCount());
        String price = String.valueOf(spl*qty);
        holder.tv_price.setText("₹"+price);
        holder.tv_priceandcount.setText(note.getSpecial()+"/"+note.getCount()+" Qty");
        if (note.getImage()!=null){
            String imgFile = note.getImage();
            Picasso.get().load(imgFile)
                    .error(R.drawable.brokenimages)
                    .placeholder(R.drawable.brokenimages)
                    .into(holder.img_pic);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getItem(int position) {
        return notes.get(position);
    }

    public void addTasks(List<Note> newNotes) {
        notes.clear();
        notes.addAll(newNotes);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tv_title,tv_price,tv_priceandcount;
        AppCompatImageView img_pic;

        public CustomViewHolder(View view) {
            super(view);

            tv_title = view.findViewById(R.id.tv_title);
            tv_price = view.findViewById(R.id.tv_price);
            img_pic = view.findViewById(R.id.img_pic);

            tv_priceandcount = view.findViewById(R.id.tv_priceandcount);

        }
    }
}
