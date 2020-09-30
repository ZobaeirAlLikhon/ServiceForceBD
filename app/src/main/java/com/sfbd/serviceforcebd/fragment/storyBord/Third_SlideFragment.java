package com.sfbd.serviceforcebd.fragment.storyBord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.SignInActivity;
import com.sfbd.serviceforcebd.activity.SignUpActivity;
import com.sfbd.serviceforcebd.model.User;

public class Third_SlideFragment extends Fragment {
    ViewPager viewPager;
    TextView next,skip,tx_signin;
    Context context;
    EditText con_pass,pass;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public Third_SlideFragment() {
        //require empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third__slide,container,false);
        viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.finish_tx);
        skip = view.findViewById(R.id.third_skip);
        con_pass = view.findViewById(R.id.sb_conpass);
        pass = view.findViewById(R.id.sb_pass);
        tx_signin = view.findViewById(R.id.txf_signin);

        tx_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getActivity().getSharedPreferences("ppp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("story",false);
                editor.apply();
                //Implements methods for data insertion and other things..
                Bundle b = getArguments();
                String password = pass.getText().toString();
                String conpass = con_pass.getText().toString();
                if (password.length()==0){
                    pass.setError("Please Enter Your Password!");
                }
                else if(conpass.length()==0){
                    con_pass.setError("Please Enter Your Password Again!");
                }
                else if (!conpass.equals(password)){
                    Toast.makeText(getContext(),"Password Does not Match",Toast.LENGTH_SHORT).show();
                }
                else {
                    String phone = b.getString("phone");
                    String name = b.getString("name");
                    String email = b.getString("email");
                    User user = new User(name, email, phone);

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
                    final String pushId = userRef.push().getKey();
                    user.setPushID(pushId);
                    mAuth = FirebaseAuth.getInstance();


                }

            }
        });

        return view;
    }
}
