package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class Community_Student_Profile extends Activity implements IJSONParseListener{

    TextView tv_student_name,tv_student_membership,tv_student_name_two,tv_student_dob,tv_student_university;
    TextView tv_student_email,tv_student_phone,tv_student_gender,tv_student_room_number;
    ImageView iv_profile_pic;
    public static String student_id="";

    int studentdetails=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;

    private static final String Student_Details_URL = "http://174.136.1.35/dev/myroomie/student/studentDtl/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_community);

        student_setGetArrayList=new ArrayList<>();
        tv_student_name=(TextView) findViewById(R.id.tv_student_name_community_profile);
        iv_profile_pic=(ImageView) findViewById(R.id.iv_profile_pic_community_profile);
        tv_student_membership=(TextView) findViewById(R.id.tv_student_membership_community_profile);
        tv_student_name_two=(TextView) findViewById(R.id.tv_student_name_two_community_profile);
        tv_student_dob=(TextView) findViewById(R.id.tv_student_dob_community_profile);
        tv_student_university=(TextView) findViewById(R.id.tv_student_university_community_profile);
        tv_student_email=(TextView) findViewById(R.id.tv_student_email_community_profile);
        tv_student_phone=(TextView) findViewById(R.id.tv_student_phone_community_profile);
        tv_student_gender=(TextView) findViewById(R.id.tv_student_gender_community_profile);
        tv_student_room_number=(TextView) findViewById(R.id.tv_student_room_number_community_profile);

        //Calling API

        if (Utils.isNetworkAvailable(Community_Student_Profile.this)) {

            StudentDetails();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student_Profile.this);
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

    void StudentDetails() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Community_Student_Profile.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", student_id);


        MyVolley.init(Community_Student_Profile.this);
        ShowProgressDilog(Community_Student_Profile.this);
        mResponse.getResponse(Request.Method.POST, Student_Details_URL, studentdetails, Community_Student_Profile.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Community_Student_Profile.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student_Profile.this);
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

        DismissProgress(Community_Student_Profile.this);
        student_setGetArrayList.clear();
        if (requestCode == studentdetails) {
            System.out.println("Response for studentdetails------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0;i<result_Array.length();i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Student_SetGet student_setGet=new Student_SetGet();
                        student_setGet.setStudent_id(jsonObject.getString("student_id"));
                        student_setGet.setCampus_id(jsonObject.getString("campus_id"));
                        student_setGet.setStudent_name(jsonObject.getString("student_name"));
                        student_setGet.setStudent_pic(jsonObject.getString("student_pic"));
                        student_setGet.setEmail(jsonObject.getString("email"));
                        student_setGet.setAddress(jsonObject.getString("address"));
                        student_setGet.setStatus(jsonObject.getString("bed_no"));
                        student_setGet.setRoom_id(jsonObject.getString("room_id"));
                        student_setGet.setRoom_no(jsonObject.getString("room_no"));
                        student_setGet.setMobile_number(jsonObject.getString("mobile_number"));
                        student_setGet.setDob(jsonObject.getString("dob"));
                        student_setGet.setMember_since(jsonObject.getString("member_since"));
                        student_setGet.setUniversity(jsonObject.getString("university"));
                        student_setGet.setGender(jsonObject.getString("sex"));
                        student_setGetArrayList.add(student_setGet);

                    }

                    tv_student_name.setText(student_setGetArrayList.get(0).getStudent_name());
                    tv_student_name_two.setText(student_setGetArrayList.get(0).getStudent_name());
                    Glide.with(Community_Student_Profile.this).load(student_setGetArrayList.get(0).getStudent_pic())
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(iv_profile_pic);
                    tv_student_dob.setText(student_setGetArrayList.get(0).getDob());
                    tv_student_email.setText(student_setGetArrayList.get(0).getEmail());
                    tv_student_phone.setText(student_setGetArrayList.get(0).getMobile_number());
                    tv_student_gender.setText(student_setGetArrayList.get(0).getGender());
                    tv_student_university.setText(student_setGetArrayList.get(0).getUniversity());
                    tv_student_membership.setText("MyRoomie Member Since "+student_setGetArrayList.get(0).getMember_since());
                    tv_student_room_number.setText("Room No "+student_setGetArrayList.get(0).getRoom_no());

                }else {
                    Toast.makeText(Community_Student_Profile.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Community_Student_Profile.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Community_Student_Profile.this);
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
