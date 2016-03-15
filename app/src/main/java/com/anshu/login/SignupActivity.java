package com.anshu.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by deadlydespo on 19-02-2016.
 */
public class SignupActivity extends Activity {

    private String name, email, username, password;
    private EditText name_edtxt, username_edtxt, email_edtxt, password_edtxt;
    Button btnSignin, alReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        name_edtxt = (EditText) findViewById(R.id.signup_name);
        username_edtxt = (EditText) findViewById(R.id.signup_username);
        email_edtxt = (EditText) findViewById(R.id.signup_email);
        password_edtxt = (EditText) findViewById(R.id.signup_password);
        btnSignin = (Button) findViewById(R.id.btn_create_account);
        alReg = (Button) findViewById(R.id.btn_already_registered);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_edtxt.getText().toString().trim();
                username = username_edtxt.getText().toString().trim();
                email = email_edtxt.getText().toString().trim();
                password = password_edtxt.getText().toString().trim();
            }
        });

        alReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
