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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import in.objectsol.my_roomie.Adapter.Student_Community_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Community_Details extends Activity implements IJSONParseListener{

    ImageView iv_back_community_details;
    TextView tv_community_name,tv_community_details_total_capacity,tv_community_details_days_of_week,tv_community_details_start_date,
            tv_community_details_end_date,tv_community_details_timings,tv_community_details_duration,tv_community_details_charges;
    Button btn_join_community_details,btn_refer_community_details;

    SharedPreferences sharedPreferences;
    int studentSignUpCommunity=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;

    public static  String id="",community_name="",duration="",start_date="",end_date="",days_of_week="", timings="", charges="", total_capacity="", status="";
    String current_date="";
    private static final String SignUp_Community_URL = "http://174.136.1.35/dev/myroomie/community/studentSignUpCommunity/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_details);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back_community_details=(ImageView) findViewById(R.id.iv_back_community_details);

        tv_community_name=(TextView) findViewById(R.id.tv_community_name);
        tv_community_details_total_capacity=(TextView) findViewById(R.id.tv_community_details_total_capacity);
        tv_community_details_days_of_week=(TextView) findViewById(R.id.tv_community_details_days_of_week);
        tv_community_details_start_date=(TextView) findViewById(R.id.tv_community_details_start_date);
        tv_community_details_end_date=(TextView) findViewById(R.id.tv_community_details_end_date);
        tv_community_details_timings=(TextView) findViewById(R.id.tv_community_details_timings);
        tv_community_details_duration=(TextView) findViewById(R.id.tv_community_details_duration);
        tv_community_details_charges=(TextView) findViewById(R.id.tv_community_details_charges);

        btn_join_community_details=(Button) findViewById(R.id.btn_join_community_details);
        btn_refer_community_details=(Button) findViewById(R.id.btn_refer_community_details);

        if(Community_Student_Updated.community_setGetArrayList.size()>0){
            tv_community_name.setText(community_name);
            tv_community_details_total_capacity.setText(total_capacity);
            tv_community_details_days_of_week.setText(days_of_week);
            tv_community_details_start_date.setText(start_date);
            tv_community_details_end_date.setText(end_date);
            tv_community_details_timings.setText(timings);
            tv_community_details_duration.setText(duration);
            tv_community_details_charges.setText(charges);

            if(status.equalsIgnoreCase("notJoin")){
                btn_join_community_details.setVisibility(View.VISIBLE);
                btn_refer_community_details.setVisibility(View.GONE);
            }else {
                btn_join_community_details.setVisibility(View.GONE);
                btn_refer_community_details.setVisibility(View.VISIBLE);
            }
        }


        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour= c.get(Calendar.HOUR_OF_DAY);
        int mMinute= c.get(Calendar.MINUTE);

        current_date= String.valueOf(mDay) +"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mYear)+" "+ String.valueOf(mHour)+":" + String.valueOf(mMinute);


        //Calling API

       /* if (Utils.isNetworkAvailable(Community_Details.this)) {

                viewSearchStudent();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Details.this);
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
        }*/



        btn_join_community_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(Community_Details.this)) {

                    studentSignUpCommunity();

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Details.this);
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

        btn_refer_community_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Student_Community_Adapter.community_id=id;
                Intent intent=new Intent(Community_Details.this,Refer_Community.class);
                startActivity(intent);
                finish();

            }
        });

        iv_back_community_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(Community_Details.this, Community_Student_Updated.class);
                startActivity(intent);*/
                finish();
            }
        });
    }

    void studentSignUpCommunity() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Community_Details.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("permission_type", "community");
        parms.putString("description", community_name);
        parms.putString("created_at", current_date);
        parms.putString("community_id", id);


        MyVolley.init(Community_Details.this);
        ShowProgressDilog(Community_Details.this);
        mResponse.getResponse(Request.Method.POST, SignUp_Community_URL, studentSignUpCommunity, Community_Details.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Community_Details.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Details.this);
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

        DismissProgress(Community_Details.this);
        student_setGetArrayList.clear();

        if (requestCode == studentSignUpCommunity) {
            System.out.println("Response for studentSignUpCommunity------" + response.toString());
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Community_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Community_Details.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Community_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Community_Details.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Community_Details.this);
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
