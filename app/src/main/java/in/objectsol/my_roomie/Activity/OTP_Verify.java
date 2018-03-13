package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static in.objectsol.my_roomie.Activity.Login.Login_URL;
import static in.objectsol.my_roomie.Activity.Login.getdoUserLogin;

/**
 * Created by objsol on 27/02/18.
 */

public class OTP_Verify extends Activity implements IJSONParseListener{

    TextInputEditText enter_otp;
    TextView otp_submit,tv_resend_otp;
    int getverifyUserOtp=601;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    String token="";
    public static String otp="";
    public static ArrayList<HashMap<String,String>> student_data;

    private static final String OTP_Verify_URL = "http://174.136.1.35/dev/myroomie/account/verifyUserOtp/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verify);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        token = FirebaseInstanceId.getInstance().getToken();
        student_data=new ArrayList<HashMap<String, String>>();

        enter_otp=(TextInputEditText) findViewById(R.id.tiet_otp);
        otp_submit=(TextView) findViewById(R.id.tv_otp);
        tv_resend_otp=(TextView) findViewById(R.id.tv_resend_otp);
        tv_resend_otp.setPaintFlags(tv_resend_otp.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        enter_otp.setText(otp);
        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(OTP_Verify.this)) {

                        doUserLogin();

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTP_Verify.this);
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

        otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(OTP_Verify.this)) {

                    if(enter_otp.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(OTP_Verify.this, "Please enter your OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        verifyUserOtp();
                    }

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTP_Verify.this);
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
        JSONRequestResponse mResponse = new JSONRequestResponse(OTP_Verify.this);
        Bundle parms = new Bundle();
        parms.putString("mobile_number", sharedPreferences.getString("mobile_number",""));

        MyVolley.init(OTP_Verify.this);
        ShowProgressDilog(OTP_Verify.this);
        mResponse.getResponse(Request.Method.POST, Login_URL, getdoUserLogin, OTP_Verify.this, parms, false);
    }

    void verifyUserOtp() {
        JSONRequestResponse mResponse = new JSONRequestResponse(OTP_Verify.this);
        Bundle parms = new Bundle();
        parms.putString("mobile_number", sharedPreferences.getString("mobile_number",""));
        parms.putString("otp", enter_otp.getText().toString());
        parms.putString("user_device_id", token);
        parms.putString("user_registration_id", "AAAAAzZJUb0:APA91bGSg2ykUn6imeovxIVBGJOskWDl9NocMS-TE4PYVQ6Nq7iXQSHj158E0dzGhljk8n_BbK5IdPKG9_NTlmYFEN_VjRuhCbTq_EdIScntR6OmdE6BBBMw2DiMQ6kDlrxBY7HdfvD4");



        MyVolley.init(OTP_Verify.this);
        ShowProgressDilog(OTP_Verify.this);
        mResponse.getResponse(Request.Method.POST, OTP_Verify_URL, getverifyUserOtp, OTP_Verify.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(OTP_Verify.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTP_Verify.this);
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

        DismissProgress(OTP_Verify.this);

        if (requestCode == getverifyUserOtp) {
            System.out.println("Response for getverifyUserOtp------" + response.toString());

            try {

                //JSONObject obj = response.getJSONObject("response");

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    JSONObject result=response.getJSONObject("result");
                    if(result.getString("user_type").equalsIgnoreCase("S")){
                        JSONObject student_details=result.getJSONObject("Student");

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("user_type",result.getString("user_type"));
                        editor.putString("student_id",student_details.getString("student_id"));
                        editor.putString("campus_id",student_details.getString("campus_id"));
                        editor.putString("campus_name",result.getString("campus_name"));
                        editor.putString("room_no",student_details.getString("room_no"));
                        editor.putString("bed_no",student_details.getString("bed_no"));
                        editor.putString("dob",student_details.getString("dob"));
                        editor.putString("email",student_details.getString("email"));
                        editor.putString("address",student_details.getString("address"));
                        editor.putString("student_name",student_details.getString("student_name"));
                        editor.putString("student_pic",student_details.getString("student_pic"));
                        editor.putString("mobile_number",student_details.getString("mobile_number"));
                        editor.putString("privacy_permission_status",student_details.getString("privacy_permission_status"));
                        editor.putString("auth_token",student_details.getString("auth_token"));
                        editor.putString("last_login",student_details.getString("last_login"));
                        editor.putString("user_type",student_details.getString("user_type"));
                        editor.apply();

                    }else if(result.getString("user_type").equalsIgnoreCase("P")){
                        JSONObject parent_details=result.getJSONObject("Parent");

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("user_type",result.getString("user_type"));
                        editor.putString("campus_name",result.getString("campus_name"));
                        editor.putString("parent_id",parent_details.getString("parent_id"));
                        editor.putString("user_type",parent_details.getString("user_type"));
                        editor.putString("campus_id",parent_details.getString("campus_id"));
                        editor.putString("parent_name",parent_details.getString("parent_name"));
                        editor.putString("parent_pic",parent_details.getString("parent_pic"));
                        editor.putString("parent_email",parent_details.getString("parent_email"));
                        editor.putString("parent_mobile",parent_details.getString("parent_mobile"));
                        editor.putString("auth_token",parent_details.getString("auth_token"));
                        editor.putString("last_login",parent_details.getString("last_login"));
                        editor.apply();

                        JSONArray student_array= result.getJSONArray("Student");
                        for(int i=0; i<student_array.length();i++){
                            JSONObject student_details=student_array.getJSONObject(i);

                            HashMap<String,String> student_hashmap= new HashMap<>();
                            student_hashmap.put("student_id",student_details.getString("student_id"));
                            student_hashmap.put("user_type",student_details.getString("user_type"));
                            student_hashmap.put("campus_id",student_details.getString("campus_id"));
                            student_hashmap.put("room_no",student_details.getString("room_no"));
                            student_hashmap.put("bed_no",student_details.getString("bed_no"));
                            student_hashmap.put("dob",student_details.getString("dob"));
                            student_hashmap.put("email",student_details.getString("email"));
                            student_hashmap.put("address",student_details.getString("address"));
                            student_hashmap.put("student_name",student_details.getString("student_name"));
                            student_hashmap.put("student_pic",student_details.getString("student_pic"));
                            student_hashmap.put("mobile_number",student_details.getString("mobile_number"));
                            student_hashmap.put("privacy_permission_status",student_details.getString("privacy_permission_status"));
                            student_hashmap.put("auth_token",student_details.getString("auth_token"));
                            student_hashmap.put("last_login",student_details.getString("last_login"));

                            student_data.add(student_hashmap);

                            SharedPreferences.Editor editor1=sharedPreferences_student.edit();
                            editor1.putString("student_id",student_details.getString("student_id"));
                            editor1.putString("campus_id",student_details.getString("campus_id"));
                            editor1.putString("room_no",student_details.getString("room_no"));
                            editor1.putString("bed_no",student_details.getString("bed_no"));
                            editor1.putString("dob",student_details.getString("dob"));
                            editor1.putString("email",student_details.getString("email"));
                            editor1.putString("address",student_details.getString("address"));
                            editor1.putString("student_name",student_details.getString("student_name"));
                            editor1.putString("student_pic",student_details.getString("student_pic"));
                            editor1.putString("mobile_number",student_details.getString("mobile_number"));
                            editor1.putString("privacy_permission_status",student_details.getString("privacy_permission_status"));
                            editor1.putString("auth_token",student_details.getString("auth_token"));
                            editor1.putString("last_login",student_details.getString("last_login"));
                            editor1.putString("user_type",student_details.getString("user_type"));
                            editor1.apply();
                        }


                    }else if(result.getString("user_type").equalsIgnoreCase("W")){

                        JSONObject warden_details=result.getJSONObject("Warden");

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("warden_id",warden_details.getString("warden_id"));
                        editor.putString("user_type",warden_details.getString("user_type"));
                        editor.putString("warden_mobile",warden_details.getString("warden_mobile"));
                        editor.putString("campus_id",warden_details.getString("campus_id"));
                        editor.putString("warden_name",warden_details.getString("warden_name"));
                        editor.putString("warden_image",warden_details.getString("warden_image"));
                        editor.putString("auth_token",warden_details.getString("auth_token"));
                        editor.putString("last_login",warden_details.getString("last_login"));

                        editor.apply();

                    }




                    Intent intent=new Intent(OTP_Verify.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(OTP_Verify.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }else   if (requestCode == getdoUserLogin) {
            System.out.println("Response for getdoUserLogin------" + response.toString());

            try {

                //JSONObject obj = response.getJSONObject("response");

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(OTP_Verify.this, "OTP sent successfully",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(OTP_Verify.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(OTP_Verify.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(OTP_Verify.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(OTP_Verify.this,Login.class);
        startActivity(intent);
        finish();
    }
}
