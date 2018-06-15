package com.example.coder_rb.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.coder_rb.myapplication.Helpers.SaveImageHelper;
import com.example.coder_rb.myapplication.Model.wall;
import com.example.coder_rb.myapplication.Wallpaper.Wallpaper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class ViewWallpaper extends AppCompatActivity implements View.OnClickListener {

    private Wallpaper selectedPhoto;
    Context mCtx;
    private ImageView fullImageView;
    private LinearLayout llSetWallpaper,download;
    private ProgressBar pbLoader;
    private  int h,wid;
    private List<wall> wallList;
    String selected_wall,category;
    private static final String TAG = ViewWallpaper.class
            .getSimpleName();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)

        {
            case Common.PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    AlertDialog dialog = new SpotsDialog(mCtx);
                    dialog.show();
                    dialog.setMessage("Please Wait.....");

                    String fileName = UUID.randomUUID().toString()+category+".png";
                    Picasso.get()
                            .load(selected_wall)
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Hello Thx For Downloading Wallpaper"));
                }

                else
                {
                    Toast.makeText(this,"You need to Grant Download permissions",Toast.LENGTH_SHORT).show();
                }

                break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         final int height = displayMetrics.heightPixels;
         final int width = displayMetrics.widthPixels;

         wall w ;


        fullImageView = (ImageView) findViewById(R.id.imgFullscreen);
        pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
        llSetWallpaper =(LinearLayout)findViewById(R.id.llSetWallpaper);
        download = (LinearLayout)findViewById(R.id.llDownloadWallpaper);
        llSetWallpaper.getBackground().setAlpha(70);
        llSetWallpaper.setOnClickListener(this);
        download.getBackground().setAlpha(70);

        selected_wall = getIntent().getStringExtra("select");
        category = getIntent().getStringExtra("title");

      download.setOnClickListener(new View.OnClickListener() {
          @TargetApi(Build.VERSION_CODES.M)
          @RequiresApi(api = Build.VERSION_CODES.M)
          @Override
          public void onClick(View v) {
              // permission

              //request runtime permission
              if(ActivityCompat.checkSelfPermission(ViewWallpaper.this,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
              {
                  requestPermissions( new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},Common.PERMISSION_REQUEST_CODE);
              }

              else
              {
                  AlertDialog dialog = new SpotsDialog(ViewWallpaper.this);
                  dialog.show();
                  dialog.setMessage("Please Wait.....");

                  String fileName = UUID.randomUUID().toString()+category+".png";
                  Picasso.get()
                          .load(selected_wall)
                          .into(new SaveImageHelper(getBaseContext(),
                                  dialog,
                                  getApplicationContext().getContentResolver(),
                                  fileName,
                                  "Hello Thx For Downloading Wallpaper"));
              }
          }
      });

        pbLoader.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(selected_wall)
                .apply(new RequestOptions().override(width,height))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pbLoader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(fullImageView).getSize(new SizeReadyCallback() {
            @Override
            public void onSizeReady(int width, int height) {
                wid = width;
                h = height;
            }
        });


    }


    @Override
    public void onClick(View v) {

      Bitmap bitmap = ((BitmapDrawable) fullImageView.getDrawable())
                .getBitmap();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;

        switch (v.getId()) {

            case R.id.llDownloadWallpaper:
                break;

            // button Set As Wallpaper tapped
            case R.id.llSetWallpaper:
                setAsWallpaper(bitmap,height,width);
                break;
            default:
                break;
        }




    }

    public  void setAsWallpaper(Bitmap bitmap,int dh, int dw) {
        try {
            WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());

            Bitmap resized = Bitmap.createScaledBitmap(bitmap,dw, dh, true);

            wm.setBitmap(resized);
            Toast.makeText(getApplicationContext(),"Wallpaper updated",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.toast_wallpaper_set_failed),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void adjustImageAspect(int bWidth, int bHeight,int dh , int dw) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if (bWidth == 0 || bHeight == 0)
            return;

        int sHeight = 0;

        if (bWidth > dw)

        {


            if (android.os.Build.VERSION.SDK_INT >= 15) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                sHeight = size.y;
            } else {
                Display display = getWindowManager().getDefaultDisplay();
                sHeight = display.getHeight();
            }

       /* int new_width = (int) Math.floor((double) bWidth * (double) sHeight
                / (double) bHeight);*/

       int new_width = bWidth;
        params.width = new_width;
        params.height = sHeight;

        Log.d(TAG, "Fullscreen image new dimensions: w = " + new_width
                + ", h = " + sHeight);

        fullImageView.setLayoutParams(params);

                }

              }

}





