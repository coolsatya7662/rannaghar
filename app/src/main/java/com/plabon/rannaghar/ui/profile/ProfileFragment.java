package com.plabon.rannaghar.ui.profile;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.activity.EditProfileActivity;
import com.plabon.rannaghar.activity.LoginActivity;
import com.plabon.rannaghar.activity.SplashActivity;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.util.localstorage.LocalStorage;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    //Satya  9800970578
    TextView name, email, mobile, address,btnEdit;

    LocalStorage localStorage;
    Gson gson = new Gson();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        address = view.findViewById(R.id.address);
        btnEdit = view.findViewById(R.id.edit);
        localStorage = new LocalStorage(getContext());


        if (localStorage.isUserLoggedIn()){
            User user = gson.fromJson(localStorage.getUserLogin(), User.class);
            name.setText(user.getFname());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
            address.setText(user.getAddress());
        }else{
            Intent homeIntent= new Intent(getActivity(), LoginActivity.class);
            startActivity(homeIntent);
            getActivity().finish();
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent= new Intent(getActivity(), EditProfileActivity.class);
                homeIntent.putExtra("n",name.getText());
                homeIntent.putExtra("e",email.getText());
                homeIntent.putExtra("m",mobile.getText());
                homeIntent.putExtra("a",address.getText());
                startActivity(homeIntent);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
    }
}
