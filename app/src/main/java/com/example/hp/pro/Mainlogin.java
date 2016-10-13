package com.example.hp.pro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Mainlogin extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText et1,et2;
    Button b1;
    TextView t1;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);

        et1=(EditText)findViewById(R.id.username);
        et2=(EditText)findViewById(R.id.password);
        b1=(Button)findViewById(R.id.button1);
        t1=(TextView)findViewById(R.id.link_signup);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                userLogin();
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        progressDialog = new ProgressDialog(Mainlogin.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = et1.getText().toString();
        String password = et2.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            et1.setError("enter a valid username");
            valid = false;
        }
        else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et2.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    private void userLogin() {

        final String  username = et1.getText().toString().trim();
       final  String password = et2.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(Mainlogin.this,response,Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Mainlogin.this,"ERROR:"+error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Config.KEY_USERNAME,username);
                map.put(Config.KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, Buttons.class);
        //intent.putExtra(Config.KEY_USERNAME, username);
        startActivity(intent);
        finish();
    }




}



