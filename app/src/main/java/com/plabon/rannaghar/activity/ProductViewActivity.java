package com.plabon.rannaghar.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.model.Cart;
import com.plabon.rannaghar.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductViewActivity extends BaseActivity {

    ImageView img;
    TextView textViewName,textViewDetails,txtprice,cartTotal;
    String name,image,atribute,price,pid,priced;
    Button btnAddToCart;
    List<Cart> cartList = new ArrayList<>();
    int cartId;
    Cart cart;
    private static int cart_count = 0;
    RelativeLayout cartTotalRelavive;
    Double totalP=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        img= findViewById(R.id.imageView);
        textViewDetails= findViewById(R.id.itemdetails);
        textViewName= findViewById(R.id.itemname);
        btnAddToCart = findViewById(R.id.addtocart);
        txtprice = findViewById(R.id.txtPrice);
        cartTotalRelavive = findViewById(R.id.relative_cart_total);
        cartTotalRelavive.setVisibility(View.INVISIBLE);
        cartTotal = findViewById(R.id.cart_total_text);

        cartList = getCartList();

        Intent n =getIntent();
        name = n.getStringExtra("n").toString();
        pid = n.getStringExtra("id").toString();
        image = n.getStringExtra("image");
        atribute = n.getStringExtra("attribute");
        price = n.getStringExtra("price");
        priced = n.getStringExtra("discount");
        // Toast.makeText(getApplicationContext(),n.getStringExtra("image"),Toast.LENGTH_SHORT).show();
        textViewName.setText(name);
        textViewDetails.setText(n.getStringExtra("description"));
        txtprice.setText(atribute + "  -   ₹" + priced + "" );

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        changeActionBarTitle(getSupportActionBar());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        Picasso.get()
                .load(Utils.ProductImage + n.getStringExtra("image"))
                .into(img);


        cart_count = cartCount();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart = new Cart(pid, name, image, "rs", priced, atribute, "1", priced);
                cartList.add(cart);
                String cartStr = gson.toJson(cartList);
                //Log.d("CART", cartStr);
                localStorage.setCart(cartStr);
                onAddProduct();
                totalP=getTotalPrice();
                if (totalP>0){
                    cartTotalRelavive.setVisibility(View.VISIBLE);
                    cartTotal.setText("Cart Total :- " + totalP + "");

                }
            }
        });

        totalP=getTotalPrice();
        if (totalP>0){
            cartTotalRelavive.setVisibility(View.VISIBLE);
            cartTotal.setText("Cart Total :- " + totalP + "");

        }

        cartTotalRelavive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent= new Intent(ProductViewActivity.this, CartActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    public void onClick(View view) {
    }

    private void changeActionBarTitle(ActionBar actionBar) {
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        TextView tv = new TextView(getApplicationContext());
        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        // Set text to display in TextView
        tv.setText(name); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(ProductViewActivity.this, MainNewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            case R.id.cart_action:
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();

    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();

    }
}