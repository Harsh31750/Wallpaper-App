package com.example.wallpaperapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    ArrayList<WallpaperData> arrayList;

    private Context context;

    public WallpaperAdapter(ArrayList<WallpaperData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        WallpaperData data = arrayList.get(position);

        Glide.with(context).load(data.getUrl()).into(holder.imageView);
        holder.likesCount.setText(String.valueOf(data.getLikesCount()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class WallpaperViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView likesCount;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wallpaper_Images);
            likesCount = itemView.findViewById(R.id.likes_count);
        }
    }
}
