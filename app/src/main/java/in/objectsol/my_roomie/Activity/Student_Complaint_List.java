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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Parent_Permission_Request_Old_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Complaint_List_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 12/03/18.
 */

public class Student_Complaint_List extends Activity implements IJSONParseListener{

    RecyclerView rv_student_complaint_list;
    ImageView iv_back;
    TextView tv_student_complaint;
    LinearLayoutManager layoutManager;
    Button btn_new_complaint;

    int getstudentsComplaintList=618;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Permission_SetGet> complaint_setGetArrayList;
    private static final String Student_Complaint_List_URL = "http://174.136.1.35/dev/myroomie/student/studentsComplaintList/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_complaint_details);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        iv_back=(ImageView) findViewById(R.id.iv_back_student_complaint_list);
        rv_student_complaint_list=(RecyclerView) findViewById(R.id.rv_student_complaint_list);
        btn_new_complaint=(Button) findViewById(R.id.btn_new_complaint);
        tv_student_complaint=(TextView) findViewById(R.id.tv_student_complaint);

        complaint_setGetArrayList=new ArrayList<>();

        rv_student_complaint_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_student_complaint_list.setLayoutManager(layoutManager);
        rv_student_complaint_list.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Student_Complaint_List.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if (Utils.isNetworkAvailable(Student_Complaint_List.this)) {

            getstudentsComplaintList();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Complaint_List.this);
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

        btn_new_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Student_Complaint_List.this,Student_Complaint_Request.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void getstudentsComplaintList() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Complaint_List.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Student_Complaint_List.this);
        ShowProgressDilog(Student_Complaint_List.this);
        mResponse.getResponse(Request.Method.POST, Student_Complaint_List_URL, getstudentsComplaintList, Student_Complaint_List.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Student_Complaint_List.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Complaint_List.this);
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

        DismissProgress(Student_Complaint_List.this);

        if (requestCode == getstudentsComplaintList) {
            System.out.println("Response for getstudentsComplaintList------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray complaintRequest_array=result.getJSONArray("ComplaintRequest");

                    if(complaintRequest_array.length()>0){
                        rv_student_complaint_list.setVisibility(View.VISIBLE);
                        tv_student_complaint.setVisibility(View.GONE);
                        for(int i=0;i<complaintRequest_array.length();i++){
                            JSONObject jsonObject=complaintRequest_array.getJSONObject(i);

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

                        Student_Complaint_List_Adapter student_complaint_list_adapter= new Student_Complaint_List_Adapter(Student_Complaint_List.this,complaint_setGetArrayList);
                        rv_student_complaint_list.setAdapter(student_complaint_list_adapter);

                    }else {
                        rv_student_complaint_list.setVisibility(View.GONE);
                        tv_student_complaint.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Student_Complaint_List.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Student_Complaint_List.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Student_Complaint_List.this);
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

        Intent intent=new Intent(Student_Complaint_List.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
