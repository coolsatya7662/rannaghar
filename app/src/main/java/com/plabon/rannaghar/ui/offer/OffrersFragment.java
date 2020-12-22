package com.plabon.rannaghar.ui.offer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.activity.ProductActivity;
import com.plabon.rannaghar.adapter.OfferAdapter;
import com.plabon.rannaghar.adapter.ProductAdapter;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.helper.Data;
import com.plabon.rannaghar.model.Category;
import com.plabon.rannaghar.model.Offer;
import com.plabon.rannaghar.model.Product;
import com.plabon.rannaghar.model.ProductResult;
import com.plabon.rannaghar.model.Token;
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
public class OffrersFragment extends Fragment {

    private static int cart_count = 0;
    Data data;

    String Tag = "Grid";
    View progress;
    LocalStorage localStorage;
    Gson gson = new Gson();
    User user;
    Token token;
    String categoryName;
    Category category;
    List<Product> productList = new ArrayList<>();
    ProductAdapter mAdapter;
    private RecyclerView recyclerView;

    public OffrersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = view.findViewById(R.id.product_rv);
        //Intent intent = getIntent();
        categoryName = "offer";

        progress = view.findViewById(R.id.progress_bar);

        localStorage = new LocalStorage(getContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        token = new Token("app963","743235");

        // Toast.makeText(getApplicationContext(),categoryName,Toast.LENGTH_SHORT).show();
        category = new Category(categoryName,"app963","743235","");


        getCategoryProduct();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Offer");
    }


    private void getCategoryProduct() {
        showProgressDialog();
        Call<ProductResult> call = RestClient.getRestService(getContext()).getCategoryProduct(category);
        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    ProductResult productResult = response.body();
                    if (productResult.getCode() == 200) {

                        productList = productResult.getProductList();
                        setUpRecyclerView();

                        // String userString = gson.toJson(productResult.getProductList());
                        //  localStorage.createUserLoginSession(userString);
                        //  Toast.makeText(getApplicationContext(), productResult.getProductList().toString(), Toast.LENGTH_LONG).show();

                    }

                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Log.d("Error", t.getMessage());
                hideProgressDialog();

            }
        });

    }
    private void setUpRecyclerView() {

        mAdapter = new ProductAdapter(productList, getContext(), Tag);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public void onToggleClicked(View view) {
        if (Tag.equalsIgnoreCase("List")) {
            Tag = "Grid";
            // setUpGridRecyclerView();
        } else {
            Tag = "List";
            setUpRecyclerView();
        }
    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }
}
