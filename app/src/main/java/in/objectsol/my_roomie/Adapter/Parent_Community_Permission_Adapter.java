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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.objectsol.my_roomie.Activity.Parent_Permission_Request;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Community_SetGet;
import in.objectsol.my_roomie.SetGet.Permission_Community_SetGet;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Parent_Community_Permission_Adapter extends RecyclerView.Adapter<Parent_Community_Permission_Adapter.MyViewHolder> implements IJSONParseListener{

    ArrayList<Permission_Community_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    int studentPermissionsStatusChange=615;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences,preferences;
    String community_id="",year="",month="",payment_amount="",payment_for="",permission_created_at="",permission_status="";
    private static final String Student_Permissions_Status_Change_Url = "http://174.136.1.35/dev/myroomie/parents/parentAcceptCommunity/";




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


    public Parent_Community_Permission_Adapter(Context mContext, ArrayList<Permission_Community_SetGet> list) {

        this.mContext = mContext;
        this.list = list;
        sharedPreferences=mContext.getSharedPreferences("Login",MODE_PRIVATE);
        preferences=mContext.getSharedPreferences("student",MODE_PRIVATE);
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
        holder.tv_community_request_parent.setText(list.get(position).getDescription());
        holder.tv_community_request.setText(list.get(position).getPermission_type());
        holder.tv_status.setText(list.get(position).getStatus());


        if(list.get(position).getStatus().equalsIgnoreCase("pending")){
            holder.iv_community_request_granted.setImageResource(R.mipmap.cross_02);
            holder.iv_community_request_rejected.setImageResource(R.mipmap.right_02);
            holder.iv_community_request_granted.setEnabled(true);
            holder.iv_community_request_rejected.setEnabled(true);
        }else if(list.get(position).getStatus().equalsIgnoreCase("accepted")){
            holder.iv_community_request_granted.setImageResource(R.mipmap.right_01);
            holder.iv_community_request_granted.setEnabled(false);
            holder.iv_community_request_rejected.setEnabled(false);
        }else if(list.get(position).getStatus().equalsIgnoreCase("declined")){
            holder.iv_community_request_rejected.setImageResource(R.mipmap.cross_01);
            holder.iv_community_request_granted.setEnabled(false);
            holder.iv_community_request_rejected.setEnabled(false);
        }

       /* if(list.get(position).getPermission_granted().equalsIgnoreCase("yes")){
            holder.iv_permission_granted.setImageResource(R.mipmap.right_01);
        }else {
            holder.iv_permission_granted.setImageResource(R.mipmap.cross_02);
        }

        if(list.get(position).getPermission_rejected().equalsIgnoreCase("yes")){
            holder.iv_permission_rejected.setImageResource(R.mipmap.cross_01);
        }else {
            holder.iv_permission_rejected.setImageResource(R.mipmap.right_02);
        }*/

        holder.iv_community_request_granted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //permission_created_at=createdTime;
                year=list.get(position).getYear();
                payment_for=list.get(position).getPayment_for();
                community_id=list.get(position).getCommunity_id();
                month=list.get(position).getMonth();
                payment_amount=list.get(position).getCharges();

                if(list.get(position).getStatus().equalsIgnoreCase("pending")){
                    holder.iv_community_request_granted.setImageResource(R.mipmap.right_01);
                    list.get(position).setStatus("accepted");
                    holder.iv_community_request_rejected.setClickable(false);
                    permission_status="accepted";


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Alert");
                    alertDialogBuilder.setMessage("Do you want to accept permission?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studentCommunityPermissionsStatusChange(permission_status);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            holder.iv_community_request_granted.setImageResource(R.mipmap.cross_02);
                            list.get(position).setStatus("pending");
                            holder.iv_community_request_rejected.setClickable(true);
                            permission_status="pending";
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else {
                    holder.iv_community_request_granted.setImageResource(R.mipmap.cross_02);
                    list.get(position).setStatus("pending");
                    holder.iv_community_request_rejected.setClickable(true);
                    permission_status="pending";

                }


            }
        });

        holder.iv_community_request_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year=list.get(position).getYear();
                payment_for=list.get(position).getPayment_for();
                community_id=list.get(position).getCommunity_id();
                month=list.get(position).getMonth();
                payment_amount=list.get(position).getCharges();

                if(list.get(position).getStatus().equalsIgnoreCase("pending")){
                    holder.iv_community_request_rejected.setImageResource(R.mipmap.cross_01);
                    list.get(position).setStatus("declined");
                    holder.iv_community_request_granted.setClickable(false);
                    permission_status="declined";


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Alert");
                    alertDialogBuilder.setMessage("Do you want to reject permission?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studentCommunityPermissionsStatusChange(permission_status);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            holder.iv_community_request_rejected.setImageResource(R.mipmap.right_02);
                            list.get(position).setStatus("pending");
                            holder.iv_community_request_granted.setClickable(true);
                            permission_status="pending";
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    //studentsDayAttendanceByWarden();
                }else {
                    holder.iv_community_request_rejected.setImageResource(R.mipmap.right_02);
                    list.get(position).setStatus("pending");
                    holder.iv_community_request_granted.setClickable(true);
                    permission_status="pending";
                    //studentsDayAttendanceByWarden();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void studentCommunityPermissionsStatusChange(String status) {
        JSONRequestResponse mResponse = new JSONRequestResponse(mContext);
        Bundle parms = new Bundle();
        parms.putString("community_id", community_id);
        parms.putString("campus_id", preferences.getString("campus_id",""));
        parms.putString("student_id", preferences.getString("student_id",""));
        parms.putString("parent_id", sharedPreferences.getString("parent_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("year", year);
        parms.putString("month", month);
        parms.putString("payment_amount", payment_amount);
        parms.putString("payment_for", payment_for);
        parms.putString("permission_status", permission_status);


        MyVolley.init(mContext);
        ShowProgressDilog(mContext);
        mResponse.getResponse(Request.Method.POST, Student_Permissions_Status_Change_Url, studentPermissionsStatusChange, this, parms, false);
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

        if (requestCode == studentPermissionsStatusChange) {
            System.out.println("Response for parentAcceptCommunity------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Intent intent=new Intent(mContext,Parent_Permission_Request.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ((Parent_Permission_Request)mContext).finish();
                    Toast.makeText(mContext, response.getString("result") , Toast.LENGTH_SHORT).show();

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
