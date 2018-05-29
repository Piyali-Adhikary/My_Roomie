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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Activity.Community_Details;
import in.objectsol.my_roomie.Activity.Community_Student_Profile;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Community_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Community_Updated_Adapter extends RecyclerView.Adapter<Student_Community_Updated_Adapter.MyViewHolder> {

    ArrayList<Community_SetGet> list;
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


    public Student_Community_Updated_Adapter(Context mContext, ArrayList<Community_SetGet> list) {

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

        holder.tv_community_name_child.setText(list.get(position).getCommunity_name());


        holder.rl_child_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Community_Details.id=list.get(position).getId();
                Community_Details.community_name=list.get(position).getCommunity_name();
                Community_Details.total_capacity=list.get(position).getTotal_capacity();
                Community_Details.days_of_week=list.get(position).getDays_of_week();
                Community_Details.start_date=list.get(position).getStart_date();
                Community_Details.end_date=list.get(position).getEnd_date();
                Community_Details.timings=list.get(position).getTimings();
                Community_Details.duration=list.get(position).getDuration();
                Community_Details.charges=list.get(position).getCharges();
                Intent intent=new Intent(mContext, Community_Details.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
