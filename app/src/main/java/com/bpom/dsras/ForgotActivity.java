package com.bpom.dsras;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AlertDialog;
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
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by apridosandyasa on 8/5/16.
 */
public class ForgotActivity extends AppCompatActivity {

    private final static String TAG = ForgotActivity.class.getSimpleName();
    private ImageView iv_email_forgot;
    private Button btn_submit_forgot;
    private TextView tv_back_forgot;
    private EditText ed_email_forgot;
    private HashMap<String, String> param_forgot = new HashMap<>();
    private String email;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        this.iv_email_forgot = (ImageView) findViewById(R.id.iv_email_forgot);
        this.ed_email_forgot = (EditText) findViewById(R.id.ed_email_forgot);
        this.btn_submit_forgot = (Button) findViewById(R.id.btn_submit_forgot);
        this.tv_back_forgot = (TextView) findViewById(R.id.tv_back_forgot);

        this.iv_email_forgot.setImageResource(R.drawable.icon_user);
        this.iv_email_forgot.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);


        this.btn_submit_forgot.setOnClickListener(new ResetPassword());
        this.tv_back_forgot.setOnClickListener(new BackToLogin());
    }

    private void obtainResetPasswordResponse() {
        this.handler = new Handler(ForgotActivity.this.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseResetPasswordResponse(msg);
            }

        };
        doNetworkService();
    }

    private void doNetworkService() {
        Intent networkIntent = new Intent(ForgotActivity.this, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_forgot);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", "forgot");
        networkIntent.putExtra("url", ConstantAPI.getResetURL());
        startService(networkIntent);
    }

    private void parseResetPasswordResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLogoutResultFromJSON(ForgotActivity.this, this.stringResponse[0]);
            Log.d(TAG, "result " + result);
            if (result.equals("OK"))
                ShowAlertDialogForgot();
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void doCheckEmailFiedls(String email) {
        if (email.equals(""))
            Toast.makeText(ForgotActivity.this, "Please fill Email fields", Toast.LENGTH_SHORT).show();
        else {
            param_forgot.put("email", email);
            if (Utility.ConnectionUtility.isNetworkConnected(ForgotActivity.this))
                obtainResetPasswordResponse();
        }
    }

    private void ShowAlertDialogForgot() {
        this.alertDialogBuilder = new AlertDialog.Builder(ForgotActivity.this)
                .setCancelable(false)
                .setMessage("Your password has been sent to your email!")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        alertDialog.dismiss();
                        ForgotActivity.this.finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    private class ResetPassword implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            email = ed_email_forgot.getText().toString();
            doCheckEmailFiedls(email);
        }
    }

    private class BackToLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ForgotActivity.this.finish();
        }
    }
}
