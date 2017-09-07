package com.bpom.dsras;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by apridosandyasa on 7/27/16.
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();
    private Button btn_submit_login;
    private ImageView iv_pass_login, iv_user_login;
    private EditText ed_user_login, ed_pass_login, ed_gcm_login;
    private TextView tv_forgot_login;
    private String user, pass;
    private HashMap<String, String> param_login;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private GoogleCloudMessaging gcm;
    private String regid;
    private String PROJECT_NUMBER = "169046277306";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        this.iv_user_login = (ImageView) findViewById(R.id.iv_user_login);
        this.ed_user_login = (EditText) findViewById(R.id.ed_user_login);
        this.iv_pass_login = (ImageView) findViewById(R.id.iv_pass_login);
        this.ed_pass_login = (EditText) findViewById(R.id.ed_pass_login);
        this.ed_gcm_login = (EditText) findViewById(R.id.ed_gcm_login);
        this.btn_submit_login = (Button) findViewById(R.id.btn_submit_login);
        this.tv_forgot_login = (TextView) findViewById(R.id.tv_forgot_login);

        this.iv_user_login.setImageResource(R.drawable.icon_user);
        this.iv_user_login.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        this.iv_pass_login.setImageResource(R.drawable.icon_pass);
        this.iv_pass_login.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.btn_submit_login.setOnClickListener(new SubmitLogin());
        this.tv_forgot_login.setOnClickListener(new ForgotPassword());

        getRegistrationId();
    }

    private void getRegistrationId() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    msg = gcm.register(PROJECT_NUMBER);
                    regid = msg;
                    Log.i("GCM",  "Device registered, registration ID=" + msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d(TAG, "registrationId " + msg);
                ed_gcm_login.setText(msg);
                if (SharedPreferencesProvider.getInstance().getLogin(LoginActivity.this) == true) {
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    LoginActivity.this.finish();
                }

            }
        }.execute(null, null, null);
    }

    private void checkLoginFields(String user, String pass) {
        if (user.equals("") && pass.equals(""))
            Toast.makeText(LoginActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
        else if (user.equals("") && !pass.equals(""))
            Toast.makeText(LoginActivity.this, "Please fill Password fields!", Toast.LENGTH_SHORT).show();
        else if (!user.equals("") && pass.equals(""))
            Toast.makeText(LoginActivity.this, "Please fill Username fields!", Toast.LENGTH_SHORT).show();
        else{
            this.param_login = new HashMap<>();
            this.param_login.put("email", user);
            this.param_login.put("password", pass);
            this.param_login.put("format", "json");
            this.param_login.put("gcm_key", this.regid);
            if (Utility.ConnectionUtility.isNetworkConnected(LoginActivity.this))
                obtainLoginResponse();
            else
                Toast.makeText(LoginActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtainLoginResponse() {
        this.handler = new Handler(LoginActivity.this.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseLoginResponse(msg);
            }

        };
        doNetworkService();
    }

    private void doNetworkService() {
        Intent networkIntent = new Intent(LoginActivity.this, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_login);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", "login");
        networkIntent.putExtra("url", ConstantAPI.getLoginURL());
        startService(networkIntent);
    }

    private void parseLoginResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLoginResultFromJSON(LoginActivity.this,
                    this.stringResponse[0], this.pass);
            if (result.equals("OK")) {
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                LoginActivity.this.finish();
            }else{
                Toast.makeText(LoginActivity.this, "Failed login to server or you do not have authorize!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {

        }
    }

    private class SubmitLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            user = ed_user_login.getText().toString();
            pass = ed_pass_login.getText().toString();
            checkLoginFields(user, pass);
        }
    }

    private class ForgotPassword implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent forgotIntent = new Intent(LoginActivity.this, ForgotActivity.class);
            startActivity(forgotIntent);
        }
    }

}
