package com.sfbd.serviceforcebd.fragment.storyBord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.SignInActivity;
import com.sfbd.serviceforcebd.activity.SignUpActivity;


public class Sec_SlideFragment extends Fragment {
    //ViewPager viewPager;
    TextView next,skip;
    Context context;
    EditText fname,email;
    TextView tx_s_signin;
    private String contact;

    public Sec_SlideFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sec__slide, container, false);
       // viewPager=getActivity().findViewById(R.id.viewPager);
        next=view.findViewById(R.id.sFinish);
        skip=view.findViewById(R.id.Sskip);
        fname = view.findViewById(R.id.sb_fname);
        email = view.findViewById(R.id.sb_email);
        tx_s_signin = view.findViewById(R.id.tx_signin);

         Bundle b =getArguments();
         contact = b.getString("phone").toString();

         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Bundle bundle = new Bundle();
                 Third_SlideFragment tf = new Third_SlideFragment();
                 String name = fname.getText().toString();
                 String mail = email.getText().toString();
                 if (name.length()==0){fname.setError("Please Enter Your Name!");}
                 else if(mail.length()==0){email.setError("Please Enter Your Email Address!");}
                 else  if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){email.setError("Please Enter Valid Email Address!");}
                 else {
                     bundle.putString("u_name",name);
                     bundle.putString("u_mail",mail);
                     bundle.putString("u_contact",contact);
                     tf.setArguments(bundle);
                     getFragmentManager().beginTransaction().replace(R.id.test_frag,tf).commit();
                 }
             }
         });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });
        tx_s_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });

        return view;
    }
}