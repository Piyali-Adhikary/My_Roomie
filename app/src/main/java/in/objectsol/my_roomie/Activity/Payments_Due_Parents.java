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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import in.objectsol.my_roomie.Adapter.Payment_Status_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Payment_SetGet;
import in.objectsol.my_roomie.SetGet.Payment_Status_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 23/03/18.
 */

public class Payments_Due_Parents extends Activity implements IJSONParseListener{

    RecyclerView rv_payment_status;
    ImageView iv_back;
    int year=0;

    LinearLayoutManager layoutManager;
    int viewPaymentStatus=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<Payment_SetGet> payment_setGetArrayList;
    private static final String View_Payment_Status_URL = "http://174.136.1.35/dev/myroomie/parents/viewDuePayments/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_status);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        payment_setGetArrayList=new ArrayList<>();
        rv_payment_status=(RecyclerView) findViewById(R.id.rv_payment_status);
        iv_back=(ImageView) findViewById(R.id.iv_back_payment_status);

        rv_payment_status.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        rv_payment_status.setLayoutManager(layoutManager);
        rv_payment_status.setItemAnimator(new DefaultItemAnimator());

        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Payments_Due_Parents.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if (Utils.isNetworkAvailable(Payments_Due_Parents.this)) {

            viewPaymentStatus();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Payments_Due_Parents.this);
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

    void viewPaymentStatus() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Payments_Due_Parents.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("year", String.valueOf(year));

        MyVolley.init(Payments_Due_Parents.this);
        ShowProgressDilog(Payments_Due_Parents.this);
        mResponse.getResponse(Request.Method.POST, View_Payment_Status_URL, viewPaymentStatus, Payments_Due_Parents.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Payments_Due_Parents.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Payments_Due_Parents.this);
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

        DismissProgress(Payments_Due_Parents.this);

        if (requestCode == viewPaymentStatus) {
            System.out.println("Response for viewPaymentStatus------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    JSONArray jan_array=result.getJSONArray("January");
                    JSONArray feb_array=result.getJSONArray("February");
                    JSONArray march_array=result.getJSONArray("March");
                    JSONArray april_array=result.getJSONArray("April");
                    JSONArray may_array=result.getJSONArray("May");
                    JSONArray june_array=result.getJSONArray("June");
                    JSONArray july_array=result.getJSONArray("July");
                    JSONArray aug_array=result.getJSONArray("August");
                    JSONArray sept_array=result.getJSONArray("September");
                    JSONArray oct_array=result.getJSONArray("October");
                    JSONArray nov_array=result.getJSONArray("November");
                    JSONArray dec_array=result.getJSONArray("December");
                    Payment_SetGet payment_setGet=new Payment_SetGet();

                    if(jan_array.length()>0){
                        ArrayList<Payment_Status_SetGet> jan_arraylist=new ArrayList<>();
                        for (int i=0;i< jan_array.length();i++){
                            JSONObject jsonObject=jan_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            jan_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_jan(jan_arraylist);
                    }

                    if(feb_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< feb_array.length();i++){
                            JSONObject jsonObject=feb_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_feb(feb_arraylist);

                    }

                    if(march_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< march_array.length();i++){
                            JSONObject jsonObject=march_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_march(feb_arraylist);

                    }

                    if(april_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< april_array.length();i++){
                            JSONObject jsonObject=april_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_april(feb_arraylist);

                    }

                    if(may_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< may_array.length();i++){
                            JSONObject jsonObject=may_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_may(feb_arraylist);

                    }

                    if(june_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< june_array.length();i++){
                            JSONObject jsonObject=june_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_june(feb_arraylist);

                    }

                    if(july_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< july_array.length();i++){
                            JSONObject jsonObject=july_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_july(feb_arraylist);

                    }

                    if(aug_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< aug_array.length();i++){
                            JSONObject jsonObject=aug_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_aug(feb_arraylist);

                    }

                    if(sept_array.length()>0){
                        ArrayList<Payment_Status_SetGet> feb_arraylist=new ArrayList<>();
                        for (int i=0;i< sept_array.length();i++){
                            JSONObject jsonObject=sept_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            feb_arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_sept(feb_arraylist);

                    }

                    if(oct_array.length()>0){
                        ArrayList<Payment_Status_SetGet> arraylist=new ArrayList<>();
                        for (int i=0;i< oct_array.length();i++){
                            JSONObject jsonObject=oct_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_oct(arraylist);

                    }

                    if(nov_array.length()>0){
                        ArrayList<Payment_Status_SetGet> arraylist=new ArrayList<>();
                        for (int i=0;i< nov_array.length();i++){
                            JSONObject jsonObject=nov_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_nov(arraylist);

                    }

                    if(dec_array.length()>0){
                        ArrayList<Payment_Status_SetGet> arraylist=new ArrayList<>();
                        for (int i=0;i< dec_array.length();i++){
                            JSONObject jsonObject=dec_array.getJSONObject(i);
                            Payment_Status_SetGet payment_status_setGet=new Payment_Status_SetGet();

                            payment_status_setGet.setId(jsonObject.getString("id"));
                            payment_status_setGet.setCampus_id(jsonObject.getString("campus_id"));
                            payment_status_setGet.setStudent_id(jsonObject.getString("student_id"));
                            payment_status_setGet.setYear(jsonObject.getString("year"));
                            payment_status_setGet.setMonth(jsonObject.getString("month"));
                            payment_status_setGet.setPayment_amount(jsonObject.getString("payment_amount"));
                            payment_status_setGet.setPayment_status(jsonObject.getString("payment_status"));
                            payment_status_setGet.setPayment_date(jsonObject.getString("payment_date"));
                            payment_status_setGet.setPayment_for(jsonObject.getString("payment_for"));
                            payment_status_setGet.setCreated_at(jsonObject.getString("created_at"));

                            arraylist.add(payment_status_setGet);
                        }

                        payment_setGet.setArrayList_dec(arraylist);

                    }

                    payment_setGetArrayList.add(payment_setGet);

                    Payment_Status_Adapter adapter=new Payment_Status_Adapter(Payments_Due_Parents.this,payment_setGetArrayList);
                    rv_payment_status.setAdapter(adapter);

                }else {
                    Toast.makeText(Payments_Due_Parents.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Payments_Due_Parents.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Payments_Due_Parents.this);
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
        Intent intent=new Intent(Payments_Due_Parents.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
