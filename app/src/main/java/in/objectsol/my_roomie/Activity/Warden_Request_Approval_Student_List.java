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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import in.objectsol.my_roomie.Adapter.Warden_Request_Approval_Student_List_Adapter;
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

public class Warden_Request_Approval_Student_List extends Activity implements IJSONParseListener{

    RecyclerView rv_student_list;
    ImageView iv_back;
    EditText et_search;
    TextView tv_header;


    int viewStudentByHostel=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Student_SetGet> student_setGetArrayList;
    LinearLayoutManager layoutManager;
    Warden_Request_Approval_Student_List_Adapter warden_adapter;
    private static final String View_Student_By_Hostel_URL = "http://174.136.1.35/dev/myroomie/warden/viewStudentByHostel/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warden_time_in_time_out);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back=(ImageView) findViewById(R.id.iv_back_warden_time_in_time_out);
        rv_student_list=(RecyclerView)findViewById(R.id.rv_students_warden_time_in_time_out);
        et_search=(EditText)findViewById(R.id.et_search_warden_time_in_time_out);
        tv_header=(TextView)findViewById(R.id.tv_header_warden_time_in_time_out);

        rv_student_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_student_list.setLayoutManager(layoutManager);
        rv_student_list.setItemAnimator(new DefaultItemAnimator());

        tv_header.setText("Request Approvals");
        //Calling API
        if (Utils.isNetworkAvailable(Warden_Request_Approval_Student_List.this)) {

                viewStudentByHostel();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Request_Approval_Student_List.this);
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
                Intent intent=new Intent(Warden_Request_Approval_Student_List.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Student_SetGet> student_searchArrayList=new ArrayList<>();
        //looping through existing elements
        if(student_setGetArrayList.size()>0){
            for(int j=0;j<student_setGetArrayList.size();j++){

                if(student_setGetArrayList.get(j).getStudent_name().toLowerCase().contains(text.toLowerCase())){

                    Student_SetGet student_setGet=new Student_SetGet();

                    student_setGet.setStudent_id(student_setGetArrayList.get(j).getStudent_id());
                    student_setGet.setCampus_id(student_setGetArrayList.get(j).getCampus_id());
                    student_setGet.setStudent_name(student_setGetArrayList.get(j).getStudent_name());
                    student_setGet.setStudent_pic(student_setGetArrayList.get(j).getStudent_pic());
                    student_setGet.setEmail(student_setGetArrayList.get(j).getEmail());
                    student_setGet.setAddress(student_setGetArrayList.get(j).getAddress());
                    student_setGet.setRoom_no(student_setGetArrayList.get(j).getRoom_no());
                    student_setGet.setBed_no(student_setGetArrayList.get(j).getBed_no());
                    student_setGet.setDob(student_setGetArrayList.get(j).getDob());
                    student_setGet.setMobile_number(student_setGetArrayList.get(j).getMobile_number());
                    student_setGet.setPrivacy_permission_status(student_setGetArrayList.get(j).getPrivacy_permission_status());
                    student_setGet.setAuth_token(student_setGetArrayList.get(j).getAuth_token());
                    student_setGet.setLast_login(student_setGetArrayList.get(j).getLast_login());
                    student_setGet.setUser_type(student_setGetArrayList.get(j).getUser_type());


                    student_searchArrayList.add(student_setGet);
                }
            }

        }

        //calling a method of the adapter class and passing the filtered list
        if(warden_adapter!=null){
            warden_adapter.filterList(student_searchArrayList);
        }

    }

    void viewStudentByHostel() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Request_Approval_Student_List.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Warden_Request_Approval_Student_List.this);
        ShowProgressDilog(Warden_Request_Approval_Student_List.this);
        mResponse.getResponse(Request.Method.POST, View_Student_By_Hostel_URL, viewStudentByHostel, Warden_Request_Approval_Student_List.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Warden_Request_Approval_Student_List.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Request_Approval_Student_List.this);
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

        DismissProgress(Warden_Request_Approval_Student_List.this);

        if (requestCode == viewStudentByHostel) {
            System.out.println("Response for viewStudentByHostel------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray students_array=result.getJSONArray("Students");

                        for(int j=0;j<students_array.length();j++){

                            JSONObject jsonObject=students_array.getJSONObject(j);
                            Student_SetGet student_setGet=new Student_SetGet();

                                student_setGet.setStudent_id(jsonObject.getString("student_id"));
                                student_setGet.setCampus_id(jsonObject.getString("campus_id"));
                                student_setGet.setRoom_no(jsonObject.getString("room_no"));
                                student_setGet.setBed_no(jsonObject.getString("bed_no"));
                                student_setGet.setDob(jsonObject.getString("dob"));
                                student_setGet.setStudent_name(jsonObject.getString("student_name"));
                                student_setGet.setStudent_pic(jsonObject.getString("student_pic"));
                                student_setGet.setEmail(jsonObject.getString("email"));
                                student_setGet.setAddress(jsonObject.getString("address"));
                                student_setGet.setMobile_number(jsonObject.getString("mobile_number"));
                                student_setGet.setPrivacy_permission_status(jsonObject.getString("privacy_permission_status"));
                                student_setGet.setAuth_token(jsonObject.getString("auth_token"));
                                student_setGet.setLast_login(jsonObject.getString("last_login"));
                                student_setGet.setUser_type(jsonObject.getString("user_type"));


                                student_setGetArrayList.add(student_setGet);

                        }



                    warden_adapter= new Warden_Request_Approval_Student_List_Adapter(Warden_Request_Approval_Student_List.this,student_setGetArrayList);
                    rv_student_list.setAdapter(warden_adapter);

                }else {
                    Toast.makeText(Warden_Request_Approval_Student_List.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Warden_Request_Approval_Student_List.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Warden_Request_Approval_Student_List.this);
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
        Intent intent=new Intent(Warden_Request_Approval_Student_List.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
