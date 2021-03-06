package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rey.material.widget.SnackBar;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.connection.ConnectionManager;
import com.sfbd.serviceforcebd.databinding.ActivityMainBinding;
import com.sfbd.serviceforcebd.fragment.CartFragment;
import com.sfbd.serviceforcebd.fragment.HomeFragment;
import com.sfbd.serviceforcebd.fragment.MoreFragment;
import com.sfbd.serviceforcebd.fragment.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private Fragment selectedFragment;
    private FirebaseAuth mAuth;
    View v;
    private int RECQUST=11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);






        /*binding.carouselView.setPageCount(sampleImages.length);
        binding.carouselView.setImageListener(imageListener);*/
    }
    public void onBackPressed() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Warning");
        alertdialog.setMessage("Are you sure, you Want to exit this apps???");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity.super.onBackPressed();
                finishAffinity();
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        AlertDialog alert=alertdialog.create();
        alertdialog.show();

    }




  /*  ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setCropToPadding(true);
            imageView.setImageResource(sampleImages[position]);
        }
    };*/

    private void initBottomNavigation() {
        Log.d(TAG, "initBottomNavigation: started");


        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeBN:
                        selectedFragment = new HomeFragment();
                        initFragment(selectedFragment);
                        break;

                    case R.id.orderBN:
                        selectedFragment = new OrderFragment();
                        initFragment(selectedFragment);
                        break;

                    case R.id.chatBN:
                        /*selectedFragment = new ChatFragment();
                        initFragment(selectedFragment);*/

                       /* Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + "313356632862434"));
                        startActivity(i);*/

                        /*Intent intent;
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.messenger.com/t/{313356632862434}"));
                        startActivity(intent);*/

                        String messengerUrl ;
                        if (isMessengerAppInstalled()) {
                            Toast.makeText(MainActivity.this, "messenger is installed , open app bubble", Toast.LENGTH_SHORT).show();
                            messengerUrl = "fb-messenger://user/313356632862434/";
                        } else {
                            Toast.makeText(MainActivity.this, "messenger is not installed , open messenger in browser", Toast.LENGTH_SHORT).show();
                            messengerUrl = "https://www.messenger.com/t/313356632862434/";
                        }
                        Intent messengerIntent = new Intent(Intent.ACTION_VIEW);
                        messengerIntent.setData(Uri.parse(messengerUrl));
                        startActivity(messengerIntent);

                        break;
                    case R.id.cartBN:
                        selectedFragment = new CartFragment();
                        initFragment(selectedFragment);
                        break;

                    case R.id.moreBN:
                        selectedFragment = new MoreFragment();
                        initFragment(selectedFragment);
                        break;

                }

                return true;
            }
        });
    }

    private void initFragment(Fragment selectedFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFragmentContainer, selectedFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
// Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask=appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,RECQUST);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null){
                    sentUserToLoginActivity();
                }else {

                    initBottomNavigation();
                    selectedFragment = new HomeFragment();
                    initFragment(selectedFragment);
                }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RECQUST)
        {
            Toast.makeText(MainActivity.this,"Start Downloade",Toast.LENGTH_SHORT).show();
            if(resultCode!=RESULT_OK)
            {
                Toast.makeText(MainActivity.this,"Faild Downloade",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void sentUserToLoginActivity() {

        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public boolean isMessengerAppInstalled() {
        try {
            getApplicationContext().getPackageManager().getApplicationInfo("com.facebook.orca", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


}
