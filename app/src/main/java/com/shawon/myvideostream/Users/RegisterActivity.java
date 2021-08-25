package com.shawon.myvideostream.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shawon.myvideostream.Dashboard;
import com.shawon.myvideostream.R;

public class RegisterActivity extends AppCompatActivity {
    ///////////////////// Varriable /////////////////////

    EditText regEmail, regPassword;
    Button regSubmit;
    TextView reg_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ///////////////// Hooks /////////////////

        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regSubmit = findViewById(R.id.reg_submit);
        reg_have_account = findViewById(R.id.reg_have_account);


        reg_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerproccess();
            }
        });


    }

    private boolean emailValidation() {
        String userEmail = regEmail.getText().toString().trim();
        if (userEmail.isEmpty()) {
            regEmail.setError("Email is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean passwordValidation() {
        String userPassword = regPassword.getText().toString();
        if (userPassword.isEmpty()) {
            regPassword.setError("Password is required");
            return false;
        } else {
            return true;
        }
    }

    private void registerproccess() {

        if (!emailValidation() | !passwordValidation()) {
            return;
        }

        String userEmail = regEmail.getText().toString().trim();
        String userPassword = regPassword.getText().toString();


        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registerd", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}