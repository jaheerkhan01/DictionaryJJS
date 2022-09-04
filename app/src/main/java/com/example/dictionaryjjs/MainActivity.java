package com.example.dictionaryjjs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name,password,confirmPassword,email;
    Button registerButton,button;
    FirebaseDatabase Firebase;
    DatabaseReference referenceToAddUser;
    FirebaseAuth mAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase firebasedatabase=FirebaseDatabase.getInstance();
       referenceToAddUser=firebasedatabase.getReference();
        email=findViewById(R.id.Email);
        name=findViewById(R.id.Name);
        password=findViewById(R.id.Password);
        confirmPassword=findViewById(R.id.ConfirmPassword);
        registerButton=findViewById(R.id.Register);

        button=findViewById(R.id.button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email.getText().toString();
                String NAME = name.getText().toString();
                String PASSWORD = password.getText().toString();
                String CONFIRM_PASSWORD = confirmPassword.getText().toString();
                if(PASSWORD.equals(CONFIRM_PASSWORD) && EMAIL.contains("nitk.edu.in")) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Student s = new Student(NAME, EMAIL, PASSWORD);
                                    Firebase = FirebaseDatabase.getInstance();
                                    referenceToAddUser.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(s)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                        Toast.makeText(MainActivity.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                                                    else
                                                        Toast.makeText(MainActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                } else {
                                    if (PASSWORD.length() < 6) {
                                        Toast.makeText(MainActivity.this, "The password should have at least 6 characters", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
            }
                    else {
                    if(!EMAIL.contains("nitk.edu.in")){
                        Toast.makeText(MainActivity.this, "Please enter the NITK Email ID", Toast.LENGTH_SHORT).show();
                    }
                    else if(!PASSWORD.equals(CONFIRM_PASSWORD)){
                        Toast.makeText(MainActivity.this, "Passwords don't match!!!", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Came here", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });

    }

}
