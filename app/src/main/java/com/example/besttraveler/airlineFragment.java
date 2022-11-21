package com.example.besttraveler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class airlineFragment extends Fragment {
    private String Result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Result = getArguments().getString("result1");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_airline, container, false);
        TextView textView = myView.findViewById(R.id.airline);
        textView.setText(Result);
        return myView;
    }
}