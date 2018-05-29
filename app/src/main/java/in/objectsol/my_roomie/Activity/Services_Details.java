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

public class Services_Details extends Activity implements IJSONParseListener{

    ImageView iv_back_services_details;
    TextView tv_services_name,tv_service_start_date,tv_service_end_date,tv_service_start_time,
            tv_service_end_time,tv_service_days,tv_service_month,tv_service_year;
    Button btn_join_services_details;

    SharedPreferences sharedPreferences;
    int studentJoinServices=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;

    public static  String id="",services_name="",charges="",services_month="",services_day="",services_start_date="",services_end_date="", services_start_time="",services_end_time="",year="";
    String current_date="";
    private static final String Student_Join_Services_URL = "http://174.136.1.35/dev/myroomie/student/studentJoinServices/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_details);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back_services_details=(ImageView) findViewById(R.id.iv_back_services_details);

        tv_services_name=(TextView) findViewById(R.id.tv_services_name);
        tv_service_start_date=(TextView) findViewById(R.id.tv_service_start_date);
        tv_service_end_date=(TextView) findViewById(R.id.tv_service_end_date);
        tv_service_start_time=(TextView) findViewById(R.id.tv_service_start_time);
        tv_service_end_time=(TextView) findViewById(R.id.tv_service_end_time);
        tv_service_days=(TextView) findViewById(R.id.tv_service_days);
        tv_service_month=(TextView) findViewById(R.id.tv_service_month);
        tv_service_year=(TextView) findViewById(R.id.tv_service_year);

        btn_join_services_details=(Button) findViewById(R.id.btn_join_services_details);

        if(Services_Student.services_setGetArrayList.size()>0){
            tv_services_name.setText(services_name);
            tv_service_start_date.setText(services_start_date);
            tv_service_end_date.setText(services_end_date);
            tv_service_start_time.setText(services_start_time);
            tv_service_end_time.setText(services_end_time);
            tv_service_days.setText(services_day);
            tv_service_month.setText(services_month);
            tv_service_year.setText(year);

           /* if(status.equalsIgnoreCase("notJoin")){
                btn_join_community_details.setVisibility(View.VISIBLE);
                btn_refer_community_details.setVisibility(View.GONE);
            }else {
                btn_join_community_details.setVisibility(View.GONE);
                btn_refer_community_details.setVisibility(View.VISIBLE);
            }*/
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



        btn_join_services_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(Services_Details.this)) {

                    studentJoinServices();

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Services_Details.this);
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

        /*btn_refer_community_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Student_Community_Adapter.community_id=id;
                Intent intent=new Intent(Services_Details.this,Refer_Community.class);
                startActivity(intent);
                finish();

            }
        });*/

        iv_back_services_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Services_Details.this, Services_Student.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void studentJoinServices() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Services_Details.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("year", year);
        parms.putString("month", services_month);
        parms.putString("payment_amount", charges);
        parms.putString("payment_for", services_name);
        parms.putString("services_id", id);


        MyVolley.init(Services_Details.this);
        ShowProgressDilog(Services_Details.this);
        mResponse.getResponse(Request.Method.POST, Student_Join_Services_URL, studentJoinServices, Services_Details.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Services_Details.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Services_Details.this);
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

        DismissProgress(Services_Details.this);
        student_setGetArrayList.clear();

        if (requestCode == studentJoinServices) {
            System.out.println("Response for studentJoinServices------" + response.toString());
            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Services_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Services_Details.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Services_Details.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Services_Details.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Services_Details.this);
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
