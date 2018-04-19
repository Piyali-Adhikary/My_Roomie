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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Student_Attendance_Warden_Adapter;
import in.objectsol.my_roomie.Adapter.Visitor_Warden_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.SetGet.Visitor_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 18/04/18.
 */

public class Visitor_Warden extends Activity implements IJSONParseListener{

    RecyclerView rv_visitor_warden;
    ImageView iv_back_visitor_warden;

    int studentVisitors=610;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private static final String Student_Visitors_URL = "http://174.136.1.35/dev/myroomie/warden/studentVisitors/";

    LinearLayoutManager layoutManager;
    Visitor_Warden_Adapter visitor_warden_adapter;
    ArrayList<Visitor_SetGet> visitorWardenArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_warden);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        visitorWardenArrayList=new ArrayList<>();
        iv_back_visitor_warden=(ImageView) findViewById(R.id.iv_back_visitor_warden);
        rv_visitor_warden=(RecyclerView)findViewById(R.id.rv_visitor_warden);

        rv_visitor_warden.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_visitor_warden.setLayoutManager(layoutManager);
        rv_visitor_warden.setItemAnimator(new DefaultItemAnimator());

        //Calling API
        if (Utils.isNetworkAvailable(Visitor_Warden.this)) {

            getStudentVisitors();


        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Visitor_Warden.this);
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

        iv_back_visitor_warden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Visitor_Warden.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    void getStudentVisitors() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Visitor_Warden.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));


        MyVolley.init(Visitor_Warden.this);
        ShowProgressDilog(Visitor_Warden.this);
        mResponse.getResponse(Request.Method.POST, Student_Visitors_URL, studentVisitors, Visitor_Warden.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Visitor_Warden.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Visitor_Warden.this);
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

        DismissProgress(Visitor_Warden.this);

        if (requestCode == studentVisitors) {
            System.out.println("Response for studentVisitors------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Visitor_SetGet visitor_setGet =new Visitor_SetGet();
                            visitor_setGet.setId(jsonObject.getString("id"));
                            visitor_setGet.setStudent_id(jsonObject.getString("student_id"));
                            visitor_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            visitor_setGet.setStudent_name(jsonObject.getString("student_name"));
                            visitor_setGet.setName(jsonObject.getString("name"));
                            visitor_setGet.setContact(jsonObject.getString("contact"));
                            visitor_setGet.setRelationship_with_student(jsonObject.getString("relationship_with_student"));
                            visitor_setGet.setTime_in(jsonObject.getString("time_in"));
                            visitor_setGet.setTime_out(jsonObject.getString("time_out"));
                            visitor_setGet.setRoom_no(jsonObject.getString("room_no"));
                            visitor_setGet.setCreated_at(jsonObject.getString("created_at"));


                            visitorWardenArrayList.add(visitor_setGet);



                    }


                    visitor_warden_adapter=new Visitor_Warden_Adapter(Visitor_Warden.this,visitorWardenArrayList);
                    rv_visitor_warden.setAdapter(visitor_warden_adapter);

                }else {
                    Toast.makeText(Visitor_Warden.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Visitor_Warden.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Visitor_Warden.this);
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
