package com.example.coder_rb.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coder_rb.myapplication.Fragments.ExploreFragment;
import com.example.coder_rb.myapplication.Fragments.Favourite;
import com.example.coder_rb.myapplication.Fragments.Settings_fragment;
import com.example.coder_rb.myapplication.Fragments.TrendingFragment;
import com.example.coder_rb.myapplication.Helpers.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)

        {
            case Common.PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();

                else
                {
                    Toast.makeText(this,"You need to Grant Download permissions",Toast.LENGTH_SHORT).show();
                }

                break;
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        title = (TextView)findViewById(R.id.titles);

        //request runtime permission
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions( new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},Common.PERMISSION_REQUEST_CODE);
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        title.setText(R.string.Trending);
        loadFragment(new TrendingFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Trending:
                    title.setText(R.string.Trending);
                    fragment = new TrendingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.Explore:
                    title.setText(R.string.Explore);
                    fragment = new ExploreFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.Favourite:
                    title.setText(R.string.Favourite);
                    fragment = new Favourite();
                    loadFragment(fragment);
                    return true;
                case R.id.Settings:
                    title.setText(R.string.Settings);
                    fragment = new Settings_fragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
