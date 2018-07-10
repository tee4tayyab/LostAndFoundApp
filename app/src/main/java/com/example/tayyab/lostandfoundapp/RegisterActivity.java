package com.example.tayyab.lostandfoundapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.InterfaceService.UserClient;
import com.example.tayyab.lostandfoundapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.Retrofit.*;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";
    private FirebaseAuth mAuth;
    EditText edtEmail, edtPassword, edtUserName, edtName;
    Button Register;
    public Boolean verify;

    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputlayoutusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);







        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.etEmail);
        edtPassword = findViewById(R.id.etPassword);
        edtName = findViewById(R.id.etName);
        edtUserName = findViewById(R.id.etUsername);
        inputLayoutName = findViewById(R.id.edtName);
        inputlayoutusername = findViewById(R.id.edtUsername);
        inputLayoutEmail = findViewById(R.id.edtEmail);
        inputLayoutPassword = findViewById(R.id.edtPassword);
//
        edtName.addTextChangedListener(new MyTextWatcher(edtName));
        edtUserName.addTextChangedListener(new MyTextWatcher(edtUserName));
        edtEmail.addTextChangedListener(new MyTextWatcher(edtEmail));
        edtPassword.addTextChangedListener(new MyTextWatcher(edtPassword));


        Register = findViewById(R.id.btnRegister);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              createAccount(edtEmail.getText().toString(), edtPassword.getText().toString());

                User user = new User(edtPassword.getText().toString(),edtUserName.getText().toString(),"",edtEmail.getText().toString(),1);

                sendNetworkRequest(user);



            }
        });


    }

    private void sendNetworkRequest(User user) {


        Retrofit.Builder builder = new Builder()
                .baseUrl("http://192.168.43.170/LostFoundApi/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Log.d("MTAG", "sendNetworkRequest: Successful");

        Retrofit retrofit = builder.build();

        UserClient client = retrofit.create(UserClient.class);

        //????????????????????????????????????????????????????
        Call<User> call = client.CreateUserAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                Log.d(TAG, "onResponse: ");



                //  Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void createAccount(String email, String password) {
        Log.e("MTAG", "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("MTAG", "createAccount: Success!");

                            // update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification();
                            updateUI(user);
                            User userdata = new User(edtPassword.getText().toString(),edtUserName.getText().toString(),"",edtEmail.getText().toString(),1);
                            sendNetworkRequest(userdata);



                        } else {
                            Log.e("MTAG", "createAccount: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void deleteUser() {
        FirebaseUser user = mAuth.getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("MTAG", "User account deleted.");
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        // Disable Verify Email button
//        findViewById(R.id.btn_verify_email).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable Verify Email button
//                        findViewById(R.id.btn_verify_email).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("MTAG", "sendEmailVerification failed!", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
           /* txtStatus.setText("User Email: " + user.getEmail() + "(verified: " + user.isEmailVerified() + ")");
            txtDetail.setText("Firebase User ID: " + user.getUid());

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.layout_signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.btn_verify_email).setEnabled(!user.isEmailVerified());*/
        } else {
            /*txtStatus.setText("Signed Out");
            txtDetail.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.layout_signed_in_buttons).setVisibility(View.GONE);*/
        }
    }


    // Floating Label Animation Code

    private boolean validateName() {
        if (edtName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Enter your Name");
            requestFocus(edtName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUsername() {
        if (edtUserName.getText().toString().trim().isEmpty()) {
            inputlayoutusername.setError("Enter your UserName");
            requestFocus(edtName);
            return false;
        } else {
            inputlayoutusername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String Email = edtEmail.getText().toString().trim();
        if (Email.isEmpty()) {
            inputLayoutEmail.setError("Enter Your Email");
            requestFocus(edtEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError("Enter Password");
            requestFocus(edtPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etName:
                    requestFocus(edtName);
                    validateName();
                    break;
                case R.id.etUsername:
                    requestFocus(edtUserName);
                    validateUsername();
                    break;
                case R.id.etEmail:
                    requestFocus(edtEmail);
                    validateEmail();
                    break;
                case R.id.etPassword:
                    requestFocus(edtPassword);
                    validatePassword();
                    break;
            }
        }


    }


    // GET PUT POST DELETE




}