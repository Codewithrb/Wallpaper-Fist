package com.example.coder_rb.myapplication.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import com.example.coder_rb.myapplication.Adapter.CategoriesAdapter;
import com.example.coder_rb.myapplication.Model.Category;
import com.example.coder_rb.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    private List<Category> categoryList;
    private ProgressBar progressBar;
    private DatabaseReference dbCategories;
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);



        recyclerView = view.findViewById(R.id.explore_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        categoryList = new ArrayList<>();
        adapter = new CategoriesAdapter(getActivity(), categoryList);
        recyclerView.setAdapter(adapter);



            dbCategories = FirebaseDatabase.getInstance().getReference("categories");
            dbCategories.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        progressBar.setVisibility(View.GONE);
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = ds.getKey();
                            String desc = ds.child("desc").getValue(String.class);
                            String thumb = ds.child("thumb").getValue(String.class);
                            String cover =ds.child("cover").getValue(String.class);

                            Category c = new Category(name, desc, thumb,cover);
                            categoryList.add(c);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }






}

