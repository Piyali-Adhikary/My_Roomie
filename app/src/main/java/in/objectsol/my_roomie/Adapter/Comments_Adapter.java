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
import in.objectsol.my_roomie.SetGet.Comment_SetGet;
import in.objectsol.my_roomie.SetGet.NewsLetter_SetGet;

/**
 * Created by objsol on 05/03/18.
 */

public class Comments_Adapter extends RecyclerView.Adapter<Comments_Adapter.MyViewHolder> {

    ArrayList<Comment_SetGet> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_warden_comment,tv_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_warden_comment = (TextView) itemView.findViewById(R.id.tv_warden_comment_child_attendance);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_child_attendance);


        }
    }


    public Comments_Adapter(Context mContext, ArrayList<Comment_SetGet> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_comment_student_attendance, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_warden_comment.setText(list.get(position).getComment());
        String date= list.get(position).getDate();
        holder.tv_date.setText(date);

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
