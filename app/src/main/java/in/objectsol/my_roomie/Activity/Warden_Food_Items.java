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
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.SetGet.SetgetItems;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 20/03/18.
 */

public class Warden_Food_Items extends Activity implements IJSONParseListener {

    RecyclerView rv_food_items;
    ImageView iv_back;

    LinearLayoutManager layoutManager;
    int viewFoodMenu=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<Permission_SetGet> permission_setGetArrayList;
    private static final String View_Food_Menu_URL = "http://174.136.1.35/dev/myroomie/warden/viewFoodMenu/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_items);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        rv_food_items=(RecyclerView) findViewById(R.id.rv_food_items);
        iv_back=(ImageView) findViewById(R.id.iv_back_food_menu);

        rv_food_items.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_food_items.setLayoutManager(layoutManager);
        rv_food_items.setItemAnimator(new DefaultItemAnimator());

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Warden_Food_Items.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        if (Utils.isNetworkAvailable(Warden_Food_Items.this)) {

            viewFoodMenu();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Food_Items.this);
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

    void viewFoodMenu() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Warden_Food_Items.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Warden_Food_Items.this);
        ShowProgressDilog(Warden_Food_Items.this);
        mResponse.getResponse(Request.Method.POST, View_Food_Menu_URL, viewFoodMenu, Warden_Food_Items.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Warden_Food_Items.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Warden_Food_Items.this);
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

        DismissProgress(Warden_Food_Items.this);

        if (requestCode == viewFoodMenu) {
            System.out.println("Response for viewFoodMenu------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");
                    ArrayList<SetgetDay> dayList = new ArrayList<>();

                    Iterator<String> iter = result.keys();

                    while (iter.hasNext()){

                        SetgetDay obj = new SetgetDay();

                        String key = iter.next();

                        obj.setDayname(key);
                        JSONArray arr = result.getJSONArray(key);

                        ArrayList<SetgetItems> breakfastList = new ArrayList<>();
                        ArrayList<SetgetItems> lunchList = new ArrayList<>();
                        ArrayList<SetgetItems> tiffinList = new ArrayList<>();
                        ArrayList<SetgetItems> dinnerList = new ArrayList<>();

                        for (int i = 0 ; i < arr.length() ; i++){

                            SetgetItems breakfast = new SetgetItems();
                            breakfast.setName(arr.getJSONObject(i).getString("breakfast"));
                            breakfastList.add(breakfast);

                            SetgetItems lunch = new SetgetItems();
                            lunch.setName(arr.getJSONObject(i).getString("lunch"));
                            lunchList.add(lunch);

                            SetgetItems tiffin = new SetgetItems();
                            tiffin.setName(arr.getJSONObject(i).getString("tiffin"));
                            tiffinList.add(tiffin);

                            SetgetItems dinner = new SetgetItems();
                            dinner.setName(arr.getJSONObject(i).getString("dinner"));
                            dinnerList.add(dinner);


                        }

                        obj.setBreakfast(breakfastList);
                        obj.setLunch(lunchList);
                        obj.setTiffin(tiffinList);
                        obj.setDinner(dinnerList);
                        dayList.add(obj);
                    }

                    dayList.size();

                    Food_Items_Adapter food_items_adapter=new Food_Items_Adapter(Warden_Food_Items.this,dayList);
                    rv_food_items.setAdapter(food_items_adapter);
                }else {
                    Toast.makeText(Warden_Food_Items.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Warden_Food_Items.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Warden_Food_Items.this);
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
        Intent intent=new Intent(Warden_Food_Items.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
