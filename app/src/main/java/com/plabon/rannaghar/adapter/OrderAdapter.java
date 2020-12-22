package com.plabon.rannaghar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.plabon.rannaghar.R;
//import com.plabon.rannaghar.activity.OrderDetailsActivity;
import com.plabon.rannaghar.model.Order;
import com.plabon.rannaghar.ui.order.MyOrderFragment;
import com.plabon.rannaghar.ui.order.OrderDetailsActivity;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    List<Order> orderList;
    Context context;
    int pQuantity = 1;
    String _subtotal, _price, _quantity;
    LocalStorage localStorage;
    Gson gson;
    Fragment fragment;

    public OrderAdapter(List<Order> orderList, Context context, Fragment fragment) {
        this.orderList = orderList;
        this.context = context;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Order order = orderList.get(position);
        holder.orderId.setText("OrderId: #" + order.getId());
        holder.date.setText("Date: " + order.getDate());
        holder.total.setText("Total: " + order.getTotal());
        holder.status.setText("Status:- " + order.getStatus());// + "     code:-" + order.getPod_cod());
        try {
            if (order.getFirstorder().equals("1")) {
                holder.total.setText("Total: " + order.getTotal() + "   First Order Discount: " + order.getFirstorderdiscount());
            }
        }catch (Exception e){

        }
        if (order.getStatus().equals("Reject")){
            holder.status.setTextColor(Color.parseColor("#ff0000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("MY_KEY", ""+order.getId()+"");

                context.startActivity(intent);
                //  Log.d(TAG, "onClick: click On 111");
                //    Toast.makeText(context,"Item Clicked - "+order.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                //Intent intent = new Intent(context, OrderDetailsActivity.class);
                //intent.putExtra("MY_KEY", ""+order.getId()+"");
                // intent.putExtra("MY_KEYN", ""+foodItem.getName()+"");
                //intent.putExtra("id", foodItem.getId());
                //context.startActivity(intent);
                //  Log.d(TAG, "onClick: click On 111");
                if (fragment instanceof MyOrderFragment){
                    ((MyOrderFragment)fragment).showDelAlertYes("Do you want Cancel the Order" , order.getId());
                }

                   // Toast.makeText(context,"Item Clicked - "+order.getId(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {

        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, date, total, status,cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.order_id);
            date = itemView.findViewById(R.id.date);
            total = itemView.findViewById(R.id.total_amount);
            status = itemView.findViewById(R.id.status);
            cancel = itemView.findViewById(R.id.cancel_oreder);

        }
    }
}
