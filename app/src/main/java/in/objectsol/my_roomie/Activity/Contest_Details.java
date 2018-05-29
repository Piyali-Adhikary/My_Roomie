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

public class Contest_Details extends Activity{

    ImageView iv_back_contest_details;
    TextView tv_contest_name_details,tv_contest_type,tv_contest_start_date,tv_contest_end_date;
    Button btn_participate_contest;

    SharedPreferences sharedPreferences;
    int studentParticipateContest=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;

    public static  String id="",contest_type="",contest_name="",contest_start_date="",contest_end_date="",contest_status="";
    String current_date="";
    private static final String Student_Participate_Contest_URL = "http://174.136.1.35/dev/myroomie/student/studentParticipateContest/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_details);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back_contest_details=(ImageView) findViewById(R.id.iv_back_contest_details);

        tv_contest_name_details=(TextView) findViewById(R.id.tv_contest_name_details);
        tv_contest_type=(TextView) findViewById(R.id.tv_contest_type);
        tv_contest_start_date=(TextView) findViewById(R.id.tv_contest_start_date);
        tv_contest_end_date=(TextView) findViewById(R.id.tv_contest_end_date);


        btn_participate_contest=(Button) findViewById(R.id.btn_participate_contest);

        if(Contest.contest_setGetArrayList.size()>0){
            tv_contest_name_details.setText(contest_name);
            tv_contest_type.setText(contest_type);
            tv_contest_start_date.setText(contest_start_date);
            tv_contest_end_date.setText(contest_end_date);



        }


        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour= c.get(Calendar.HOUR_OF_DAY);
        int mMinute= c.get(Calendar.MINUTE);

        current_date= String.valueOf(mDay) +"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear)+" "+ String.valueOf(mHour)+":" + String.valueOf(mMinute);





        btn_participate_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Contest_Details.this, Contest_Submit.class);
                intent.putExtra("contest_id",id);
                startActivity(intent);
                finish();
            }
        });


        iv_back_contest_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Contest_Details.this, Contest.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /*void studentParticipateContest() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Contest_Details.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("year", year);
        parms.putString("month", services_month);
        parms.putString("payment_amount", charges);
        parms.putString("payment_for", services_name);
        parms.putString("services_id", id);


        MyVolley.init(Contest_Details.this);
        ShowProgressDilog(Contest_Details.this);
        mResponse.getResponse(Request.Method.POST, Student_Participate_Contest_URL, studentParticipateContest, Contest_Details.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Contest_Details.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Contest_Details.this);
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

        DismissProgress(Contest_Details.this);
        student_setGetArrayList.clear();

        if (requestCode == studentParticipateContest) {
            System.out.println("Response for studentParticipateContest------" + response.toString());
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Contest_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Contest_Details.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Contest_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Contest_Details.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Contest_Details.this);
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
    }*/




    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(Community_Details.this, Community_Student_Updated.class);
        startActivity(intent);*/
        finish();
    }
}
