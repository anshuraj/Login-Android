package com.anshu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by deadlydespo on 20-02-2016.
 */
public class ProfileActivity extends AppCompatActivity {

    TextView welcome;
    Button btnLogout;
    SessionManager session;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        welcome = (TextView) findViewById(R.id.welcome);
        btnLogout = (Button) findViewById(R.id.logout);

        session = new SessionManager(getApplicationContext());
        if(!session.isLoggedIn()){
            logoutUser();
        }
        String user = session.getUserDetails().getString("User", null);
        welcome.setText("Welcome " + user);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

    }
    private void logoutUser(){
        session.setLogin(false, null);

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
