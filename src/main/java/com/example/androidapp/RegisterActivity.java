package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

      EditText confirm_password_txt,email_txt,password_txt;
      Button register_btn;
      TextView gotologintext;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_btn=findViewById(R.id.registerbutton);
        confirm_password_txt=findViewById(R.id.confirmpwd);
      //  firstname_txt=findViewById(R.id.firstname);
      //  lastname_txt=findViewById(R.id.lastname);
        email_txt=findViewById(R.id.email);
        gotologintext=findViewById(R.id.gobacktologin);
        password_txt=findViewById(R.id.password);
        register_btn.setOnClickListener(v -> createacc());
        gotologintext.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this,LoginActivity.class)));
    }
    public void createacc(){
        String email=email_txt.getText().toString();
        String password=password_txt.getText().toString();
        String confirmpwd=confirm_password_txt.getText().toString();
      //  String firstname=firstname_txt.getText().toString();
      //  String lastname=lastname_txt.getText().toString();
        boolean confirmed=confirmdata(email,password,confirmpwd);
        if(!confirmed){
            return;
        }
        createaccinfirb(email,password);
    }
    boolean confirmdata(String email,String password,String confirmpwd){
      /*  if(!Pattern.matches("[a-zA-Z]+",firstname)){
         firstname_txt.setError("invalid firstname");
         return false;
        }
        else if(!Pattern.matches("[a-zA-Z]+",lastname)){
            lastname_txt.setError("invalid lastname");
            return false;
        }*/
      if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        email_txt.setError("invalid email format");
        return false;
     }
     else if(password.length()<8){
         password_txt.setError("password must contain more than 8 digits");
         return false;
     }
     else if(!password.equals(confirmpwd)){
         confirm_password_txt.setError("the password doesn't match");
         return false;
     }
     return true;
    }
    public void createaccinfirb(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"account created successfully",Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}