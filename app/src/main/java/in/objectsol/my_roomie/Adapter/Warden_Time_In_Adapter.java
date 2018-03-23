package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Activity.Student_Attendance_By_Warden;
import in.objectsol.my_roomie.Activity.Warden_Check_in_Check_out;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Warden_Time_In_Adapter extends RecyclerView.Adapter<Warden_Time_In_Adapter.MyViewHolder> {

    ArrayList<Student_SetGet> list;
    Context mContext;
    String status="";
    Boolean isPresent=true;
    SharedPreferences sharedPreferences;
    String student_id="";
    public static ArrayList<HashMap<String, String>> submit_student_attendance_arraylist;





    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView student_name,student_location;
        public ImageView  student_attendance;
        public CircleImageView student_image;
        public CardView cv_child_student_list;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.tv_student_name_child_student_list);
            student_location = (TextView) itemView.findViewById(R.id.tv_student_location_child_student_list);
            student_image = (CircleImageView) itemView.findViewById(R.id.iv_student_image_child_student_list);
            student_attendance = (ImageView) itemView.findViewById(R.id.iv_student_attendance_child);
            cv_child_student_list = (CardView) itemView.findViewById(R.id.cv_child_student_list);
        }
    }


    public Warden_Time_In_Adapter(Context mContext, ArrayList<Student_SetGet> list) {

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
        //holder.student_image.setImageResource(R.mipmap.profile_round_grey);
       // student_id=list.get(position).getStudent_id();

        holder.student_attendance.setVisibility(View.GONE);

        Glide.with(mContext).load(list.get(position).getStudent_pic())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.student_image);

        holder.cv_child_student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Warden_Check_in_Check_out.student_id=list.get(position).getStudent_id();
                Warden_Check_in_Check_out.student_name=list.get(position).getStudent_name();
                Intent intent= new Intent(mContext, Warden_Check_in_Check_out.class);
                mContext.startActivity(intent);

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
