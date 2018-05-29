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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Student_Community_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Community_Updated_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Community_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Community_Student_Updated extends Activity implements IJSONParseListener{

    RecyclerView rv_students_community;
    ImageView iv_back_community;

    SharedPreferences sharedPreferences;
    int viewCommunity=611;
    ProgressDialog pDialog;
    public static  ArrayList<Community_SetGet> community_setGetArrayList;
    String student="";

    private static final String View_Community_URL = "http://174.136.1.35/dev/myroomie/community/viewCommunity/";
    LinearLayoutManager layoutManager;
    Student_Community_Updated_Adapter student_community_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_updated);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        community_setGetArrayList=new ArrayList<>();
        iv_back_community=(ImageView) findViewById(R.id.iv_back_community);
        rv_students_community=(RecyclerView)findViewById(R.id.rv_students_community);

        rv_students_community.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_students_community.setLayoutManager(layoutManager);
        rv_students_community.setItemAnimator(new DefaultItemAnimator());

        //Calling API

        if (Utils.isNetworkAvailable(Community_Student_Updated.this)) {

            viewCommunity();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student_Updated.this);
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




        iv_back_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Community_Student_Updated.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void viewCommunity() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Community_Student_Updated.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));


        MyVolley.init(Community_Student_Updated.this);
        ShowProgressDilog(Community_Student_Updated.this);
        mResponse.getResponse(Request.Method.POST, View_Community_URL, viewCommunity, Community_Student_Updated.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Community_Student_Updated.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student_Updated.this);
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

        DismissProgress(Community_Student_Updated.this);
        community_setGetArrayList.clear();

        if (requestCode == viewCommunity) {
            System.out.println("Response for viewCommunity------" + response.toString());
            hideSoftKeyboard();
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Community_SetGet community_setGet=new Community_SetGet();
                        community_setGet.setId(jsonObject.getString("id"));
                        community_setGet.setCommunity_name(jsonObject.getString("community_name"));
                        community_setGet.setDuration(jsonObject.getString("duration"));
                        community_setGet.setStart_date(jsonObject.getString("start_date"));
                        community_setGet.setEnd_date(jsonObject.getString("end_date"));
                        community_setGet.setDays_of_week(jsonObject.getString("days_of_week"));
                        community_setGet.setTimings(jsonObject.getString("timings"));
                        community_setGet.setCharges(jsonObject.getString("charges"));
                        community_setGet.setTotal_capacity(jsonObject.getString("total_capacity"));
                        community_setGet.setStatus(jsonObject.getString("status"));

                        community_setGetArrayList.add(community_setGet);

                    }


                    student_community_adapter=new Student_Community_Updated_Adapter(Community_Student_Updated.this,community_setGetArrayList);
                    rv_students_community.setAdapter(student_community_adapter);

                }else {
                    Toast.makeText(Community_Student_Updated.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Community_Student_Updated.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Community_Student_Updated.this);
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
        Intent intent = new Intent(Community_Student_Updated.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
