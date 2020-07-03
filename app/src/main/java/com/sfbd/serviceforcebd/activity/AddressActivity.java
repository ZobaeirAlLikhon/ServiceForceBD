package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sfbd.serviceforcebd.databinding.ActivityAddressBinding;
import com.sfbd.serviceforcebd.model.Order;
import com.sfbd.serviceforcebd.model.User;
import com.sfbd.serviceforcebd.util.SDF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddressActivity extends AppCompatActivity {

    private ActivityAddressBinding binding;
    private static final String TAG = "AddressActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private User user;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        initFireBaseUser();
        initView();

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });

        bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "onCreate: " + bundle.getString("address"));
            binding.addressET.getEditText().setText(bundle.getString("address"));
            binding.addressET.setHelperText("You can edit your address!");
        }

    }


    private void initFireBaseOrderPlace() {
        Log.d(TAG, "initFireBaseOrderPlace: started");
        binding.progressBarId.setVisibility(View.VISIBLE);
        String currentUserId = firebaseUser.getUid();
        DatabaseReference orderRef = databaseReference.child("orders");

        String userId = user.getUserID();
        String name = user.getName();
        String address = binding.addressET.getEditText().getText().toString();
        String contact = binding.contactET.getEditText().getText().toString();
        String orderItem = bundle.getString("orderItem");
        String date = binding.dateET.getEditText().getText().toString();
        String time = binding.timeET.getEditText().getText().toString();
        String isPlaced = "Placed";


        Order order = new Order(userId, name, address, contact, orderItem, date, time, isPlaced);
        String pushId = orderRef.push().getKey();
        order.setOrderId(pushId);
        orderRef.child("Admin").child("newOrder").child(pushId).setValue(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                orderRef.child("orderByUser").child(userId).child(pushId).setValue(order).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        Toast.makeText(this, "Your order Placed!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,MainActivity.class));
                        binding.progressBarId.setVisibility(View.GONE);
                    }

                });
            } else {
                Toast.makeText(this, "Please order again!", Toast.LENGTH_SHORT).show();
                binding.progressBarId.setVisibility(View.GONE);
            }
        });


    }

    private void initView() {
        Log.d(TAG, "initView: started");

        binding.dateET.getEditText().setOnClickListener(view -> {
            openStartDatePicker();
        });

        binding.timeET.getEditText().setOnClickListener(view -> {
            openTimePicker();
        });

        binding.orderBtn.setOnClickListener(view -> {
            orderService();
        });
    }

    private void orderService() {

        if (!validateAddress() | !validateContact() | !validateDate() | !validateTime()) {
            return;
        }

        Log.d(TAG, "orderService: validate");
        initFireBaseOrderPlace();


    }

    private void initFireBaseUser() {
        DatabaseReference userRef = databaseReference.child("users");
        String currentUserId = firebaseUser.getUid();
        Log.d(TAG, "initFireBase: userID: " + currentUserId);

      /*  userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        Log.d(TAG, "onDataChange: User: "+ user.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+databaseError.getMessage());
            }
        });*/

        userRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: User" + user.toString());
                    binding.contactET.getEditText().setText(user.getPhone());
//                    binding.contactET.getEditText().setText(user.ge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void openStartDatePicker() {

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
                    binding.dateET.getEditText().setText(stringDate);
                }

            }
        };

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year, month, 1);
        datePickerDialog.show();
    }

    private void openTimePicker() {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int s = mcurrentTime.get(Calendar.AM_PM);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddressActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

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
                binding.timeET.getEditText().setText(selectedHour + ":" + selectedMinute + " " + AM_PM);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    private boolean validateAddress() {
        String name = binding.addressET.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            binding.addressET.setError("Field can't be empty!");
            return false;
        } else {
            binding.addressET.setError(null);
            return true;
        }
    }

    private boolean validateContact() {
        String email = binding.contactET.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.contactET.setError("Field can't be empty!");
            return false;
        } else {
            binding.contactET.setError(null);
            return true;
        }
    }

    private boolean validateDate() {
        String email = binding.dateET.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.dateET.setError("Field can't be empty!");
            return false;
        } else {
            binding.dateET.setError(null);
            return true;
        }
    }

    private boolean validateTime() {
        String password = binding.timeET.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            binding.timeET.setError("Field can't be empty!");
            return false;
        } else {
            binding.timeET.setError(null);
            return true;
        }
    }
}
