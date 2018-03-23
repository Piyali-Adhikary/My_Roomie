package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Adapter.Student_Attendance_Warden_Adapter;
import in.objectsol.my_roomie.Adapter.Warden_Check_In_Adapter;
import in.objectsol.my_roomie.Adapter.Warden_Time_In_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 15/03/18.
 */

public class Warden_Check_in_Check_out extends Activity implements IJSONParseListener{

    ImageView iv_back;
    CircleImageView iv_student_image;
    TextView tv_date,tv_previous_check_in,tv_student_name;
    EditText et_enter_check_in_time,et_enter_check_out_time;
    RecyclerView rv_check_in_check_out;
    Button btn_save;
    String current_date="";
    public static String student_id="",student_name="";

    int CheckInCheckOutViewByWarden=612;
    int studentsCheckInCheckOutByWarden=613;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Student_SetGet> student_setGetArrayList,previous_arraylist;
    LinearLayoutManager layoutManager;
    Warden_Time_In_Adapter warden_adapter;
    private static final String Check_In_Check_Out_View_By_Warden_URL = "http://174.136.1.35/dev/myroomie/warden/CheckInCheckOutViewByWarden/";
    private static final String Students_Check_In_Check_Out_By_Warden_URL = "http://174.136.1.35/dev/myroomie/warden/studentsCheckInCheckOutByWarden/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warden_check_in_check_out);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        previous_arraylist=new ArrayList<>();
        student_setGetArrayList=new ArrayList<>();
        iv_back=(ImageView) findViewById(R.id.iv_back_warden_check_in_check_out);
        iv_student_image=(CircleImageView) findViewById(R.id.iv_student_image_warden_check_in);
        tv_date=(TextView) findViewById(R.id.tv_date_warden_check_in);
        tv_previous_check_in=(TextView) findViewById(R.id.tv_previous_check_in);
        tv_student_name=(TextView) findViewById(R.id.tv_student_name_warden_check_in);
        et_enter_check_in_time=(EditText) findViewById(R.id.et_enter_check_in_time);
        et_enter_check_out_time=(EditText) findViewById(R.id.et_enter_check_out_time);
        rv_check_in_check_out=(RecyclerView) findViewById(R.id.rv_check_in_check_out);
        btn_save=(Button) findViewById(R.id.btn_save_check_in_check_out);

        rv_check_in_check_out.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_check_in_check_out.setLayoutManager(layoutManager);
        rv_check_in_check_out.setItemAnimator(new DefaultItemAnimator());

        tv_student_name.setText(student_name);
        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        current_date= String.valueOf(mYear) +"-"+String.valueOf(mMonth+1)+"-"+String.valueOf(mDay);

        //Calling API
        if (Utils.isNetworkAvailable(Warden_Check_in_Check_out.this)) {

            CheckInCheckOutViewByWarden();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Check_in_Check_out.this);
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Warden_Check_in_Check_out.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        et_enter_check_in_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Warden_Check_in_Check_out.this, new TimePickerDialog.OnTimeSetListener() {

                    String clickedhour="",clickedmin="";
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if(selectedHour<10){
                            clickedhour="0"+String.valueOf(selectedHour);
                        }else {
                            clickedhour="";
                        }

                        if(selectedMinute<10){
                            clickedmin="0"+String.valueOf(selectedMinute);
                            if (clickedhour.equalsIgnoreCase("")){
                                et_enter_check_in_time.setText( selectedHour + ":" + clickedmin);
                            }else {
                                et_enter_check_in_time.setText( clickedhour + ":" + clickedmin);
                            }

                        }else {

                            if (clickedhour.equalsIgnoreCase("")){
                                et_enter_check_in_time.setText( selectedHour + ":" + selectedMinute);
                            }else {
                                et_enter_check_in_time.setText( clickedhour + ":" + selectedMinute);
                            }
                        }



                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        et_enter_check_out_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Warden_Check_in_Check_out.this, new TimePickerDialog.OnTimeSetListener() {
                    String clickedhour="",clickedmin="";
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10){
                            clickedhour="0"+String.valueOf(selectedHour);
                        }else {
                            clickedhour="";
                        }

                        if(selectedMinute<10){
                            clickedmin="0"+String.valueOf(selectedMinute);
                            if (clickedhour.equalsIgnoreCase("")){
                                et_enter_check_out_time.setText( selectedHour + ":" + clickedmin);
                            }else {
                                et_enter_check_out_time.setText( clickedhour + ":" + clickedmin);
                            }

                        }else {

                            if (clickedhour.equalsIgnoreCase("")){
                                et_enter_check_out_time.setText( selectedHour + ":" + selectedMinute);
                            }else {
                                et_enter_check_out_time.setText( clickedhour + ":" + selectedMinute);
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_enter_check_in_time.getText().toString().equalsIgnoreCase("")){

                }else if(et_enter_check_out_time.getText().toString().equalsIgnoreCase("")){

                }else {

                    if (Utils.isNetworkAvailable(Warden_Check_in_Check_out.this)) {

                        Students_Check_In_Check_Out_By_Warden();

                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Check_in_Check_out.this);
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
            }
        });

    }

    void Students_Check_In_Check_Out_By_Warden() {
        JSONArray jsonArray=new JSONArray();

        try{

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("warden_id",sharedPreferences.getString("warden_id",""));
            jsonObject.put("campus_id",sharedPreferences.getString("campus_id",""));
            jsonObject.put("auth_key",sharedPreferences.getString("auth_token",""));
            jsonObject.put("student_id",student_id);
            jsonObject.put("check_in_time", et_enter_check_in_time.getText());
            jsonObject.put("check_out_time", et_enter_check_out_time.getText());
            jsonObject.put("date", tv_date.getText());

            jsonArray.put(jsonObject);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Check_in_Check_out.this);
        Bundle parms = new Bundle();
        parms.putString("checkincheckout", jsonArray.toString());


        MyVolley.init(Warden_Check_in_Check_out.this);
        ShowProgressDilog(Warden_Check_in_Check_out.this);
        mResponse.getResponse(Request.Method.POST, Students_Check_In_Check_Out_By_Warden_URL, studentsCheckInCheckOutByWarden, Warden_Check_in_Check_out.this, parms, false);
    }

    void CheckInCheckOutViewByWarden() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Check_in_Check_out.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("student_id", student_id);
        parms.putString("date", current_date);

        MyVolley.init(Warden_Check_in_Check_out.this);
        ShowProgressDilog(Warden_Check_in_Check_out.this);
        mResponse.getResponse(Request.Method.POST, Check_In_Check_Out_View_By_Warden_URL, CheckInCheckOutViewByWarden, Warden_Check_in_Check_out.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Warden_Check_in_Check_out.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Check_in_Check_out.this);
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

        DismissProgress(Warden_Check_in_Check_out.this);

        if (requestCode == CheckInCheckOutViewByWarden) {
            System.out.println("Response for CheckInCheckOutViewByWarden------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray add_Check_In_check_Out_array=result.getJSONArray("add_Check_In_check_Out");
                    JSONArray prev_Check_In_check_Out_array=result.getJSONArray("prev_Check_In_check_Out");

                    for(int j=0;j<add_Check_In_check_Out_array.length();j++){

                        JSONObject jsonObject=add_Check_In_check_Out_array.getJSONObject(j);
                        Student_SetGet student_setGet=new Student_SetGet();

                        student_setGet.setStudent_id(jsonObject.getString("student_id"));
                        student_setGet.setCampus_id(jsonObject.getString("campus_id"));
                        student_setGet.setStudent_name(jsonObject.getString("student_name"));
                        student_setGet.setStudent_pic(jsonObject.getString("student_pic"));
                        student_setGet.setCheck_in_time(jsonObject.getString("check_in_time"));
                        student_setGet.setCheck_out_time(jsonObject.getString("check_out_time"));
                        student_setGet.setDate(jsonObject.getString("date"));


                        student_setGetArrayList.add(student_setGet);

                    }

                    tv_date.setText(student_setGetArrayList.get(0).getDate());
                    et_enter_check_in_time.setText(student_setGetArrayList.get(0).getCheck_in_time());
                    et_enter_check_out_time.setText(student_setGetArrayList.get(0).getCheck_out_time());

                    Glide.with(Warden_Check_in_Check_out.this).load(student_setGetArrayList.get(0).getStudent_pic())
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_student_image);

                    if (prev_Check_In_check_Out_array.length()>0){
                        rv_check_in_check_out.setVisibility(View.VISIBLE);
                        tv_previous_check_in.setVisibility(View.GONE);

                        for(int i=0;i<prev_Check_In_check_Out_array.length();i++){

                            JSONObject jsonObject1=prev_Check_In_check_Out_array.getJSONObject(i);
                            Student_SetGet student_setGet=new Student_SetGet();

                            student_setGet.setStudent_id(jsonObject1.getString("student_id"));
                            student_setGet.setCampus_id(jsonObject1.getString("campus_id"));
                            student_setGet.setStudent_name(jsonObject1.getString("student_name"));
                            student_setGet.setStudent_pic(jsonObject1.getString("student_pic"));
                            student_setGet.setCheck_in_time(jsonObject1.getString("check_in_time"));
                            student_setGet.setCheck_out_time(jsonObject1.getString("check_out_time"));
                            student_setGet.setDate(jsonObject1.getString("date"));


                            previous_arraylist.add(student_setGet);

                        }

                        Warden_Check_In_Adapter warden_adapter=new Warden_Check_In_Adapter(Warden_Check_in_Check_out.this,previous_arraylist);
                        rv_check_in_check_out.setAdapter(warden_adapter);
                    }else {
                        rv_check_in_check_out.setVisibility(View.GONE);
                        tv_previous_check_in.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Warden_Check_in_Check_out.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }else if(requestCode == studentsCheckInCheckOutByWarden){
            System.out.println("Response for studentsCheckInCheckOutByWarden------" + response.toString());

            try{
                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Warden_Check_in_Check_out.this,response.getString("result"),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Warden_Check_in_Check_out.this,Warden_Time_In_Time_Out.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Warden_Check_in_Check_out.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(Warden_Check_in_Check_out.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Warden_Check_in_Check_out.this);
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
