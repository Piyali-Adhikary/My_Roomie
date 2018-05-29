package in.objectsol.my_roomie.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import in.objectsol.my_roomie.Activity.Food_Menu;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.SetgetDay;
import in.objectsol.my_roomie.SetGet.SetgetItems;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

/**
 * Created by objsol on 05/03/18.
 */

public class Food_Menu_Adapter extends RecyclerView.Adapter<Food_Menu_Adapter.MyViewHolder> {

    ArrayList<SetgetDay> list;
    Context mContext;
    int getFoodMenuTimings=616;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    ArrayList<HashMap> arrayList_food_timings;
    private static final String View_Food_Menu_Timings = "http://174.136.1.35/dev/myroomie/student/foodMenuTimings/";



    public class MyViewHolder extends RecyclerView.ViewHolder{


        public LinearLayout ll_breakfast,ll_lunch,ll_tiffin,ll_dinner;
        public TextView tv_cancel_tiffin,tv_cancel_breakfast,tv_cancel_lunch,tv_cancel_dinner;
        public MyViewHolder(View itemView) {
            super(itemView);

            ll_breakfast = (LinearLayout) itemView.findViewById(R.id.ll_breakfast_child_food_menu);
            ll_lunch = (LinearLayout) itemView.findViewById(R.id.ll_lunch_child_food_menu);
            ll_dinner = (LinearLayout) itemView.findViewById(R.id.ll_dinner_child_food_menu);
            ll_tiffin = (LinearLayout) itemView.findViewById(R.id.ll_tiffin_child_food_menu);

            tv_cancel_tiffin = (TextView) itemView.findViewById(R.id.tv_cancel_tiffin);
            tv_cancel_breakfast = (TextView) itemView.findViewById(R.id.tv_cancel_breakfast);
            tv_cancel_lunch = (TextView) itemView.findViewById(R.id.tv_cancel_lunch);
            tv_cancel_dinner = (TextView) itemView.findViewById(R.id.tv_cancel_dinner);


        }
    }


    public Food_Menu_Adapter(Context mContext, ArrayList<SetgetDay> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_food_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_cancel_breakfast.setVisibility(View.GONE);
        holder.tv_cancel_tiffin.setVisibility(View.GONE);
        holder.tv_cancel_lunch.setVisibility(View.GONE);
        holder.tv_cancel_dinner.setVisibility(View.GONE);

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
