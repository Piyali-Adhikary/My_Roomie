package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.NewsLetter_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;

/**
 * Created by objsol on 05/03/18.
 */

public class Newsletter_Adapter extends RecyclerView.Adapter<Newsletter_Adapter.MyViewHolder> {

    ArrayList<NewsLetter_SetGet> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_title,tv_date, tv_newsletter_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_child_student_newsletter);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date_child_student_newsletter);
            tv_newsletter_content = (TextView) itemView.findViewById(R.id.tv_newsletter_content_child_newsletter);

        }
    }


    public Newsletter_Adapter(Context mContext, ArrayList<NewsLetter_SetGet> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_student_newsletter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_title.setText(list.get(position).getNotice_title());
        holder.tv_date.setText(list.get(position).getDatetime());
        holder.tv_newsletter_content.setText(list.get(position).getNotice_content());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
