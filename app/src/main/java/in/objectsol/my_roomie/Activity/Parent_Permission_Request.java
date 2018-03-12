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

import in.objectsol.my_roomie.Adapter.Parent_Permission_Request_Adapter;
import in.objectsol.my_roomie.Adapter.Parent_Permission_Request_Old_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 06/03/18.
 */

public class Parent_Permission_Request extends Activity implements IJSONParseListener{

    RecyclerView rv_parent_permission_request,rv_parent_permission_request_old;
    ImageView iv_back;
    TextView tv_old_permission,tv_new_permission;

    int getstudentPermissions=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<Permission_SetGet> permission_setGetArrayList,permission_old_setGetArrayList;
    LinearLayoutManager layoutManager,layoutManager1;

    private static final String Student_Permissions_URL = "http://174.136.1.35/dev/myroomie/parents/studentPermissions/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_request_parent);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        permission_setGetArrayList=new ArrayList<>();
        permission_old_setGetArrayList=new ArrayList<>();
        rv_parent_permission_request=(RecyclerView)findViewById(R.id.rv_parent_permission_request);
        rv_parent_permission_request_old=(RecyclerView)findViewById(R.id.rv_parent_permission_request_old);
        //btn_send=(Button) findViewById(R.id.btn_send_parent_permission_request);
        iv_back=(ImageView) findViewById(R.id.iv_back_permission_request_parent);
        tv_old_permission=(TextView) findViewById(R.id.tv_old_permission);
        tv_new_permission=(TextView) findViewById(R.id.tv_new_permission);


        rv_parent_permission_request.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_parent_permission_request.setLayoutManager(layoutManager);
        rv_parent_permission_request.setItemAnimator(new DefaultItemAnimator());

        rv_parent_permission_request_old.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        rv_parent_permission_request_old.setLayoutManager(layoutManager1);
        rv_parent_permission_request_old.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Parent_Permission_Request.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Utils.isNetworkAvailable(Parent_Permission_Request.this)) {

            getstudentPermissionsList();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Permission_Request.this);
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

    void getstudentPermissionsList() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Parent_Permission_Request.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Parent_Permission_Request.this);
        ShowProgressDilog(Parent_Permission_Request.this);
        mResponse.getResponse(Request.Method.POST, Student_Permissions_URL, getstudentPermissions, Parent_Permission_Request.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Parent_Permission_Request.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Permission_Request.this);
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

        DismissProgress(Parent_Permission_Request.this);

        if (requestCode == getstudentPermissions) {
            System.out.println("Response for getstudentPermissions------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray newpermission_array=result.getJSONArray("NewPermissionRequest");
                    JSONArray oldpermission_array=result.getJSONArray("OldPermissionRequest");

                    if(newpermission_array.length()>0){
                        rv_parent_permission_request.setVisibility(View.VISIBLE);
                        tv_new_permission.setVisibility(View.GONE);
                        for(int i=0;i<newpermission_array.length();i++){
                            JSONObject jsonObject=newpermission_array.getJSONObject(i);

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

                        Parent_Permission_Request_Adapter parent_permission_request_adapter=new Parent_Permission_Request_Adapter(Parent_Permission_Request.this,permission_setGetArrayList);
                        rv_parent_permission_request.setAdapter(parent_permission_request_adapter);

                    }else {
                        rv_parent_permission_request.setVisibility(View.GONE);
                        tv_new_permission.setVisibility(View.VISIBLE);
                    }

                    if(oldpermission_array.length()>0){
                        rv_parent_permission_request_old.setVisibility(View.VISIBLE);
                        tv_old_permission.setVisibility(View.GONE);
                        for(int i=0;i<oldpermission_array.length();i++){
                            JSONObject jsonObject=oldpermission_array.getJSONObject(i);

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

                            permission_old_setGetArrayList.add(permission_setGet);
                        }

                        Parent_Permission_Request_Old_Adapter request_old_adapter=new Parent_Permission_Request_Old_Adapter(Parent_Permission_Request.this,permission_old_setGetArrayList);
                        rv_parent_permission_request_old.setAdapter(request_old_adapter);

                    }else {
                        rv_parent_permission_request_old.setVisibility(View.GONE);
                        tv_old_permission.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Parent_Permission_Request.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Parent_Permission_Request.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Parent_Permission_Request.this);
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
