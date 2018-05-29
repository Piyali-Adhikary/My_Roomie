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

import in.objectsol.my_roomie.Adapter.Comments_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Attendance_Warden_Adapter;
import in.objectsol.my_roomie.Adapter.Visitor_Warden_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Comment_SetGet;
import in.objectsol.my_roomie.SetGet.Visitor_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 23/04/18.
 */

public class Warden_Comment extends Activity implements IJSONParseListener{

    RecyclerView rv_comment_student_attendance;
    ImageView iv_back;
    TextView tv_save;
    EditText et_comments_student_attendance;

    int studentsPrevAttendanceCommentByWarden=610;
    int studentsCommentsByWarden=611;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private static final String Students_Comments_By_Warden_URL = "http://174.136.1.35/dev/myroomie/warden/studentsCommentsByWarden/";
    private static final String Students_Prev_Attendance_Comment_By_Warden_URL = "http://174.136.1.35/dev/myroomie/warden/studentsPrevAttendanceCommentByWarden/";

    LinearLayoutManager layoutManager;
    Comments_Adapter comments_adapter;
    ArrayList<Comment_SetGet> comment_setGetArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_student_attendance_by_warden);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        comment_setGetArrayList=new ArrayList<>();
        iv_back=(ImageView) findViewById(R.id.iv_back_comments);
        rv_comment_student_attendance=(RecyclerView)findViewById(R.id.rv_comment_student_attendance);
        tv_save=(TextView)findViewById(R.id.tv_save_comment_student_attendance);
        et_comments_student_attendance=(EditText) findViewById(R.id.et_comments_student_attendance);

        rv_comment_student_attendance.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_comment_student_attendance.setLayoutManager(layoutManager);
        rv_comment_student_attendance.setItemAnimator(new DefaultItemAnimator());


        //Calling API
        if (Utils.isNetworkAvailable(Warden_Comment.this)) {


            getStudentsCommentsByWarden();


        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Comment.this);
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


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Calling API
                if (Utils.isNetworkAvailable(Warden_Comment.this)) {

                    if(et_comments_student_attendance.getText().toString().equalsIgnoreCase("")){

                        Toast.makeText(Warden_Comment.this, "Please enter your comment", Toast.LENGTH_SHORT).show();
                    }else {
                        getStudentsPrevAttendanceCommentByWarden();
                    }

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Comment.this);
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



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(Warden_Comment.this,MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }

    void getStudentsPrevAttendanceCommentByWarden() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Comment.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("student_id", Student_Attendance_Warden_Adapter.student_id_comment);
        parms.putString("date", Student_Attendance_Warden_Adapter.date_comment);
        parms.putString("comment", et_comments_student_attendance.getText().toString());
        parms.putString("status", Student_Attendance_Warden_Adapter.status_comment);


        MyVolley.init(Warden_Comment.this);
        ShowProgressDilog(Warden_Comment.this);
        mResponse.getResponse(Request.Method.POST, Students_Prev_Attendance_Comment_By_Warden_URL, studentsPrevAttendanceCommentByWarden, Warden_Comment.this, parms, false);
    }

    void getStudentsCommentsByWarden() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Comment.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("student_id", Student_Attendance_Warden_Adapter.student_id_comment);
        parms.putString("date", Student_Attendance_Warden_Adapter.date_comment);


        MyVolley.init(Warden_Comment.this);
        ShowProgressDilog(Warden_Comment.this);
        mResponse.getResponse(Request.Method.POST, Students_Comments_By_Warden_URL, studentsCommentsByWarden, Warden_Comment.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Warden_Comment.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Comment.this);
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

        DismissProgress(Warden_Comment.this);

        if (requestCode == studentsPrevAttendanceCommentByWarden) {
            System.out.println("Response for studentsPrevAttendanceCommentByWarden------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");
                    comment_setGetArrayList.clear();
                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Comment_SetGet comment_setGet =new Comment_SetGet();
                        comment_setGet.setId(jsonObject.getString("id"));
                        comment_setGet.setStudent_id(jsonObject.getString("student_id"));
                        comment_setGet.setDate(jsonObject.getString("date"));
                        comment_setGet.setStatus(jsonObject.getString("status"));
                        comment_setGet.setComment(jsonObject.getString("comment"));

                        comment_setGetArrayList.add(comment_setGet);



                    }


                    comments_adapter=new Comments_Adapter(Warden_Comment.this,comment_setGetArrayList);
                    rv_comment_student_attendance.setAdapter(comments_adapter);
                    et_comments_student_attendance.getText().clear();

                }else {
                    Toast.makeText(Warden_Comment.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }else  if (requestCode == studentsCommentsByWarden) {
            System.out.println("Response for studentsCommentsByWarden------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");
                    comment_setGetArrayList.clear();
                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Comment_SetGet comment_setGet =new Comment_SetGet();
                        comment_setGet.setId(jsonObject.getString("id"));
                        comment_setGet.setStudent_id(jsonObject.getString("student_id"));
                        comment_setGet.setDate(jsonObject.getString("date"));
                        comment_setGet.setStatus(jsonObject.getString("status"));
                        comment_setGet.setComment(jsonObject.getString("comment"));

                        comment_setGetArrayList.add(comment_setGet);



                    }


                    comments_adapter=new Comments_Adapter(Warden_Comment.this,comment_setGetArrayList);
                    rv_comment_student_attendance.setAdapter(comments_adapter);

                }else {
                    Toast.makeText(Warden_Comment.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Warden_Comment.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Warden_Comment.this);
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
