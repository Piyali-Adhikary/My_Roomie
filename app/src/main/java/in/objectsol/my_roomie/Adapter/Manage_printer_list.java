package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Comment_SetGet;

/**
 * Created by puspak on 11/10/17.
 */

public class Manage_printer_list extends ArrayAdapter {

    ArrayList<Comment_SetGet> list;
    Context mContext;
    int resourceID;



    public Manage_printer_list(Context mContext, int resourceID, ArrayList<Comment_SetGet> list)
    {
        super(mContext, resourceID,list);
        this.mContext = mContext;
        this.resourceID = resourceID;
        this.list = list;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;

        final ViewHolder holder;



        if(convertView == null)
        {
            LayoutInflater inflater =  (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resourceID, null);
            holder = new ViewHolder();

            holder.wastage_product_id=(TextView)view.findViewById(R.id.single_Cause);




            view.setTag(holder);

        }
        else
            holder = (ViewHolder)view.getTag();


        holder.wastage_product_id.setText(list.get(position).getRooms());


        return view;

    }





    //********* Create a holder Class to contain inflated xml file elements *********//*
    public class ViewHolder
    {


        public TextView wastage_product_id;



    }
}
