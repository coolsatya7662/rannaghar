package com.plabon.rannaghar.ui.order;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.activity.LoginActivity;
import com.plabon.rannaghar.adapter.OrderAdapter;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.Order;
import com.plabon.rannaghar.model.OrdersResult;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {

    //Satya  9800970578
    LocalStorage localStorage;
    LinearLayout linearLayout;
    private List<Order> orderList = new ArrayList<>();
    Gson gson = new Gson();
    private RecyclerView recyclerView;
    private OrderAdapter mAdapter;
    Order order;
    View progress;
    private List<Order> newOrderList = new ArrayList<>();
    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        recyclerView = view.findViewById(R.id.order_rv);
        linearLayout = view.findViewById(R.id.no_order_ll);
        progress = view.findViewById(R.id.progress_bar);
        localStorage = new LocalStorage(getContext());

        if (localStorage.isUserLoggedIn()){
            User user = gson.fromJson(localStorage.getUserLogin(), User.class);
            order = new Order(user.getId(), user.getToken());
            fetchOrderDetails(order);
        }else{
            Intent homeIntent= new Intent(getContext(), LoginActivity.class);
            startActivity(homeIntent);
            getActivity().finish();
        }



        return view;
    }

    private void fetchOrderDetails(Order order) {

        Call<OrdersResult> call = RestClient.getRestService(getContext()).orderDetails(order);
        call.enqueue(new Callback<OrdersResult>() {
            @Override
            public void onResponse(Call<OrdersResult> call, Response<OrdersResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    OrdersResult ordersResult = response.body();
                    if (ordersResult.getCode() == 200) {

                        orderList = ordersResult.getOrderList();
                        setupOrderRecycleView();

                    }

                }
            }

            @Override
            public void onFailure(Call<OrdersResult> call, Throwable t) {

            }
        });

    }

    private void setupOrderRecycleView() {
        if (orderList.isEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        mAdapter = new OrderAdapter(orderList, getContext(),MyOrderFragment.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MyOrder");
    }

/*    private void fetchOrderCancel(Order orderr) {
        showProgressDialog();
        Call<OrdersResult> call = RestClient.getRestService(getContext()).orderDetailsCancel(orderr);
        call.enqueue(new Callback<OrdersResult>() {
            @Override
            public void onResponse(Call<OrdersResult> call, Response<OrdersResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    OrdersResult ordersResult = response.body();
                    if (ordersResult.getCode() == 200) {

                       //orderList = ordersResult.getOrderList();
                       /// setupOrderRecycleView();
                        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
                        order = new Order(user.getId(), user.getToken());
                        fetchOrderDetails(order);
                       // Toast.makeText(getContext(),"ji",Toast.LENGTH_LONG).show();

                    }
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<OrdersResult> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }

    public void cancelOrder(String id){
        User user = gson.fromJson(localStorage.getUserLogin(), User.class);
        order = new Order(user.getId(), user.getToken(),"0");
        fetchOrderCancel(order);
    }*/

    public void showDelAlertYes(String msg, final String sids){
        //  AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext(),R.style.myDialog);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //setCarEntry(carnumber.getText().toString(),spinnerCarType.getSelectedItem().toString(),mobile.getText().toString(),date.getText().toString(),time.getText().toString(),uid,spinnerCarLoadType.getSelectedItem().toString());
                //dialogInterface.dismiss();
             //   cancelOrder(sids);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }


}
