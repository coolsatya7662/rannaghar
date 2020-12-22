package com.plabon.rannaghar.ui.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.adapter.DetailsOrderAdapter;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.ModelDetailsOrders;
import com.plabon.rannaghar.model.Order;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OrderDetailsActivity extends AppCompatActivity {

    //Satya  9800970578

    static final String TAG = "PrintDemoActivity";
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    Button btnSearch,btnOrderDelivered,btnSettDeliveryBoy;
    Button btnSendDraw,orderaccept;
    Button btnSend,btnMap,btnShareBill;
    Button btnClose;
    EditText edtContext;
    Context context;
    View viewOtpLayout;
    View progress;

    String msg = "";
    String msgP = "";
    String DIVIDER = "--------------------------------";
    String DIVIDER_DOUBLE = "================================";
    String BREAK = "\n";
    String SPACE5 = "     ";
    String SPACE4 = "   ";
    SharedPreferences sharedPreferences;
    String header = "";

    String id,storeid;
    RecyclerView recyclerView;
    ArrayList<ModelDetailsOrders> foodList;

    String userid,address,dprice,DATE,orderDate,cod;

    Double total=0.0,qty=0.0,price=0.0,carttotal=0.0,shipcharge=0.0,_cgst= 0.0,_sgst=0.0;

    TextView tvAddress,tvTotalPrice,tvOrderId,orderdetailsorderdate,txtcod;
    Button btnAccept,btnCancle;
    RecyclerView rc;
    Double lat,lng;
    Spinner spinner;
    LocalStorage localStorage;
    Order order;
    User user;
    Gson gson = new Gson();
    String fOrder,fOrderDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        id = getIntent().getExtras().getString("MY_KEY");

        tvAddress=findViewById(R.id.orderdetailsaddress);
        tvTotalPrice=findViewById(R.id.orderdetailscarttotal);
        tvOrderId=findViewById(R.id.orderdetailsorderid);
        recyclerView=findViewById(R.id.orderdetailsrecycleview);
        progress = findViewById(R.id.progress_bar);



        tvOrderId.setText("Order Id : " + id.toString());
        orderdetailsorderdate = findViewById(R.id.orderdetailsorderdate);

        foodList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        localStorage = new LocalStorage(getApplicationContext());
        user = gson.fromJson(localStorage.getUserLogin(), User.class);
        userid = user.getId();

        getResponse();
    }

    /*private void getResponse(){
        showProgressDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OrderApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        OrderApiInterface api = retrofit.create(OrderApiInterface.class);

        Call<String> call = api.getString("app963",id,userid);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Log.i("onSuccess", response.body().toString());
                        hideProgressDialog();
                        String jsonresponse = response.body().toString();

                        if (jsonresponse.isEmpty() || jsonresponse==null){
                            //textViewName.setText("No Category Found");
                        }
                        else {
                            // Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
                            writeTv1(jsonresponse);
                        }

                        //  Toast.makeText(MainActivity.this,"Strint--" + jsonresponse,Toast.LENGTH_LONG).show();

                    } else {
                        hideProgressDialog();
                        Toast.makeText(OrderDetailsActivity.this,"null found",Toast.LENGTH_SHORT).show();
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeTv1(String response){

        try {

            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.length()>0) {
                // ArrayList<MainCategory> retroModelArrayList = new ArrayList<>();

                JSONArray dataArray = obj.getJSONArray("myorder_json_data");

                if (dataArray.length() > 0) {


                    for (int i = 0; i < dataArray.length(); i++) {
                        // MainCategory retroModel = new MainCategory();
                        // foodList =new ArrayList<>();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        // Toast.makeText(getApplicationContext(),"i" + dataobj.getString("price").toString(),Toast.LENGTH_SHORT).show();
                        //  for (int j = 0; j < dataobj.length(); j++) {
                        foodList.add(new ModelDetailsOrders(
                                dataobj.getString("id"),
                                "1",//dataobj.getString("productId"),
                                "Conformed",//dataobj.getString("orderStatus"),
                                dataobj.getString("price"),
                                dataobj.getString("quantity"),
                                "",// dataobj.getString("orderDate"),
                                dataobj.getString("productName"),
                                RestClient.BASE_URL + dataobj.getString("image"),
                                dataobj.getString("attribute"),
                                dataobj.getString("cgst"),
                                dataobj.getString("sgst"),
                                dataobj.getString("discount"),
                                dataobj.getString("hsn")
                        ));
                        //  }

                        try {
                            price =  Double.parseDouble(dataobj.getString("discount"));
                            qty = Double.parseDouble(dataobj.getString("quantity"));
                            total = price*qty;

                            _cgst = _cgst + ((total * Double.parseDouble(dataobj.getString("cgst")))/100);
                            _sgst = _sgst + ((total * Double.parseDouble(dataobj.getString("sgst")))/100);

                            carttotal = carttotal + total;// + _cgst + _sgst;
                        }catch (Exception e){
                            price =  Double.parseDouble(dataobj.getString("discount"));
                            qty = Double.parseDouble(dataobj.getString("quantity"));
                            total = price*qty;
                        }


                        dprice  = "0.0";//dataobj.getString("deliverycost");

                        msgP= msgP + "#" + (i+1) + "." + dataobj.getString("productName") + " / " + dataobj.getString("attribute") + BREAK;
                        msgP= msgP + "-- Rs-" + dataobj.getString("discount") + "*" +
                                dataobj.getString("quantity") +" Rs-" + total.toString();
                        msgP= msgP + BREAK;
                        msgP= msgP + "CGST ₹" + _cgst;
                        msgP= msgP + " | SGST ₹" + _sgst;
                        msgP= msgP + BREAK + DIVIDER;

                        // DATE ="15/06/2020";// dataobj.getString("orderDate");

                    }
                    msgP += DIVIDER_DOUBLE + BREAK;

                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                    recyclerView.setLayoutManager(rvLiLayoutManager);

                    DetailsOrderAdapter adapter = new DetailsOrderAdapter(OrderDetailsActivity.this, foodList);

                    recyclerView.setAdapter(adapter);



                    JSONArray addressArray = obj.getJSONArray("address_json_data");

                    if (addressArray.length() > 0) {


                        for (int i = 0; i < addressArray.length(); i++) {
                            // MainCategory retroModel = new MainCategory();
                            // foodList =new ArrayList<>();
                            JSONObject dataobj = addressArray.getJSONObject(i);
                            //  Toast.makeText(getApplicationContext(),"i" + dataobj.getString("fname").toString(),Toast.LENGTH_SHORT).show();
                            lat= dataobj.getDouble("lat");//25.0217498;
                            lng =dataobj.getDouble("lon");//88.155329;
                            orderDate = dataobj.getString("orderDate");
                            address="Name : " + dataobj.getString("fname") +"\n"+ "Mobile : "
                                    + dataobj.getString("mobile")
                                    +"\n"+ "Address : "
                                    + dataobj.getString("address")
                                    + "\n" + DIVIDER
                                    +"\n"+ "Delivery Type : "
                                    + dataobj.getString("order_type")
                                    +"\n"+ "Payment Type : "
                                    + dataobj.getString("pay_type");

                            shipcharge =dataobj.getDouble("shipping_charge");

                        }

                        tvAddress.setText(address);
                        orderdetailsorderdate.setText(orderDate);

                        tvTotalPrice.setText("Total : ₹" + carttotal.toString() + " Delivery : ₹" + shipcharge + " CGST = ₹" + _cgst + " SGST = ₹" + _sgst + " Net Amount = ₹" + (carttotal+shipcharge+_cgst+_sgst));

                        msg="-----------SD MART----------" + BREAK + DIVIDER_DOUBLE + BREAK;

                        msg += " Order Number : " + id.toString();
                        msg += BREAK + DIVIDER + BREAK;
                        msg += "Customer Details/DeliveryAddress";
                        msg += BREAK + DIVIDER + BREAK;
                        msg += address;
                        msg += BREAK + DIVIDER_DOUBLE + BREAK;
                        msg += "Poduct Details :";
                        msg += BREAK + DIVIDER + BREAK;
                        msg += msgP;
                        //  msg += BREAK + DIVIDER_DOUBLE + BREAK;
                        msg += "   TOTAL         RS- " + carttotal + BREAK;
                        msg += "   CGST          RS- " + _cgst + BREAK;
                        msg += "   SGST          RS- " + _sgst + BREAK;
                        msg += "   Delivey       RS- " + shipcharge.toString() + BREAK;
                        msg += "   TOTAL AMOUNT  RS- " + (carttotal+ shipcharge + _cgst + _sgst) + BREAK;
                        msg += "   DATE   - " + orderDate;
                        msg += BREAK + DIVIDER_DOUBLE + BREAK + BREAK + BREAK ;
                        msgP="";
                    }

                }
                else{
                    //textViewName.setText("No Category Found");
                }


            }else {
                Toast.makeText(OrderDetailsActivity.this, obj.optString("message")+"40", Toast.LENGTH_SHORT).show();
            }

            //  progressDialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(OrderDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }*/

    private void getResponse(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OrderApiInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        OrderApiInterface api = retrofit.create(OrderApiInterface.class);

        Call<String> call = api.getString("app963",id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();

                        if (jsonresponse.isEmpty() || jsonresponse==null){
                            //textViewName.setText("No Category Found");
                        }
                        else {
                            // Toast.makeText(getApplicationContext(),jsonresponse,Toast.LENGTH_SHORT).show();
                            writeTv1(jsonresponse);
                        }

                        //  Toast.makeText(MainActivity.this,"Strint--" + jsonresponse,Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(OrderDetailsActivity.this,"null found",Toast.LENGTH_SHORT).show();
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeTv1(String response){

        try {

            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.length()>0) {
                // ArrayList<MainCategory> retroModelArrayList = new ArrayList<>();

                JSONArray dataArray = obj.getJSONArray("myorder_json_data");

                if (dataArray.length() > 0) {


                    for (int i = 0; i < dataArray.length(); i++) {
                        // MainCategory retroModel = new MainCategory();
                        // foodList =new ArrayList<>();
                        JSONObject dataobj = dataArray.getJSONObject(i);
                        //   Toast.makeText(getApplicationContext(),"i" + dataobj.getString("price").toString(),Toast.LENGTH_SHORT).show();
                        //  for (int j = 0; j < dataobj.length(); j++) {
                        foodList.add(new ModelDetailsOrders(
                                dataobj.getString("id"),
                                "1",//dataobj.getString("productId"),
                                "Conformed",//dataobj.getString("orderStatus"),
                                dataobj.getString("price"),
                                dataobj.getString("quantity"),
                                "",// dataobj.getString("orderDate"),
                                dataobj.getString("productName"),
                                RestClient.BASE_URL + "assets/images/ProductImage/product/" + dataobj.getString("image")
                        ));
                        //  }

                        price =  Double.parseDouble(dataobj.getString("price"));
                        qty = Double.parseDouble(dataobj.getString("quantity"));
                        total = price*qty;
                        carttotal = carttotal + total;

                        dprice  = "40";//dataobj.getString("deliverycost");

                        msgP= msgP + dataobj.getString("productName") + BREAK;
                        msgP= msgP + "------ Rs-" + dataobj.getString("price") + "*" +
                                dataobj.getString("quantity") +" Rs-" + total.toString();
                        msgP= msgP + BREAK;

                        DATE ="15/06/2020";// dataobj.getString("orderDate");

                    }
                    msgP += DIVIDER_DOUBLE + BREAK;

                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                    recyclerView.setLayoutManager(rvLiLayoutManager);

                    DetailsOrderAdapter adapter = new DetailsOrderAdapter(OrderDetailsActivity.this, foodList);

                    recyclerView.setAdapter(adapter);

                    tvTotalPrice.setText("Order Total : ₹" + carttotal.toString() );

                    JSONArray addressArray = obj.getJSONArray("address_json_data");

                    if (addressArray.length() > 0) {


                        for (int i = 0; i < addressArray.length(); i++) {
                            // MainCategory retroModel = new MainCategory();
                            // foodList =new ArrayList<>();
                            JSONObject dataobj = addressArray.getJSONObject(i);
                            //     Toast.makeText(getApplicationContext(),"i" + dataobj.getString("fname").toString(),Toast.LENGTH_SHORT).show();
                            lat= 23.0503429;//dataobj.getDouble("lat");
                            lng =88.831439;// dataobj.getDouble("lng");
                            // orderDate = dataobj.getString("orderDate");
                            address="Name : " + dataobj.getString("fname") +"\n"+ "Mobile : "
                                    + dataobj.getString("mobile")
                                    +"\n"+ "Address : "
                                    + dataobj.getString("address")
                                    + "\n" + "Date : "
                                    + dataobj.getString("orderDate")
                                    + "\n" + "Delivery Time : "
                                    + dataobj.getString("dtime");

                            fOrder = dataobj.getString("firstorder");
                            fOrderDis = dataobj.getString("firstorderdiscount");

                        }

                        tvAddress.setText(address);
                        if (fOrder.equals("1")){
                            Double d,p;
                            d = Double.parseDouble(fOrderDis);
                            p = carttotal - d;
                            tvTotalPrice.setText("Order Total : ₹" + carttotal.toString() + " , First Order Discount: " + d + ", Payable : " +p);
                        }

                        msg="-----------RannaGhar----------" + BREAK + DIVIDER_DOUBLE + BREAK;

                        msg += " Order Number : " + id.toString();
                        msg += BREAK + DIVIDER + BREAK;
                        msg += "Customer Details/DeliveryAddress";
                        msg += BREAK + DIVIDER + BREAK;
                        msg += address;
                        msg += BREAK + DIVIDER_DOUBLE + BREAK;
                        msg += "Poduct Details :";
                        msg += BREAK + DIVIDER + BREAK;
                        msg += msgP;
                        //  msg += BREAK + DIVIDER_DOUBLE + BREAK;
                        msg += "   TOTAL         RS- " + carttotal + BREAK;
                        msg += "   Delivey       RS- " + dprice.toString() + BREAK;
                        msg += "   DATE          " + DATE;
                        msg += BREAK + DIVIDER_DOUBLE + BREAK + BREAK;
                        msgP="";
                    }

                }
                else{
                    //textViewName.setText("No Category Found");
                }

            }else {
                Toast.makeText(OrderDetailsActivity.this, obj.optString("message")+"40", Toast.LENGTH_SHORT).show();
            }

            //  progressDialog.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(OrderDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public interface OrderApiInterface {

        String JSONURL = RestClient.BASE_URL + "api/v1/";

        // @GET("json_parsing.php")
        @GET("getOrderDetailsd.php")
        Call<String> getString(@Query("token") String token, @Query("id") String id);

    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

}