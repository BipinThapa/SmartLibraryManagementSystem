package com.example.login.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.Volley;
import com.example.login.R;
import com.example.login.app.AppConfig;
import com.example.login.app.AppController;
import com.example.login.helper.SessionManager;
import com.example.login.helper.SQLiteHandler;
import com.example.login.MainActivity;

import android.widget.Spinner;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    Spinner spinnerCourse;
    ArrayList<String> CourseList = new ArrayList<>();
    ArrayAdapter<String> courseAdapter;
    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requestQueue = Volley.newRequestQueue(this);
        spinnerCourse = findViewById(R.id.spinnerCourses);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                AppConfig.url_Courses, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("courses");
                    CourseList.add(0, "Select a Course");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String courseName = jsonObject.optString("title");
                        CourseList.add(courseName);
                        courseAdapter = new ArrayAdapter<>(RegisterActivity.this,
                                android.R.layout.simple_spinner_item, CourseList);
                        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCourse.setAdapter(courseAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        inputFirstName = (EditText) findViewById(R.id.firstname);
        inputLastName = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String firstName = inputFirstName.getText().toString().trim();
                String lastName = inputLastName.getText().toString().trim();
                String course=  spinnerCourse.getSelectedItem().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                Toast.makeText(getApplicationContext(),firstName+":"+lastName+":"+course+":"+email+":"+password, Toast.LENGTH_LONG)
                        .show();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !course.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(firstName,lastName,course, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String firstname,final String lastname,final String course_id, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String sid = jObj.getString("sid");
                        JSONObject user = jObj.getJSONObject("user");
                        String firstname = user.getString("firstname");
                        String lastname = user.getString("lastname");
                        String course_id = user.getString("course_id");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(firstname,lastname,course_id, email, sid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("course_id", course_id);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };
        requestQueue.add(strReq);
        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}