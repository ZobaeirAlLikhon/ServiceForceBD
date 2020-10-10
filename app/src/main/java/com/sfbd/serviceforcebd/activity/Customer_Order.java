package com.sfbd.serviceforcebd.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sfbd.serviceforcebd.R;

public class Customer_Order extends AppCompatActivity {
    private Bundle bundle;
    private String name,address,contact,deliver_Date,deliver_Time,price
            ,quantity,orderId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oderi_nvoice);
        reciveDataFromOrder();

    }

    private void reciveDataFromOrder() {
        bundle = getIntent().getExtras();
        address=bundle.getString("address");
        contact=bundle.getString("contact");
        name=bundle.getString("name");
        deliver_Date=bundle.getString("deliver_Date");
        deliver_Time=bundle.getString("deliver_Time");
        price=bundle.getString("price");
        quantity=bundle.getString("quantity");
        orderId=bundle.getString("orderId");
    }
}
