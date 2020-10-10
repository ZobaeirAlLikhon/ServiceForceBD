package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityOrderDetailsBinding;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity {
    String name,address,contact,deliver_Date,deliver_Time,price,pName,quantity,orderId,currentDate,currentTime;
    Bundle bundle;
    private ActivityOrderDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reciveDataFromOrder();
        binding.nTV.setText("Name: "+name);
        binding.pTV.setText("Phone: "+contact);
        binding.aTV.setText("Address: "+address);
        binding.oTV.setText("OrderId: "+orderId);
        binding.sTV.setText("Services Name: "+pName);
        binding.qTV.setText("Quantity: "+quantity);
        binding.priTV.setText("Price "+price+"TK");
        binding.dateOfdelivered.setText(deliver_Date);
        binding.timeOfdelivered.setText(deliver_Time);
        binding.dateOfPurchaase.setText(currentDate);
        binding.timeOfPurchase.setText(currentTime);
        binding.homeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderDetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void reciveDataFromOrder() {
        Bundle bundle = getIntent().getExtras();
        address= bundle.getString("address");
        contact= bundle.getString("contact");
        name= bundle.getString("name");
        deliver_Date= bundle.getString("deliver_Date");
        deliver_Time= bundle.getString("deliver_Time");
        price= bundle.getString("price");
        quantity= bundle.getString("quantity");
        orderId= bundle.getString("orderId");
        pName=bundle.getString("pname");
        currentDate=bundle.getString("currentDate");
        currentTime=bundle.getString("currentTime");


    }
}