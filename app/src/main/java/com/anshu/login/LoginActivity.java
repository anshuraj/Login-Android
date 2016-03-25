package com.anshu.login;

import com.anshu.login.SessionManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    Button BtnLogin, BtnSignup;
    EditText login_edtxt, pass_edtxt;
    String username, password;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        BtnLogin = (Button) findViewById(R.id.login);
        BtnSignup = (Button) findViewById(R.id.signup);
        login_edtxt = (EditText) findViewById(R.id.login_username);
        pass_edtxt = (EditText) findViewById(R.id.login_password);

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to profile activity
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = login_edtxt.getText().toString();
                password = pass_edtxt.getText().toString();


                if(username.isEmpty() && password.isEmpty()){
                    Snackbar.make(findViewById(R.id.snackbarPosition), "Enter credentials", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Enter credentials", Toast.LENGTH_SHORT).show();
                    //Log.d("In if UNAME, PASS", username + " " + password);
                }
                else{
                    StringRequest jsonReq = new StringRequest(Request.Method.POST,
                            appConfig.URL_LOGIN, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "Login Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");

                                if(!error) {
                                    session.setLogin(true, username);
                                    Toast.makeText(getApplicationContext(), "Welcome " + jObj.getString("name"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Error: " + jObj.getString("msg"), Toast.LENGTH_SHORT).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", "Login Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Error: No Response", Toast.LENGTH_LONG).show();
                            VolleyLog.d("Response:%n %s", error.getMessage());

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", username);
                            params.put("password", password);

                            return params;
                        }
                    };

                    VolleySingleton.getInstance().addToRequestQueue(jsonReq, "jsonReq");
                }

            }
        });

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
    }



}
