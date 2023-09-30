package ru.mirea.vorobev.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import ru.mirea.vorobev.mireaproject.databinding.FragmentAuthentificationBinding;


public class FirebaseAuthentication extends AppCompatActivity {
    private FragmentAuthentificationBinding binding;
    private static final String TAG = FirebaseAuth.class.getSimpleName();
    private FirebaseAuth LogIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAuthentificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LogIn = FirebaseAuth.getInstance();

        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.fieldEmail.getText().toString();
                String password = binding.fieldPassword.getText().toString();
                signIn(email, password);

            }
        });
        binding.emailCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.fieldEmail.getText().toString();
                String password = binding.fieldPassword.getText().toString();
                createAccount(email, password);
            }
        });

        binding.androidIdSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithAndroidId();
            }
        });
        binding.createAccountInID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountInID();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = LogIn.getCurrentUser();
        updateUI(currentUser);
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        LogIn.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = LogIn.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }
    private void createAccountInID() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String email = androidId + "@mail.ru";
        String password = androidId;

        Log.d(TAG, "createAccount:" + email);

        LogIn.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = LogIn.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void signInWithAndroidId() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String email = androidId + "@mail.ru"; // Создаем адрес электронной почты на основе ANDROID_ID
        String password = androidId; // Используем ANDROID_ID как пароль

        Log.d(TAG, "signInWithAndroidId:" + email);
        LogIn.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithAndroidId:success");
                            FirebaseUser user = LogIn.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithAndroidId:failure", task.getException());
                            Toast.makeText(FirebaseAuthentication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        LogIn.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = LogIn.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(FirebaseAuthentication.this, "Authentication  failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            binding.status.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = binding.fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.fieldEmail.setError("Required.");
            valid = false;
        } else {
            binding.fieldEmail.setError(null);
        }

        String password = binding.fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            binding.fieldPassword.setError("Required.");
            valid = false;
        } else {
            binding.fieldPassword.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            binding.status.setText(getString(R.string.emailpassword_status_fmt,user.getEmail(), user.isEmailVerified()));
            binding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.emailPasswordButtons.setVisibility(View.GONE);
            binding.emailPasswordFields.setVisibility(View.GONE);
            binding.signedInButtons.setVisibility(View.VISIBLE);
            Intent intent = new Intent(FirebaseAuthentication.this, MainActivity.class);
            startActivity(intent);
        } else {
            binding.status.setText(R.string.signed_out);
            binding.detail.setText(null);
            binding.emailPasswordButtons.setVisibility(View.VISIBLE);
            binding.emailPasswordFields.setVisibility(View.VISIBLE);
            binding.signedInButtons.setVisibility(View.GONE);
        }
    }
}


