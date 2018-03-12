package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Permission_Types_SetGet;

/**
 * Created by objsol on 05/03/18.
 */

public class Permission_Types_Adapter extends ArrayAdapter {
    ArrayList<Permission_Types_SetGet> list;
    Context mContext;
    int resourceID;

    @SuppressWarnings("unchecked")
    public Permission_Types_Adapter(Context mContext, int resourceID, ArrayList<Permission_Types_SetGet> list) {
        super(mContext, resourceID, list);
        this.mContext = mContext;
        this.resourceID = resourceID;
        this.list = list;

    }

    @Override
    public int getCount() {
        int count = list.size();
        return count > 0 ? count : 0;
        //return count > 0 ? count - 1 : count;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resourceID, null);
            holder = new ViewHolder();

            holder.tv = (TextView) view.findViewById(R.id.tv_spinner);


            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        Permission_Types_SetGet newCategorySetGet=list.get(position);
        holder.tv.setText(newCategorySetGet.getPermission_type());



        return view;

    }

    //********* Create a holder Class to contain inflated xml file elements *********//*
    public static class ViewHolder {
        public TextView tv;


    }
}
