package com.LilBro.LitBro.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LilBro.LitBro.R;


public class MainFragment extends Fragment {

    private String description;
    TextView textDescription;

    public MainFragment(String description) {
        this.description = description;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        this.textDescription = (TextView) view.findViewById(R.id.textDescription);
        this.textDescription.setText(this.description);

        // Inflate the layout for this fragment
        return view;
    }
}
