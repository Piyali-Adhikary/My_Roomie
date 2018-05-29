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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Contest_Submit extends Activity implements IJSONParseListener{

    ImageView iv_back_contest_submit;
    EditText et_contest_submit;
    Button btn_submit_contest;

    SharedPreferences sharedPreferences;
    int studentParticipateContest=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;

    public static  String contest_id="";
    String current_date="";
    private static final String Student_Participate_Contest_URL = "http://174.136.1.35/dev/myroomie/student/studentParticipateContest/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_submit);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back_contest_submit=(ImageView) findViewById(R.id.iv_back_contest_submit);

        et_contest_submit=(EditText) findViewById(R.id.et_contest_submit);

        btn_submit_contest=(Button) findViewById(R.id.btn_submit_contest);

        contest_id=getIntent().getStringExtra("contest_id");
        btn_submit_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(Contest_Submit.this)) {

                    if(et_contest_submit.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(Contest_Submit.this, "Please enter your content.", Toast.LENGTH_SHORT).show();
                    }else {
                        studentParticipateContest();
                    }


                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Contest_Submit.this);
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
        });


        iv_back_contest_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Contest_Submit.this, Contest_Details.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void studentParticipateContest() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Contest_Submit.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("contest_id", contest_id);
        parms.putString("contest_answer", et_contest_submit.getText().toString());



        MyVolley.init(Contest_Submit.this);
        ShowProgressDilog(Contest_Submit.this);
        mResponse.getResponse(Request.Method.POST, Student_Participate_Contest_URL, studentParticipateContest, Contest_Submit.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Contest_Submit.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Contest_Submit.this);
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

        DismissProgress(Contest_Submit.this);
        student_setGetArrayList.clear();

        if (requestCode == studentParticipateContest) {
            System.out.println("Response for studentParticipateContest------" + response.toString());
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Contest_Submit.this,response.getString("result"),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Contest_Submit.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Contest_Submit.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Contest_Submit.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Contest_Submit.this);
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
       /* Intent intent = new Intent(Community_Details.this, Community_Student_Updated.class);
        startActivity(intent);*/
        finish();
    }
}
