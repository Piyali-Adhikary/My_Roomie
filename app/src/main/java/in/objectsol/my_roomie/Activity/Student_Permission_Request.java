package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
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
 * Created by objsol on 02/03/18.
 */

public class Student_Permission_Request extends Activity implements IJSONParseListener{

    Spinner sp_student_permission_request;
    EditText et_from,et_to,et_permission_description;
    Button btn_submit_student_permission_request;
    ImageView iv_back_student_permission_request;
    ArrayList<Permission_Types_SetGet> arrayList_permission_reason;

    String date_time="",date="";
    int getPermissionRequest=602;
    int getPermissionTypes=603;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String spinner_selection="";
    Boolean isFromDate=false;
    private static final String Permission_Request_URL = "http://174.136.1.35/dev/myroomie/student/permissionRequest/";
    private static final String Permission_Types_URL = "http://174.136.1.35/dev/myroomie/operations/permissionTypes/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_permission_request);

        arrayList_permission_reason=new ArrayList<>();
        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);

        sp_student_permission_request=(Spinner) findViewById(R.id.sp_student_permission_request);
        et_from=(EditText) findViewById(R.id.et_from);
        et_to=(EditText) findViewById(R.id.et_to);
        et_permission_description=(EditText) findViewById(R.id.et_permission_description);
        btn_submit_student_permission_request=(Button) findViewById(R.id.btn_submit_student_permission_request);
        iv_back_student_permission_request=(ImageView) findViewById(R.id.iv_back_student_permission_request);


        iv_back_student_permission_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Student_Permission_Request.this,Student_Permission.class);
                startActivity(intent);
                finish();
            }
        });

        et_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFromDate=true;
                datePicker();
            }
        });

        et_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isFromDate=false;
                datePicker();
            }
        });

        btn_submit_student_permission_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner_selection.equalsIgnoreCase("--Select Reasons--")){
                    Toast.makeText(Student_Permission_Request.this, "Please Select Permission Reasons", Toast.LENGTH_SHORT).show();
                }else if(et_from.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Student_Permission_Request.this, "Please Select Time", Toast.LENGTH_SHORT).show();
                }else if(et_from.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Student_Permission_Request.this, "Please Select Time", Toast.LENGTH_SHORT).show();
                }else if(et_permission_description.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Student_Permission_Request.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                }else {
                    if (Utils.isNetworkAvailable(Student_Permission_Request.this)) {

                        doPermissionRequest();
                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Permission_Request.this);
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

        Permission_Types_SetGet permission_types_setGet=new Permission_Types_SetGet();
        permission_types_setGet.setPermission_type("--Select Reasons--");
        arrayList_permission_reason.add(permission_types_setGet);

        if (Utils.isNetworkAvailable(Student_Permission_Request.this)) {

            getPermissionTypes();
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Permission_Request.this);
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


        Permission_Types_Adapter arrayAdapter=new Permission_Types_Adapter(Student_Permission_Request.this,R.layout.spinner_item,arrayList_permission_reason);
        sp_student_permission_request.setAdapter(arrayAdapter);
        sp_student_permission_request.setSelection(0);

        sp_student_permission_request.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_selection = arrayList_permission_reason.get(i).getPermission_type();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
    }

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        date=String.valueOf(mYear) +"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay)+" "+String.valueOf(mHour) + ":" + String.valueOf(mMinute);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        date_time = String.valueOf(year) +"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(dayOfMonth);
                        //*************Call Time Picker Here ********************
                        tiemPicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void tiemPicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if(isFromDate){
                            et_from.setText(date_time+" "+hourOfDay + ":" + minute);
                        }else {
                            et_to.setText(date_time+" "+hourOfDay + ":" + minute);
                        }

                    }
                }, mHour, mMinute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    void getPermissionTypes() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Permission_Request.this);
        Bundle parms = new Bundle();

        MyVolley.init(Student_Permission_Request.this);
        ShowProgressDilog(Student_Permission_Request.this);
        mResponse.getResponse(Request.Method.POST, Permission_Types_URL, getPermissionTypes, Student_Permission_Request.this, parms, false);
    }

    void doPermissionRequest() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Permission_Request.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("permission_type", spinner_selection);
        parms.putString("from_time", et_from.getText().toString());
        parms.putString("to_time", et_to.getText().toString());
        parms.putString("description", et_permission_description.getText().toString());
        parms.putString("created_at", date);
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Student_Permission_Request.this);
        ShowProgressDilog(Student_Permission_Request.this);
        mResponse.getResponse(Request.Method.POST, Permission_Request_URL, getPermissionRequest, Student_Permission_Request.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Student_Permission_Request.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Permission_Request.this);
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

        DismissProgress(Student_Permission_Request.this);

        if (requestCode == getPermissionRequest) {
            System.out.println("Response for getPermissionRequest------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(this, response.getString("result"), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Student_Permission_Request.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Student_Permission_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }else if(requestCode == getPermissionTypes){

            System.out.println("Response for getPermissionTypes------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                   JSONObject result=response.getJSONObject("result");
                    JSONArray permission_array=result.getJSONArray("permissionType");

                    for(int i=0;i<permission_array.length();i++){
                        JSONObject jsonObject=permission_array.getJSONObject(i);
                        Permission_Types_SetGet permission_types_setGet=new Permission_Types_SetGet();
                        permission_types_setGet.setId(jsonObject.getString("id"));
                        permission_types_setGet.setPermission_type(jsonObject.getString("permission_type"));

                        arrayList_permission_reason.add(permission_types_setGet);
                    }

                }else {
                    Toast.makeText(Student_Permission_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Student_Permission_Request.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Student_Permission_Request.this);
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

        Intent intent=new Intent(Student_Permission_Request.this,Student_Permission.class);
        startActivity(intent);
        finish();
    }

}
