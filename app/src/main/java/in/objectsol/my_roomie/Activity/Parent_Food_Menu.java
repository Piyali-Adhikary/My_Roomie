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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.objectsol.my_roomie.Adapter.Food_Menu_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.SetGet.SetgetItems;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 16/03/18.
 */

public class Parent_Food_Menu extends Activity implements IJSONParseListener{

    RecyclerView rv_food_items;
    ImageView iv_back;
    Button btn_exit_food_menu;
    Spinner sp_food_menu;
    public static String spinner_selection="";

    LinearLayoutManager layoutManager;
    int viewFoodMenu=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,sharedPreferences_student;
    ArrayList<String> day_setGetArrayList;
    ArrayList<SetgetDay> dayList1,dayList;
    private static final String View_Food_Menu_URL = "http://174.136.1.35/dev/myroomie/parents/viewFoodMenu/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_menu);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
         dayList1 = new ArrayList<>();
        dayList = new ArrayList<>();
        day_setGetArrayList=new ArrayList<>();
        sp_food_menu=(Spinner) findViewById(R.id.sp_food_menu);
        btn_exit_food_menu=(Button) findViewById(R.id.btn_exit_food_menu);
        rv_food_items=(RecyclerView) findViewById(R.id.rv_food_menu);
        iv_back=(ImageView) findViewById(R.id.iv_back_food_menu);

        rv_food_items.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_food_items.setLayoutManager(layoutManager);
        rv_food_items.setItemAnimator(new DefaultItemAnimator());

        day_setGetArrayList.add("Monday");
        day_setGetArrayList.add("Tuesday");
        day_setGetArrayList.add("Wednesday");
        day_setGetArrayList.add("Thursday");
        day_setGetArrayList.add("Friday");
        day_setGetArrayList.add("Saturday");
        day_setGetArrayList.add("Sunday");

        ArrayAdapter arrayAdapter=new ArrayAdapter(Parent_Food_Menu.this,android.R.layout.simple_list_item_1,day_setGetArrayList);
        sp_food_menu.setAdapter(arrayAdapter);

        sp_food_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_selection=sp_food_menu.getSelectedItem().toString();

                if(dayList.size()>0){
                    dayList1.clear();
                    for(int j=0;j<dayList.size();j++){
                        if(dayList.get(j).getDayname().equalsIgnoreCase(Parent_Food_Menu.spinner_selection)){

                            SetgetDay setgetDay=new SetgetDay();
                            setgetDay.setTiffin(dayList.get(j).getTiffin());
                            setgetDay.setBreakfast(dayList.get(j).getBreakfast());
                            setgetDay.setLunch(dayList.get(j).getLunch());
                            setgetDay.setDinner(dayList.get(j).getDinner());
                            dayList1.add(setgetDay);
                        }
                    }


                    Food_Menu_Adapter food_items_adapter=new Food_Menu_Adapter(Parent_Food_Menu.this,dayList1);
                    rv_food_items.setAdapter(food_items_adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Parent_Food_Menu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_exit_food_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Parent_Food_Menu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (Utils.isNetworkAvailable(Parent_Food_Menu.this)) {

            viewFoodMenu();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Food_Menu.this);
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
        JSONRequestResponse mResponse = new JSONRequestResponse(Parent_Food_Menu.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences_student.getString("campus_id",""));
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(Parent_Food_Menu.this);
        ShowProgressDilog(Parent_Food_Menu.this);
        mResponse.getResponse(Request.Method.POST, View_Food_Menu_URL, viewFoodMenu, Parent_Food_Menu.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Parent_Food_Menu.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Parent_Food_Menu.this);
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

        DismissProgress(Parent_Food_Menu.this);

        if (requestCode == viewFoodMenu) {
            System.out.println("Response for viewFoodMenu------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {
                    JSONObject result=response.getJSONObject("result");


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

                    for(int i=0;i<dayList.size();i++){
                        if(dayList.get(i).getDayname().equalsIgnoreCase(Parent_Food_Menu.spinner_selection)){

                            SetgetDay setgetDay=new SetgetDay();
                            setgetDay.setTiffin(dayList.get(i).getTiffin());
                            setgetDay.setBreakfast(dayList.get(i).getBreakfast());
                            setgetDay.setLunch(dayList.get(i).getLunch());
                            setgetDay.setDinner(dayList.get(i).getDinner());
                            dayList1.add(setgetDay);
                        }
                    }


                    Food_Menu_Adapter food_items_adapter=new Food_Menu_Adapter(Parent_Food_Menu.this,dayList1);
                    rv_food_items.setAdapter(food_items_adapter);
                }else {
                    Toast.makeText(Parent_Food_Menu.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Parent_Food_Menu.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Parent_Food_Menu.this);
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
        Intent intent=new Intent(Parent_Food_Menu.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
