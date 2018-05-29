package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.objectsol.my_roomie.Adapter.Manage_printer_list;
import in.objectsol.my_roomie.Adapter.Rooms_Adapter;
import in.objectsol.my_roomie.Adapter.Student_Attendance_Warden_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Comment_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Attendance_By_Warden extends Activity implements IJSONParseListener{

    RecyclerView rv_student_list;
    ImageView iv_back_student_attendance;
    EditText et_search,et_enter_date;
    TextView actv_rooms;
    Button btn_send_student_attendance;
    LinearLayout ll_send_attendance;
    public static String date="",current_date="", date_adapter="";
    int viewStudentByHostel=611;
    int viewRooomsByHostel=613;
    int studentsDayAttendanceByWarden=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Student_SetGet> student_setGetArrayList;
    ArrayList<Comment_SetGet> rooms_arraylist;

    LinearLayoutManager layoutManager;
    Student_Attendance_Warden_Adapter warden_adapter;


    public static String room="1";


    private static final String View_Rooms_By_Hostel_URL = "http://174.136.1.35/dev/myroomie/warden/viewRooomsByHostel/";
    private static final String View_Student_By_Hostel_URL = "http://174.136.1.35/dev/myroomie/warden/viewStudentByHostelRooms/";
    private static final String Students_Day_Attendance_By_Warden_URL = "http://174.136.1.35/dev/myroomie/warden/studentsDayAttendanceByWarden/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        rooms_arraylist=new ArrayList<>();
        iv_back_student_attendance=(ImageView) findViewById(R.id.iv_back_student_attendance);
        rv_student_list=(RecyclerView)findViewById(R.id.rv_students);
        et_search=(EditText)findViewById(R.id.et_search_student_attendance);
        et_enter_date=(EditText)findViewById(R.id.et_enter_date);
        btn_send_student_attendance=(Button) findViewById(R.id.btn_send_student_attendance);
        ll_send_attendance=(LinearLayout) findViewById(R.id.ll_send_attendance);
        actv_rooms=(TextView) findViewById(R.id.actv_rooms);
        student_setGetArrayList=new ArrayList<>();


        rv_student_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv_student_list.setLayoutManager(layoutManager);
        rv_student_list.setItemAnimator(new DefaultItemAnimator());

        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour= c.get(Calendar.HOUR_OF_DAY);
        if(mHour<2){
            mDay=mDay-1;
        }

        current_date= String.valueOf(mDay) +"/"+String.valueOf(mMonth+1)+"/"+String.valueOf(mYear);
        date=current_date;
        et_enter_date.setText(date);


        //Calling API

        if (Utils.isNetworkAvailable(Student_Attendance_By_Warden.this)) {

            if (et_enter_date.getText().toString().equalsIgnoreCase("")){

                Toast.makeText(this, "Please select date.", Toast.LENGTH_SHORT).show();
            }else {

                viewRooomsByHostel();

            }

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Attendance_By_Warden.this);
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





        actv_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int[] locations = new int[2];
                actv_rooms.getLocationOnScreen(locations);
                int x = locations[0];
                int y = locations[1];

                if (rooms_arraylist.size() > 0) {
                    showPopup_Error(Student_Attendance_By_Warden.this, actv_rooms, actv_rooms.getWidth(), x, y);
                } else
                    Toast.makeText(Student_Attendance_By_Warden.this, "No rooms available", Toast.LENGTH_SHORT).show();

            }
        });
        et_enter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_enter_date.requestFocus();
                datePicker();

            }
        });

        et_enter_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Utils.isNetworkAvailable(Student_Attendance_By_Warden.this)) {

                    if (et_enter_date.getText().toString().equalsIgnoreCase("")){

                        Toast.makeText(Student_Attendance_By_Warden.this, "Please select date.", Toast.LENGTH_SHORT).show();
                    }else {

                        if(et_enter_date.getText().toString().equalsIgnoreCase(current_date)){
                            ll_send_attendance.setVisibility(View.VISIBLE);
                        }else {
                            ll_send_attendance.setVisibility(View.GONE);
                        }
                        student_setGetArrayList.clear();
                        viewStudentByHostel(room);
                    }

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Attendance_By_Warden.this);
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

        iv_back_student_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Student_Attendance_By_Warden.this,MainActivity.class);
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

        btn_send_student_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Student_Attendance_Warden_Adapter.submit_student_attendance_arraylist==null){

                }else{
                    studentsDayAttendanceByWarden();
                }



            }
        });
    }




    public void showPopup_Error(final Activity context, final TextView comments, int width_new, int x, int y) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        int popupWidth = width_new;
        int popupHeight = (height) - 300;


        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.total_view);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.requisition_comments, viewGroup);

        ListView requisition_list_Comments = (ListView) layout.findViewById(R.id.requisition_list_comments);



        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                popup.dismiss();

            }
        });
        if (rooms_arraylist.size() > 0) {
            requisition_list_Comments.setAdapter(new Manage_printer_list(context,R.layout.wastage_single,rooms_arraylist));


            requisition_list_Comments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    comments.setText(rooms_arraylist.get(i).getRooms());


                    room=rooms_arraylist.get(i).getRooms();


                    viewStudentByHostel(rooms_arraylist.get(i).getRooms());


                    popup.dismiss();
                }
            });
        }


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        //popup.setAnimationStyle(R.style.Animation);
        popup.showAsDropDown(comments);


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
                    student_setGet.setStatus(student_setGetArrayList.get(j).getStatus());

                    student_searchArrayList.add(student_setGet);
                }
            }

        }

        //calling a method of the adapter class and passing the filtered list

        if(warden_adapter!=null){
            warden_adapter.filterList(student_searchArrayList);
        }

    }

    void studentsDayAttendanceByWarden() {

        String attendance="";

        if(Student_Attendance_Warden_Adapter.submit_student_attendance_arraylist!=null && Student_Attendance_Warden_Adapter.submit_student_attendance_arraylist.size()>0){

            try{
                JSONArray jsonArray= new JSONArray(Student_Attendance_Warden_Adapter.submit_student_attendance_arraylist);
                attendance=jsonArray.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {

            try{
                JSONArray jsonArray= new JSONArray();
                for(int i=0;i<student_setGetArrayList.size();i++){
                    JSONObject jsonObject=new JSONObject();

                    jsonObject.put("warden_id", sharedPreferences.getString("warden_id",""));
                    jsonObject.put("campus_id", sharedPreferences.getString("campus_id",""));
                    jsonObject.put("auth_key", sharedPreferences.getString("auth_token",""));
                    jsonObject.put("student_id", student_setGetArrayList.get(i).getStudent_id());
                    jsonObject.put("status", "");
                    jsonObject.put("date", date);

                    jsonArray.put(jsonObject);
                }
                attendance=jsonArray.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Attendance_By_Warden.this);
        Bundle parms = new Bundle();
        parms.putString("attendance", attendance);

        MyVolley.init(Student_Attendance_By_Warden.this);
        ShowProgressDilog(Student_Attendance_By_Warden.this);
        mResponse.getResponse(Request.Method.POST, Students_Day_Attendance_By_Warden_URL, studentsDayAttendanceByWarden, this, parms, false);
    }

    void viewStudentByHostel(String roomid) {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Attendance_By_Warden.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("date", et_enter_date.getText().toString());
        parms.putString("room_id", roomid);

        MyVolley.init(Student_Attendance_By_Warden.this);
        ShowProgressDilog(Student_Attendance_By_Warden.this);
        mResponse.getResponse(Request.Method.POST, View_Student_By_Hostel_URL, viewStudentByHostel, Student_Attendance_By_Warden.this, parms, false);
    }

    void viewRooomsByHostel() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Student_Attendance_By_Warden.this);
        Bundle parms = new Bundle();
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Student_Attendance_By_Warden.this);
        ShowProgressDilog(Student_Attendance_By_Warden.this);
        mResponse.getResponse(Request.Method.POST, View_Rooms_By_Hostel_URL, viewRooomsByHostel, Student_Attendance_By_Warden.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Student_Attendance_By_Warden.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Attendance_By_Warden.this);
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

        DismissProgress(Student_Attendance_By_Warden.this);
        student_setGetArrayList.clear();

        if (requestCode == viewStudentByHostel) {
            System.out.println("Response for viewStudentByHostel------" + response.toString());

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
                        student_setGet.setStatus(jsonObject.getString("status"));
                        student_setGet.setRoom_no(jsonObject.getString("room_id"));
                        student_setGetArrayList.add(student_setGet);

                    }
                    /*for (int i=0;i<result_Array.length();i++){
                        JSONArray jsonArray=result_Array.getJSONArray(i);
                        Student_SetGet student_setGet=new Student_SetGet();
                        for(int j=0;j<jsonArray.length();j++){

                            JSONObject jsonObject=jsonArray.getJSONObject(j);

                            if(j==0){
                                student_setGet.setStudent_id(jsonObject.getString("student_id"));
                                student_setGet.setCampus_id(jsonObject.getString("campus_id"));
                                student_setGet.setStudent_name(jsonObject.getString("student_name"));
                                student_setGet.setStudent_pic(jsonObject.getString("student_pic"));
                                student_setGet.setEmail(jsonObject.getString("email"));
                                student_setGet.setAddress(jsonObject.getString("address"));

                            }else if(j==1){

                                student_setGet.setStatus(jsonObject.getString("status"));
                                student_setGetArrayList.add(student_setGet);
                            }


                        }


                    }*/

                    warden_adapter=new Student_Attendance_Warden_Adapter(Student_Attendance_By_Warden.this,student_setGetArrayList);
                    rv_student_list.setAdapter(warden_adapter);

                }else {
                    Toast.makeText(Student_Attendance_By_Warden.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }else if (requestCode == studentsDayAttendanceByWarden) {
            System.out.println("Response for studentsDayAttendanceByWarden------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Student_Attendance_By_Warden.this, response.getString("result") , Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Student_Attendance_By_Warden.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Student_Attendance_By_Warden.this,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }else if (requestCode == viewRooomsByHostel) {
            System.out.println("Response for viewRooomsByHostel------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_Array=response.getJSONArray("result");

                    for (int i=0; i<result_Array.length(); i++){
                        JSONObject jsonObject=result_Array.getJSONObject(i);

                        Comment_SetGet comment_setGet=new Comment_SetGet();
                        comment_setGet.setId(jsonObject.getString("id"));
                        comment_setGet.setCampus_id(jsonObject.getString("campus_id"));
                        comment_setGet.setRooms(jsonObject.getString("room_no"));

                        rooms_arraylist.add(comment_setGet);
                    }

                   /* Rooms_Adapter rooms_adapter=new Rooms_Adapter(Student_Attendance_By_Warden.this,rooms_arraylist);
                    actv_rooms.setAdapter(rooms_adapter);*/

                    viewStudentByHostel(room);
                }else {
                    Toast.makeText(Student_Attendance_By_Warden.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Student_Attendance_By_Warden.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Student_Attendance_By_Warden.this);
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

    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        date = String.valueOf(dayOfMonth) +"/"+String.valueOf(monthOfYear+1)+"/"+ String.valueOf(year);
                        et_enter_date.setText(date);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();



    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Student_Attendance_By_Warden.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
