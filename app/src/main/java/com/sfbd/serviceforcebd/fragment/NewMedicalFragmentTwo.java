package com.sfbd.serviceforcebd.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.ServicesActivity;
import com.sfbd.serviceforcebd.databinding.NewMedicalFragTwoBinding;

public class NewMedicalFragmentTwo extends Fragment{
    private Spinner spinner;
    private  static String item,name,phone,docname;
    private TextInputLayout textInputLayout1,textInputLayout2,textInputLayout3;
    private MaterialButton sendbtn,calbtn;
    private NewMedicalFragTwoBinding binding;
    public NewMedicalFragmentTwo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_medical_frag_two,container,false);
        //initialize text view
        textInputLayout1 = view.findViewById(R.id.pname);
        textInputLayout2 = view.findViewById(R.id.pphone);
        textInputLayout3 = view.findViewById(R.id.dname);
        //initialize spinner and it's item
        spinner = view.findViewById(R.id.spinner);

        //initialize button
        sendbtn = view.findViewById(R.id.sendbtn_sfd);
        calbtn = view.findViewById(R.id.calbtnsfd);

        //GET VALUE FROM FIELD


        //spinner adapter to populate spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.serial_for_a_Doctor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(adapter);

        // listener on spinner to select spinner item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
            }
        });

    sendbtn.setOnClickListener(v -> {

        name = textInputLayout1.getEditText().getText().toString();
        phone = textInputLayout2.getEditText().getText().toString();
        docname = textInputLayout3.getEditText().getText().toString();

        if (name.length()==0){
            textInputLayout1.setError("Please Enter Your Name!");
        }
        else if (phone.length()==0 || phone.length()<11){
            textInputLayout2.setError("Please Enter Valid Phone Number!");
        }
        else if (docname.length()==0){
            textInputLayout3.setError("Please Enter Doctor's name!");
        }
        else if (item.equals("Choose a Category")){
            Toast.makeText(getContext(),"Please Enter a Category",Toast.LENGTH_SHORT).show();
        }
        else{
            ///here use model and firebase database to insert data on database...
            String test = name + " "+phone + " "+ docname + " "+item;
            Toast.makeText(getContext(), test, Toast.LENGTH_SHORT).show();
        }

    });
    calbtn.setOnClickListener(v ->{
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01707071417"));
                getContext().startActivity(intent);
    });


        return view;
    }


}
