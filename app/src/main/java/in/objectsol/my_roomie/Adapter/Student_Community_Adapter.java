package in.objectsol.my_roomie.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.Activity.Community_Details;
import in.objectsol.my_roomie.Activity.Community_Student_Profile;
import in.objectsol.my_roomie.Activity.MainActivity;
import in.objectsol.my_roomie.Activity.Parent_Permission_Request;
import in.objectsol.my_roomie.Activity.Student_Attendance_By_Warden;
import in.objectsol.my_roomie.Activity.Warden_Comment;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Student_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Student_Community_Adapter extends RecyclerView.Adapter<Student_Community_Adapter.MyViewHolder> implements IJSONParseListener {

    ArrayList<Student_SetGet> list;
    Context mContext;
    String status="",friend_id="",campus_id;
    Boolean isPresent=true;
    String student_id="";
    public static String community_id="",status_comment="",date_comment="",attendance_id_comment="";
    public static ArrayList<HashMap<String, String>> submit_student_attendance_arraylist;

    int studentReferCommunity=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    private static final String Student_Refer_Community_Url = "http://174.136.1.35/dev/myroomie/student/studentReferCommunity/";



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView student_name,student_location;
        public CircleImageView student_image;
        public RelativeLayout rl_child_student_list;
        public MyViewHolder(View itemView) {
            super(itemView);
            student_name = (TextView) itemView.findViewById(R.id.tv_student_name_child_student_list_community);
            student_location = (TextView) itemView.findViewById(R.id.tv_student_location_child_student_list_community);
            student_image = (CircleImageView) itemView.findViewById(R.id.iv_student_image_child_student_list_community);
            rl_child_student_list = (RelativeLayout) itemView.findViewById(R.id.rl_child_student_list_community);
        }
    }


    public Student_Community_Adapter(Context mContext, ArrayList<Student_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
        submit_student_attendance_arraylist=new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_community_student_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.student_name.setText(list.get(position).getStudent_name());
        holder.student_location.setText(list.get(position).getEmail());

        Glide.with(mContext).load(list.get(position).getStudent_pic())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.student_image);


        holder.rl_child_student_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Community_Student_Profile.student_id=list.get(position).getStudent_id();
                friend_id=list.get(position).getStudent_id();
                campus_id=list.get(position).getCampus_id();
                studentReferCommunity();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



   /* public void filterList(ArrayList<Student_SetGet> filterdNames) {
        this.list = filterdNames;
        notifyDataSetChanged();
    }*/


    void studentReferCommunity() {
        JSONRequestResponse mResponse = new JSONRequestResponse(mContext);
        Bundle parms = new Bundle();
        parms.putString("community_id", community_id);
        parms.putString("campus_id", campus_id);
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("friend_id", friend_id);
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(mContext);
        ShowProgressDilog(mContext);
        mResponse.getResponse(Request.Method.POST, Student_Refer_Community_Url, studentReferCommunity, this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(mContext);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("System Error");
        alertDialogBuilder.setMessage("Sorry Some Error Occurred");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void SuccessResponse(JSONObject response, int requestCode) {

        DismissProgress(mContext);

        if (requestCode == studentReferCommunity) {
            System.out.println("Response for studentReferCommunity------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Toast.makeText(mContext, response.getString("result") , Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mContext,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ((Community_Details)mContext).finish();




                }else {
                    Toast.makeText(mContext,response.getString("result"),Toast.LENGTH_LONG).show();
                }
            }

            catch (Exception e)
            {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(mContext);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(mContext);

        Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
        Intent intent=new Intent(mContext,Parent_Permission_Request.class);
        mContext.startActivity(intent);
        ((Parent_Permission_Request)mContext).finish();
    }

    void ShowProgressDilog(Context c) {
        pDialog = new ProgressDialog(c);
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setContentView(R.layout.layout_progress_dilog);
    }

    void DismissProgress(Context c) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }
}
