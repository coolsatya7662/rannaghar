package com.plabon.rannaghar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plabon.rannaghar.model.ModelDetailsOrders;
import com.plabon.rannaghar.R;

import java.util.List;

public class DetailsOrderAdapter extends RecyclerView.Adapter<DetailsOrderAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ModelDetailsOrders> productList;
   // private DatabaseHelper db;
   // private final DatabaseHelper myDb=new DatabaseHelper(mCtx);

    private double percent=0.0;

    public DetailsOrderAdapter(Context mCtx, List<ModelDetailsOrders> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mCtx);
        View view= layoutInflater.inflate(R.layout.details_order_card,viewGroup,false);

        return new  ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {

        final ModelDetailsOrders product = productList.get(i);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .into(productViewHolder.imageView);

       // productViewHolder.textVieworderid.setText(product.getOrderId());
        productViewHolder.txtViewOrderStatus.setText("Status: " + product.getOrderStatus() + "  Date:" + product.getOrderdate());
        productViewHolder.txtViewOrderStatus.setVisibility(View.INVISIBLE);
        productViewHolder.textViewShortDesc.setText(product.getProductName());
        productViewHolder.textViewPrice.setText("₹ " +product.getProductprice());
     //   productViewHolder.textViewRating.setText(" Qty " +product.getQty() + "  ₹ " +(Double.parseDouble(product.getProductprice().toString()) * Integer.parseInt(product.getQty())));
        productViewHolder.textViewRating.setText(" Qty " +product.getQty());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textVieworderid, textViewShortDesc, textViewRating, textViewPrice,textViewWeight,textViewDate,txtViewOrderStatus;
        ImageView imageView;
        Button btnCart;

        public ProductViewHolder(View itemView) {
            super(itemView);

           // textVieworderid = itemView.findViewById(R.id.textViewOrderId);
            textViewShortDesc = itemView.findViewById(R.id.textViewTitlemyorder);
            textViewRating = itemView.findViewById(R.id.textViewPrice);
            textViewPrice = itemView.findViewById(R.id.textViewtxtPrice);
          //  textViewWeight = itemView.findViewById(R.id.textViewWeight);
            //textViewDate = itemView.findViewById(R.id.textViewProductOff);
            imageView = itemView.findViewById(R.id.imageViewmyorder);
            txtViewOrderStatus = itemView.findViewById(R.id.textViewMyOrderStatus);


        }
    }
}
