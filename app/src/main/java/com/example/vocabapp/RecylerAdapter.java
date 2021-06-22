package com.example.vocabapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder> {

   private final int[] icons = {R.drawable.asset_50_learn,R.drawable.asset_51_review,R.drawable.asset_52_record,R.drawable.asset_53_dictionary};
   private Context context;

    public RecylerAdapter (Context context){
       this.context = context;
   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View studentView =
                inflater.inflate(R.layout.icon_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setBackgroundResource(icons[position]);

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
