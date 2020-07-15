package com.sfbd.serviceforcebd.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.model.Order;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private View OrderView;
    private RecyclerView recyclerView;
    private DatabaseReference orderRef,orderRef1;
    private FirebaseAuth mAuth;
    private String curentUserId;

    public OrderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        OrderView= inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView= OrderView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();
        curentUserId=mAuth.getCurrentUser().getUid();
        orderRef= FirebaseDatabase.getInstance().getReference().child("orders").child("orderByUser").child(curentUserId);
//        orderRef1= FirebaseDatabase.getInstance().getReference().child("Admin").child("newOrder");

        return OrderView;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Order>().setQuery(orderRef,Order.class).build();
        FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter= new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
//                holder.product_name.setText(model.getOrderItem());
//                holder.order_date.setText(model.getOrderDate());

                String userId=getRef(position).getKey();
                orderRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        holder.product_name.setText(dataSnapshot.child(userId).child("orderItem").getValue().toString());
                        holder.order_date.setText(dataSnapshot.child(userId).child("orderDate").getValue().toString());
                        holder.product_status.setText("Product Status");
                        holder.order_place.setText("Order "+ dataSnapshot.child(userId).child("placed").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_order_item,parent,false);
               OrderViewHolder viewHolder=new OrderViewHolder(v);
               return viewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class OrderViewHolder extends RecyclerView.ViewHolder
    {

        TextView product_name,order_date,product_status,order_place;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=itemView.findViewById(R.id.productNameTV);
            order_date=itemView.findViewById(R.id.productDateTV);
            product_status=itemView.findViewById(R.id.productStatus);
            order_place=itemView.findViewById(R.id.productStatusTV);


        }
    }
}
