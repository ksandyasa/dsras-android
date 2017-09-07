package com.bpom.dsras.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bpom.dsras.R;
import com.bpom.dsras.service.NetworkConnection;
import com.bpom.dsras.utility.ConstantAPI;
import com.bpom.dsras.utility.SharedPreferencesProvider;
import com.bpom.dsras.utility.Utility;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by apridosandyasa on 8/6/16.
 */
public class ChangeProfileFragment extends Fragment {

    private final static String TAG = ChangeProfileFragment.class.getSimpleName();
    private Context context;
    private View view;
    private ImageView iv_name_changeprofile, iv_email_changeprofile,
                        iv_phone_changeprofile;
    private EditText ed_name_changeprofile, ed_email_changeprofile,
                    ed_phone_changeprofile;
    private Button btn_submit_changeprofile;
    private String name, email, phone;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private HashMap<String, String> param_changeprof = new HashMap<>();
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};

    public ChangeProfileFragment() {

    }

    @SuppressLint("ValidFragment")
    public ChangeProfileFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_changeprofile, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.iv_name_changeprofile = (ImageView) view.findViewById(R.id.iv_name_changeprofile);
        this.iv_email_changeprofile = (ImageView) view.findViewById(R.id.iv_email_changeprofile);
        this.iv_phone_changeprofile = (ImageView) view.findViewById(R.id.iv_phone_changeprofile);
        this.ed_name_changeprofile = (EditText) view.findViewById(R.id.ed_name_changeprofile);
        this.ed_email_changeprofile = (EditText) view.findViewById(R.id.ed_email_changeprofile);
        this.ed_phone_changeprofile = (EditText) view.findViewById(R.id.ed_phone_changeprofile);
        this.btn_submit_changeprofile = (Button) view.findViewById(R.id.btn_submit_changeprofile);

        this.iv_name_changeprofile.setImageResource(R.drawable.icon_user);
        this.iv_name_changeprofile.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        this.iv_email_changeprofile.setImageResource(R.drawable.icon_email);
        this.iv_email_changeprofile.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        this.iv_phone_changeprofile.setImageResource(R.drawable.icon_phone);
        this.iv_phone_changeprofile.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.btn_submit_changeprofile.setOnClickListener(new ActionSaveProfile());
    }

    private void obtainObtainChangeProfileResponse() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseChangeProfileResponse(msg);
            }

        };
        doNetworkService();
    }

    private void doNetworkService() {
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_changeprof);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", "changeprofile");
        networkIntent.putExtra("url", ConstantAPI.getLoginURL());
        this.context.startService(networkIntent);
    }

    private void parseChangeProfileResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLoginResultFromJSON(this.context, this.stringResponse[0], SharedPreferencesProvider.getInstance().getPassword(this.context));
            Log.d(TAG, "result " + result);
            if (result.equals("OK")) {
                ShowAlertDialogChangeProfile();
            }
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void ShowAlertDialogChangeProfile() {
        this.alertDialogBuilder = new AlertDialog.Builder(this.context)
                .setCancelable(false)
                .setMessage("Your profile has been saved!")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        alertDialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    private class ActionSaveProfile implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            name = ed_name_changeprofile.getText().toString();
            email = ed_email_changeprofile.getText().toString();
            phone = ed_phone_changeprofile.getText().toString();

            param_changeprof.put("nama", name);
            param_changeprof.put("email", email);
            param_changeprof.put("telepon", phone);
            param_changeprof.put("email", SharedPreferencesProvider.getInstance().getEmail(context));
            param_changeprof.put("password", SharedPreferencesProvider.getInstance().getPassword(context));

            if (Utility.ConnectionUtility.isNetworkConnected(context))
                obtainObtainChangeProfileResponse();
            else
                Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }
}
