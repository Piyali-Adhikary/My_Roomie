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
import java.util.Iterator;


import in.objectsol.my_roomie.Adapter.Food_Items_Adapter;
import in.objectsol.my_roomie.Adapter.Newsletter_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.NewsLetter_SetGet;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.SetGet.SetgetItems;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 20/03/18.
 */

public class Newsletter extends Activity implements IJSONParseListener {

    RecyclerView rv_newsletter;
    ImageView iv_back;

    LinearLayoutManager layoutManager;
    int viewNewsletter = 616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<NewsLetter_SetGet> newsletter_arrayList;
    private static final String View_Newsletter_URL = "http://174.136.1.35/dev/myroomie/student/viewNewsletter/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsletter);

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        newsletter_arrayList=new ArrayList<>();
        rv_newsletter = (RecyclerView) findViewById(R.id.rv_newsletter);
        iv_back = (ImageView) findViewById(R.id.iv_back_newsletter);

        rv_newsletter.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_newsletter.setLayoutManager(layoutManager);
        rv_newsletter.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Newsletter.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if (Utils.isNetworkAvailable(Newsletter.this)) {

            getNewsletter();

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Newsletter.this);
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

    void getNewsletter() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Newsletter.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Newsletter.this);
        ShowProgressDilog(Newsletter.this);
        mResponse.getResponse(Request.Method.POST, View_Newsletter_URL, viewNewsletter, Newsletter.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Newsletter.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Newsletter.this);
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

        DismissProgress(Newsletter.this);

        if (requestCode == viewNewsletter) {
            System.out.println("Response for viewNewsletter------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONArray result_array=response.getJSONArray("result");

                    for(int i=0;i<result_array.length();i++){
                        JSONObject objects = result_array.getJSONObject(i);

                        NewsLetter_SetGet newsLetter_setGet=new NewsLetter_SetGet();
                        newsLetter_setGet.setNotice_id(objects.getString("notice_id"));
                        newsLetter_setGet.setCampus_id(objects.getString("campus_id"));
                        newsLetter_setGet.setNotice_title(objects.getString("notice_title"));
                        newsLetter_setGet.setNotice_content(objects.getString("notice_content"));
                        newsLetter_setGet.setDatetime(objects.getString("datetime"));

                        newsletter_arrayList.add(newsLetter_setGet);

                    }

                    Newsletter_Adapter newsletter_adapter= new Newsletter_Adapter(Newsletter.this,newsletter_arrayList);
                    rv_newsletter.setAdapter(newsletter_adapter);

                }else {
                    Toast.makeText(Newsletter.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Newsletter.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Newsletter.this);
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
        Intent intent = new Intent(Newsletter.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
