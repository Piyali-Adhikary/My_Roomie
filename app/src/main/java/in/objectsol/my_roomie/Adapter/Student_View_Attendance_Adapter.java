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
import in.objectsol.my_roomie.SetGet.Attendance_SetGet;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_View_Attendance_Adapter extends RecyclerView.Adapter<Student_View_Attendance_Adapter.MyViewHolder> {

    ArrayList<Attendance_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String student_id="";



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_date;
        public ImageView iv_right,iv_cross;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_child_student_view_attendance);
            iv_right = (ImageView) itemView.findViewById(R.id.iv_right_child_student_attendance);
            iv_cross = (ImageView) itemView.findViewById(R.id.iv_cross_child_student_attendance);
        }
    }


    public Student_View_Attendance_Adapter(Context mContext, ArrayList<Attendance_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_student_view_attendance, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String date= list.get(position).getDate();
        holder.tv_date.setText(date);

        if(list.get(position).getStatus().equalsIgnoreCase("present")){
            holder.iv_right.setImageResource(R.mipmap.right_atten_01);
            holder.iv_cross.setImageResource(R.mipmap.cross_atten_02);
        }else {
            holder.iv_right.setImageResource(R.mipmap.right_atten_02);
            holder.iv_cross.setImageResource(R.mipmap.cross_atten_01);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
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
