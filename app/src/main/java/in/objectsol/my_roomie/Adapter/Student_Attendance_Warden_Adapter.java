package in.objectsol.my_roomie.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.objectsol.my_roomie.Activity.Student_Attendance_By_Warden;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_Types_SetGet;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Attendance_Warden_Adapter extends RecyclerView.Adapter<Student_Attendance_Warden_Adapter.MyViewHolder> {

    ArrayList<Student_SetGet> list;
    Context mContext;
    String status="";
    Boolean isPresent=true;
    SharedPreferences sharedPreferences;
    String student_id="";
    public static ArrayList<HashMap<String, String>> submit_student_attendance_arraylist;





    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView student_name,student_location;
        public ImageView student_image,student_attendance;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.tv_student_name_child_student_list);
            student_location = (TextView) itemView.findViewById(R.id.tv_student_location_child_student_list);
            student_image = (ImageView) itemView.findViewById(R.id.iv_student_image_child_student_list);
            student_attendance = (ImageView) itemView.findViewById(R.id.iv_student_attendance_child);
        }
    }


    public Student_Attendance_Warden_Adapter(Context mContext, ArrayList<Student_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
        submit_student_attendance_arraylist=new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_student_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.student_name.setText(list.get(position).getStudent_name());
        holder.student_location.setText(list.get(position).getEmail());
        holder.student_image.setImageResource(R.mipmap.profile_round_grey);
        student_id=list.get(position).getStudent_id();


        if(Student_Attendance_By_Warden.date.equalsIgnoreCase(Student_Attendance_By_Warden.current_date)){
            holder.student_attendance.setEnabled(true);
        }else {
            holder.student_attendance.setEnabled(false);
        }


        if(list.get(position).getStatus().equalsIgnoreCase("absent")){
            holder.student_attendance.setImageResource(R.mipmap.add2);
            status="absent";
        }else if(list.get(position).getStatus().equalsIgnoreCase("present")){
            holder.student_attendance.setImageResource(R.mipmap.add);
            status="present";
        }
        holder.student_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(position).getStatus().equalsIgnoreCase("absent")){
                    holder.student_attendance.setImageResource(R.mipmap.add);
                    status="present";
                    list.get(position).setStatus("present");

                    //studentsDayAttendanceByWarden();
                }else {
                    holder.student_attendance.setImageResource(R.mipmap.add2);
                    status="absent";
                    list.get(position).setStatus("absent");
                    //studentsDayAttendanceByWarden();
                }

                HashMap<String, String> stringHashMap=new HashMap<String, String>();

                stringHashMap.put("warden_id", sharedPreferences.getString("warden_id",""));
                stringHashMap.put("campus_id", sharedPreferences.getString("campus_id",""));
                stringHashMap.put("auth_key", sharedPreferences.getString("auth_token",""));
                stringHashMap.put("student_id", list.get(position).getStudent_id());
                stringHashMap.put("status", status);
                stringHashMap.put("date", Student_Attendance_By_Warden.date);

                submit_student_attendance_arraylist.add(stringHashMap);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public void filterList(ArrayList<Student_SetGet> filterdNames) {
        this.list = filterdNames;
        notifyDataSetChanged();
    }
}