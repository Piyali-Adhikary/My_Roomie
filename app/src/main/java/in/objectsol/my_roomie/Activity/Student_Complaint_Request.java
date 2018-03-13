package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import in.objectsol.my_roomie.Adapter.Permission_Types_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_Types_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 12/03/18.
 */

public class Student_Complaint_Request extends Activity implements IJSONParseListener{

    Spinner sp_student_complaint_types;
    EditText et_complaint_description;
    Button btn_submit_student_complaint;
    ImageView iv_back_student_complaint;
    ArrayList<Permission_Types_SetGet> arrayList_complaint_reason;

    String date="";
    int getComplaintTypes=616;
    int getComplaintRequest=617;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String spinner_selection="";
    private static final String Complaint_Request_URL = "http://174.136.1.35/dev/myroomie/student/complainForm/";
    private static final String Complaint_Types_URL = "http://174.136.1.35/dev/myroomie/operations/complainTypes/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_complaint);

        arrayList_complaint_reason=new ArrayList<>();
        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);

        sp_student_complaint_types=(Spinner) findViewById(R.id.sp_student_complaint_types);
        et_complaint_description=(EditText) findViewById(R.id.et_complaint_description);
        btn_submit_student_complaint=(Button) findViewById(R.id.btn_submit_student_complaint);
        iv_back_student_complaint=(ImageView) findViewById(R.id.iv_back_student_complaint);

        iv_back_student_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Student_Complaint_Request.this,Student_Complaint_List.class);
                startActivity(intent);
                finish();
            }
        });


        Permission_Types_SetGet permission_types_setGet=new Permission_Types_SetGet();
        permission_types_setGet.setPermission_type("--Select Reasons--");
        arrayList_complaint_reason.add(permission_types_setGet);

        if (Utils.isNetworkAvailable(Student_Complaint_Request.this)) {

            getComplaintTypes();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Complaint_Request.this);
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


        Permission_Types_Adapter arrayAdapter=new Permission_Types_Adapter(Student_Complaint_Request.this,R.layout.spinner_item,arrayList_complaint_reason);
        sp_student_complaint_types.setAdapter(arrayAdapter);
        sp_student_complaint_types.setSelection(0);

        sp_student_complaint_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_selection = arrayList_complaint_reason.get(i).getPermission_type();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit_student_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner_selection.equalsIgnoreCase("--Select Reasons--")){
                    Toast.makeText(Student_Complaint_Request.this, "Please Select Complaint Reason", Toast.LENGTH_SHORT).show();
                }else if(et_complaint_description.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Student_Complaint_Request.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isNetworkAvailable(Student_Complaint_Request.this)) {

                        getCurrentDate();
                        doComplaintRequest();
                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Complaint_Request.this);
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

    void getComplaintTypes() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Complaint_Request.this);
        Bundle parms = new Bundle();

        MyVolley.init(Student_Complaint_Request.this);
        ShowProgressDilog(Student_Complaint_Request.this);
        mResponse.getResponse(Request.Method.POST, Complaint_Types_URL, getComplaintTypes, Student_Complaint_Request.this, parms, false);
    }


    void doComplaintRequest() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Complaint_Request.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("complain_type", spinner_selection);
        parms.putString("description", et_complaint_description.getText().toString());
        parms.putString("created_at", date );
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Student_Complaint_Request.this);
        ShowProgressDilog(Student_Complaint_Request.this);
        mResponse.getResponse(Request.Method.POST, Complaint_Request_URL, getComplaintRequest, Student_Complaint_Request.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Student_Complaint_Request.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Complaint_Request.this);
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

        DismissProgress(Student_Complaint_Request.this);

        if (requestCode == getComplaintRequest) {
            System.out.println("Response for getComplaintRequest------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(this, response.getString("result"), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Student_Complaint_Request.this,MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(Student_Complaint_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }else if(requestCode == getComplaintTypes){

                 System.out.println("Response for getComplaintTypes------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    JSONObject result=response.getJSONObject("result");
                    JSONArray permission_array=result.getJSONArray("complainType");

                    for(int i=0;i<permission_array.length();i++){
                        JSONObject jsonObject=permission_array.getJSONObject(i);
                        Permission_Types_SetGet permission_types_setGet=new Permission_Types_SetGet();
                        permission_types_setGet.setId(jsonObject.getString("id"));
                        permission_types_setGet.setPermission_type(jsonObject.getString("complain_type"));

                        arrayList_complaint_reason.add(permission_types_setGet);
                    }

                }else {
                    Toast.makeText(Student_Complaint_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Student_Complaint_Request.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Student_Complaint_Request.this);
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

    private void getCurrentDate(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        date=String.valueOf(mYear) +"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+" "+String.valueOf(mHour) + ":" + String.valueOf(mMinute);

    }
}
