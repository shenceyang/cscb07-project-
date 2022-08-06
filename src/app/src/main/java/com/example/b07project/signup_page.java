package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_page extends AppCompatActivity {
    private SignupPresenter signupPresenter;
    private DatabaseReference database = FirebaseDatabase.getInstance("https://android-sport-app-default-rtdb.firebaseio.com/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        this.signupPresenter = new SignupPresenter(database);
        TextView username = (TextView) findViewById(R.id.signup_username);
        TextView password = (TextView) findViewById(R.id.signup_password);
        MaterialButton signupbtn = (MaterialButton) findViewById(R.id.createbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO check if empty and set error

                signupPresenter.checkDuplicateAdmin(username.getText().toString(), signup_page.this, new SignupCallback.CheckDuplicateAdminCallback() {
                    @Override
                    public void checkDuplicateAdminCallback() {
                        // Runs if not admin
                        // Check for existing user with same username
                        // Will show Toast if duplicate
                        signupPresenter.checkDuplicateUser(username.getText().toString(), signup_page.this, new SignupCallback.CheckDuplicateUserCallback() {
                            @Override
                            public void checkDuplicateUserCallback() {
                                Customer c = new Customer(username.getText().toString(), password.getText().toString());
                                DatabaseReference d = FirebaseDatabase.getInstance("https://android-sport-app-default-rtdb.firebaseio.com/").getReference();
                                CustomerPresenter customerPresenter = new CustomerPresenter(new CustomerView(), d);
                                customerPresenter.pushCustomer(c);
                            }
                        });
                    }
                });
            }
        });



    }


}