package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Activity.Warden_Check_in_Check_out;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Warden_Check_In_Adapter extends RecyclerView.Adapter<Warden_Check_In_Adapter.MyViewHolder> {

    ArrayList<Student_SetGet> list;
    Context mContext;
    SharedPreferences sharedPreferences;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_date;
        public EditText et_enter_check_in_time,et_enter_check_out_time;
        public CircleImageView student_image;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_child_warden_check_in);
            et_enter_check_in_time = (EditText) itemView.findViewById(R.id.et_enter_check_in_time_child_warden_check_in);
            et_enter_check_out_time = (EditText) itemView.findViewById(R.id.et_enter_check_out_time_child_warden_check_in);
            student_image = (CircleImageView) itemView.findViewById(R.id.iv_student_image_child_warden_check_in);
        }
    }


    public Warden_Check_In_Adapter(Context mContext, ArrayList<Student_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_warden_check_in, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_date.setText(list.get(position).getDate());
        holder.et_enter_check_in_time.setText(list.get(position).getCheck_in_time());
        holder.et_enter_check_out_time.setText(list.get(position).getCheck_out_time());


        Glide.with(mContext).load(list.get(position).getStudent_pic())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.student_image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
