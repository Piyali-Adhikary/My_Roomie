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

import java.util.ArrayList;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Parent_Permission_Request_Old_Adapter extends RecyclerView.Adapter<Parent_Permission_Request_Old_Adapter.MyViewHolder> {

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


    public Parent_Permission_Request_Old_Adapter(Context mContext, ArrayList<Permission_SetGet> list) {

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

        holder.tv_date.setText(list.get(position).getCreated_at());
        holder.tv_permission.setText(list.get(position).getPermission_type());
        holder.tv_permission_description.setText(list.get(position).getDescription());
        holder.tv_from.setText("From : " +list.get(position).getFrom_time());
        holder.tv_to.setText("To : " +list.get(position).getTo_time());

        if(list.get(position).getStatus().equalsIgnoreCase("accepted")){
            holder.iv_permission_granted.setImageResource(R.mipmap.right_01);
        }else {
            holder.iv_permission_rejected.setImageResource(R.mipmap.cross_01);
        }

//        if(list.get(position).getPermission_granted().equalsIgnoreCase("yes")){
//            holder.iv_permission_granted.setImageResource(R.mipmap.right_01);
//        }else {
//            holder.iv_permission_granted.setImageResource(R.mipmap.cross_02);
//        }
//
//        if(list.get(position).getPermission_rejected().equalsIgnoreCase("yes")){
//            holder.iv_permission_rejected.setImageResource(R.mipmap.cross_01);
//        }else {
//            holder.iv_permission_rejected.setImageResource(R.mipmap.right_02);
//        }

//        holder.iv_permission_granted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(list.get(position).getPermission_granted().equalsIgnoreCase("no")){
//                    holder.iv_permission_granted.setImageResource(R.mipmap.right_01);
//                    list.get(position).setPermission_granted("yes");
//
//                    //studentsDayAttendanceByWarden();
//                }else {
//                    holder.iv_permission_granted.setImageResource(R.mipmap.cross_02);
//                    list.get(position).setPermission_granted("no");
//                    //studentsDayAttendanceByWarden();
//                }
//
//
//            }
//        });

//        holder.iv_permission_rejected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(list.get(position).getPermission_rejected().equalsIgnoreCase("no")){
//                    holder.iv_permission_rejected.setImageResource(R.mipmap.cross_01);
//                    list.get(position).setPermission_rejected("yes");
//
//                    //studentsDayAttendanceByWarden();
//                }else {
//                    holder.iv_permission_rejected.setImageResource(R.mipmap.right_02);
//                    list.get(position).setPermission_rejected("no");
//                    //studentsDayAttendanceByWarden();
//                }
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
