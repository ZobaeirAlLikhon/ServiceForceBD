package com.sfbd.serviceforcebd.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sfbd.serviceforcebd.R;

import java.util.HashMap;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingFragment extends AppCompatDialogFragment {
    EditText editText;
    Context context;
    MaterialRatingBar ratingBar1;
    float a;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//        return super.onCreateDialog(savedInstanceState);
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Rating_feedback");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ratting_alart_fragment,null);
        editText=view.findViewById(R.id.textBox);
        ratingBar1=view.findViewById(R.id.ratingBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        String userId=firebaseUser.getUid();
        builder.setView(view).setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().dismiss();

            }
        }).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text=editText.getText().toString();
                a=ratingBar1.getRating();
                HashMap<String, Object> result = new HashMap<>();
                result.put("rating",a);
                result.put("comment",text);
                SharedPreferences preferences=getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("fristTime",false);
                editor.apply();
                dbref.child(userId).updateChildren(result);
                Toast.makeText(getContext(),"submitted",Toast.LENGTH_LONG).show();
            }
        });
        return builder.create();
    }
}
