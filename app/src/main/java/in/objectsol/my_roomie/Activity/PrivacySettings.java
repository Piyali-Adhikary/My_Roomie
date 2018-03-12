package in.objectsol.my_roomie.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_Types_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by suvadip on 5/3/18.
 */
public class PrivacySettings extends AppCompatActivity implements IJSONParseListener{

    ImageView iv_back_student_privacy_settings,img_privacy_settings_attendance,img_privacy_settings_checkin_out;
    Boolean isAttendance=true, isCheckedIN=true;

    int doPrivacySettingsStatusChange=610;
    int getPrivacySettingsStatusList=614;

    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String privacy_type="";
    String status="";
    ArrayList<HashMap<String,String>> arrayList_privacy_settings;
    private static final String Privacy_Settings_Status_Change_URL = "http://174.136.1.35/dev/myroomie/student/privacySettingsStatusChange/";
    private static final String Privacy_Settings_Status_List_URL = "http://174.136.1.35/dev/myroomie/student/privacySettingsStatusList/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.privacy_settings);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        arrayList_privacy_settings=new ArrayList<>();
        iv_back_student_privacy_settings=(ImageView)findViewById(R.id.iv_back_student_privacy_settings);
        img_privacy_settings_attendance=(ImageView)findViewById(R.id.img_privacy_settings_attendance);
        img_privacy_settings_checkin_out=(ImageView)findViewById(R.id.img_privacy_settings_checkin_out);




        if (Utils.isNetworkAvailable(PrivacySettings.this)) {

            getPrivacySettingsStatusList();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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



        iv_back_student_privacy_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(PrivacySettings.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        img_privacy_settings_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                privacy_type="attendance";
                if(isAttendance){
                    img_privacy_settings_attendance.setImageResource(R.mipmap.hide);
                    isAttendance=false;
                    status="hide";

                    if (Utils.isNetworkAvailable(PrivacySettings.this)) {

                        doPermissionRequest();

                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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
                }else {
                    img_privacy_settings_attendance.setImageResource(R.mipmap.show);
                    isAttendance=true;
                    status="show";
                    if (Utils.isNetworkAvailable(PrivacySettings.this)) {

                        doPermissionRequest();

                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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

            }
        });

        img_privacy_settings_checkin_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                privacy_type="checkincheckout";
                if(isCheckedIN){
                    img_privacy_settings_checkin_out.setImageResource(R.mipmap.hide);
                    isCheckedIN=false;
                    status="hide";
                    if (Utils.isNetworkAvailable(PrivacySettings.this)) {

                        doPermissionRequest();

                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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
                }else {
                    img_privacy_settings_checkin_out.setImageResource(R.mipmap.show);
                    isCheckedIN=true;
                    status="show";
                    if (Utils.isNetworkAvailable(PrivacySettings.this)) {

                        doPermissionRequest();

                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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

            }
        });
    }

    void getPrivacySettingsStatusList() {
        JSONRequestResponse mResponse = new JSONRequestResponse(PrivacySettings.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(PrivacySettings.this);
        ShowProgressDilog(PrivacySettings.this);
        mResponse.getResponse(Request.Method.POST, Privacy_Settings_Status_List_URL, getPrivacySettingsStatusList, PrivacySettings.this, parms, false);
    }

    void doPermissionRequest() {
        JSONRequestResponse mResponse = new JSONRequestResponse(PrivacySettings.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("privacy_type", privacy_type);
        parms.putString("status", status);

        MyVolley.init(PrivacySettings.this);
        ShowProgressDilog(PrivacySettings.this);
        mResponse.getResponse(Request.Method.POST, Privacy_Settings_Status_Change_URL, doPrivacySettingsStatusChange, PrivacySettings.this, parms, false);
    }


    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(PrivacySettings.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PrivacySettings.this);
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

        DismissProgress(PrivacySettings.this);

        if (requestCode == doPrivacySettingsStatusChange) {
            System.out.println("Response for doPrivacySettingsStatusChange------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(this, response.getString("result"), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Student_Permission_Request.this, MainActivity.class);
//                    startActivity(intent);

                } else {
                    Toast.makeText(PrivacySettings.this, response.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }
        }else if (requestCode == getPrivacySettingsStatusList) {
            System.out.println("Response for getPrivacySettingsStatusList------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    JSONArray result_array=response.getJSONArray("result");
                    HashMap<String,String> stringHashMap;
                    for(int i=0; i<result_array.length(); i++){
                        JSONObject jsonObject=result_array.getJSONObject(i);

                        stringHashMap=new HashMap<>();

                        stringHashMap.put("privacy_type",jsonObject.getString("privacy_type"));
                        stringHashMap.put("status",jsonObject.getString("status"));

                        arrayList_privacy_settings.add(stringHashMap);
                    }
                    if(arrayList_privacy_settings.size()>0){
                        for(int i=0;i<arrayList_privacy_settings.size();i++){
                            if(arrayList_privacy_settings.get(i).get("privacy_type").equalsIgnoreCase("checkincheckout")){

                                if(arrayList_privacy_settings.get(i).get("status").equalsIgnoreCase("hide")){
                                    img_privacy_settings_checkin_out.setImageResource(R.mipmap.hide);
                                    isCheckedIN=false;
                                }else {
                                    img_privacy_settings_checkin_out.setImageResource(R.mipmap.show);
                                    isCheckedIN=true;
                                }
                            }else if(arrayList_privacy_settings.get(i).get("privacy_type").equalsIgnoreCase("attendance")){

                                if(arrayList_privacy_settings.get(i).get("status").equalsIgnoreCase("hide")){
                                    img_privacy_settings_attendance.setImageResource(R.mipmap.hide);
                                    isAttendance=false;
                                }else {
                                    img_privacy_settings_attendance.setImageResource(R.mipmap.show);
                                    isAttendance=true;
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(PrivacySettings.this, response.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(PrivacySettings.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(PrivacySettings.this);
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
        // super.onBackPressed();

        Intent intent=new Intent(PrivacySettings.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
