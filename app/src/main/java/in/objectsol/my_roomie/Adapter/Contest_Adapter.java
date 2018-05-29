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

import in.objectsol.my_roomie.Activity.Contest;
import in.objectsol.my_roomie.Activity.Contest_Details;
import in.objectsol.my_roomie.Activity.Services_Details;
import in.objectsol.my_roomie.Activity.Services_Student;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Contest_SetGet;
import in.objectsol.my_roomie.SetGet.Services_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Contest_Adapter extends RecyclerView.Adapter<Contest_Adapter.MyViewHolder> {

    ArrayList<Contest_SetGet> list;
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


    public Contest_Adapter(Context mContext, ArrayList<Contest_SetGet> list) {

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

        holder.tv_community_name_child.setText(list.get(position).getContest_name());


        holder.rl_child_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contest_Details.id=list.get(position).getId();
                Contest_Details.contest_name=list.get(position).getContest_name();
                Contest_Details.contest_type=list.get(position).getContest_type();
                Contest_Details.contest_start_date=list.get(position).getContest_start_date();
                Contest_Details.contest_end_date=list.get(position).getContest_end_date();

                Intent intent=new Intent(mContext, Contest_Details.class);
                mContext.startActivity(intent);
                ((Contest)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
