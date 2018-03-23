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
import in.objectsol.my_roomie.Adapter.Warden_New_Complaint_List_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 13/03/18.
 */

public class Warden_Complaint_Status extends Activity implements IJSONParseListener{

    RecyclerView rv_warden_complaint,rv_warden_complaint_old;
    ImageView iv_back;
    TextView tv_old_warden_complaint,tv_new_warden_complaint;

    int studentComplaints=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Permission_SetGet> complaint_setGetArrayList,complaint_old_setGetArrayList;
    LinearLayoutManager layoutManager,layoutManager1;

    private static final String Student_Complaints_URL = "http://174.136.1.35/dev/myroomie/warden/studentComplaints/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warden_complaint);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        complaint_setGetArrayList=new ArrayList<>();
        complaint_old_setGetArrayList=new ArrayList<>();
        rv_warden_complaint=(RecyclerView)findViewById(R.id.rv_warden_complaint);
        rv_warden_complaint_old=(RecyclerView)findViewById(R.id.rv_warden_complaint_old);
        iv_back=(ImageView) findViewById(R.id.iv_back_warden_complaint);
        tv_old_warden_complaint=(TextView) findViewById(R.id.tv_old_warden_complaint);
        tv_new_warden_complaint=(TextView) findViewById(R.id.tv_new_warden_complaint);


        rv_warden_complaint.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_warden_complaint.setLayoutManager(layoutManager);
        rv_warden_complaint.setItemAnimator(new DefaultItemAnimator());

        rv_warden_complaint_old.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        rv_warden_complaint_old.setLayoutManager(layoutManager1);
        rv_warden_complaint_old.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Warden_Complaint_Status.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Utils.isNetworkAvailable(Warden_Complaint_Status.this)) {

            getstudentComplaints();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Complaint_Status.this);
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


    void getstudentComplaints() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Complaint_Status.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Warden_Complaint_Status.this);
        ShowProgressDilog(Warden_Complaint_Status.this);
        mResponse.getResponse(Request.Method.POST, Student_Complaints_URL, studentComplaints, Warden_Complaint_Status.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Warden_Complaint_Status.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Complaint_Status.this);
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

        DismissProgress(Warden_Complaint_Status.this);

        if (requestCode == studentComplaints) {
            System.out.println("Response for studentComplaints------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray newpermission_array=result.getJSONArray("ComplaintRequest");
                    JSONArray oldpermission_array=result.getJSONArray("OldComplaintRequest");

                    if(newpermission_array.length()>0){
                        rv_warden_complaint.setVisibility(View.VISIBLE);
                        tv_new_warden_complaint.setVisibility(View.GONE);
                        for(int i=0;i<newpermission_array.length();i++){
                            JSONObject jsonObject=newpermission_array.getJSONObject(i);

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

                        Warden_New_Complaint_List_Adapter new_complaint_list_adapter=new Warden_New_Complaint_List_Adapter(Warden_Complaint_Status.this,complaint_setGetArrayList);
                        rv_warden_complaint.setAdapter(new_complaint_list_adapter);

                    }else {
                        rv_warden_complaint.setVisibility(View.GONE);
                        tv_new_warden_complaint.setVisibility(View.VISIBLE);
                    }

                    if(oldpermission_array.length()>0){
                        rv_warden_complaint_old.setVisibility(View.VISIBLE);
                        tv_old_warden_complaint.setVisibility(View.GONE);
                        for(int i=0;i<oldpermission_array.length();i++){
                            JSONObject jsonObject=oldpermission_array.getJSONObject(i);

                            Permission_SetGet permission_setGet=new Permission_SetGet();

                            permission_setGet.setStudent_id(jsonObject.getString("student_id"));
                            permission_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            permission_setGet.setId(jsonObject.getString("id"));
                            permission_setGet.setPermission_type(jsonObject.getString("complain_type"));
                            permission_setGet.setDescription(jsonObject.getString("description"));
                            permission_setGet.setCreated_at(jsonObject.getString("created_at"));
                            permission_setGet.setStatus(jsonObject.getString("status"));

                            complaint_old_setGetArrayList.add(permission_setGet);
                        }

                        Student_Complaint_List_Adapter adapter=new Student_Complaint_List_Adapter(Warden_Complaint_Status.this,complaint_old_setGetArrayList);
                        rv_warden_complaint_old.setAdapter(adapter);

                    }else {
                        rv_warden_complaint_old.setVisibility(View.GONE);
                        tv_old_warden_complaint.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Warden_Complaint_Status.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Warden_Complaint_Status.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Warden_Complaint_Status.this);
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
        Intent intent=new Intent(Warden_Complaint_Status.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
