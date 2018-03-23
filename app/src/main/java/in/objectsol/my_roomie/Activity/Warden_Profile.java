package in.objectsol.my_roomie.Activity;

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
 * Created by objsol on 14/03/18.
 */

public class Warden_Profile extends AppCompatActivity {

    ImageView iv_back;
    CircleImageView iv;
    TextView tv_profile_name,tv_warden_id,tv_warden_no;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile_warden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_warden_profile);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        iv = (CircleImageView) findViewById(R.id.iv_profile_pic_warden_profile);
        iv_back = (ImageView) findViewById(R.id.iv_back_warden_profile);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_warden_profile);

        tv_profile_name=(TextView)findViewById(R.id.tv_warden_profile_name);
        tv_warden_id=(TextView)findViewById(R.id.tv_warden_profile_warden_id);
        tv_warden_no=(TextView)findViewById(R.id.tv_warden_no);

        tv_profile_name.setText(sharedPreferences.getString("warden_name",""));
        tv_warden_id.setText(sharedPreferences.getString("warden_id",""));
        tv_warden_no.setText(sharedPreferences.getString("warden_mobile",""));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Warden_Profile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Glide.with(Warden_Profile.this).load(sharedPreferences.getString("warden_image",""))
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
        Intent intent=new Intent(Warden_Profile.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
