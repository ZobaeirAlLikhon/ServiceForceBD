package com.sfbd.serviceforcebd.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.CartAddressActivity;
import com.sfbd.serviceforcebd.activity.Goru;
import com.sfbd.serviceforcebd.activity.SubCatagoryDetails;
import com.sfbd.serviceforcebd.adapter.CartAdapter;
import com.sfbd.serviceforcebd.connection.ConnectionManager;
import com.sfbd.serviceforcebd.model.CartModel;
import com.sfbd.serviceforcebd.model.Sd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;

public class CartFragment extends Fragment {
    View cartView;
    Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String curentUserId;
    private DatabaseReference dbref,dbref1,dbref2;
    private static final String TAG = "CartFragment";
    TextView tottalQuantity,tottalPrice;
    Button orderBtn;
    private int quantity,price=0;
    ArrayList<CartModel> list;
    CartAdapter adapter;
    String proName,price1,noOfItem;
    private LottieAnimationView notFoundAnimationView;
    RelativeLayout layout;
    ProgressDialog progressDialog;


    public CartFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartView= inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView= cartView.findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tottalQuantity=cartView.findViewById(R.id.tottalProduct);
        tottalPrice=cartView.findViewById(R.id.tottalPrice);
        orderBtn=(Button) cartView.findViewById(R.id.order);
        notFoundAnimationView=cartView.findViewById(R.id.notFoundAnimLV);
        layout=cartView.findViewById(R.id.bottom_lay);
        Log.d(TAG,"cart");
        progressDialog=new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mAuth=FirebaseAuth.getInstance();
        curentUserId=mAuth.getCurrentUser().getUid();
        dbref= FirebaseDatabase.getInstance().getReference().child("Cart").child("UserCart").child(curentUserId);
        dbref1= FirebaseDatabase.getInstance().getReference().child("orders").child("cartOrders").child(curentUserId);
        dbref2= FirebaseDatabase.getInstance().getReference().child("Cart").child("UserCart").child(curentUserId);
        String key=dbref1.getKey();
        if(!ConnectionManager.connection(getContext()))
        {
            progressDialog.dismiss();
            new AlertDialog.Builder(getContext())
                    .setTitle("No Internet Connection!!")
                    .setMessage("please turn on your data connection")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    }).show();

            Toast.makeText(getContext(),"No internet",Toast.LENGTH_LONG).show();
        }

//        cartDetails();
        list=new ArrayList<CartModel>();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int s=0;
                int q=0;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {

                    CartModel cm=dataSnapshot1.getValue(CartModel.class);
                    String price=  dataSnapshot1.child("productPrice").getValue().toString();
                    String quantity=  dataSnapshot1.child("noOfProduct").getValue().toString();
                    int sumPrice=Integer.parseInt(price);
                    int sumQuantity=Integer.parseInt(quantity);
                    s=s+sumPrice;
                    q=q+sumQuantity;
                    list.add(cm);

                }
                layout.setVisibility(View.VISIBLE);
                tottalPrice.setText("Total Price: "+String.valueOf(s));
                tottalQuantity.setText("Total Quantity: "+String.valueOf(q));
                proName="cartOrder";
                price1=String.valueOf(s);
                noOfItem=String.valueOf(q);
                adapter= new CartAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                orderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cart Order Button code her
                        String ini="1";
                        Intent intent=new Intent(getContext(),CartAddressActivity.class);
                        intent.putExtra("proName","Multiple_Product");
                        intent.putExtra("price",price1);
                        intent.putExtra("noOfItem",noOfItem);
                        intent.putExtra("category","Cart");
                        intent.putExtra("lists",list);
                        startActivity(intent);

                    }
                });
                if(!dataSnapshot.exists())
                {
                    notFoundAnimationView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.INVISIBLE);

                }
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return true;
                    }
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                        list.remove(viewHolder.getAdapterPosition());
                       String a= list.get(viewHolder.getAdapterPosition()).getPushId().toString();
//                        Toast.makeText(getContext(),a,Toast.LENGTH_LONG).show();
                        dbref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot2 : snapshot.getChildren())
                                {
                                  if(dataSnapshot2.getKey().equals(a))
                                  {
                                      dbref2.child(dataSnapshot2.getKey()).removeValue();
                                      list.clear();
                                  }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }).attachToRecyclerView(recyclerView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return cartView;
    }




    /*private void cartDetails() {
        FirebaseRecyclerOptions<CartModel> oft=new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(dbref,CartModel.class).build();
        FirebaseRecyclerAdapter<CartModel,CartHolder> ad=new FirebaseRecyclerAdapter<CartModel, CartHolder>(oft) {
            @Override
            protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull CartModel model) {

                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = 0;
                        int count1=0;
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            int tproduct = Integer.parseInt( ds.child("noOfProduct").getValue().toString());
                            int tprice = Integer.parseInt( ds.child("productPrice").getValue().toString());
                            count = count + tproduct;
                            count1=count1+tprice;
                        }
                        tottalQuantity.setText(String.valueOf("Total Product="+String.valueOf(count)));
                        tottalPrice.setText(String.valueOf("Total Price="+String.valueOf(count1)+" Tk"));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                String userId=getRef(position).getKey();
                holder.cartProName.setText("Name :"+model.getProductName());
                holder.cartPrice1.setText("Price :"+model.getProductPrice());
                holder.cartQuantity.setText("Quantity :"+model.getNoOfProduct());
                holder.cartId.setText("Id"+userId);



//                dbref.child(userId).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        quantity=quantity +Integer.parseInt(dataSnapshot.child("noOfProduct").getValue().toString());
////                        price=price +Integer.parseInt(dataSnapshot.child("productPrice").getValue().toString());
//
////                        holder.cartProName.setText("Name: "+dataSnapshot.child("productName").getValue().toString());
////                        holder.cartPrice1.setText("Name: "+dataSnapshot.child("productPrice").getValue().toString());
//
////                        holder.cartPrice1.setText("Price: "+dataSnapshot.child("productPrice").getValue().toString());
////                        holder.cartQuantity.setText("Quantity: "+dataSnapshot.child("noOfProduct").getValue().toString());
////                        holder.cartId.setText("Id: "+userId);
////                        tottalQuantity.setText(String.valueOf("Tottal Product="+quantity));
////                        tottalPrice.setText(String.valueOf("Tottal Price="+price+" Tk"));
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });



            }

            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
                CartHolder holder=new CartHolder(v);
                return holder;
            }

            public void deleteItem(int possition)
            {
                getSnapshots().getSnapshot(possition).getRef().removeValue();
            }
        };

        recyclerView.setAdapter(ad);
        ad.startListening();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

             ad.getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).getRef().removeValue();

            }
        }).attachToRecyclerView(recyclerView);



    }*/


//    public static class CartHolder extends RecyclerView.ViewHolder
//    {
//        Context context;
//
//        TextView cartProName,cartPrice1,cartQuantity,cartId;
//        public CartHolder(@NonNull View itemView) {
//            super(itemView);
//            cartProName=itemView.findViewById(R.id.cartProName);
//            cartPrice1=itemView.findViewById(R.id.cartPrice);
//            cartQuantity=itemView.findViewById(R.id.cartQuanity);
//            cartId=itemView.findViewById(R.id.cartId);
//
//        }
//
//
//    }

}
