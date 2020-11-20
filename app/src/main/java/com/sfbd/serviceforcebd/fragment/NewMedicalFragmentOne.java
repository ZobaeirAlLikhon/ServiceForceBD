package com.sfbd.serviceforcebd.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;
import com.sfbd.serviceforcebd.activity.SubCatagoryDetails;
import com.sfbd.serviceforcebd.connection.ConnectionManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NewMedicalFragmentOne extends Fragment {

    private MaterialButton send_btn, call_btn;
    private TextView camera_btn;
    private static final int MY_PERMISSION_CONSTANT = 5;
    private TextInputLayout textInputLayout, textInputLayout1, textInputLayout2;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    String med_name, address, contact,cDate,cTime,address_string,image_name,user_name;
    private DatabaseReference dref,dref_one;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Uri filepath;
    private Bitmap photo;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    public NewMedicalFragmentOne() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_medical_frag_one, container, false);
        //initialize button with id
        send_btn = view.findViewById(R.id.sendbtnmed);
        call_btn = view.findViewById(R.id.callbtnmed);

        //camera text initialization
        camera_btn = view.findViewById(R.id.camera_btn);

        //Material TextInputlayout initialization
        textInputLayout = view.findViewById(R.id.medbox);
        textInputLayout1 = view.findViewById(R.id.mc_useraddress);
        textInputLayout2 = view.findViewById(R.id.mc_phone);

        //initialize image view
        imageView = view.findViewById(R.id.imgpres);


        //firebase reference------

        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dref_one = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        dref_one.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //location provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        call_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:01707071417"));
            getContext().startActivity(intent);
        });
        send_btn.setOnClickListener(v -> {
            if(!ConnectionManager.connection(getContext()))
            {

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



                Toast.makeText(getContext(),"No internet",Toast.LENGTH_LONG).show();
            }
            else {
                med_name = textInputLayout.getEditText().getText().toString();
                address = textInputLayout1.getEditText().getText().toString();
                contact = textInputLayout2.getEditText().getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                cTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()).toString();
                cDate = sdf.format(new Date());
                if (med_name.length() == 0) {
                    textInputLayout.setError("Please Enter Your Medicine Name!");
                } else if (address.length() == 0) {
                    textInputLayout1.setError("Please Enter Your Address!");
                } else if (contact.length() == 0 || contact.length() < 11) {
                    textInputLayout2.setError("Please Enter Your Phone Number!");
                } else {
                    dref = FirebaseDatabase.getInstance().getReference().child("orders").child("Medical").child("Medicine Corner");
                    try {
                        uploadImage();
                    } catch (Exception e) {
                        image_name = "user Does not capture prescription";
                    }
                    HashMap<String, Object> medCorner = new HashMap<>();
                    medCorner.put("Image Name", image_name);
                    medCorner.put("Medicine Name", med_name);
                    medCorner.put("Address", address);
                    medCorner.put("Contact Number", contact);
                    medCorner.put("Order Date", cDate);
                    medCorner.put("Order Time", cTime);
                    dref.child(cDate).child(cTime).updateChildren(medCorner);

                    Toast.makeText(getContext(), "Oder Placed. You will be Contacted Shortly!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

            }

        });

        camera_btn.setOnClickListener(v -> {

                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else {
                    askPermission();
                }


        });



        //------------------check location -------------------start here//
        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            getLastLocation();
        } else {
            askPermission();
        }


        return view;
    }







    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);

        }
    }



         //---------------------------location-------------------------------start here
    private void getLastLocation() {

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    address_string = getCompleteAddressString(location.getLatitude(), location.getLongitude());
                    textInputLayout1.getEditText().setText(address_string);

                }

            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }




    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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
            } else {
                //Log.w("My Current loction address", "No Address returned!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
    //-----------------------------location ----------------------finish here



    //---------------upload photo function---------------

    private void uploadImage() {

        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG,100,baos);
        image_name = user_name+"_"+textInputLayout2.getEditText().getText().toString();
        StorageReference ref = storageReference.child("prescriptions/"+image_name);
        byte [] b = baos.toByteArray();
        ref.putBytes(b)
                       .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                           @Override
                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               //do nothing
                           }
                       })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

    }
    //------------upload image ----------- finish here


    private void askPermission() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                ||

                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)

                    ||

                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA))
            {

                Toast.makeText(getContext(), "You should give the permission!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},MY_PERMISSION_CONSTANT);

            }

            /* First time user use this application*/
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA}, MY_PERMISSION_CONSTANT);
            }
        }
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*Permission Granted*/
                getLastLocation();
            }
            else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else {
                /*Permission Denied*/
                Toast.makeText(getContext(), " permission denied, boo! Disable the functionality that depends on this permission.", Toast.LENGTH_SHORT).show();

            }
        }
    }




}

