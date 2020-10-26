package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityMediAddressBinding;
import com.sfbd.serviceforcebd.model.DocOrder;
import com.sfbd.serviceforcebd.model.Order;
import com.sfbd.serviceforcebd.model.User;
import com.sfbd.serviceforcebd.util.SDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MediAddressActivity extends AppCompatActivity {
    private ActivityMediAddressBinding binding;
    private static final int LOCATION_REQUEST_CODE = 1001;
    private static final String TAG = "MediAddressActivity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private User user;
    private Bundle bundle;
    private String address;
    private String docName,docVisit,date,time,catagory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMediAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        bundle = getIntent().getExtras();
        docName=bundle.getString("proName");
        docVisit=bundle.getString("price");
        catagory=bundle.getString("category");
        binding.dName.setText("Doctor Name: "+docName);
        binding.dVisit.setText("Doctor Name: "+docVisit);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }
        initView();
        initFireBaseUser();

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
                    binding.contactETM.getEditText().setText(user.getPhone());
//                    binding.contactET.getEditText().setText(user.ge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initView() {
        Log.d(TAG, "initView: started");

        binding.dateETM.getEditText().setOnClickListener(view -> {
            openStartDatePicker();
        });

        binding.timeETM.getEditText().setOnClickListener(view -> {
            openTimePicker();
        });

        binding.submitBTNM.setOnClickListener(view -> {
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
                    binding.dateETM.getEditText().setText(stringDate);
                }

            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void openTimePicker() {


        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int s = mcurrentTime.get(Calendar.AM_PM);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MediAddressActivity.this,android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
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
                binding.timeETM.getEditText().setText(selectedHour + ":" + selectedMinute + " " + AM_PM);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mTimePicker.show();


    }
    private boolean validateAddress() {
        String name = binding.addressETM.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            binding.addressETM.setError("Field can't be empty!");
            return false;
        } else {
            binding.addressETM.setError(null);
            return true;
        }
    }

    private boolean validateContact() {
        String email = binding.contactETM.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.contactETM.setError("Field can't be empty!");
            return false;
        } else {
            binding.contactETM.setError(null);
            return true;
        }
    }

    private boolean validateDate() {
        String email = binding.dateETM.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.dateETM.setError("Field can't be empty!");
            return false;
        } else {
            binding.dateETM.setError(null);
            return true;
        }
    }

    private boolean validateTime() {
        String password = binding.timeETM.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            binding.timeETM.setError("Field can't be empty!");
            return false;
        } else {
            binding.timeETM.setError(null);
            return true;
        }
    }



    //Location********************************************************************************************************
    private void askLocationPermission() {
        Log.d(TAG, "askLocationPermission: started");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "You should give the permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            /* First time user use this application*/
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*Permission Granted*/
                getLastLocation();
            } else {
                /*Permission Denied*/

            }
        }
    }


    private void getLastLocation() {
        Log.d(TAG, "getLastLocation: started");

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    Log.d(TAG, "onSuccess: Location : " + location.toString());
                    Log.d(TAG, "onSuccess: Latitude: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: Longitude: " + location.getLongitude());
                    Log.d(TAG, "onSuccess: Time: " + location.getTime());

                    address = getCompleteAddressString(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "onSuccess: Address : " + address);
                    binding.addressETM.getEditText().setText(address);

                }

            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });

        /*
        // OR You can write this bellow
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

            }
        });*/

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }
                strAdd = strReturnedAddress.toString();
                //Log.w("My Current loction address", strReturnedAddress.toString());
                Log.d(TAG, "getCompleteAddressString: " + strReturnedAddress.toString());
            } else {
                //Log.w("My Current loction address", "No Address returned!");
                Log.d(TAG, "getCompleteAddressString: No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "My Current location address Can not get Address!");
        }
        return strAdd;
    }
    // order*************************************************************************************


    private void initFireBaseOrderPlace() {
        Log.d(TAG, "initFireBaseOrderPlace: started");
        //binding.progressBarId.setVisibility(View.VISIBLE);
        String currentUserId = firebaseUser.getUid();
        DatabaseReference orderRef = databaseReference.child("orders");
//        Intent intent = null;
//        Bundle args = intent.getBundleExtra("productList");
//        ArrayList<CartModel> list = (ArrayList<CartModel>) args.getSerializable("ARRAYLIST");
//        list= (ArrayList<CartModel>) bundle.getSerializable("ARRAYLIST");
        String userId = user.getUserID();
        String name = user.getName();
        String address = binding.addressETM.getEditText().getText().toString();
        String contact = binding.contactETM.getEditText().getText().toString();
        String orderItem = bundle.getString("category");
        String pname=bundle.getString("proName");
        String pprice=bundle.getString("price");
//        String nop=bundle.getString("noOfItem");
//        String ini=bundle.getString("ini");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy");
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()).toString();
        String currentDate = sdf.format(new Date());


        String date = binding.dateETM.getEditText().getText().toString();
        String time = binding.timeETM.getEditText().getText().toString();
        String isPlaced = "Placed";
//        String tP=tottalP.getText().toString();
        int numOfproduct=1;


        DocOrder order = new DocOrder(userId, name, address, contact, orderItem, date, time, isPlaced,pname,pprice,currentDate,currentTime);
        String pushId = orderRef.push().getKey();
        order.setOrderId(pushId);
        orderRef.child("Admin").child("newOrder").child(pushId).setValue(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                orderRef.child("Health Care").child(userId).child(pushId).setValue(order).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){

                        Toast.makeText(this, "Your order Placed!", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(MediAddressActivity.this,OrderDetails.class);
//                        intent.putExtra("address",address);
//                        intent.putExtra("contact",contact);
//                        intent.putExtra("name",name);
//                        intent.putExtra("deliver_Date",date);
//                        intent.putExtra("deliver_Time",time);
//                        intent.putExtra("price",pprice);
//                        intent.putExtra("pname",pname);
//                        intent.putExtra("quantity",nop);
//                        intent.putExtra("orderId",pushId);
//                        intent.putExtra("currentTime",currentTime);
//                        intent.putExtra("currentDate",currentDate);
//                        startActivity(intent);

                        finish();
                        binding.progressBarId.setVisibility(View.GONE);
                    }

                });
            } else {
                Toast.makeText(this, "Please order again!", Toast.LENGTH_SHORT).show();
                binding.progressBarId.setVisibility(View.GONE);
            }
        });
    }


}