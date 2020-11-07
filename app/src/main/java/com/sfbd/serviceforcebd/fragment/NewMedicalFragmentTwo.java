package com.sfbd.serviceforcebd.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;
import com.sfbd.serviceforcebd.activity.MediAddressActivity;
import com.sfbd.serviceforcebd.activity.ServicesActivity;
import com.sfbd.serviceforcebd.connection.ConnectionManager;
import com.sfbd.serviceforcebd.databinding.NewMedicalFragTwoBinding;
import com.sfbd.serviceforcebd.util.SDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NewMedicalFragmentTwo extends Fragment{
    private Spinner spinner;
    private  static String item,name,phone,docname,serialDate,serialTime,CDate,CTime;
    private TextInputLayout textInputLayout1,textInputLayout2,textInputLayout3,textInputLayout4,textInputLayout5;
    private MaterialButton sendbtn,calbtn;
    private NewMedicalFragTwoBinding binding;
    DatabaseReference databaseReference;

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
        textInputLayout4 = view.findViewById(R.id.dateForSerial);
        textInputLayout5 = view.findViewById(R.id.timeForSerial);
        //initialize spinner and it's item
        spinner = view.findViewById(R.id.spinner);

        //initialize button
        sendbtn = view.findViewById(R.id.sendbtn_sfd);
        calbtn = view.findViewById(R.id.calbtnsfd);
        //Database init


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
    //Date and time
        textInputLayout4.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //date
                DatePicker();
            }
        });
        textInputLayout5.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker();
            }
        });
//        send btn
    sendbtn.setOnClickListener(v -> {
        if (!ConnectionManager.connection(getContext())) {

            AlertDialog d = new AlertDialog.Builder(getContext())
                    .setTitle("No Internet Connection!!")
                    .setMessage("please turn on your data connection")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    }).show();
            d.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));


            Toast.makeText(getContext(), "No internet", Toast.LENGTH_LONG).show();
        } else {


            name = textInputLayout1.getEditText().getText().toString();
            phone = textInputLayout2.getEditText().getText().toString();
            docname = textInputLayout3.getEditText().getText().toString();
            serialDate = textInputLayout4.getEditText().getText().toString();
            serialTime = textInputLayout5.getEditText().getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            CTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()).toString();
            CDate = sdf.format(new Date());
            if (name.length() == 0) {
                textInputLayout1.setError("Please Enter Your Name!");
            } else if (phone.length() == 0 || phone.length() < 11) {
                textInputLayout2.setError("Please Enter Valid Phone Number!");
            } else if (docname.length() == 0) {
                textInputLayout3.setError("Please Enter Doctor's name!");
            } else if (serialDate.length() == 0) {
                textInputLayout3.setError("Please Enter Serial Date!");
            } else if (serialTime.length() == 0) {
                textInputLayout3.setError("Please Enter Serial Time!");
            } else if (item.equals("Choose a Category")) {
                Toast.makeText(getContext(), "Please Enter a Category", Toast.LENGTH_SHORT).show();
            } else {
                ///here use model and firebase database to insert data on database...
                databaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("Medical").child("Serial for a Doctor");
                String pushId = databaseReference.push().getKey();
                HashMap<String, Object> serial = new HashMap<>();
                serial.put("Patient's Name", name);
                serial.put("Patient's Phone Number", phone);
                serial.put("Doctor's Name", docname);
                serial.put("Doctor Category", item);
                serial.put("Serial_Date", serialDate);
                serial.put("Serial_Time", serialTime);
                databaseReference.child(CDate).child(CTime).updateChildren(serial);
//            String test = name + " "+phone + " "+ docname + " "+item;
                Toast.makeText(getContext(), "Serial Done!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        }
    });

    calbtn.setOnClickListener(v ->{
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01707071417"));
                getContext().startActivity(intent);
    });


        return view;
    }

    private void TimePicker() {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int s = mcurrentTime.get(Calendar.AM_PM);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute){


                String AM_PM;
                if (selectedHour < 1) {
                    selectedHour = selectedHour + 12;
                    AM_PM = "AM";
                } else if (selectedHour < 12) {
                    AM_PM = "AM";
                } else if (selectedHour == 12) {
                    AM_PM = "PM";
                } else {
                    selectedHour = selectedHour - 12;
                    AM_PM = "PM";
                }
                textInputLayout5.getEditText().setText(selectedHour + ":" + selectedMinute + " " + AM_PM);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mTimePicker.show();

    }

    private void DatePicker() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String currentDate = year + "/" + month + "/" + day + " 00:00:00";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //eventStartDate = date.getTime()/1000;
                if (date != null) {
                    long epochTime = date.getTime() / 1000;
                    String stringDate = SDF.getDate(epochTime);
                    textInputLayout4.getEditText().setText(stringDate);
                }

            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }


}
