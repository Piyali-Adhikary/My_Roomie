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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Student_View_Attendance_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Attendance_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 14/03/18.
 */

public class Parent_View_Student_Attendance extends Activity implements IJSONParseListener{

    RecyclerView rv_student_view_attendance;
    ImageView iv_back;
    LinearLayout ll_student_name;
    TextView tv_student_name;

    int studentAttendanceView=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<Attendance_SetGet> attendance_setGetArrayList;
    LinearLayoutManager layoutManager;


    private static final String Student_Attendance_View_URL = "http://174.136.1.35/dev/myroomie/parents/studentAttendanceView/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_view_attendance);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        attendance_setGetArrayList=new ArrayList<>();
        rv_student_view_attendance=(RecyclerView)findViewById(R.id.rv_student_view_attendance);
        iv_back=(ImageView)findViewById(R.id.iv_back_student_view_attendance);
        ll_student_name=(LinearLayout) findViewById(R.id.ll_student_name);
        tv_student_name=(TextView) findViewById(R.id.tv_student_name_student_view_attendance);

        rv_student_view_attendance.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_student_view_attendance.setLayoutManager(layoutManager);
        rv_student_view_attendance.setItemAnimator(new DefaultItemAnimator());

        ll_student_name.setVisibility(View.VISIBLE);
        tv_student_name.setText("Student Name : "+sharedPreferences_student.getString("student_name",""));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Parent_View_Student_Attendance.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Utils.isNetworkAvailable(Parent_View_Student_Attendance.this)) {

            studentAttendanceView();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_View_Student_Attendance.this);
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

    void studentAttendanceView() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Parent_View_Student_Attendance.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Parent_View_Student_Attendance.this);
        ShowProgressDilog(Parent_View_Student_Attendance.this);
        mResponse.getResponse(Request.Method.POST, Student_Attendance_View_URL, studentAttendanceView, Parent_View_Student_Attendance.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Parent_View_Student_Attendance.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_View_Student_Attendance.this);
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

        DismissProgress(Parent_View_Student_Attendance.this);

        if (requestCode == studentAttendanceView) {
            System.out.println("Response for studentAttendanceView------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray attendance_array=result.getJSONArray("attendance");

                    for(int i=0;i<attendance_array.length();i++) {
                        JSONObject jsonObject = attendance_array.getJSONObject(i);

                        Attendance_SetGet attendance_setGet=new Attendance_SetGet();

                        attendance_setGet.setDate(jsonObject.getString("date"));
                        attendance_setGet.setStatus(jsonObject.getString("status"));

                        attendance_setGetArrayList.add(attendance_setGet);
                    }

                    Student_View_Attendance_Adapter attendanceAdapter= new Student_View_Attendance_Adapter(Parent_View_Student_Attendance.this,attendance_setGetArrayList);
                    rv_student_view_attendance.setAdapter(attendanceAdapter);

                }else {
                    Toast.makeText(Parent_View_Student_Attendance.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Parent_View_Student_Attendance.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Parent_View_Student_Attendance.this);
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
        Intent intent=new Intent(Parent_View_Student_Attendance.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
