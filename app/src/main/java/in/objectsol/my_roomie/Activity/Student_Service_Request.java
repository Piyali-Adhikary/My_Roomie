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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Parent_Permission_Request_Adapter;
import in.objectsol.my_roomie.Adapter.Parent_Permission_Request_Old_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Complaint_List_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Permission_Request_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 14/03/18.
 */

public class Student_Service_Request extends Activity implements IJSONParseListener{

    RecyclerView rv_permission_service_request,rv_complaints_service_request;
    ImageView iv_back;
    TextView tv_complaints_service_request,tv_permission_service_request;

    int studentsServiceRequests=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<Permission_SetGet> permission_setGetArrayList,complaint_setGetArrayList;
    LinearLayoutManager layoutManager,layoutManager1;

    private static final String Student_Service_Request_URL = "http://174.136.1.35/dev/myroomie/student/studentsServiceRequests/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_service_request);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        permission_setGetArrayList=new ArrayList<>();
        complaint_setGetArrayList=new ArrayList<>();
        rv_permission_service_request=(RecyclerView)findViewById(R.id.rv_permission_service_request);
        rv_complaints_service_request=(RecyclerView)findViewById(R.id.rv_complaints_service_request);
        iv_back=(ImageView) findViewById(R.id.iv_back_student_service_request);
        tv_complaints_service_request=(TextView) findViewById(R.id.tv_complaints_service_request);
        tv_permission_service_request=(TextView) findViewById(R.id.tv_permission_service_request);


        rv_permission_service_request.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_permission_service_request.setLayoutManager(layoutManager);
        rv_permission_service_request.setItemAnimator(new DefaultItemAnimator());

        rv_complaints_service_request.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        rv_complaints_service_request.setLayoutManager(layoutManager1);
        rv_complaints_service_request.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Student_Service_Request.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Utils.isNetworkAvailable(Student_Service_Request.this)) {

            getstudentsServiceRequests();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Service_Request.this);
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

    void getstudentsServiceRequests() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Service_Request.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Student_Service_Request.this);
        ShowProgressDilog(Student_Service_Request.this);
        mResponse.getResponse(Request.Method.POST, Student_Service_Request_URL, studentsServiceRequests, Student_Service_Request.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Student_Service_Request.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Service_Request.this);
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

        DismissProgress(Student_Service_Request.this);

        if (requestCode == studentsServiceRequests) {
            System.out.println("Response for studentsServiceRequests------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray permission_array=result.getJSONArray("permission_request");
                    JSONArray complaints_array=result.getJSONArray("complaint");

                    if(permission_array.length()>0){
                        rv_permission_service_request.setVisibility(View.VISIBLE);
                        tv_permission_service_request.setVisibility(View.GONE);
                        for(int i=0;i<permission_array.length();i++){
                            JSONObject jsonObject=permission_array.getJSONObject(i);

                            Permission_SetGet permission_setGet=new Permission_SetGet();

                            permission_setGet.setStudent_id(jsonObject.getString("student_id"));
                            permission_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            permission_setGet.setId(jsonObject.getString("id"));
                            permission_setGet.setPermission_type(jsonObject.getString("permission_type"));
                            permission_setGet.setFrom_time(jsonObject.getString("from_time"));
                            permission_setGet.setTo_time(jsonObject.getString("to_time"));
                            permission_setGet.setDescription(jsonObject.getString("description"));
                            permission_setGet.setCreated_at(jsonObject.getString("created_at"));
                            permission_setGet.setStatus(jsonObject.getString("status"));

                            permission_setGetArrayList.add(permission_setGet);
                        }

                        Student_Permission_Request_Adapter adapter=new Student_Permission_Request_Adapter(Student_Service_Request.this,permission_setGetArrayList);
                        rv_permission_service_request.setAdapter(adapter);

                    }else {
                        rv_permission_service_request.setVisibility(View.GONE);
                        tv_permission_service_request.setVisibility(View.VISIBLE);
                    }

                    if(complaints_array.length()>0){
                        rv_complaints_service_request.setVisibility(View.VISIBLE);
                        tv_complaints_service_request.setVisibility(View.GONE);
                        for(int i=0;i<complaints_array.length();i++){
                            JSONObject jsonObject=complaints_array.getJSONObject(i);

                            Permission_SetGet permission_setGet=new Permission_SetGet();

                            permission_setGet.setStudent_id(jsonObject.getString("student_id"));
                            permission_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            permission_setGet.setId(jsonObject.getString("id"));
                            permission_setGet.setPermission_type(jsonObject.getString("complain_type"));
                            permission_setGet.setDescription(jsonObject.getString("description"));
                            permission_setGet.setCreated_at(jsonObject.getString("created_at"));
                            permission_setGet.setStatus(jsonObject.getString("status"));

                            complaint_setGetArrayList.add(permission_setGet);
                        }

                        Student_Complaint_List_Adapter student_complaint_list_adapter= new Student_Complaint_List_Adapter(Student_Service_Request.this,complaint_setGetArrayList);
                        rv_complaints_service_request.setAdapter(student_complaint_list_adapter);


                    }else {
                        rv_complaints_service_request.setVisibility(View.GONE);
                        tv_complaints_service_request.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Student_Service_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Student_Service_Request.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Student_Service_Request.this);
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
        Intent intent=new Intent(Student_Service_Request.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
