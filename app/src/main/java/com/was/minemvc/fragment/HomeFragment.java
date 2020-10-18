package com.was.minemvc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.was.minemvc.R;


public class HomeFragment extends Fragment {

    TextView textContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_text, container, false);

        textContent = root.findViewById(R.id.tv_content);
        textContent.setText(R.string.title_home);
        return root;
    }
}