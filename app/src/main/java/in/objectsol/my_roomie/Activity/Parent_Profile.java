package in.objectsol.my_roomie.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import in.objectsol.my_roomie.R;

/**
 * Created by objsol on 13/03/18.
 */

public class Parent_Profile extends AppCompatActivity{

    ImageView iv_back;
    CircleImageView iv;
    TextView tv_profile_name,tv_campus_name,tv_room_no,tv_bed_no,tv_dob,tv_email,tv_address,tv_parent_phone_number,tv_parent_email;
    TextView tv_student_name;
    SharedPreferences sharedPreferences,sharedPreferences_student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_parent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_parent_profile);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        sharedPreferences_student=getSharedPreferences("student",MODE_PRIVATE);
        iv = (CircleImageView) findViewById(R.id.iv_profile_pic_parent_profile);
        iv_back = (ImageView) findViewById(R.id.iv_back_parent_profile);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_parent_profile);

        tv_profile_name=(TextView)findViewById(R.id.tv_parent_profile_name);
        tv_parent_phone_number=(TextView)findViewById(R.id.tv_parent_profile_phone_number);
        tv_parent_email=(TextView)findViewById(R.id.tv_parent_profile_email);
        tv_student_name=(TextView)findViewById(R.id.tv_parent_profile_student_name);
        tv_campus_name=(TextView)findViewById(R.id.tv_parent_profile_campus_name);
        tv_room_no=(TextView)findViewById(R.id.tv_parent_profile_room_no);
        tv_bed_no=(TextView)findViewById(R.id.tv_parent_profile_bed_no);
        tv_dob=(TextView)findViewById(R.id.tv_parent_profile_dob);
        tv_email=(TextView)findViewById(R.id.tv_parent_profile_student_email);
        tv_address=(TextView)findViewById(R.id.tv_parent_profile_address);


        tv_profile_name.setText(sharedPreferences.getString("parent_name",""));
        tv_parent_phone_number.setText(sharedPreferences.getString("parent_mobile",""));
        tv_parent_email.setText(sharedPreferences.getString("parent_email",""));
        tv_student_name.setText(sharedPreferences_student.getString("student_name",""));
        tv_campus_name.setText(sharedPreferences.getString("campus_name",""));
        tv_room_no.setText(sharedPreferences_student.getString("room_no",""));
        tv_bed_no.setText(sharedPreferences_student.getString("bed_no",""));
        tv_dob.setText(sharedPreferences_student.getString("dob",""));
        tv_email.setText(sharedPreferences_student.getString("email",""));
        tv_address.setText(sharedPreferences_student.getString("address",""));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Parent_Profile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Glide.with(Parent_Profile.this).load(sharedPreferences.getString("parent_pic",""))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);


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
        Intent intent=new Intent(Parent_Profile.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
