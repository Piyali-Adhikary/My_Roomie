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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Adapter.Parent_Time_In_Adapter;
import in.objectsol.my_roomie.Adapter.Warden_Check_In_Adapter;
import in.objectsol.my_roomie.Adapter.Warden_Time_In_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 16/03/18.
 */

public class Parent_Time_in_Time_out extends Activity implements IJSONParseListener{

    ImageView iv_back;
    TextView tv_parent_time_in_time_out;
    RecyclerView rv_check_in_check_out;

    int studentCheckincheckoutView=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<Student_SetGet> previous_arraylist;
    LinearLayoutManager layoutManager;
    private static final String Student_Check_in_check_out_View_URL = "http://174.136.1.35/dev/myroomie/parents/studentCheckincheckoutView/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_check_in_check_out);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        previous_arraylist=new ArrayList<>();
        iv_back=(ImageView) findViewById(R.id.iv_back_parent_time_in_time_out);
        tv_parent_time_in_time_out=(TextView) findViewById(R.id.tv_parent_time_in_time_out);
        rv_check_in_check_out=(RecyclerView) findViewById(R.id.rv_parent_time_in_time_out);

        rv_check_in_check_out.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_check_in_check_out.setLayoutManager(layoutManager);
        rv_check_in_check_out.setItemAnimator(new DefaultItemAnimator());

        //Calling API
        if (Utils.isNetworkAvailable(Parent_Time_in_Time_out.this)) {

            studentCheckincheckoutView();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Time_in_Time_out.this);
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
                Intent intent=new Intent(Parent_Time_in_Time_out.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void studentCheckincheckoutView() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Parent_Time_in_Time_out.this);
        Bundle parms = new Bundle();
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Parent_Time_in_Time_out.this);
        ShowProgressDilog(Parent_Time_in_Time_out.this);
        mResponse.getResponse(Request.Method.POST, Student_Check_in_check_out_View_URL, studentCheckincheckoutView, Parent_Time_in_Time_out.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Parent_Time_in_Time_out.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Time_in_Time_out.this);
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

        DismissProgress(Parent_Time_in_Time_out.this);

        if (requestCode == studentCheckincheckoutView) {
            System.out.println("Response for studentCheckincheckoutView------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray Check_In_check_Out_array=result.getJSONArray("checkincheckout");
                    Parent_Time_In_Adapter.student_pic=result.getString("student_pic");


                    if (Check_In_check_Out_array.length()>0){
                        rv_check_in_check_out.setVisibility(View.VISIBLE);
                        tv_parent_time_in_time_out.setVisibility(View.GONE);

                        for(int i=0;i<Check_In_check_Out_array.length();i++){

                            JSONObject jsonObject1=Check_In_check_Out_array.getJSONObject(i);
                            Student_SetGet student_setGet=new Student_SetGet();

                            student_setGet.setCheck_in_time(jsonObject1.getString("check_in_time"));
                            student_setGet.setCheck_out_time(jsonObject1.getString("check_out_time"));
                            student_setGet.setDate(jsonObject1.getString("date"));


                            previous_arraylist.add(student_setGet);

                        }

                        Parent_Time_In_Adapter adapter=new Parent_Time_In_Adapter(Parent_Time_in_Time_out.this,previous_arraylist);
                        rv_check_in_check_out.setAdapter(adapter);
                    }else {
                        rv_check_in_check_out.setVisibility(View.GONE);
                        tv_parent_time_in_time_out.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(Parent_Time_in_Time_out.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Parent_Time_in_Time_out.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Parent_Time_in_Time_out.this);
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
        Intent intent=new Intent(Parent_Time_in_Time_out.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
