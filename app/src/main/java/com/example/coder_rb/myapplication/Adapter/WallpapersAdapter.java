package com.example.coder_rb.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.coder_rb.myapplication.Model.wall;
import com.example.coder_rb.myapplication.R;
import com.example.coder_rb.myapplication.ViewWallpaper;
import com.example.coder_rb.myapplication.Wallpaper.Wallpaper;
import com.example.coder_rb.myapplication.WallpaperActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WallpapersAdapter extends RecyclerView.Adapter<WallpapersAdapter.WallpaperViewHolder> {



    private Context mCtx;
    private List<wall> wallList;

    wall w;


    public WallpapersAdapter(Context mCtx, List<wall> wallList) {
        this.mCtx = mCtx;
        this.wallList = wallList;
    }


    @NonNull
    @Override
    public WallpapersAdapter.WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.wallpaper_item_layout, parent, false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpapersAdapter.WallpaperViewHolder holder, int position) {
         w = wallList.get(position);
        Glide.with(mCtx)
                .load(w.url)
                .into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return wallList.size();
    }




    public class WallpaperViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView textView;
        ImageView imageView;




        public WallpaperViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.w_thumb);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int p = getAdapterPosition();
            wall w = wallList.get(p);

            Intent i = new Intent(mCtx,
                    ViewWallpaper.class);

            i.putExtra("select",w.url);


            mCtx.startActivity(i);


        }


    }



}
