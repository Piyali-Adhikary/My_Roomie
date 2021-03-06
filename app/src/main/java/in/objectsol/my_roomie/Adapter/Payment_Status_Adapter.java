package in.objectsol.my_roomie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.objectsol.my_roomie.Activity.Payment_Debit;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.SetGet.Payment_SetGet;
import in.objectsol.my_roomie.SetGet.SetgetDay;

/**
 * Created by objsol on 05/03/18.
 */

public class Payment_Status_Adapter extends RecyclerView.Adapter<Payment_Status_Adapter.MyViewHolder> {

    ArrayList<Payment_SetGet> list;
    Context mContext;
    ImageView imageView_feb;
    TextView tv_pay_button;
    String amount,payment_for,id,created_at,payment_status,year,month;



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

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);


                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,20,10,20);
                linearLayout.setTag("linear");


                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_jan().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);
                tv_amount.setTag("amount");

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_jan().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);
                tv_payment_for.setTag("payment_for");

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);



                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setTag("image");
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_jan().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_jan().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);
                if (list.get(position).getArrayList_jan().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                              //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                                 id= list.get(position).getArrayList_jan().get(view.getId()).getId();
                                 payment_status= list.get(position).getArrayList_jan().get(view.getId()).getPayment_status();
                                 year=list.get(position).getArrayList_jan().get(view.getId()).getYear();
                                 month=list.get(position).getArrayList_jan().get(view.getId()).getMonth();
                                 created_at=list.get(position).getArrayList_jan().get(view.getId()).getCreated_at();
                                 amount=list.get(position).getArrayList_jan().get(view.getId()).getPayment_amount();
                                 payment_for=list.get(position).getArrayList_jan().get(view.getId()).getPayment_for();




                            /*    for(int k= 0 ;k < parent.getChildCount() ;k++)
                                {
                                    LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                    if(v.getTag().toString().equalsIgnoreCase("linear"))
                                    {
                                        LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                        for(int i= 0 ; i< l1.getChildCount() ;i++)
                                        {
                                            TextView tv= (TextView) l1.getChildAt(i);
                                            if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                            {
                                                //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                                amount=tv.getText().toString();


                                            }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                               // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                                payment_for=tv.getText().toString();
                                            }
                                        }


                                    }


                                }*/


                                Intent intent = new Intent(mContext, Payment_Debit.class);
                                intent.putExtra("amount",amount);
                                intent.putExtra("payment_for",payment_for);
                                intent.putExtra("payment_status",payment_status);
                                intent.putExtra("id",id);
                                intent.putExtra("year",year);
                                intent.putExtra("month",month);
                                intent.putExtra("created_at",created_at);
                                mContext.startActivity(intent);



                            }
                        });



                }else {

                    String date= list.get(position).getArrayList_jan().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);
                    tv_payment_date.setTag("date");

                    linearLayout.addView(tv_payment_date);
                }

                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_jan.addView(parent);
            }
        }

        if(list.get(position).getArrayList_feb().size()>0){

            for (int i=0;i<list.get(position).getArrayList_feb().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                //parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_feb().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_feb().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_feb().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_feb().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_feb().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_feb().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_feb().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_feb().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_feb().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_feb().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_feb().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_feb().get(view.getId()).getPayment_for();




                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });


                }else {

                    String date= list.get(position).getArrayList_feb().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }


                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_feb.addView(parent);
            }
        }

        if(list.get(position).getArrayList_march().size()>0){

            for (int i=0;i<list.get(position).getArrayList_march().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_march().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_march().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_march().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_march().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_march().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_march().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_march().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_march().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_march().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_march().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_march().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_march().get(view.getId()).getPayment_for();




                           /* for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }
*/

                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {

                    String date= parseDateToddMMyyyy(list.get(position).getArrayList_march().get(i).getPayment_date());
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);
                    linearLayout.addView(tv_payment_date);
                }



                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_march.addView(parent);
            }

        }

        if(list.get(position).getArrayList_april().size()>0){

            for (int i=0;i<list.get(position).getArrayList_april().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_april().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_april().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_april().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_april().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_april().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_april().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_april().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_april().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_april().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_april().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_april().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_april().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {

                    String date= list.get(position).getArrayList_april().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }




                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_april.addView(parent);
            }

        }

        if(list.get(position).getArrayList_may().size()>0){

            for (int i=0;i<list.get(position).getArrayList_may().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_may().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_may().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_may().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_may().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_may().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_may().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_may().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_may().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_may().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_may().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_may().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_may().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date= list.get(position).getArrayList_may().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }


                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_may.addView(parent);
            }

        }

        if(list.get(position).getArrayList_june().size()>0){

            for (int i=0;i<list.get(position).getArrayList_june().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_june().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_june().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);



                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_june().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_june().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_june().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_june().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_june().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_june().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_june().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_june().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_june().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_june().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });
                }else {
                    String date= list.get(position).getArrayList_june().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }



                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_june.addView(parent);
            }

        }

        if(list.get(position).getArrayList_july().size()>0){

            for (int i=0;i<list.get(position).getArrayList_july().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_july().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_july().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);

                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_july().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_july().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_july().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_july().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_july().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_july().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_july().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_july().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_july().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_july().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {

                    String date= list.get(position).getArrayList_july().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }



                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_july.addView(parent);
            }

        }

        if(list.get(position).getArrayList_aug().size()>0){

            for (int i=0;i<list.get(position).getArrayList_aug().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_aug().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_aug().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_aug().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_aug().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_aug().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_aug().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_aug().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_aug().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_aug().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_aug().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_aug().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_aug().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date= list.get(position).getArrayList_aug().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }



                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_aug.addView(parent);
            }

        }

        if(list.get(position).getArrayList_sept().size()>0){

            for (int i=0;i<list.get(position).getArrayList_sept().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_sept().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_sept().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_sept().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_sept().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_sept().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_sept().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_sept().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_sept().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_sept().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_sept().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_sept().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_sept().get(view.getId()).getPayment_for();



                           /* for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date= list.get(position).getArrayList_sept().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }




                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_sept.addView(parent);
            }

        }

        if(list.get(position).getArrayList_oct().size()>0){

            for (int i=0;i<list.get(position).getArrayList_oct().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_oct().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_oct().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_oct().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_oct().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_oct().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                          //  Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_oct().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_oct().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_oct().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_oct().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_oct().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_oct().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_oct().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date= list.get(position).getArrayList_oct().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }




                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_oct.addView(parent);
            }

        }

        if(list.get(position).getArrayList_nov().size()>0){

            for (int i=0;i<list.get(position).getArrayList_nov().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_nov().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_nov().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_nov().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_nov().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }
                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_nov().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_nov().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_nov().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_nov().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_nov().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_nov().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_nov().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_nov().get(view.getId()).getPayment_for();



                           /* for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date= list.get(position).getArrayList_nov().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);

                    linearLayout.addView(tv_payment_date);
                }




                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_nov.addView(parent);
            }

        }

        if(list.get(position).getArrayList_dec().size()>0){

            for (int i=0;i<list.get(position).getArrayList_dec().size();i++){

                final LinearLayout parent = new LinearLayout(mContext);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);
                parent.setBackgroundResource(R.drawable.ll_border_blue);
                parent.setGravity(Gravity.CENTER);

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.LEFT);
                linearLayout.setPadding(10,10,10,10);

                TextView tv_amount= new TextView(mContext);
                tv_amount.setText("Amount : " + list.get(position).getArrayList_dec().get(i).getPayment_amount());
                tv_amount.setPadding(10,5,10,5);

                TextView tv_payment_for= new TextView(mContext);
                tv_payment_for.setText("Payment For : " + list.get(position).getArrayList_dec().get(i).getPayment_for());
                tv_payment_for.setPadding(10,5,10,5);


                linearLayout.addView(tv_amount);
                linearLayout.addView(tv_payment_for);

                LinearLayout linearLayout1 = new LinearLayout(mContext);
                linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f));
                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                linearLayout1.setGravity(Gravity.LEFT);
                linearLayout1.setPadding(10,20,10,20);
                linearLayout1.setTag("linear_down");

                ImageView imageView=new ImageView(mContext);
                imageView.setPadding(10,10,10,10);
                if (list.get(position).getArrayList_dec().get(i).getPayment_status().equalsIgnoreCase("paid")){

                    imageView.setImageResource(R.mipmap.paid);
                }else if (list.get(position).getArrayList_dec().get(i).getPayment_status().equalsIgnoreCase("due")){

                    imageView.setImageResource(R.mipmap.due);
                }else {

                }

                linearLayout1.addView(imageView);

                if (list.get(position).getArrayList_dec().get(i).getPayment_date().equalsIgnoreCase("")){

                    LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(200, 80);
                    tv_pay_button= new TextView(mContext);
                    tv_pay_button.setGravity(Gravity.CENTER);
                    tv_pay_button.setLayoutParams(layoutParams);
                    tv_pay_button.setText("Pay");
                    tv_pay_button.setPadding(10,5,10,5);
                    tv_pay_button.setBackgroundColor(Color.parseColor("#929000"));
                    tv_pay_button.setTextColor(Color.WHITE);
                    tv_pay_button.setTag("pay_button");
                    tv_pay_button.setId(i);

                    linearLayout.addView(tv_pay_button);


                    tv_pay_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // Toast.makeText(mContext, "Tag" + view.getTag(), Toast.LENGTH_SHORT).show();


                            id= list.get(position).getArrayList_dec().get(view.getId()).getId();
                            payment_status= list.get(position).getArrayList_dec().get(view.getId()).getPayment_status();
                            year=list.get(position).getArrayList_dec().get(view.getId()).getYear();
                            month=list.get(position).getArrayList_dec().get(view.getId()).getMonth();
                            created_at=list.get(position).getArrayList_dec().get(view.getId()).getCreated_at();
                            amount=list.get(position).getArrayList_dec().get(view.getId()).getPayment_amount();
                            payment_for=list.get(position).getArrayList_dec().get(view.getId()).getPayment_for();



                            /*for(int k= 0 ;k < parent.getChildCount() ;k++)
                            {
                                LinearLayout v= (LinearLayout) parent.getChildAt(k);
                                if(v.getTag().toString().equalsIgnoreCase("linear"))
                                {
                                    LinearLayout l1= (LinearLayout) parent.getChildAt(k);
                                    for(int i= 0 ; i< l1.getChildCount() ;i++)
                                    {
                                        TextView tv= (TextView) l1.getChildAt(i);
                                        if(tv.getTag().toString().equalsIgnoreCase("amount"))
                                        {
                                            //Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            amount=tv.getText().toString();


                                        }else if(tv.getTag().toString().equalsIgnoreCase("payment_for")){
                                            // Toast.makeText(mContext, ""+tv.getText(), Toast.LENGTH_SHORT).show();
                                            payment_for=tv.getText().toString();
                                        }
                                    }


                                }


                            }*/


                            Intent intent = new Intent(mContext, Payment_Debit.class);
                            intent.putExtra("amount",amount);
                            intent.putExtra("payment_for",payment_for);
                            intent.putExtra("payment_status",payment_status);
                            intent.putExtra("id",id);
                            intent.putExtra("year",year);
                            intent.putExtra("month",month);
                            intent.putExtra("created_at",created_at);
                            mContext.startActivity(intent);



                        }
                    });

                }else {
                    String date=list.get(position).getArrayList_dec().get(i).getPayment_date();
                    TextView tv_payment_date= new TextView(mContext);
                    tv_payment_date.setText("Payment Date : " + date);
                    tv_payment_date.setPadding(10,5,10,5);


                    linearLayout.addView(tv_payment_date);
                }




                parent.addView(linearLayout);
                parent.addView(linearLayout1);
                holder.ll_dec.addView(parent);
            }

        }



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
