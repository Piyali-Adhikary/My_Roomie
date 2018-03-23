package in.objectsol.my_roomie.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Food_Items_Adapter extends RecyclerView.Adapter<Food_Items_Adapter.MyViewHolder> {

    ArrayList<SetgetDay> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_day;
        public LinearLayout ll_breakfast,ll_lunch,ll_tiffin,ll_dinner;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_day = (TextView) itemView.findViewById(R.id.tv_day_child_food_items);
            ll_breakfast = (LinearLayout) itemView.findViewById(R.id.ll_breakfast_child_food_items);
            ll_lunch = (LinearLayout) itemView.findViewById(R.id.ll_lunch_child_food_items);
            ll_tiffin = (LinearLayout) itemView.findViewById(R.id.ll_tiffin_child_food_items);
            ll_dinner = (LinearLayout) itemView.findViewById(R.id.ll_dinner_child_food_items);

        }
    }


    public Food_Items_Adapter(Context mContext, ArrayList<SetgetDay> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_food_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_day.setText(list.get(position).getDayname());

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
