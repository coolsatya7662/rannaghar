package com.plabon.rannaghar.ui.confirmpay;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.activity.BaseActivity;
import com.plabon.rannaghar.activity.CartActivity;
import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.adapter.CheckoutCartAdapter;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.Cart;
import com.plabon.rannaghar.model.FirstOrder;
import com.plabon.rannaghar.model.FirstOrdersResult;
import com.plabon.rannaghar.model.Order;
import com.plabon.rannaghar.model.OrderItem;
import com.plabon.rannaghar.model.OrdersResult;
import com.plabon.rannaghar.model.PlaceOrder;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.util.localstorage.LocalStorage;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment {
    LocalStorage localStorage;
    List<Cart> cartList = new ArrayList<>();
    Gson gson;
    RecyclerView recyclerView;
    CheckoutCartAdapter adapter;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    LinearLayout disc;
    TextView back, order;
    TextView total, shipping, totalAmount;
    Double _total, _shipping, _totalAmount, _discount;
    ProgressDialog progressDialog;
    List<Order> orderList = new ArrayList<>();
    List<OrderItem> orderItemList = new ArrayList<>();
    PlaceOrder confirmOrder;
    String orderNo;
    String uMobile,uEmail,uFname,orderType;
    String id;
    OrderItem orderItem = new OrderItem();
    FirstOrder ordercheck;
    String firstOrder="0";
    User user;
    String time;


    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        localStorage = new LocalStorage(getContext());
        recyclerView = view.findViewById(R.id.cart_rv);
        totalAmount = view.findViewById(R.id.total_amount);
        total = view.findViewById(R.id.total);
        shipping = view.findViewById(R.id.shipping_amount);
        back = view.findViewById(R.id.back);
        order = view.findViewById(R.id.place_order);
        disc = view.findViewById(R.id.lineardiscount);
        progressDialog = new ProgressDialog(getContext());
        gson = new Gson();
        orderList = ((BaseActivity) getActivity()).getOrderList();
        orderList = ((BaseActivity) getActivity()).getOrderList();
        cartList = ((BaseActivity) getContext()).getCartList();
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        ordercheck =new FirstOrder(user.getId(),user.getToken());
        time = localStorage.getOrderTime();
        checkFirstOrder(ordercheck);

        uMobile = user.getMobile();
        uEmail = user.getEmail();
        uFname = user.getFname();


        Log.d("Confirm Order ran1==>", gson.toJson(orderItemList));
        _total = ((BaseActivity) getActivity()).getTotalPrice();
        _shipping = 0.0;


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // placeUserOrder();
                //startPayment();

                for (int i = 0; i < cartList.size(); i++) {

                    orderItem = new OrderItem(cartList.get(i).getTitle(), cartList.get(i).getQuantity(), cartList.get(i).getAttribute(), cartList.get(i).getCurrency(), cartList.get(i).getImage(), cartList.get(i).getPrice(), cartList.get(i).getSubTotal());
                    orderItemList.add(orderItem);
                }

                confirmOrder = new PlaceOrder(user.getToken(), user.getFname(), " ", user.getMobile(), user.getCity(), user.getAddress(), user.getId(),user.getZip(),firstOrder,_discount+"",time, orderItemList);


                if (localStorage.getPayTypt().toString().equals("COD")){
                    placeUserOrder();
                }else if(localStorage.getPayTypt().toString().equals("RAZOR")){
                    // launchPaymentFlow();
                    //startPayment();
                    placeUserOrder();
                }
            }
        });

        setUpCartRecyclerview();
        return view;
    }

    public void placeUserOrder() {
        progressDialog.setMessage("Confirming Order...");
        progressDialog.show();
        Log.d("Confirm Order rann==>", gson.toJson(confirmOrder));
        Call<OrdersResult> call = RestClient.getRestService(getContext()).confirmPlaceOrder(confirmOrder);
        call.enqueue(new Callback<OrdersResult>() {
            @Override
            public void onResponse(Call<OrdersResult> call, Response<OrdersResult> response) {
                Log.d("respose==>", response.body().getCode() + "");

                OrdersResult ordersResult = response.body();

                if (ordersResult.getCode() == 200) {
                    localStorage.deleteCart();
                    showCustomDialog();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrdersResult> call, Throwable t) {
                Log.d("Error respose==>", t.getMessage() + "");
                progressDialog.dismiss();
            }
        });


    }


    private void showCustomDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        // Include dialog.xml file
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCanceledOnTouchOutside(false);
        ImageView cl=dialog.findViewById(R.id.dclos);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), MainNewActivity.class));
                getActivity().finish();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(getContext(), MainNewActivity.class));
                getActivity().finish();
            }
        });
        // Set dialog title

        dialog.show();
    }

    private void setUpCartRecyclerview() {
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        adapter = new CheckoutCartAdapter(cartList, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Confirm");
    }

     public void startPayment() {

     //**   * Instantiate Checkout   *//*
        Checkout checkout = new Checkout();

        //checkout.setKeyID("rzp_live_4Zd2W08yQ2Jo0R");
         checkout.setKeyID("rzp_live_xJvRiJMarnPheO");

        //**   * Set your logo here   *//*
        checkout.setImage(R.drawable.icon);
        //**   * Reference to current activity   *//*
        final Activity activity = getActivity();
        //**   * Pass your payment options to the Razorpay Checkout as a JSONObject   *//*
        try {
            JSONObject options = new JSONObject();
            options.put("name", "nethut");
            options.put("description", "Shopping Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", _totalAmount *100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", uEmail);
            preFill.put("contact", uMobile);

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch(Exception e) {
            // Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }



    public void showAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void checkFirstOrder(FirstOrder order) {
        Log.d("first Order==>", gson.toJson(order));
        progressDialog.show();

        Call<FirstOrdersResult> call = RestClient.getRestService(getContext()).firstOrderDetails(order);
        call.enqueue(new Callback<FirstOrdersResult>() {
            @Override
            public void onResponse(Call<FirstOrdersResult> call, Response<FirstOrdersResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    FirstOrdersResult categoryResult = response.body();
                    if (categoryResult.getCode() == 400) {

                        // categoryList = categoryResult.getCategoryList();
                        // setupCategoryRecycleView();
                        firstOrder="1";
                        showAlert("Congratulations you have 20% discount");

                    }
                    if (categoryResult.getCode() == 200) {

                        // categoryList = categoryResult.getCategoryList();
                        // setupCategoryRecycleView();
                        //showAlert("You are not aligible 20% discount");
                        firstOrder="0";

                    }

                }
                init();
//                shimer.setVisibility(View.INVISIBLE);
//                shimerCat.setVisibility(View.INVISIBLE);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<FirstOrdersResult> call, Throwable t) {
                Log.d("Error==>", t.getMessage());
            }
        });

    }

    private void init(){
        if (firstOrder.equals("1")){
            _discount = ((_total * 20)/100);
            _shipping =  _discount;
            shipping.setText(_shipping + "");
        }else{
            shipping.setVisibility(View.GONE);
            disc.setVisibility(View.GONE);
            _discount=0.0;
        }

        _totalAmount = _total - _shipping;
        total.setText(_total + "");

        totalAmount.setText(_totalAmount + "");
    }
}
