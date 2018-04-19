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

public class Student_Complaint_List_Adapter extends RecyclerView.Adapter<Student_Complaint_List_Adapter.MyViewHolder> {

    ArrayList<Permission_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    int studentsDayAttendanceByWarden=612;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String student_id="";
    public static boolean isFromStudent=false;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_complaint,tv_date,tv_complaint_description,tv_status,tv_student_name,tv_room_no;
        public ImageView iv_child_complaint_granted;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_complaint = (TextView) itemView.findViewById(R.id.tv_child_student_complaint);
            tv_date = (TextView) itemView.findViewById(R.id.tv_child_complaint_created_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_child_complaint_status);
            tv_complaint_description = (TextView) itemView.findViewById(R.id.tv_child_complaint_description);
            iv_child_complaint_granted = (ImageView) itemView.findViewById(R.id.iv_child_complaint_granted);

            tv_student_name = (TextView) itemView.findViewById(R.id.tv_student_name_child_student_complaint);
            tv_room_no = (TextView) itemView.findViewById(R.id.tv_room_no_child_student_complaint);
        }
    }


    public Student_Complaint_List_Adapter(Context mContext, ArrayList<Permission_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_student_complaint, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String createdTime=list.get(position).getCreated_at();
        holder.tv_date.setText(createdTime);
        holder.tv_complaint.setText(list.get(position).getPermission_type());
        holder.tv_complaint_description.setText(list.get(position).getDescription());

        if(isFromStudent){
            holder.tv_room_no.setVisibility(View.GONE);
            holder.tv_student_name.setVisibility(View.GONE);
        }else {
            holder.tv_room_no.setVisibility(View.VISIBLE);
            holder.tv_student_name.setVisibility(View.VISIBLE);
            holder.tv_room_no.setText("Room No : " + list.get(position).getRoom_no());
            holder.tv_student_name.setText("Name : " + list.get(position).getStudent_name());
        }

        if(list.get(position).getStatus().equalsIgnoreCase("pending")){

            holder.tv_status.setText("Status : Not Yet Fixed" );

        }else if(list.get(position).getStatus().equalsIgnoreCase("fixed")){

            holder.tv_status.setText("Status : Not Yet Verified");

        }else if(list.get(position).getStatus().equalsIgnoreCase("verified")){

            holder.tv_status.setText("Status : Verified");
        }

        if(list.get(position).getStatus().equalsIgnoreCase("verified")){
            holder.iv_child_complaint_granted.setImageResource(R.mipmap.right_01);
        }else {
            holder.iv_child_complaint_granted.setImageResource(R.mipmap.cross_02);
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
