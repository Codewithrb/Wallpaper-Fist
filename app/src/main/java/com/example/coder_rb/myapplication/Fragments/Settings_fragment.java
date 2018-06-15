package com.example.coder_rb.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coder_rb.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings_fragment extends Fragment {


    public Settings_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_fragment, container, false);
    }

}
