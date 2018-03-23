package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Payment_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;

/**
 * Created by objsol on 05/03/18.
 */

public class Payment_Status_Adapter extends RecyclerView.Adapter<Payment_Status_Adapter.MyViewHolder> {

    ArrayList<Payment_SetGet> list;
    Context mContext;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_jan,tv_feb,tv_march,tv_april, tv_may, tv_june, tv_july, tv_aug, tv_sept, tv_oct, tv_nov, tv_dec;
        public LinearLayout ll_jan,ll_feb,ll_march, ll_april, ll_may, ll_june, ll_july, ll_aug,ll_sept, ll_oct, ll_nov, ll_dec;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_jan = (TextView) itemView.findViewById(R.id.tv_jan_payment_status);
            tv_feb = (TextView) itemView.findViewById(R.id.tv_feb_payment_status);
            tv_march = (TextView) itemView.findViewById(R.id.tv_march_payment_status);
            tv_april = (TextView) itemView.findViewById(R.id.tv_april_payment_status);
            tv_may = (TextView) itemView.findViewById(R.id.tv_may_payment_status);
            tv_june = (TextView) itemView.findViewById(R.id.tv_june_payment_status);
            tv_july = (TextView) itemView.findViewById(R.id.tv_july_payment_status);
            tv_aug = (TextView) itemView.findViewById(R.id.tv_aug_payment_status);
            tv_sept = (TextView) itemView.findViewById(R.id.tv_sept_payment_status);
            tv_oct = (TextView) itemView.findViewById(R.id.tv_oct_payment_status);
            tv_nov = (TextView) itemView.findViewById(R.id.tv_nov_payment_status);
            tv_dec = (TextView) itemView.findViewById(R.id.tv_dec_payment_status);


            ll_jan = (LinearLayout) itemView.findViewById(R.id.ll_jan_child_payment_status);
            ll_feb = (LinearLayout) itemView.findViewById(R.id.ll_feb_child_payment_status);
            ll_march = (LinearLayout) itemView.findViewById(R.id.ll_march_child_payment_status);
            ll_april = (LinearLayout) itemView.findViewById(R.id.ll_april_child_payment_status);
            ll_may = (LinearLayout) itemView.findViewById(R.id.ll_may_child_payment_status);
            ll_june = (LinearLayout) itemView.findViewById(R.id.ll_june_child_payment_status);
            ll_july = (LinearLayout) itemView.findViewById(R.id.ll_july_child_payment_status);
            ll_aug = (LinearLayout) itemView.findViewById(R.id.ll_aug_child_payment_status);
            ll_sept = (LinearLayout) itemView.findViewById(R.id.ll_sept_child_payment_status);
            ll_oct = (LinearLayout) itemView.findViewById(R.id.ll_oct_child_payment_status);
            ll_nov = (LinearLayout) itemView.findViewById(R.id.ll_nov_child_payment_status);
            ll_dec = (LinearLayout) itemView.findViewById(R.id.ll_dec_child_payment_status);


        }
    }


    public Payment_Status_Adapter(Context mContext, ArrayList<Payment_SetGet> list) {

        this.mContext = mContext;
        this.list = list;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_payment_status, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(list.get(position).getArrayList_jan().size()>0){

            for (int i=0;i<list.get(position).getArrayList_jan().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,20,10,20);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_jan().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_jan().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_jan().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_jan().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_jan().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_jan().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_jan.addView(parent);
            }
        }

        if(list.get(position).getArrayList_feb().size()>0){

            for (int i=0;i<list.get(position).getArrayList_feb().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_feb().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_feb().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_feb().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_feb().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_feb().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_feb().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_feb.addView(parent);
            }
        }

        if(list.get(position).getArrayList_march().size()>0){

            for (int i=0;i<list.get(position).getArrayList_march().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_march().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_march().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_march().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_march().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_march().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_march().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_march.addView(parent);
            }

        }

        if(list.get(position).getArrayList_april().size()>0){

            for (int i=0;i<list.get(position).getArrayList_april().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_april().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_april().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_april().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_april().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_april().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_april().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_april.addView(parent);
            }

        }

        if(list.get(position).getArrayList_may().size()>0){

            for (int i=0;i<list.get(position).getArrayList_may().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_may().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_may().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_may().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_may().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_may().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_may().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_may.addView(parent);
            }

        }

        if(list.get(position).getArrayList_june().size()>0){

            for (int i=0;i<list.get(position).getArrayList_june().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_june().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_june().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_june().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_june().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_june().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_june().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_june.addView(parent);
            }

        }

        if(list.get(position).getArrayList_july().size()>0){

            for (int i=0;i<list.get(position).getArrayList_july().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_july().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_july().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_july().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_july().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_july().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_july().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_july.addView(parent);
            }

        }

        if(list.get(position).getArrayList_aug().size()>0){

            for (int i=0;i<list.get(position).getArrayList_aug().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_aug().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_aug().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_aug().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_aug().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_aug().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_aug().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_aug.addView(parent);
            }

        }

        if(list.get(position).getArrayList_sept().size()>0){

            for (int i=0;i<list.get(position).getArrayList_sept().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_sept().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_sept().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_sept().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_sept().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_sept().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_sept().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_sept.addView(parent);
            }

        }

        if(list.get(position).getArrayList_oct().size()>0){

            for (int i=0;i<list.get(position).getArrayList_oct().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_oct().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_oct().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_oct().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_oct().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_oct().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_oct().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_oct.addView(parent);
            }

        }

        if(list.get(position).getArrayList_nov().size()>0){

            for (int i=0;i<list.get(position).getArrayList_nov().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_nov().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_nov().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_nov().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_nov().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_nov().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_nov().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_nov.addView(parent);
            }

        }

        if(list.get(position).getArrayList_dec().size()>0){

            for (int i=0;i<list.get(position).getArrayList_dec().size();i++){

                LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_dec().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_dec().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                TextView tv_payment_date= new TextView(mContext);
                tv_payment_date.setText("Payment Date : " + list.get(position).getArrayList_dec().get(i).getPayment_date());
                tv_payment_date.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                if (list.get(position).getArrayList_dec().get(i).getPayment_date().equalsIgnoreCase("")){

                }else {
                    linearLayout.addView(tv_payment_date);
                }


                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_dec().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_dec().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                parent.addView(linearLayout);
                parent.addView(imageView);
                holder.ll_dec.addView(parent);
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
