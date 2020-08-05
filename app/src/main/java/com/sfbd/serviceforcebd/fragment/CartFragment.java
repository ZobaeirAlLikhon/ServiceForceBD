package com.sfbd.serviceforcebd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.model.CartModel;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    View cartView;
    Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private String curentUserId;
    private DatabaseReference dbref;
    private static final String TAG = "CartFragment";
    TextView tottalQuantity,tottalPrice;
    Button orderBtn;
    private int quantity,price;




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
        orderBtn=cartView.findViewById(R.id.order);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cart Order Button code here
            }
        });

        mAuth=FirebaseAuth.getInstance();
        curentUserId=mAuth.getCurrentUser().getUid();
        dbref= FirebaseDatabase.getInstance().getReference().child("Cart").child("UserCart").child(curentUserId);
        cartDetails();
        return cartView;
    }

    private void cartDetails() {
        FirebaseRecyclerOptions<CartModel> oft=new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(dbref,CartModel.class).build();
        FirebaseRecyclerAdapter<CartModel,CartHolder> ad=new FirebaseRecyclerAdapter<CartModel, CartHolder>(oft) {
            @Override
            protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull CartModel model) {

                String userId=getRef(position).getKey();
                dbref.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        quantity=quantity +Integer.parseInt(dataSnapshot.child("noOfProduct").getValue().toString());
                        price=price +Integer.parseInt(dataSnapshot.child("productPrice").getValue().toString());

                        holder.cartProName.setText("Name: "+dataSnapshot.child("productName").getValue().toString());
                        holder.cartPrice.setText("Price: "+dataSnapshot.child("productPrice").getValue().toString());
                        holder.cartQuantity.setText("Quantity: "+dataSnapshot.child("noOfProduct").getValue().toString());
                        holder.cartId.setText("Id: "+userId);
                        tottalQuantity.setText(String.valueOf("Tottal Product="+quantity));
                        tottalPrice.setText(String.valueOf("Tottal Price="+price+" Tk"));
                      /*  holder.dltImageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,"Delete this item",Toast.LENGTH_LONG).show();
                            }
                        });*/

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false);
                CartHolder holder=new CartHolder(v);
                return holder;
            }
        };

        recyclerView.setAdapter(ad);
        ad.startListening();

    }


    public static class CartHolder extends RecyclerView.ViewHolder
    {
        Context context;

        TextView cartProName,cartPrice,cartQuantity,cartId,cartDate;
        ImageButton dltImageButton;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartProName=itemView.findViewById(R.id.cartProName);
            cartPrice=itemView.findViewById(R.id.cartPrice);
            cartQuantity=itemView.findViewById(R.id.cartQuanity);
            cartId=itemView.findViewById(R.id.cartId);

        }


    }

}
