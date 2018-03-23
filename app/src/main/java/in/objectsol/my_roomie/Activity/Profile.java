package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.R;

/**
 * Created by objsol on 13/03/18.
 */

public class Profile extends AppCompatActivity{

    ImageView iv_back;
    CircleImageView iv;
    TextView tv_profile_name,tv_campus_name,tv_room_no,tv_bed_no,tv_dob,tv_email,tv_address;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        iv=(CircleImageView) findViewById(R.id.iv_profile_pic);
        iv_back=(ImageView) findViewById(R.id.iv_back_student_profile);
        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);


        tv_profile_name=(TextView)findViewById(R.id.tv_student_profile_name);
        tv_campus_name=(TextView)findViewById(R.id.tv_student_profile_campus_name);
        tv_room_no=(TextView)findViewById(R.id.tv_student_profile_room_no);
        tv_bed_no=(TextView)findViewById(R.id.tv_student_profile_bed_no);
        tv_dob=(TextView)findViewById(R.id.tv_student_profile_dob);
        tv_email=(TextView)findViewById(R.id.tv_student_profile_email);
        tv_address=(TextView)findViewById(R.id.tv_student_profile_address);

        tv_profile_name.setText(sharedPreferences.getString("student_name",""));
        tv_campus_name.setText(sharedPreferences.getString("campus_name",""));
        tv_room_no.setText(sharedPreferences.getString("room_no",""));
        tv_bed_no.setText(sharedPreferences.getString("bed_no",""));
        tv_dob.setText(sharedPreferences.getString("dob",""));
        tv_email.setText(sharedPreferences.getString("email",""));
        tv_address.setText(sharedPreferences.getString("address",""));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Glide.with(Profile.this).load(sharedPreferences.getString("student_pic",""))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);


//        Glide.with(Profile.this).load(sharedPreferences.getString("student_pic","")).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(Profile.this.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                iv.setImageDrawable(circularBitmapDrawable);
//            }
//        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    iv.setVisibility(View.GONE);
                } else if (isShow) {
                    isShow = false;

                    iv.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
