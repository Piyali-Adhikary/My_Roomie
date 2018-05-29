package in.objectsol.my_roomie.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import java.util.HashMap;

import in.objectsol.my_roomie.Activity.Food_Menu_Updated;
import in.objectsol.my_roomie.Activity.MainActivity;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Food_Menu_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Food_Menu_Updated_Adapter extends RecyclerView.Adapter<Food_Menu_Updated_Adapter.MyViewHolder> implements IJSONParseListener{

    ArrayList<SetgetDay> list;
    Context mContext;
    int cancelFoodMenu=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<HashMap> arrayList_food_timings;
    public static String day="";
    String food_menu_id="";

    private static final String Cancel_Food_Menu_URL = "http://174.136.1.35/dev/myroomie/student/cancelFoodMenu/";



    public class MyViewHolder extends RecyclerView.ViewHolder{


        public LinearLayout ll_breakfast,ll_lunch,ll_tiffin,ll_dinner;
        public LinearLayout ll_breakfast1,ll_lunch1,ll_tiffin1,ll_dinner1;
        public TextView tv_cancel_tiffin,tv_cancel_breakfast,tv_cancel_lunch,tv_cancel_dinner;
        public MyViewHolder(View itemView) {
            super(itemView);

            ll_breakfast = (LinearLayout) itemView.findViewById(R.id.ll_breakfast_child_food_menu);
            ll_lunch = (LinearLayout) itemView.findViewById(R.id.ll_lunch_child_food_menu);
            ll_dinner = (LinearLayout) itemView.findViewById(R.id.ll_dinner_child_food_menu);
            ll_tiffin = (LinearLayout) itemView.findViewById(R.id.ll_tiffin_child_food_menu);

            ll_breakfast1 = (LinearLayout) itemView.findViewById(R.id.ll_child_food_menu_breakfast);
            ll_lunch1 = (LinearLayout) itemView.findViewById(R.id.ll_child_food_menu_lunch);
            ll_dinner1 = (LinearLayout) itemView.findViewById(R.id.ll_child_food_menu_dinner);
            ll_tiffin1 = (LinearLayout) itemView.findViewById(R.id.ll_child_food_menu_tiffin);

            tv_cancel_tiffin = (TextView) itemView.findViewById(R.id.tv_cancel_tiffin);
            tv_cancel_breakfast = (TextView) itemView.findViewById(R.id.tv_cancel_breakfast);
            tv_cancel_lunch = (TextView) itemView.findViewById(R.id.tv_cancel_lunch);
            tv_cancel_dinner = (TextView) itemView.findViewById(R.id.tv_cancel_dinner);


        }
    }


    public Food_Menu_Updated_Adapter(Context mContext, ArrayList<SetgetDay> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_food_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        for (int i=0;i< list.get(position).getBreakfast().size();i++){
            TextView textView=new TextView(mContext);
            textView.setText(list.get(position).getBreakfast().get(i).getName());
            textView.setTextColor(Color.parseColor("#303F9F"));
            textView.setTextSize(12);

            holder.ll_breakfast.addView(textView);
        }

        for (int i=0;i< list.get(position).getLunch().size();i++){
            TextView textView=new TextView(mContext);
            textView.setText(list.get(position).getLunch().get(i).getName());
            textView.setTextColor(Color.parseColor("#303F9F"));
            textView.setTextSize(12);

            holder.ll_lunch.addView(textView);
        }

        for (int i=0;i< list.get(position).getTiffin().size();i++){
            TextView textView=new TextView(mContext);
            textView.setText(list.get(position).getTiffin().get(i).getName());
            textView.setTextColor(Color.parseColor("#303F9F"));
            textView.setTextSize(12);

            holder.ll_tiffin.addView(textView);
        }

        for (int i=0;i< list.get(position).getDinner().size();i++){
            TextView textView=new TextView(mContext);
            textView.setText(list.get(position).getDinner().get(i).getName());
            textView.setTextColor(Color.parseColor("#303F9F"));
            textView.setTextSize(12);

            holder.ll_dinner.addView(textView);
        }

        Boolean isAfterBreakfast=dateAfter(list.get(position).getBreakfast().get(0).getBefore_12_hours());
        if(isAfterBreakfast){
            holder.tv_cancel_breakfast.setVisibility(View.GONE);
        }else {
            holder.tv_cancel_breakfast.setVisibility(View.VISIBLE);
        }

        Boolean isAfterLunch=dateAfter(list.get(position).getLunch().get(0).getBefore_12_hours());
        if(isAfterLunch){
            holder.tv_cancel_lunch.setVisibility(View.GONE);
        }else {
            holder.tv_cancel_lunch.setVisibility(View.VISIBLE);
        }


        Boolean isAfterTiffin=dateAfter(list.get(position).getTiffin().get(0).getBefore_12_hours());
        if(isAfterTiffin){
            holder.tv_cancel_tiffin.setVisibility(View.GONE);
        }else {
            holder.tv_cancel_tiffin.setVisibility(View.VISIBLE);
        }

        Boolean isAfterDinner=dateAfter(list.get(position).getDinner().get(0).getBefore_12_hours());
        if(isAfterDinner){
            holder.tv_cancel_dinner.setVisibility(View.GONE);
        }else {
            holder.tv_cancel_dinner.setVisibility(View.VISIBLE);
        }


        if(list.get(position).getBreakfast().get(0).getCancel().equalsIgnoreCase("no")){

       }else {
            holder.tv_cancel_breakfast.setVisibility(View.GONE);
            holder.ll_breakfast1.setBackgroundResource(R.color.LightRed);
        }

        if(list.get(position).getLunch().get(0).getCancel().equalsIgnoreCase("no")){

        }else {
            holder.tv_cancel_lunch.setVisibility(View.GONE);
            holder.ll_lunch1.setBackgroundResource(R.color.LightRed);
        }

        if(list.get(position).getTiffin().get(0).getCancel().equalsIgnoreCase("no")){

        }else {
            holder.tv_cancel_tiffin.setVisibility(View.GONE);
            holder.ll_tiffin1.setBackgroundResource(R.color.LightRed);
        }


        if(list.get(position).getDinner().get(0).getCancel().equalsIgnoreCase("no")){

        }else {
            holder.tv_cancel_dinner.setVisibility(View.GONE);
            holder.ll_dinner1.setBackgroundResource(R.color.LightRed);
        }


        holder.tv_cancel_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_menu_id=list.get(position).getBreakfast().get(0).getFood_menu_id();

                cancelFoodMenu();
            }
        });

        holder.tv_cancel_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_menu_id=list.get(position).getLunch().get(0).getFood_menu_id();

                cancelFoodMenu();
            }
        });

        holder.tv_cancel_tiffin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_menu_id=list.get(position).getTiffin().get(0).getFood_menu_id();

                cancelFoodMenu();
            }
        });

        holder.tv_cancel_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food_menu_id=list.get(position).getDinner().get(0).getFood_menu_id();

                cancelFoodMenu();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    void cancelFoodMenu() {
        JSONRequestResponse mResponse = new JSONRequestResponse(mContext);
        Bundle parms = new Bundle();
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("cancel_date", Food_Menu_Updated.current_date);
        parms.putString("cancel_day", Food_Menu_Updated.spinner_selection);
        parms.putString("food_menu_id", food_menu_id);


        MyVolley.init(mContext);
        ShowProgressDilog(mContext);
        mResponse.getResponse(Request.Method.POST, Cancel_Food_Menu_URL, cancelFoodMenu, this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(mContext);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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

        DismissProgress(mContext);
        arrayList_food_timings=new ArrayList<>();
        if (requestCode == cancelFoodMenu) {
            System.out.println("Response for cancelFoodMenu------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(mContext, response.getString("result"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);

                    //JSONArray result = response.getJSONArray("result");

                   /* for(int i=0;i<result.length();i++){

                        JSONObject jsonObject=result.getJSONObject(i);

                        HashMap hashMap=new HashMap();
                        hashMap.put("id",jsonObject.getString("id"));
                        hashMap.put("foodmenu_content",jsonObject.getString("foodmenu_content"));
                        hashMap.put("start_time",jsonObject.getString("start_time"));
                        hashMap.put("end_time",jsonObject.getString("end_time"));

                        arrayList_food_timings.add(hashMap);
                    }*/


                }else {
                    Toast.makeText(mContext, response.getString("result"), Toast.LENGTH_SHORT).show();
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
        DismissProgress(mContext);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(mContext);
    }

    void ShowProgressDilog(Context c) {
        pDialog = new ProgressDialog(c);
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.setContentView(R.layout.layout_progress_dilog);
    }

    void DismissProgress(Context c) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public Boolean dateAfter(String date){
        Boolean isAfter=false;
        Date date4,current_date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            Calendar c = Calendar.getInstance();
            current_date=c.getTime();

            Date strDate = sdf.parse(date);
            /*Calendar calendar = Calendar.getInstance();
            calendar.setTime(strDate);
            calendar.add(Calendar.HOUR, 4);
            date4 = calendar.getTime();*/
            if (current_date.after(strDate)) {
                isAfter=true;
            }else {
                isAfter=false;
            }

        }catch (ParseException e){
            e.printStackTrace();
        }

        return isAfter;
    }
}
