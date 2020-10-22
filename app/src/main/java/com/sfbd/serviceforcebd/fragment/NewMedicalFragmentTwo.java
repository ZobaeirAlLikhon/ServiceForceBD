package com.sfbd.serviceforcebd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.ServicesActivity;
import com.sfbd.serviceforcebd.databinding.NewMedicalFragTwoBinding;

public class NewMedicalFragmentTwo extends Fragment{
    private Spinner spinner;
    private  static String item;
    private TextInputLayout ppname,ss;
    private MaterialButton btn;
    public NewMedicalFragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_medical_frag_two,container,false);
        //initialize spinner and it's item
        spinner = view.findViewById(R.id.spinner);
        ppname = view.findViewById(R.id.pname);
        btn = view.findViewById(R.id.sendnowbtn);
        ss = view.findViewById(R.id.dname);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.serial_for_a_Doctor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                if (item.equals("Choose a Category")){
                    //do nothing
                    Toast.makeText(getContext(), "Please Enter a Category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),item,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
            }
        });

        String spname = ppname.getEditText().getText().toString();
        ss.getEditText().setText("Hello for testing purpose.....");

    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (spname.isEmpty()){
                Toast.makeText(getContext(),"return string is empty",Toast.LENGTH_SHORT).show();
            }
else{ Toast.makeText(getContext(), "sss"+spname, Toast.LENGTH_SHORT).show();}

        }
    });



        return view;
    }
}
