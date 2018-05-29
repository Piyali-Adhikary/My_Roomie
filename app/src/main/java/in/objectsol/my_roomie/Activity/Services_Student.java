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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Student_Community_Updated_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Services_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Community_SetGet;
import in.objectsol.my_roomie.SetGet.Services_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Services_Student extends Activity implements IJSONParseListener{

    RecyclerView rv_students_services;
    ImageView iv_back_services;

    SharedPreferences sharedPreferences;
    int studentRequestServicesShow=611;
    ProgressDialog pDialog;
    public static  ArrayList<Services_SetGet> services_setGetArrayList;
    String student="";

    private static final String Student_Request_Services_Show_URL = "http://174.136.1.35/dev/myroomie/student/studentRequestServicesShow/";
    LinearLayoutManager layoutManager;
    Student_Services_Adapter services_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_student);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        services_setGetArrayList=new ArrayList<>();
        iv_back_services=(ImageView) findViewById(R.id.iv_back_services);
        rv_students_services=(RecyclerView)findViewById(R.id.rv_students_services);

        rv_students_services.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_students_services.setLayoutManager(layoutManager);
        rv_students_services.setItemAnimator(new DefaultItemAnimator());

        //Calling API

        if (Utils.isNetworkAvailable(Services_Student.this)) {

            studentRequestServicesShow();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Services_Student.this);
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




        iv_back_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Services_Student.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void studentRequestServicesShow() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Services_Student.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));


        MyVolley.init(Services_Student.this);
        ShowProgressDilog(Services_Student.this);
        mResponse.getResponse(Request.Method.POST, Student_Request_Services_Show_URL, studentRequestServicesShow, Services_Student.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Services_Student.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Services_Student.this);
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

        DismissProgress(Services_Student.this);
        services_setGetArrayList.clear();

        if (requestCode == studentRequestServicesShow) {
            System.out.println("Response for studentRequestServicesShow------" + response.toString());
            //hideSoftKeyboard();
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Services_SetGet services_setGet=new Services_SetGet();
                        services_setGet.setId(jsonObject.getString("id"));
                        services_setGet.setServices_name(jsonObject.getString("services_name"));
                        services_setGet.setCharges(jsonObject.getString("charges"));
                        services_setGet.setServices_month(jsonObject.getString("services_month"));
                        services_setGet.setServices_day(jsonObject.getString("services_day"));
                        services_setGet.setServices_start_date(jsonObject.getString("services_start_date"));
                        services_setGet.setServices_end_date(jsonObject.getString("services_end_date"));
                        services_setGet.setServices_start_time(jsonObject.getString("services_start_time"));
                        services_setGet.setServices_end_time(jsonObject.getString("services_end_time"));
                        services_setGet.setYear(jsonObject.getString("year"));

                        services_setGetArrayList.add(services_setGet);

                    }


                    services_adapter=new Student_Services_Adapter(Services_Student.this,services_setGetArrayList);
                    rv_students_services.setAdapter(services_adapter);

                }else {
                    Toast.makeText(Services_Student.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Services_Student.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Services_Student.this);
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


    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Services_Student.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
