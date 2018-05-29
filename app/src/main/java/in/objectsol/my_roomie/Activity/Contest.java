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

import in.objectsol.my_roomie.Adapter.Contest_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Services_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Contest_SetGet;
import in.objectsol.my_roomie.SetGet.Services_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Contest extends Activity implements IJSONParseListener{

    RecyclerView rv_students_contest;
    ImageView iv_back_contest;

    SharedPreferences sharedPreferences;
    int studentContestList=611;
    ProgressDialog pDialog;
    public static  ArrayList<Contest_SetGet> contest_setGetArrayList;
    String student="";

    private static final String Student_Contest_List_URL = "http://174.136.1.35/dev/myroomie/student/studentContestList/";
    LinearLayoutManager layoutManager;
    Contest_Adapter contest_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        contest_setGetArrayList=new ArrayList<>();
        iv_back_contest=(ImageView) findViewById(R.id.iv_back_contest);
        rv_students_contest=(RecyclerView)findViewById(R.id.rv_students_contest);

        rv_students_contest.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_students_contest.setLayoutManager(layoutManager);
        rv_students_contest.setItemAnimator(new DefaultItemAnimator());

        //Calling API

        if (Utils.isNetworkAvailable(Contest.this)) {

            studentContestList();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Contest.this);
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




        iv_back_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Contest.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void studentContestList() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Contest.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));


        MyVolley.init(Contest.this);
        ShowProgressDilog(Contest.this);
        mResponse.getResponse(Request.Method.POST, Student_Contest_List_URL, studentContestList, Contest.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Contest.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Contest.this);
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

        DismissProgress(Contest.this);
        contest_setGetArrayList.clear();

        if (requestCode == studentContestList) {
            System.out.println("Response for studentContestList------" + response.toString());
            //hideSoftKeyboard();
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Contest_SetGet contest_setGet=new Contest_SetGet();
                        contest_setGet.setId(jsonObject.getString("id"));
                        contest_setGet.setContest_type(jsonObject.getString("contest_type"));
                        contest_setGet.setContest_name(jsonObject.getString("contest_name"));
                        contest_setGet.setContest_start_date(jsonObject.getString("contest_start_date"));
                        contest_setGet.setContest_end_date(jsonObject.getString("contest_end_date"));
                        contest_setGet.setContest_status(jsonObject.getString("contest_status"));


                        contest_setGetArrayList.add(contest_setGet);

                    }


                    contest_adapter=new Contest_Adapter(Contest.this,contest_setGetArrayList);
                    rv_students_contest.setAdapter(contest_adapter);

                }else {
                    Toast.makeText(Contest.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Contest.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Contest.this);
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


   /* public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Contest.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
