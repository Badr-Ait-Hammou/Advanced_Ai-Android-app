package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailtxt,passwordtxt;
    Button loginbutton;
    TextView signuptext;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailtxt=findViewById(R.id.emaillogin);
        passwordtxt=findViewById(R.id.passwordlogin);
        loginbutton=findViewById(R.id.loginbutton);
        signuptext=findViewById(R.id.signuptxt);

        loginbutton.setOnClickListener(v -> loginuser());
        signuptext.setOnClickListener((v -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class))));

    }
    void loginuser(){
        String email=emailtxt.getText().toString();
        String password=passwordtxt.getText().toString();
        boolean inserteddatavalidated=validateinserteddata(email,password);
        if(!inserteddatavalidated){

            return;
        }
       loginaccinfirebase(email,password);
    }

    void loginaccinfirebase(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                        emailtxt.setText("");
                        passwordtxt.setText("");
                       // startActivity(new Intent(LoginActivity.this,MainActivity2.class));
                        startActivity(new Intent(LoginActivity.this,AiActivity.class));

                    }else {
                            Toast.makeText(LoginActivity.this, "invalid account or password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }

        });
    }
    boolean validateinserteddata(String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailtxt.setError("invalid email format");
            return false;
        }else if(password.length()<8){
            passwordtxt.setError("password too short");
            return false;
        }
        return true;
    }
}