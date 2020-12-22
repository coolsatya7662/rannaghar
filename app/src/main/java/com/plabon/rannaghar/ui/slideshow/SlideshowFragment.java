package com.plabon.rannaghar.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.plabon.rannaghar.R;
import com.plabon.rannaghar.activity.LoginActivity;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

public class SlideshowFragment extends Fragment {

    //Satya  9800970578

    private SlideshowViewModel slideshowViewModel;
    LocalStorage localStorage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        localStorage = new LocalStorage(getContext());
       // localStorage.logoutUser();
        //startActivity(new Intent(getContext(), LoginActivity.class));
        //getActivity().finish();

        return root;
    }
}