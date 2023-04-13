package com.example.dynamictrivial;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import com.example.dynamictrivial.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private EditText txtemail, txtpassoword;
    private Button login_btn;
    FirebaseAuth mAuth;
    String loginemail, loginpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtemail = findViewById(R.id.email_login_edittext);
        txtpassoword = findViewById(R.id.password_login_edittext);
        login_btn = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validatePassword()) {
                    return;
                }

                mAuth.signInWithEmailAndPassword(loginemail,loginpassword).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, StartupActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                );


            }
        });

        TextView loginLink = findViewById(R.id.login_textview);
        loginLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // do something when the TextView is clicked
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validateEmail() {
        loginemail = txtemail.getText().toString().trim();
        if (TextUtils.isEmpty(loginemail)) {
            Toast.makeText(LoginActivity.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
            Toast.makeText(LoginActivity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePassword() {
        loginpassword = txtpassoword.getText().toString().trim();
        if (TextUtils.isEmpty(loginpassword)) {
            Toast.makeText(LoginActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}