package harico.groupdeallast.login;

/**
 * Created by Windows 7 on 10/14/2015.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import harico.groupdeallast.AppConfig;
import harico.groupdeallast.R;
import harico.groupdeallast.SessionManager;
import harico.groupdeallast.activity.MainActivity;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;
import harico.groupdeallast.signup.RegisterActivity;


/**
 * Created by gowthamandroiddeveloperlogin Chandrasekar on 04-09-2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.getSimpleName();

    Button registerHere;
    Button signIn;
    TextInputLayout emailLogin;
    TextInputLayout passwordLogin;
    EditText etEmailLogin;
    EditText etPasswordLogin;

    private ProgressDialog progressDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         getSupportActionBar().hide();
        //initializing toolbar
//        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolBar);
        //initializing views
        registerHere=(Button)findViewById(R.id.registerhere_button);
        signIn=(Button)findViewById(R.id.signin_button);
        emailLogin=(TextInputLayout)findViewById(R.id.email_loginlayout);
        passwordLogin=(TextInputLayout)findViewById(R.id.password_loginlayout);
        etEmailLogin = (EditText) findViewById(R.id.email_login);
        etPasswordLogin = (EditText) findViewById(R.id.password_login);
        //setting onclick listeners
        registerHere.setOnClickListener(this);
        signIn.setOnClickListener(this);

        //setting progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());


        //If the session is logged in move to MainActivity
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    /**
     * function to verify login details
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);



                    Boolean error = jObj.getBoolean("error");

//                    System.out.println("Your now at if" + error);


                    if (!error) {
                        // User successfully logged in
                        // Create login session
                        session.setLogin(true);

//                        Details details=new Details();
//                        details.setUser_email(jObj.getString("email"));
//                        details.setUser_name(jObj.getString("name"));
                        // Launching  main activity
                        String username=jObj.getString("name");
                        String email=jObj.getString("email");
                        AppController.setString(LoginActivity.this, "email", email);
                        AppController.setString(LoginActivity.this, "name", username);

//                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString("email", email);
//                        editor.putString("name",username);
//                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
//                        String email=details.getUser_email();
//                        String name=details.getUser_name();
//                        intent.putExtra("email", email);
//                        intent.putExtra("name", name);
//                        System.out.println("msg"+email);
//                        System.out.println("msg"+name);
                        startActivity(intent);
                        finish();
                    }

                    else {
                        // login error
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(LoginActivity.this,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    /*
    function to show dialog
     */
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    /*
    function to hide dialog
     */
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //on clicking register button move to Register Activity
            case R.id.registerhere_button:
                Intent intent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            //on clicking the signin button check for the empty field then call the checkLogin() function
            case R.id.signin_button:

                       Details details=new Details();

//                details.setUser_email(etEmailLogin.getText().toString());
//                String email=details.getUser_email();

                String password = etPasswordLogin.getText().toString();
                String email=etEmailLogin.getText().toString();

                // Check for empty data
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // login User
                    checkLogin(email, password);

                } else {
                    // show snackbar to enter credentials
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
        }

    }

}