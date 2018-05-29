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

/**
 * Created by objsol on 05/03/18.
 */

public class Rooms_Adapter extends RecyclerView.Adapter<Rooms_Adapter.MyViewHolder> {

    ArrayList<Comment_SetGet> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_rooms;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_rooms = (TextView) itemView.findViewById(R.id.tv_rooms);


        }
    }


    public Rooms_Adapter(Context mContext, ArrayList<Comment_SetGet> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_rooms, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_rooms.setText(list.get(position).getRooms());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
