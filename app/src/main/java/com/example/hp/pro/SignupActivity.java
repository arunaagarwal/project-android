package com.example.hp.pro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText editTextConfirmOtp;


    private Button buttonConfirm;
    private RequestQueue requestQueue;


    EditText et1,et2,et3,et4;
    Button b1;
    TextView t1;

    private String username;
    private String email;
    private String password;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestQueue = Volley.newRequestQueue(this);

        et1=(EditText)findViewById(R.id.username);
        et2=(EditText)findViewById(R.id.email);
        et3=(EditText)findViewById(R.id.password);
        et4=(EditText)findViewById(R.id.phone);
        b1=(Button)findViewById(R.id.btn_signup);
        t1=(TextView)findViewById(R.id.link_login);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
                register();

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        b1.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_PopupOverlay);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = et1.getText().toString();
        String email = et2.getText().toString();
        String password = et3.getText().toString();
        String phone = et4.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        b1.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        b1.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = et1.getText().toString();
        String email = et2.getText().toString();
        String password = et3.getText().toString();
        String phone = et4.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            et1.setError("at least 3 characters");
            valid = false;
        } else {
            et1.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et2.setError("enter a valid email address");
            valid = false;
        } else {
            et2.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et3.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et3.setError(null);
        }

        return valid;

    }

    private void confirmOtp() throws JSONException {

        LayoutInflater li = LayoutInflater.from(this);

        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);


        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        alert.setView(confirmDialog);


        final AlertDialog alertDialog = alert.create();


        alertDialog.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();


                final ProgressDialog loading = ProgressDialog.show(SignupActivity.this, "Authenticating", "Please wait while we check the entered code", false, false);


                final String otp = editTextConfirmOtp.getText().toString().trim();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CONFIRM_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("success")) {

                                    loading.dismiss();


                                    startActivity(new Intent(SignupActivity.this, Buttons.class));
                                } else {

                                    Toast.makeText(SignupActivity.this, "Wrong OTP Please Try Again", Toast.LENGTH_LONG).show();
                                    try {

                                        confirmOtp();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alertDialog.dismiss();
                                Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put(Config.KEY_OTP, otp);
                        params.put(Config.KEY_USERNAME, username);
                        return params;
                    }
                };


                requestQueue.add(stringRequest);
            }
        });
    }



    private void register() {


        final ProgressDialog loading = ProgressDialog.show(this, "Registering", "Please wait...", false, false);



        username = et1.getText().toString().trim();
        email = et2.getText().toString().trim();
        password = et3.getText().toString().trim();
        phone = et4.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {

                            JSONObject jsonResponse = new JSONObject(response);


                            if(jsonResponse.getString(Config.TAG_RESPONSE).equalsIgnoreCase("Success")){

                                confirmOtp();
                            }else{

                                Toast.makeText(SignupActivity.this, "Username or Phone number already registered", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_Email, email);
                params.put(Config.KEY_PASSWORD, password);
                params.put(Config.KEY_PHONE, phone);
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }





}


