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

import com.bpom.dsras.MainActivity;
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
public class ChangePasswordFragment extends Fragment {

    private final static String TAG = ChangePasswordFragment.class.getSimpleName();
    private Context context;
    private View view;
    private ImageView iv_current_changepassword, iv_new_changepassword,
                        iv_confirm_changepassword;
    private EditText ed_current_changepassword, ed_new_changepassword,
                    ed_confirm_changepassword;
    private Button btn_submit_changepassword;
    private String currentpass, newpass, confirmpass;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private HashMap<String, String> param_changepass = new HashMap<>();
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};

    public ChangePasswordFragment() {

    }

    @SuppressLint("ValidFragment")
    public ChangePasswordFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_changepassword, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.iv_current_changepassword = (ImageView) view.findViewById(R.id.iv_current_changepassword);
        this.iv_new_changepassword = (ImageView) view.findViewById(R.id.iv_new_changepassword);
        this.iv_confirm_changepassword = (ImageView) view.findViewById(R.id.iv_confirm_changepassword);
        this.ed_current_changepassword = (EditText) view.findViewById(R.id.ed_current_changepassword);
        this.ed_new_changepassword = (EditText) view.findViewById(R.id.ed_new_changepassword);
        this.ed_confirm_changepassword = (EditText) view.findViewById(R.id.ed_confirm_changepassword);
        this.btn_submit_changepassword = (Button) view.findViewById(R.id.btn_submit_changepassword);

        this.iv_current_changepassword.setImageResource(R.drawable.icon_current);
        this.iv_current_changepassword.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        this.iv_new_changepassword.setImageResource(R.drawable.icon_newpass);
        this.iv_new_changepassword.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);
        this.iv_confirm_changepassword.setImageResource(R.drawable.icon_confirm);
        this.iv_confirm_changepassword.setColorFilter(getResources().getColor(R.color.colorCustomGreen), PorterDuff.Mode.SRC_ATOP);

        this.btn_submit_changepassword.setOnClickListener(new ActionSavePassword());
    }

    private void obtainObtainChangePasswordResponse() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseChangePasswordResponse(msg);
            }

        };
        doNetworkService();
    }

    private void doNetworkService() {
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("params", this.param_changepass);
        networkIntent.putExtra("type", "post");
        networkIntent.putExtra("mode", "changepass");
        networkIntent.putExtra("url", ConstantAPI.getLoginURL());
        this.context.startService(networkIntent);
    }

    private void parseChangePasswordResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            String result = Utility.ParserJSONUtility.getLoginResultFromJSON(this.context, this.stringResponse[0], this.newpass);
            Log.d(TAG, "result " + result);
            if (result.equals("OK")) {
                SharedPreferencesProvider.getInstance().setPassword(this.context, this.newpass);
                ShowAlertDialogChangePassword();
            }
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void ShowAlertDialogChangePassword() {
        this.alertDialogBuilder = new AlertDialog.Builder(this.context)
                .setCancelable(false)
                .setMessage("Your password has been saved!")
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

    private void doCheckAllFields(String currentpass, String newpass, String confirmpass) {
        if (currentpass.equals("") && newpass.equals("") && confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill all Fields", Toast.LENGTH_SHORT).show();
        else if (!currentpass.equals("") && newpass.equals("") && confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill New and Confirm Passowrd Fields", Toast.LENGTH_SHORT).show();
        else if (currentpass.equals("") && !newpass.equals("") && confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill Current and Confirm Password Fields", Toast.LENGTH_SHORT).show();
        else if (currentpass.equals("") && newpass.equals("") && !confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill Current and New Password Fields", Toast.LENGTH_SHORT).show();
        else if (!currentpass.equals("") && !newpass.equals("") && confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill Confirm Password Fields", Toast.LENGTH_SHORT).show();
        else if (!currentpass.equals("") && newpass.equals("") && !confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill New Password Fields", Toast.LENGTH_SHORT).show();
        else if (currentpass.equals("") && !newpass.equals("") && !confirmpass.equals(""))
            Toast.makeText(this.context, "Please fill Current Password Fields", Toast.LENGTH_SHORT).show();
        else {
            doCheckNewAndConfirmPassword(currentpass, newpass, confirmpass);
        }
    }

    private void doCheckNewAndConfirmPassword(String currentpass, String newpass, String confirmpass) {
        if (newpass.toLowerCase().equals(confirmpass.toLowerCase())) {
            this.param_changepass.put("old_pass", currentpass);
            this.param_changepass.put("new_pass", newpass);
            this.param_changepass.put("api_key", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            this.param_changepass.put("email", SharedPreferencesProvider.getInstance().getEmail(this.context));
            this.param_changepass.put("password", SharedPreferencesProvider.getInstance().getPassword(this.context));
            if (Utility.ConnectionUtility.isNetworkConnected(this.context))
                obtainObtainChangePasswordResponse();
        }else{
            Toast.makeText(this.context, "Confirm and New Password doesn't match", Toast.LENGTH_SHORT).show();
        }

    }

    private class ActionSavePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            currentpass = ed_current_changepassword.getText().toString();
            newpass = ed_new_changepassword.getText().toString();
            confirmpass = ed_confirm_changepassword.getText().toString();

            doCheckAllFields(currentpass, newpass, confirmpass);
        }
    }

}
