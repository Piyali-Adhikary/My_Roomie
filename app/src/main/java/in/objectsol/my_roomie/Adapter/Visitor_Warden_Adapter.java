package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.NewsLetter_SetGet;
import in.objectsol.my_roomie.SetGet.Visitor_SetGet;

/**
 * Created by objsol on 05/03/18.
 */

public class Visitor_Warden_Adapter extends RecyclerView.Adapter<Visitor_Warden_Adapter.MyViewHolder> {

    ArrayList<Visitor_SetGet> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_room_no,tv_date, tv_visitor_name,tv_visitor_ph,tv_student_name,tv_relationship,tv_time_in,tv_time_out;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_room_no = (TextView) itemView.findViewById(R.id.tv_room_no_child_visitor_warden);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_child_visitor_warden);
            tv_visitor_name = (TextView) itemView.findViewById(R.id.tv_visitor_name_child_visitors_warden);
            tv_visitor_ph = (TextView) itemView.findViewById(R.id.tv_visitor_ph_child_visitors_warden);
            tv_student_name = (TextView) itemView.findViewById(R.id.tv_student_name_child_visitor_warden);
            tv_relationship = (TextView) itemView.findViewById(R.id.tv_relationship_child_visitors_warden);
            tv_time_in = (TextView) itemView.findViewById(R.id.tv_time_in_child_visitors_warden);
            tv_time_out = (TextView) itemView.findViewById(R.id.tv_time_out_child_visitors_warden);

        }
    }


    public Visitor_Warden_Adapter(Context mContext, ArrayList<Visitor_SetGet> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_visitors_warden, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_room_no.setText("Room No : " + list.get(position).getRoom_no());
        String date= list.get(position).getCreated_at();
        holder.tv_date.setText(date);
        holder.tv_visitor_name.setText("Visitor's Name : " + list.get(position).getName());
        holder.tv_visitor_ph.setText("Visitor's Contact : "+list.get(position).getContact());
        holder.tv_student_name.setText("Student Name : " +list.get(position).getStudent_name());
        holder.tv_relationship.setText("Relation With Student : "+list.get(position).getName());
        holder.tv_time_in.setText("Time-IN : "+list.get(position).getTime_in());
        holder.tv_time_out.setText("Time-OUT : "+list.get(position).getTime_out());

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
