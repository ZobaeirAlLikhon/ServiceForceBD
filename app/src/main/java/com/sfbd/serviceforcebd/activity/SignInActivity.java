package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.sfbd.serviceforcebd.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private ActivitySignInBinding binding;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private TextView textView;

    CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FacebookSdk.sdkInitialize(SignInActivity.this);
//      foeget password
        binding.forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPass();
            }
        });
        mAuth = FirebaseAuth.getInstance();
//        log in with google and facebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = binding.loginButton;
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            public void onError(FacebookException error) {


            }
        });
    }
    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user !=null)
        {
            Intent intent=new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(SignInActivity.this, "Sign in contunue....",
                    Toast.LENGTH_SHORT).show();
        }

    }


    private void forgetPass() {
        Intent intent=new Intent(this,ResetPass.class);
        startActivity(intent);
    }

    public void startSignUpActivity(View view) {
        Log.d(TAG, "startSignUpActivity: started");
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private boolean validateEmail() {
        String email = binding.emailET.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.emailET.setError("Field can't be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailET.setError("Please enter a valid email address!");
            return false;
        } else {
            binding.emailET.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = binding.passwordET.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            binding.passwordET.setError("Field can't be empty!");
            return false;
        } else if (password.length() < 6) {
            binding.passwordET.setError("Password must have at least 6 characters!");
            return false;
        } else {
            binding.passwordET.setError(null);
            return true;
        }
    }

    public void confirmSignIn(View view) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        binding.pleaseWaitLA.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        String email = binding.emailET.getEditText().getText().toString().trim();
        String password = binding.passwordET.getEditText().getText().toString().trim();


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    binding.pleaseWaitLA.setVisibility(View.GONE);
                } else {
                    Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    binding.pleaseWaitLA.setVisibility(View.GONE);
                }
            }
        });

       /* startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();*/
    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            sentUserToMainActivity();
        }
    }

    private void sentUserToMainActivity() {

        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }*/
}
