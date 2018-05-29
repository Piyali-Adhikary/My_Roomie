package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.objectsol.my_roomie.Activity.Community_Details;
import in.objectsol.my_roomie.Activity.Services_Details;
import in.objectsol.my_roomie.Activity.Services_Student;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Community_SetGet;
import in.objectsol.my_roomie.SetGet.Services_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Services_Adapter extends RecyclerView.Adapter<Student_Services_Adapter.MyViewHolder> {

    ArrayList<Services_SetGet> list;
    Context mContext;
    SharedPreferences sharedPreferences;





    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_community_name_child;
        public RelativeLayout rl_child_community;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_community_name_child = (TextView) itemView.findViewById(R.id.tv_community_name_child);
            rl_child_community = (RelativeLayout) itemView.findViewById(R.id.rl_child_community);
        }
    }


    public Student_Services_Adapter(Context mContext, ArrayList<Services_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_community_updated, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_community_name_child.setText(list.get(position).getServices_name());


        holder.rl_child_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Services_Details.id=list.get(position).getId();
                Services_Details.services_name=list.get(position).getServices_name();
                Services_Details.charges=list.get(position).getCharges();
                Services_Details.services_month=list.get(position).getServices_month();
                Services_Details.services_day=list.get(position).getServices_day();
                Services_Details.services_start_date=list.get(position).getServices_start_date();
                Services_Details.services_end_date=list.get(position).getServices_end_date();
                Services_Details.services_start_time=list.get(position).getServices_start_time();
                Services_Details.services_end_time=list.get(position).getServices_end_time();
                Services_Details.year=list.get(position).getYear();
                Intent intent=new Intent(mContext, Services_Details.class);
                mContext.startActivity(intent);
                ((Services_Student)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
