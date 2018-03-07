package com.example.edvardsen.quick.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edvardsen.quick.R;

/**
 * Created by Epico-u-01 on 3/7/2018.
 */

public class PrivateFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_private, parent, false);
    }
}
