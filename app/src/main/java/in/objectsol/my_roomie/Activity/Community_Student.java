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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.objectsol.my_roomie.Adapter.Student_Community_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Comment_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 02/05/18.
 */

public class Community_Student extends Activity implements IJSONParseListener{

    RecyclerView rv_students_community;
    ImageView iv_back_community;
    EditText et_search;
    Button btn_submit_student_community;
    SharedPreferences sharedPreferences;
    int searchStudent=611;
    ProgressDialog pDialog;
    ArrayList<Student_SetGet> student_setGetArrayList;
    String student="";

    private static final String Search_Student_URL = "http://174.136.1.35/dev/myroomie/student/searchStudent/";
    LinearLayoutManager layoutManager;
    Student_Community_Adapter student_community_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        student_setGetArrayList=new ArrayList<>();
        iv_back_community=(ImageView) findViewById(R.id.iv_back_community);
        rv_students_community=(RecyclerView)findViewById(R.id.rv_students_community);
        et_search=(EditText)findViewById(R.id.et_search_student_community);
        btn_submit_student_community=(Button) findViewById(R.id.btn_submit_student_community);

        rv_students_community.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_students_community.setLayoutManager(layoutManager);
        rv_students_community.setItemAnimator(new DefaultItemAnimator());

        //Calling API

        if (Utils.isNetworkAvailable(Community_Student.this)) {

                viewSearchStudent();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student.this);
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

        /*et_search.addTextChangedListener(new TextWatcher() {


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
        });*/

        btn_submit_student_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(Community_Student.this)) {

                    if(et_search.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(Community_Student.this, "Please enter some text to search.", Toast.LENGTH_SHORT).show();
                    }else {

                        viewSearchStudent();
                    }


                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student.this);
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

        iv_back_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Community_Student.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void viewSearchStudent() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Community_Student.this);
        Bundle parms = new Bundle();
        parms.putString("student", et_search.getText().toString());


        MyVolley.init(Community_Student.this);
        ShowProgressDilog(Community_Student.this);
        mResponse.getResponse(Request.Method.POST, Search_Student_URL, searchStudent, Community_Student.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Community_Student.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Community_Student.this);
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

        DismissProgress(Community_Student.this);
        student_setGetArrayList.clear();

        if (requestCode == searchStudent) {
            System.out.println("Response for searchStudent------" + response.toString());
            hideSoftKeyboard();
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
                        student_setGetArrayList.add(student_setGet);

                    }


                    student_community_adapter=new Student_Community_Adapter(Community_Student.this,student_setGetArrayList);
                    rv_students_community.setAdapter(student_community_adapter);

                }else {
                    Toast.makeText(Community_Student.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Community_Student.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Community_Student.this);
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

    /*private void filter(String text) {
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
                    student_setGet.setStatus(student_setGetArrayList.get(j).getBed_no());
                    student_setGet.setRoom_id(student_setGetArrayList.get(j).getRoom_id());
                    student_setGet.setRoom_no(student_setGetArrayList.get(j).getRoom_no());

                    student_searchArrayList.add(student_setGet);
                }
            }

        }

        //calling a method of the adapter class and passing the filtered list

        if(student_community_adapter!=null){
            student_community_adapter.filterList(student_searchArrayList);
        }

    }*/

    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Community_Student.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
