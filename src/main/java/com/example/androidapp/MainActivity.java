package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
private Button button;
private TextView donthaveacc;
private TextView alreadyhaveacc;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.loginbutton);
        TextView username=findViewById(R.id.username);
        TextView password=findViewById(R.id.password);
        MaterialButton loginbtn=findViewById(R.id.loginbutton);
        donthaveacc=findViewById(R.id.donthaveanaccount);
        alreadyhaveacc=findViewById(R.id.alreadyhaveacc);



        donthaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("badr") && password.getText().toString().equals("badr")){

                    Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_SHORT).show();
                    openActivity2();
                    username.setText("");
                    password.setText("");

                } else if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this,"One of the fields is empty",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }

            }
        });


    }
    public void openActivity2(){
        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);

    }
    public void openRegisterActivity(){
        Intent intent =new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


}