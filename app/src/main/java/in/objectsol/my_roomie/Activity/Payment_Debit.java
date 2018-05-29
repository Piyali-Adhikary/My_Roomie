package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import in.objectsol.my_roomie.Adapter.Student_Attendance_Warden_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 25/04/18.
 */

public class Payment_Debit extends Activity implements IJSONParseListener{

    ImageView iv_back;
    TextView tv_amount_debit;
    EditText et_card_number,et_expiry_date,et_card_cvv,et_card_holder_name;
    Button btn_complete_payment;
    String amount,payment_for,id,created_at,payment_status,year,month;

    String date="";
    public static boolean isfromParent=false;
    int parentPayment=612;
    int studentPayment=613;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;

    private static final String Student_Payment_URL = "http://174.136.1.35/dev/myroomie/student/studentPayment/";
    private static final String Parent_Payment_URL = "http://174.136.1.35/dev/myroomie/parents/parentPayment/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debit_card_layout);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        if (isfromParent){
            sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        }

        iv_back=(ImageView) findViewById(R.id.iv_back_comments);
        et_card_number=(EditText) findViewById(R.id.et_card_number);
        et_expiry_date=(EditText) findViewById(R.id.et_expiry_date);
        et_card_cvv=(EditText) findViewById(R.id.et_card_cvv);
        et_card_holder_name=(EditText) findViewById(R.id.et_card_holder_name);
        btn_complete_payment=(Button) findViewById(R.id.btn_complete_payment);
        tv_amount_debit=(TextView) findViewById(R.id.tv_amount_debit);


        amount=getIntent().getStringExtra("amount");
        payment_for=getIntent().getStringExtra("payment_for");
        payment_status=getIntent().getStringExtra("payment_status");
        id=getIntent().getStringExtra("id");
        year=getIntent().getStringExtra("year");
        month=getIntent().getStringExtra("month");
        created_at=getIntent().getStringExtra("created_at");

        tv_amount_debit.setText("Payable Amount : " +amount);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        et_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_expiry_date.requestFocus();
                datePicker();
            }
        });

        btn_complete_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_card_number.getText().toString().equalsIgnoreCase("") || et_card_number.getText().toString().length()>16 || et_card_number.getText().toString().length()<16){
                    Toast.makeText(Payment_Debit.this, "Please Enter Your Card Number.", Toast.LENGTH_SHORT).show();
                }else if (et_expiry_date.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Payment_Debit.this, "Please Enter Your Card Expiry Date.", Toast.LENGTH_SHORT).show();
                }else if (et_card_cvv.getText().toString().equalsIgnoreCase("") || et_card_cvv.getText().toString().length()>3 || et_card_cvv.getText().toString().length()< 3 ){
                    Toast.makeText(Payment_Debit.this, "Please Enter Your Card CVV/CVC Number.", Toast.LENGTH_SHORT).show();
                }else {

                    if (Utils.isNetworkAvailable(Payment_Debit.this)) {

                        if(isfromParent){
                            getparentPayment();
                        }else {
                            getstudentPayment();
                        }


                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Payment_Debit.this);
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
                        String month_date="";
                        if((monthOfYear+1)<10){
                            month_date="0"+(monthOfYear+1);
                        }
                        date = month_date+"/"+ String.valueOf(year);
                        et_expiry_date.setText(date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();



    }


    void getparentPayment() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Payment_Debit.this);
        Bundle parms = new Bundle();
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("year", year);
        parms.putString("payment_id", id);
        parms.putString("month", month);
        parms.putString("payment_amount", amount);
        parms.putString("payment_for", payment_for);
        parms.putString("payment_status", payment_status);
        parms.putString("payment_created_at", created_at);
        parms.putString("card_no", et_card_number.getText().toString());
        parms.putString("cvv", et_card_cvv.getText().toString());
        parms.putString("expiry_date", et_expiry_date.getText().toString());

        MyVolley.init(Payment_Debit.this);
        ShowProgressDilog(Payment_Debit.this);
        mResponse.getResponse(Request.Method.POST, Parent_Payment_URL, parentPayment, Payment_Debit.this, parms, false);
    }

    void getstudentPayment() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Payment_Debit.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("year", year);
        parms.putString("payment_id", id);
        parms.putString("month", month);
        parms.putString("payment_amount", amount);
        parms.putString("payment_for", payment_for);
        parms.putString("payment_status", payment_status);
        parms.putString("payment_created_at", created_at);
        parms.putString("card_no", et_card_number.getText().toString());
        parms.putString("cvv", et_card_cvv.getText().toString());
        parms.putString("expiry_date", et_expiry_date.getText().toString());

        MyVolley.init(Payment_Debit.this);
        ShowProgressDilog(Payment_Debit.this);
        mResponse.getResponse(Request.Method.POST, Student_Payment_URL, studentPayment, Payment_Debit.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Payment_Debit.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Payment_Debit.this);
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

        DismissProgress(Payment_Debit.this);

        if (requestCode == parentPayment) {
            System.out.println("Response for parentPayment------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(Payment_Debit.this, response.getString("result") , Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Payment_Debit.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Payment_Debit.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Payment_Debit.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Payment_Debit.this);
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
