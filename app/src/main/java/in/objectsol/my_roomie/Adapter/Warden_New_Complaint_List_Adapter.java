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

import java.util.ArrayList;

import in.objectsol.my_roomie.Activity.Parent_Permission_Request;
import in.objectsol.my_roomie.Activity.Warden_Complaint_Status;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_SetGet;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by objsol on 05/03/18.
 */

public class Warden_New_Complaint_List_Adapter extends RecyclerView.Adapter<Warden_New_Complaint_List_Adapter.MyViewHolder> implements IJSONParseListener{

    ArrayList<Permission_SetGet> list;
    Context mContext;
    int resourceID;
    String status="";
    Boolean isPresent=true;
    int studentComplaintStatusChange=619;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    String student_id="",complaint_status="", complaint_id="",description="", complain_created_at="" , complain_type="";
    private static final String Warden_Complaint_Status_Change_Url = "http://174.136.1.35/dev/myroomie/warden/studentComplaintStatusChange/";



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_complaint,tv_date,tv_complaint_description,tv_status;
        public ImageView iv_child_complaint_granted;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_complaint = (TextView) itemView.findViewById(R.id.tv_child_student_complaint);
            tv_date = (TextView) itemView.findViewById(R.id.tv_child_complaint_created_date);
            tv_status = (TextView) itemView.findViewById(R.id.tv_child_complaint_status);
            tv_complaint_description = (TextView) itemView.findViewById(R.id.tv_child_complaint_description);
            iv_child_complaint_granted = (ImageView) itemView.findViewById(R.id.iv_child_complaint_granted);

        }
    }


    public Warden_New_Complaint_List_Adapter(Context mContext, ArrayList<Permission_SetGet> list) {

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

        holder.tv_date.setText(list.get(position).getCreated_at());
        holder.tv_complaint.setText(list.get(position).getPermission_type());
        holder.tv_complaint_description.setText(list.get(position).getDescription());

        complaint_id=list.get(position).getId();
        student_id=list.get(position).getStudent_id();
        complain_type=list.get(position).getPermission_type();
        description=list.get(position).getDescription();
        complain_created_at=list.get(position).getCreated_at();


        if(list.get(position).getStatus().equalsIgnoreCase("pending")){

            holder.tv_status.setText("Status : Not Yet Fixed" );

        }else if(list.get(position).getStatus().equalsIgnoreCase("fixed")){

            holder.tv_status.setText("Status : Not Yet Verified");

        }

//        if(list.get(position).getStatus().equalsIgnoreCase("verified")){
//            holder.iv_child_complaint_granted.setImageResource(R.mipmap.right_01);
//        }else {
//            holder.iv_child_complaint_granted.setImageResource(R.mipmap.cross_02);
//        }

        if(list.get(position).getPermission_granted().equalsIgnoreCase("yes")){
            holder.iv_child_complaint_granted.setImageResource(R.mipmap.right_01);
        }else {
            holder.iv_child_complaint_granted.setImageResource(R.mipmap.cross_02);
        }

        holder.iv_child_complaint_granted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(position).getPermission_granted().equalsIgnoreCase("no")){
                    holder.iv_child_complaint_granted.setImageResource(R.mipmap.right_01);
                    list.get(position).setPermission_granted("yes");
                    complaint_status="verified";


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Alert");
                    alertDialogBuilder.setMessage("Do you want to submit?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studentComplaintStatusChange(complaint_status);
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            holder.iv_child_complaint_granted.setImageResource(R.mipmap.cross_02);
                            list.get(position).setPermission_granted("no");
                            complaint_status="";
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else {
                    holder.iv_child_complaint_granted.setImageResource(R.mipmap.cross_02);
                    list.get(position).setPermission_granted("no");
                    complaint_status="";

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    void studentComplaintStatusChange(String status) {
        JSONRequestResponse mResponse = new JSONRequestResponse(mContext);
        Bundle parms = new Bundle();
        parms.putString("complaint_id", complaint_id);
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("student_id", student_id);
        parms.putString("warden_id", sharedPreferences.getString("warden_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));
        parms.putString("complain_type", complain_type);
        parms.putString("complain_created_at", complain_created_at);
        parms.putString("complain_status", status);
        parms.putString("description", description);

        MyVolley.init(mContext);
        ShowProgressDilog(mContext);
        mResponse.getResponse(Request.Method.POST, Warden_Complaint_Status_Change_Url, studentComplaintStatusChange, this, parms, false);
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

        if (requestCode == studentComplaintStatusChange) {
            System.out.println("Response for studentComplaintStatusChange------" + response.toString());

            try {


                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Intent intent=new Intent(mContext,Warden_Complaint_Status.class);
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
}
