package in.objectsol.my_roomie.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_Community_SetGet;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Community_Request_Adapter extends RecyclerView.Adapter<Student_Community_Request_Adapter.MyViewHolder> {

    ArrayList<Permission_Community_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    int studentsDayAttendanceByWarden=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String student_id="";



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_community_request_parent,tv_created_date,tv_community_request,tv_status;
        public ImageView iv_community_request_granted,iv_community_request_rejected;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_community_request_parent = (TextView) itemView.findViewById(R.id.tv_community_request_parent);
            tv_created_date = (TextView) itemView.findViewById(R.id.tv_community_request_created_date);
            tv_community_request = (TextView) itemView.findViewById(R.id.tv_community_request);
            tv_status = (TextView) itemView.findViewById(R.id.tv_community_request_status);
            iv_community_request_granted = (ImageView) itemView.findViewById(R.id.iv_community_request_granted);
            iv_community_request_rejected = (ImageView) itemView.findViewById(R.id.iv_community_request_rejected);
        }
    }


    public Student_Community_Request_Adapter(Context mContext, ArrayList<Permission_Community_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_community_request_parent, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String createdTime=list.get(position).getCreated_at();
        holder.tv_created_date.setText(createdTime);
        holder.tv_community_request_parent.setText(list.get(position).getPermission_type());
        holder.tv_community_request.setText(list.get(position).getDescription());
        holder.tv_status.setText(list.get(position).getStatus());


        if(list.get(position).getStatus().equalsIgnoreCase("pending")){
            holder.iv_community_request_granted.setImageResource(R.mipmap.cross_02);
            holder.iv_community_request_rejected.setImageResource(R.mipmap.right_02);
        }else if(list.get(position).getStatus().equalsIgnoreCase("accepted")){
            holder.iv_community_request_granted.setImageResource(R.mipmap.right_01);
        }else if(list.get(position).getStatus().equalsIgnoreCase("rejected")){
            holder.iv_community_request_rejected.setImageResource(R.mipmap.cross_01);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm";
        String outputPattern = "dd-MM-yyyy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
