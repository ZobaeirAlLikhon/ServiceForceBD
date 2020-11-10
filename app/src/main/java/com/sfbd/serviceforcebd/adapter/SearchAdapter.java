package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MedicalHealthDetails;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.activity.SubCatagoryDetails;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    Context context;
    List<String> name;
    private TypedArray img;


    public SearchAdapter(Context context, List<String> name) {
        this.context = context;
        this.name = name;

    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(context).inflate(R.layout.rv_service_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.textView.setText(name.get(position));
        holder.textView1.setVisibility(View.INVISIBLE);
        holder.imageView1.setVisibility(View.INVISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.get(position).equals("Sofa cleaning")||name.get(position).equals("Full home deep Cleaning")
                        ||name.get(position).equals("Home Cleaning")||name.get(position).equals("Office Cleaning")
                        ||name.get(position).equals("Pest control service")||name.get(position).equals("Carpet/Mattress Cleaning")
                        ||name.get(position).equals("Disinfection Service For Virus")
                        ||name.get(position).equals("Hire a Female Cleaner")||name.get(position).equals("Hire a Male Cleaner")
                        ||name.get(position).equals("Water Tank Cleaning")||name.get(position).equals("Pipe Line cleaning"))
                        {
                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                            intent.putExtra("service_name","Cleaning");
                            intent.putExtra("category", name.get(position));
                            context.startActivity(intent);

                        }
                else if(name.get(position).equals("Ac Repair Services")||name.get(position).equals("Microwave Repair Services")
                        ||name.get(position).equals("Refigerator Repair Services")
                        ||name.get(position).equals("Washing Machine Repair Services")
                        ||name.get(position).equals("IPS Repair Services"))
                        {
                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                            intent.putExtra("service_name","Appliance");
                            intent.putExtra("category", name.get(position));
                            context.startActivity(intent);

                        }
                else if(name.get(position).equals("Monthly subscription packages")||name.get(position).equals("Ironing service")
                        ||name.get(position).equals("Washing Servicing")||name.get(position).equals("Others"))
                        {
                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                            intent.putExtra("service_name","Laundry");
                            intent.putExtra("category", name.get(position));
                            context.startActivity(intent);
                        }
                else if(name.get(position).equals("Plumbing and sanitary Service")
                        ||name.get(position).equals("Electronics for Electrical Service"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Electric & Plumbing Serviecs");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Home Interior")||name.get(position).equals("Office Interior")
                        ||name.get(position).equals("Renovation &amp; Décor")
                        ||name.get(position).equals("Interior Design")||name.get(position).equals("Interior Design Consultancy Service")
                        ||name.get(position).equals("Thai, Glass &amp; Drill work Service")||name.get(position).equals("Partex work Service")
                        ||name.get(position).equals("Wood work Service")||name.get(position).equals("Carpentry Service")
                        ||name.get(position).equals("Painting Service"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Painting & Renovation Serviecs");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Car wash &amp; polish")||name.get(position).equals("Bike wash &amp; polish")
                        ||name.get(position).equals("Monthly Subscription Package"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Care & Bike Care");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Desktop service")||name.get(position).equals("Laptop service")
                        ||name.get(position).equals("CCTV camera service")||name.get(position).equals("Smartphone Repair"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Electronics & Gadgets");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Eid Festival offer")||name.get(position).equals("Personal Beuty Care")
                        ||name.get(position).equals("Body spa &amp; Massage")||name.get(position).equals("Machover for women")
                        ||name.get(position).equals("Mehedi design")||name.get(position).equals("Mehendi Design")
                        ||name.get(position).equals("Bridal makcup")||name.get(position).equals("salon service for man")
                        ||name.get(position).equals("Groom up package for men"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Beauty Services");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("House Shifting service")||name.get(position).equals("Office Shifting service")
                        ||name.get(position).equals("Transport for shifting"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Shifting");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Consult with a Doctor")||name.get(position).equals("Psychiatrist/mental health")
                        ||name.get(position).equals("Pathology test")||name.get(position).equals("Serial for a Doctor")
                        ||name.get(position).equals("Medicine Home Delivery")||name.get(position).equals("Emergency Service"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, MedicalHealthDetails.class);
                                            intent.putExtra("service_name","Medical");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Birthday")||name.get(position).equals("Marriage Day")
                        ||name.get(position).equals("Political Function")||name.get(position).equals("Social Function")
                        ||name.get(position).equals("Get together")||name.get(position).equals("Photography")
                        ||name.get(position).equals("Interior Decoration")||name.get(position).equals("Painting"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Event Management");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Website Development")||name.get(position).equals("Mobile Application")
                        ||name.get(position).equals("Graphics Design")||name.get(position).equals("Software Build")
                        ||name.get(position).equals("Social Media Marketing")||name.get(position).equals("SEO")
                        ||name.get(position).equals("Monthly Subscription Package"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","IT Support");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Groceriesa")||name.get(position).equals("Fresh Bazar5")
                        ||name.get(position).equals("Health &amp; Hygiene")||name.get(position).equals("Covid-19 Safety Kits")
                        ||name.get(position).equals("Silk")||name.get(position).equals("Cosmetics"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Market Servicet");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Get a Tutor")||name.get(position).equals("Admission for Batch")
                        ||name.get(position).equals("Training Course IT")||name.get(position).equals("Internship"))
                                        {
                                            Toast.makeText(context, name.get(position),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, SubCatagoryDetails.class);
                                            intent.putExtra("service_name","Tuition Service");
                                            intent.putExtra("category", name.get(position));
                                            context.startActivity(intent);
                                        }
                else if(name.get(position).equals("Get a Tuition"))
                                        {
                                            Intent intent =new Intent(context, Silk.class);
                                            context.startActivity(intent);
                                        }

            }
        });
    }
    public void updateList(List<String> newName)
    {
        name=new ArrayList<>();
        name.addAll(newName);
        notifyDataSetChanged();



    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        ImageView imageView,imageView1;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.itemTV);
            imageView=itemView.findViewById(R.id.iconeIV);
            textView1=itemView.findViewById(R.id.countLike);
            imageView1=itemView.findViewById(R.id.likeIV);

        }
    }
}
