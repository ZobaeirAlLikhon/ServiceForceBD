package com.sfbd.serviceforcebd.fragment.storyBord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.SnackBar;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;
import com.sfbd.serviceforcebd.activity.SignInActivity;
import com.sfbd.serviceforcebd.activity.SignUpActivity;
import com.sfbd.serviceforcebd.model.User;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class Third_SlideFragment extends Fragment {
   // ViewPager viewPager;
    TextView next,skip,tx_signin;
    Context context;
    EditText con_pass,pass;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private String phone,name,email;

    public Third_SlideFragment() {
        //require empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third__slide,container,false);
       // viewPager = getActivity().findViewById(R.id.viewPager);
        next = view.findViewById(R.id.finish_tx);
        skip = view.findViewById(R.id.third_skip);
        con_pass = view.findViewById(R.id.sb_conpass);
        pass = view.findViewById(R.id.sb_pass);
        tx_signin = view.findViewById(R.id.txf_signin);

        Bundle b = getArguments();
        phone = b.getString("u_contact").toString();
        name = b.getString("u_name").toString();
        email = b.getString("u_mail").toString();

        //Toast.makeText(getContext(),phone+" "+name+" "+email,Toast.LENGTH_SHORT).show();


        tx_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implements methods for data insertion and other things..

                String password = pass.getText().toString();
                String conpass = con_pass.getText().toString();
                if (password.length() == 0) {
                    pass.setError("Please Enter Your Password!");
                } else if (conpass.length() == 0) {
                    con_pass.setError("Please Enter Your Password Again!");
                } else if (!conpass.equals(password)) {
                    Toast.makeText(getContext(), "Password Does not Match", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User(name, email, phone);
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
                    final String pushId = userRef.push().getKey();
                    user.setPushID(pushId);
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                currentUser = mAuth.getCurrentUser();
                                String userId = currentUser.getUid();
                                user.setUserID(userId);
                                userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "Successful!", Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                            user.updateProfile(profileUpdates);
                                            HashMap<String, Object> cupon = new HashMap<>();
                                            cupon.put("cupon_ID","SFBD-2020");
                                            userRef.child(userId).updateChildren(cupon);
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getContext(), "This Email have already an account!", Toast.LENGTH_SHORT).show();
                                }

                        }
                        }
                    });


                }

            }
        });

                    return view;
                }
            }
