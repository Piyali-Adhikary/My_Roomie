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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import in.objectsol.my_roomie.Adapter.Food_Menu_Adapter;
import in.objectsol.my_roomie.Adapter.Food_Menu_Updated_Adapter;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Food_Menu_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.SetGet.SetgetItems;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 16/03/18.
 */

public class Food_Menu_Updated extends Activity implements IJSONParseListener{

    RecyclerView rv_food_items;
    ImageView iv_back;
    Button btn_exit_food_menu;
    Spinner sp_food_menu;
    public static String spinner_selection="";
    public static String current_date="";

    LinearLayoutManager layoutManager;
    int viewFoodMenu=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<String> day_setGetArrayList;
    ArrayList<SetgetDay> dayList1,dayList;

    ArrayList<Food_Menu_SetGet> food_menu_ArrayList;
    private static final String View_Food_Menu_URL = "http://174.136.1.35/dev/myroomie/student/viewFoodMenu/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_menu);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
         dayList1 = new ArrayList<>();
        dayList = new ArrayList<>();
        food_menu_ArrayList = new ArrayList<>();
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

        ArrayAdapter arrayAdapter=new ArrayAdapter(Food_Menu_Updated.this,android.R.layout.simple_list_item_1,day_setGetArrayList);
        sp_food_menu.setAdapter(arrayAdapter);
        sp_food_menu.setSelection(0);
        spinner_selection="Monday";

        //Get Today's Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int mHour= c.get(Calendar.HOUR_OF_DAY);
        int month=mMonth+1;
        String mMonth1="";
        if(month<10){
            mMonth1="0"+String.valueOf(month);
        }else {
            mMonth1=String.valueOf(month);
        }

        current_date= String.valueOf(mYear) +"-"+mMonth1+"-"+String.valueOf(mDay);

        sp_food_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_selection=sp_food_menu.getSelectedItem().toString();


                if (Utils.isNetworkAvailable(Food_Menu_Updated.this)) {

                    viewFoodMenu();

                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Food_Menu_Updated.this);
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


                /*if(dayList.size()>0){
                    dayList1.clear();
                    for(int j=0;j<dayList.size();j++){
                        if(dayList.get(j).getDayname().equalsIgnoreCase(Food_Menu_Updated.spinner_selection)){

                            SetgetDay setgetDay=new SetgetDay();
                            setgetDay.setTiffin(dayList.get(j).getTiffin());
                            setgetDay.setBreakfast(dayList.get(j).getBreakfast());
                            setgetDay.setLunch(dayList.get(j).getLunch());
                            setgetDay.setDinner(dayList.get(j).getDinner());
                            dayList1.add(setgetDay);
                        }
                    }


                    Food_Menu_Adapter food_items_adapter=new Food_Menu_Adapter(Food_Menu_Updated.this,dayList1);
                    rv_food_items.setAdapter(food_items_adapter);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Food_Menu_Updated.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_exit_food_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Food_Menu_Updated.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*if (Utils.isNetworkAvailable(Food_Menu_Updated.this)) {

            viewFoodMenu();

        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Food_Menu_Updated.this);
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
        }*/
    }

    void viewFoodMenu() {
        JSONRequestResponse mResponse = new JSONRequestResponse(Food_Menu_Updated.this);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        //parms.putString("date_cur", current_date);
        parms.putString("cancel_date", current_date);
        parms.putString("day", spinner_selection);

        MyVolley.init(Food_Menu_Updated.this);
        ShowProgressDilog(Food_Menu_Updated.this);
        mResponse.getResponse(Request.Method.POST, View_Food_Menu_URL, viewFoodMenu, Food_Menu_Updated.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(Food_Menu_Updated.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Food_Menu_Updated.this);
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

        DismissProgress(Food_Menu_Updated.this);
        dayList.clear();
        if (requestCode == viewFoodMenu) {
            System.out.println("Response for viewFoodMenu------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    ArrayList<SetgetItems> breakfastList = new ArrayList<>();
                    ArrayList<SetgetItems> lunchList = new ArrayList<>();
                    ArrayList<SetgetItems> tiffinList = new ArrayList<>();
                    ArrayList<SetgetItems> dinnerList = new ArrayList<>();
                    JSONObject result=response.getJSONObject("result");

                    JSONObject jsonObject=result.getJSONObject("day");

                    if(jsonObject.has("breakfast")){
                        JSONObject json_breakfast=jsonObject.getJSONObject("breakfast");

                        SetgetItems setgetItems=new SetgetItems();
                        setgetItems.setName(json_breakfast.getString("name"));
                        setgetItems.setCancel(json_breakfast.getString("cancel"));
                        setgetItems.setStart_time(json_breakfast.getString("start_time"));
                        setgetItems.setEnd_time(json_breakfast.getString("end_time"));
                        setgetItems.setBefore_12_hours(json_breakfast.getString("before_12_hours"));
                        setgetItems.setFood_menu_id(json_breakfast.getString("food_menu_id"));

                        breakfastList.add(setgetItems);
                    }

                    if(jsonObject.has("lunch")){
                        JSONObject json_breakfast=jsonObject.getJSONObject("lunch");

                        SetgetItems setgetItems=new SetgetItems();
                        setgetItems.setName(json_breakfast.getString("name"));
                        setgetItems.setCancel(json_breakfast.getString("cancel"));
                        setgetItems.setStart_time(json_breakfast.getString("start_time"));
                        setgetItems.setEnd_time(json_breakfast.getString("end_time"));
                        setgetItems.setBefore_12_hours(json_breakfast.getString("before_12_hours"));
                        setgetItems.setFood_menu_id(json_breakfast.getString("food_menu_id"));

                        lunchList.add(setgetItems);
                    }

                    if(jsonObject.has("tiffin")){
                        JSONObject json_breakfast=jsonObject.getJSONObject("tiffin");

                        SetgetItems setgetItems=new SetgetItems();
                        setgetItems.setName(json_breakfast.getString("name"));
                        setgetItems.setCancel(json_breakfast.getString("cancel"));
                        setgetItems.setStart_time(json_breakfast.getString("start_time"));
                        setgetItems.setEnd_time(json_breakfast.getString("end_time"));
                        setgetItems.setBefore_12_hours(json_breakfast.getString("before_12_hours"));
                        setgetItems.setFood_menu_id(json_breakfast.getString("food_menu_id"));

                        tiffinList.add(setgetItems);
                    }

                    if(jsonObject.has("dinner")){
                        JSONObject json_breakfast=jsonObject.getJSONObject("dinner");

                        SetgetItems setgetItems=new SetgetItems();
                        setgetItems.setName(json_breakfast.getString("name"));
                        setgetItems.setCancel(json_breakfast.getString("cancel"));
                        setgetItems.setStart_time(json_breakfast.getString("start_time"));
                        setgetItems.setEnd_time(json_breakfast.getString("end_time"));
                        setgetItems.setBefore_12_hours(json_breakfast.getString("before_12_hours"));
                        setgetItems.setFood_menu_id(json_breakfast.getString("food_menu_id"));

                        dinnerList.add(setgetItems);
                    }

                    SetgetDay setgetDay= new SetgetDay();
                   // Food_Menu_Updated_Adapter.day=result.getString("day");
                    setgetDay.setDayname(result.getString("day"));
                    setgetDay.setBreakfast(breakfastList);
                    setgetDay.setLunch(lunchList);
                    setgetDay.setTiffin(tiffinList);
                    setgetDay.setDinner(dinnerList);

                    dayList.add(setgetDay);


                    Food_Menu_Updated_Adapter food_items_adapter=new Food_Menu_Updated_Adapter(Food_Menu_Updated.this,dayList);
                    rv_food_items.setAdapter(food_items_adapter);
                }else {
                    Toast.makeText(Food_Menu_Updated.this,response.getString("result"),Toast.LENGTH_LONG).show();
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
        DismissProgress(Food_Menu_Updated.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(Food_Menu_Updated.this);
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
        Intent intent=new Intent(Food_Menu_Updated.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}
