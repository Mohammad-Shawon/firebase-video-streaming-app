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
import com.google.firebase.auth.FirebaseUser;
import com.shawon.myvideostream.Dashboard;
import com.shawon.myvideostream.R;

public class LoginActivity extends AppCompatActivity {
    ///////////////////// Varriable //////////////////
    EditText logingEmail, loginPassword;
    Button loginButton;
    TextView logingCreateAccount;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ///////////////////////////// Hooks /////////////////////////

        logingEmail = findViewById(R.id.log_email);
        loginPassword = findViewById(R.id.log_password);
        loginButton = findViewById(R.id.log_button);
        logingCreateAccount = findViewById(R.id.log_create_account);


        logingCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProccess();
            }
        });


    }

    private boolean emailValidation() {
        String userEmail = logingEmail.getText().toString().trim();
        if (userEmail.isEmpty()) {
            logingEmail.setError("Email is required");
            return false;
        } else {
            return true;
        }
    }

    private boolean passwordValidation() {
        String userPassword = loginPassword.getText().toString();
        if (userPassword.isEmpty()) {
            loginPassword.setError("Password is required");
            return false;
        } else {
            return true;
        }
    }

    public void loginProccess() {
        if (!emailValidation() | !passwordValidation()) {
            return;
        }


        String userEmail = logingEmail.getText().toString().trim();
        String userPassword = loginPassword.getText().toString();

        auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}