package com.example.dictionaryjjs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    private EditText email,password;
    private ProgressBar spinner;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);



            login =(Button) findViewById(R.id.login);
            email = (EditText) findViewById(R.id.emailId);
            password = (EditText) findViewById(R.id.password);
            spinner = (ProgressBar)findViewById(R.id.progressBar);
            spinner.setVisibility(View.GONE);

            login.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.login)
            {
                spinner.setVisibility(View.VISIBLE);
                String userEmail = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();
                email.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                login.setVisibility(View.GONE);

                //sending credentials entered to validate to firebase
                mAuth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Intent itype = getIntent();
                        int type = itype.getIntExtra("type",2);
//                        Toast.makeText(MainActivity2.this, ""+type, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity2.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent i;
                        i = new Intent(MainActivity2.this, MainActivityAPI.class);
                        spinner.setVisibility(View.GONE);
                        startActivity(i);
                        finish();


                    }
                    else {
                        Toast.makeText(MainActivity2.this, "login failed", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);
                        email.setVisibility(View.VISIBLE);
                        password.setVisibility(View.VISIBLE);
                        login.setVisibility(View.VISIBLE);
                    }

                });





            }

        }
    }
