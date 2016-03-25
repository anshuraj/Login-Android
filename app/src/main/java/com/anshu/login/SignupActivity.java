package com.anshu.login;

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

/**
 * Created by deadlydespo on 19-02-2016.
 */
public class SignupActivity extends AppCompatActivity {

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

                if(username.isEmpty() && password.isEmpty() && email.isEmpty() && name.isEmpty()){
                    Snackbar.make(findViewById(R.id.snackbarPosition), "Enter credentials", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Enter credentials", Toast.LENGTH_SHORT).show();
                    //Log.d("In if UNAME, PASS", username + " " + password);
                }
                else{
                    StringRequest jsonReq = new StringRequest(Request.Method.POST,
                            appConfig.URL_SIGNUP, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "Signup Response: " + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                boolean error = jObj.getBoolean("error");

                                if(!error) {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

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
                            Log.e("TAG", "Signup Error: " + error.getMessage());
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
                            params.put("email", email);
                            params.put("name", name);

                            return params;
                        }
                    };

                    VolleySingleton.getInstance().addToRequestQueue(jsonReq, "jsonReq");
                }
            }
        });

        alReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
