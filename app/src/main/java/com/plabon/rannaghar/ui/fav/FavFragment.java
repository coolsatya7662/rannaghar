package com.plabon.rannaghar.ui.fav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.plabon.rannaghar.R;
import com.plabon.rannaghar.ui.slideshow.SlideshowViewModel;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

public class FavFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    LocalStorage localStorage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fav, container, false);
        //localStorage = new LocalStorage(getContext());
       // localStorage.logoutUser();
        //startActivity(new Intent(getContext(), LoginActivity.class));
        //getActivity().finish();

        return root;
    }
}