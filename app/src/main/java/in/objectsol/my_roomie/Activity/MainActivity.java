package in.objectsol.my_roomie.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import in.objectsol.my_roomie.Others.Config;
import in.objectsol.my_roomie.Others.NotificationUtils;
import in.objectsol.my_roomie.Others.Utils;
import in.objectsol.my_roomie.R;
import in.objectsol.my_roomie.Utils.IJSONParseListener;
import in.objectsol.my_roomie.Utils.JSONRequestResponse;
import in.objectsol.my_roomie.Utils.MyVolley;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IJSONParseListener {

    ImageView iv_attendance_warden;
    ImageView iv_parent_permission_request;

    String name = "";
    ImageView imageView;
    TextView nav_name;
    ImageView imageView_profile;
    SharedPreferences sharedPreferences;
    LinearLayout ll_student_dashboard,ll_parent_dashboard,ll_warden_dashboard;
    String user_id="";

    int getisAllowedPrivacyFromParent=601;
    int dologout=612;
    ProgressDialog pDialog;
    private static final String Is_Privacy_Allowed_From_Parent_URL = "http://174.136.1.35/dev/myroomie/student/isAllowedPrivacyFromParent/";
    private static final String Logout_URL = "http://174.136.1.35/dev/myroomie/account/logout/";
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        if(sharedPreferences.getString("user_type","").equalsIgnoreCase("S")){
            name="Student";
        }else if(sharedPreferences.getString("user_type","").equalsIgnoreCase("P")){
            name="Parent";
        }else if(sharedPreferences.getString("user_type","").equalsIgnoreCase("W")){
            name="Warden";
        }

        // ................................. drawer ....................................
        imageView = (ImageView) findViewById(R.id.imageView_profile);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        nav_name=(TextView)header.findViewById(R.id.nav_name);
        imageView_profile=(ImageView)header. findViewById(R.id.imageView_profile);
        navigationView.setVisibility(View.VISIBLE);


        if (name.equalsIgnoreCase("Student")) {

            nav_name.setText(sharedPreferences.getString("student_name",""));
            //navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
            navigationView.setNavigationItemSelectedListener(this);

            Glide.with(MainActivity.this).load(sharedPreferences.getString("student_pic",""))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView_profile);

        } else if (name.equalsIgnoreCase("Parent")) {


            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_parent_drawer);
            //navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
            navigationView.setNavigationItemSelectedListener(this);
            nav_name.setText(sharedPreferences.getString("parent_name",""));

        } else if (name.equalsIgnoreCase("Warden")) {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_warden_drawer);
            navigationView.setNavigationItemSelectedListener(this);
            nav_name.setText(sharedPreferences.getString("warden_name",""));
        }

//        Picasso.with(mContext).load(list.get(position).getProduct_image())
//                .transform(new CircleTransform())
//                .placeholder(R.drawable.sub)
//                .into(holder.iv_order_for_me);


        // ............................... mainactivity ...........................

        ll_student_dashboard=(LinearLayout) findViewById(R.id.ll_student_dashboard);
        ll_parent_dashboard=(LinearLayout) findViewById(R.id.ll_parent_dashboard);
        ll_warden_dashboard=(LinearLayout) findViewById(R.id.ll_warden_dashboard);


        if (name.equalsIgnoreCase("Student")) {

            ll_student_dashboard.setVisibility(View.VISIBLE);
            ll_parent_dashboard.setVisibility(View.GONE);
            ll_warden_dashboard.setVisibility(View.GONE);

        } else if (name.equalsIgnoreCase("Parent")) {

            ll_parent_dashboard.setVisibility(View.VISIBLE);
            ll_student_dashboard.setVisibility(View.GONE);
            ll_warden_dashboard.setVisibility(View.GONE);

        } else if (name.equalsIgnoreCase("Warden")) {

            ll_warden_dashboard.setVisibility(View.VISIBLE);
            ll_parent_dashboard.setVisibility(View.GONE);
            ll_student_dashboard.setVisibility(View.GONE);
        }

        //Warden View
        iv_attendance_warden=(ImageView)findViewById(R.id.iv_attendance_warden);

        iv_attendance_warden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Student_Attendance_By_Warden.class);
                startActivity(intent);
                finish();
            }
        });

        //Parent View

        iv_parent_permission_request=(ImageView)findViewById(R.id.iv_parent_permission_request);

        iv_parent_permission_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Parent_Permission_Request.class);
                startActivity(intent);
                finish();
            }
        });
        //FCM BroadcastReceiver

        mRegistrationBroadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }

            }
        };
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(name.equalsIgnoreCase("Student")){

            if (id == R.id.nav_profile) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_permission_request) {

                Intent intent= new Intent(MainActivity.this,Student_Permission.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_complaint_form) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(MainActivity.this,Student_Complaint_List.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_newsletter) {

                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_payment_status) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_attendance) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_privacy_settings) {

                if (Utils.isNetworkAvailable(MainActivity.this)) {

                    getisAllowedPrivacyFromParent();
                }
                else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("No Network Available");
                    alertDialogBuilder.setMessage("Please Turn On Your Internet Connection");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            } else if (id == R.id.nav_logout) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                user_id=sharedPreferences.getString("student_id","");
                Dologout();
            }
        }else if (name.equalsIgnoreCase("Parent")){

            if (id == R.id.nav_profile_parent) {
                // Handle the camera action
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_food_menu) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_payments_due) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_time_in_time_out) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_permission_request_parent) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this, Parent_Permission_Request.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_attend) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_logout_parent) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                user_id=sharedPreferences.getString("parent_id","");
                Dologout();
            }

        }else if (name.equalsIgnoreCase("Warden")){

            if (id == R.id.nav_profile_warden) {
                // Handle the camera action
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_attendance_warden) {

                Intent intent=new Intent(MainActivity.this, Student_Attendance_By_Warden.class);
                startActivity(intent);
                finish();

            } else if (id == R.id.nav_complaints_submitted) {

                Intent intent=new Intent(MainActivity.this, Warden_Complaint_Status.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.nav_request_approval) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_security) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_visitors) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_enter_time_in) {
                Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_logout_warden) {
                //Toast.makeText(MainActivity.this, "Yet to be Implemented", Toast.LENGTH_SHORT).show();

                user_id=sharedPreferences.getString("warden_id","");
                Dologout();
            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void Dologout() {
        JSONRequestResponse mResponse = new JSONRequestResponse(MainActivity.this);
        Bundle parms = new Bundle();
        parms.putString("user_id", user_id);
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("user_type", sharedPreferences.getString("user_type",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(MainActivity.this);
        ShowProgressDilog(MainActivity.this);
        mResponse.getResponse(Request.Method.POST, Logout_URL, dologout, MainActivity.this, parms, false);
    }

    void getisAllowedPrivacyFromParent() {
        JSONRequestResponse mResponse = new JSONRequestResponse(MainActivity.this);
        Bundle parms = new Bundle();
        parms.putString("student_id", sharedPreferences.getString("student_id",""));
        parms.putString("campus_id", sharedPreferences.getString("campus_id",""));
        parms.putString("auth_key", sharedPreferences.getString("auth_token",""));

        MyVolley.init(MainActivity.this);
        ShowProgressDilog(MainActivity.this);
        mResponse.getResponse(Request.Method.POST, Is_Privacy_Allowed_From_Parent_URL, getisAllowedPrivacyFromParent, MainActivity.this, parms, false);
    }

    @Override
    public void ErrorResponse(VolleyError error, int requestCode) {

        DismissProgress(MainActivity.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("System Error");
        alertDialogBuilder.setMessage("Sorry Some Error Occurred");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void SuccessResponse(JSONObject response, int requestCode) {

        DismissProgress(MainActivity.this);

        if (requestCode == getisAllowedPrivacyFromParent) {
            System.out.println("Response for getisAllowedPrivacyFromParent------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    Intent intent= new Intent(MainActivity.this,PrivacySettings.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, response.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }
        }else if (requestCode == dologout){

            System.out.println("Response for dologout------" + response.toString());

            try {

                if (response.getString("status_code").equalsIgnoreCase("success")) {

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent= new Intent(MainActivity.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, response.getString("result"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void SuccessResponseArray(JSONArray response, int requestCode) {
        DismissProgress(MainActivity.this);
    }

    @Override
    public void SuccessResponseRaw(String response, int requestCode) {
        DismissProgress(MainActivity.this);
    }

    void ShowProgressDilog(Context c) {
        pDialog = new ProgressDialog(c);
        pDialog.show();
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setContentView(R.layout.layout_progress_dilog);
    }

    void DismissProgress(Context c) {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
