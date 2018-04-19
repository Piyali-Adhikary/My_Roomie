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
import in.objectsol.my_roomie.SetGet.Permission_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Permission_Request_Adapter extends RecyclerView.Adapter<Student_Permission_Request_Adapter.MyViewHolder> {

    ArrayList<Permission_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    int studentsDayAttendanceByWarden=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String student_id="";



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_permission,tv_date,tv_permission_description,tv_from,tv_to;
        public ImageView iv_permission_granted,iv_permission_rejected;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_permission = (TextView) itemView.findViewById(R.id.tv_permission_request_parent);
            tv_date = (TextView) itemView.findViewById(R.id.tv_permission_created_date);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from_permission);
            tv_to = (TextView) itemView.findViewById(R.id.tv_to_permission);
            tv_permission_description = (TextView) itemView.findViewById(R.id.tv_permission_description);
            iv_permission_granted = (ImageView) itemView.findViewById(R.id.iv_permission_granted);
            iv_permission_rejected = (ImageView) itemView.findViewById(R.id.iv_permission_rejected);
        }
    }


    public Student_Permission_Request_Adapter(Context mContext, ArrayList<Permission_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_layout_permission_request_parent, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String createdTime=parseDateToddMMyyyy(list.get(position).getCreated_at());
        holder.tv_date.setText(createdTime);
        holder.tv_permission.setText(list.get(position).getPermission_type());
        holder.tv_permission_description.setText(list.get(position).getDescription());

        String fromTime=parseDateToddMMyyyy(list.get(position).getFrom_time());
        holder.tv_from.setText("From : " +fromTime);
        String toTime=parseDateToddMMyyyy(list.get(position).getTo_time());
        holder.tv_to.setText("To : " +toTime);

        if(list.get(position).getStatus().equalsIgnoreCase("accepted")){
            holder.iv_permission_granted.setImageResource(R.mipmap.right_01);
        }else if(list.get(position).getStatus().equalsIgnoreCase("pending")){
            holder.iv_permission_granted.setImageResource(R.mipmap.cross_02);
            holder.iv_permission_rejected.setImageResource(R.mipmap.right_02);
        }else {
            holder.iv_permission_rejected.setImageResource(R.mipmap.cross_01);
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
