package com.sfbd.serviceforcebd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private int quantity,price=0;




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
        Log.d(TAG,"cart");
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cart Order Button code here
                Toast.makeText(getContext(),"Comming Soon",Toast.LENGTH_LONG).show();
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



    }


    public static class CartHolder extends RecyclerView.ViewHolder
    {
        Context context;

        TextView cartProName,cartPrice1,cartQuantity,cartId;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartProName=itemView.findViewById(R.id.cartProName);
            cartPrice1=itemView.findViewById(R.id.cartPrice);
            cartQuantity=itemView.findViewById(R.id.cartQuanity);
            cartId=itemView.findViewById(R.id.cartId);

        }


    }

}
