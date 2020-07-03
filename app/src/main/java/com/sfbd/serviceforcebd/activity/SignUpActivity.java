package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.sfbd.serviceforcebd.databinding.ActivitySignUpBinding;
import com.sfbd.serviceforcebd.model.User;
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

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }


    private boolean validateName() {
        String name = binding.nameET.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            binding.nameET.setError("Field can't be empty!");
            return false;
        } else {
            binding.nameET.setError(null);
            return true;
        }
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

    private boolean validatePhone() {
        String email = binding.phoneET.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            binding.phoneET.setError("Field can't be empty!");
            return false;
        } else {
            binding.phoneET.setError(null);
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

    private boolean validateConfirmPassword() {
        String password = binding.passwordET.getEditText().getText().toString().trim();
        String confirmPassword = binding.confirmPasswordET.getEditText().getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordET.setError("Field can't be empty!");
            return false;
        } else if (confirmPassword.length() < 6) {
            binding.confirmPasswordET.setError("Password must have at least 6 characters!");
            return false;
        } else if (!password.equals(confirmPassword)) {
            binding.confirmPasswordET.setError("Password are not matching!");
            return false;
        } else {
            binding.confirmPasswordET.setError(null);
            return true;
        }
    }

    public void confirmSignUp(View view) {
        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        binding.pleaseWaitLA.setVisibility(View.VISIBLE);

        String name = binding.nameET.getEditText().getText().toString().trim();
        String email = binding.emailET.getEditText().getText().toString().trim();
        String phone = binding.phoneET.getEditText().getText().toString().trim();
        String password = binding.passwordET.getEditText().getText().toString().trim();

        User user = new User(name, email, phone);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        final String pushId = userRef.push().getKey();
        user.setPushID(pushId);

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();
                            String userId = currentUser.getUid();

                            user.setUserID(userId);

                            userRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SignUpActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                        user.updateProfile(profileUpdates);

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                        binding.pleaseWaitLA.setVisibility(View.GONE);
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    /*progressBar.setVisibility(View.GONE);*/
                                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    binding.pleaseWaitLA.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpActivity.this, "This Email have already an account!", Toast.LENGTH_SHORT).show();
                                binding.pleaseWaitLA.setVisibility(View.GONE);
                            }
                            binding.pleaseWaitLA.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initFirebase() {


    }


    public void gotoSignIn(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }
}
