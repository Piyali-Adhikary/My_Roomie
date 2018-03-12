package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 27/02/18.
 */

public class Login extends Activity implements IJSONParseListener{

    TextInputEditText enter_mobile_number;
    TextView login_submit;
    public static int getdoUserLogin=600;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;

    public static final String Login_URL = "http://174.136.1.35/dev/myroomie/account/doUserLogin/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);

        enter_mobile_number=(TextInputEditText) findViewById(R.id.tiet_mobile_number);
        login_submit=(TextView) findViewById(R.id.tv_login);

        if(sharedPreferences.getString("auth_token","").equalsIgnoreCase("")){

        }else {
            Intent intent=new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doUserLogin();

                if (Utils.isNetworkAvailable(Login.this)) {

                    if(enter_mobile_number.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(Login.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                    }else {
                        doUserLogin();
                    }
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
                    alertDialogBuilder.setTitle("No Network Available");
                    alertDialogBuilder.setMessage("Please Turn On Your Internet Connection");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }



    void doUserLogin() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Login.this);
        Bundle parms = new Bundle();
        parms.putString("mobile_number", enter_mobile_number.getText().toString());

        MyVolley.init(Login.this);
        ShowProgressDilog(Login.this);
        mResponse.getResponse(Request.Method.POST, Login_URL, getdoUserLogin, Login.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Login.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
        alertDialogBuilder.setTitle("System Error");
        alertDialogBuilder.setMessage("Sorry Some Error Occurred");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void SuccessResponse(JSONObject response, int requestCode) {

        DismissProgress(Login.this);

        if (requestCode == getdoUserLogin) {
            System.out.println("Response for getdoUserLogin------" + response.toString());

            try {

                //JSONObject obj = response.getJSONObject("response");

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("mobile_number",enter_mobile_number.getText().toString());
                    editor.apply();

                    OTP_Verify.otp=response.getString("result");

                    Intent intent=new Intent(Login.this,OTP_Verify.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Login.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Login.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Login.this);
    }

    void ShowProgressDilog(Context c) {
        pDialog = new ProgressDialog(c);
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setContentView(R.layout.layout_progress_dilog);
    }

    void DismissProgress(Context c) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }
}
