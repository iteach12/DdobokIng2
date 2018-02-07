package com.psj.iteach.ddoboking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
//import android.icu.util.Calendar;
import java.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout introduce_Layout;
    FrameLayout camera_Layout;
    FrameLayout gallary_Layout;
    FrameLayout schedule_Layout;
    FrameLayout share_Layout;
    FrameLayout phonebook_Layout;

    WebView webView;
    ImageView imageView;
    //Wifi 접속 관련 체크
    NetworkInfo mNetworkState;
    Boolean isCheckedWifiState;

    private int CLICKED_MENU;

    private static final int INTRODUCE_LAYOUT = 0;
    private static final int CAMERA_LAYOUT = 1;
    private static final int GALLARY_LAYOUT = 2;
    private static final int SCHEDULE_LAYOUT = 3;
    private static final int SHARE_LAYOUT = 4;
    private static final int PHONEBOOK_LAYOUT = 5;

    String url;
    SharedPreferences uv4l_streaming_url;
    SharedPreferences.Editor editor;

    TextView ddobokAge;
    TextView ddobok50day;
    TextView ddobok100day;
    TextView ddobokHospital;
    TextView ddobokBirthday;


    AlertDialog alertDialog_exit;
    AlertDialog.Builder alertDialogBuilder_exit;

    AlertDialog alertDialog_profile;
    AlertDialog.Builder alertDialogBuilder_profile;

    Intent intent_of_loginData_mode;
    boolean MODE;
    String ddobokabba;
    String elementaryTeacher;
    String primaryDeveloper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    CLICKED_MENU=INTRODUCE_LAYOUT;

                    String dday = "17. 07. 25. (+"+String.valueOf(caldate(2017,6,25)+")");
                    ddobokAge.setText(dday);

                    setVisibilityOfLayout();
                    actionOfMenu(CLICKED_MENU);


                    return true;
                case R.id.navigation_dashboard:
                    CLICKED_MENU=CAMERA_LAYOUT;
                    showWebViewIfWifi();

                    return true;
                case R.id.navigation_schedule:
                    CLICKED_MENU=SCHEDULE_LAYOUT;
                    String dday_50day = "17. 09. 12. (D"+String.valueOf(caldate(2017,8,12)+")");
                    String dday_100day = "17. 11. 01. (D"+String.valueOf(caldate(2017,10,01)+")");
                    String dday_hospital = "17. 09. 12. (D"+String.valueOf(caldate(2017,8,12)+")");
                    String dday_birthday = "18. 07. 25. (D"+String.valueOf(caldate(2018,6,25)+")");


                    ddobok50day.setText(dday_50day);
                    ddobok100day.setText(dday_100day);
                    ddobokHospital.setText(dday_hospital);
                    ddobokBirthday.setText(dday_birthday);

                    setVisibilityOfLayout();
                    actionOfMenu(CLICKED_MENU);
                    return true;
                case R.id.navigation_gallary:
                    CLICKED_MENU=GALLARY_LAYOUT;
                    setVisibilityOfLayout();
                    actionOfMenu(CLICKED_MENU);
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF40c4ff));

        init();

        intent_of_loginData_mode = getIntent();
        MODE = intent_of_loginData_mode.getExtras().getBoolean("MODE");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String dDay = "17. 07. 25. (+"+String.valueOf(caldate(2017,6,25)+")");
        ddobokAge.setText(dDay);




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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            // 다이얼로그 보여주기
            alertDialog_exit.show();
            Log.d("MYTAG", "exit clicked");

            return true;
        }else if(id == R.id.action_profile){

            alertDialog_profile.show();
            Log.d("MYTAG", "profile clicked");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_introduce) {
            // Handle the camera action
            CLICKED_MENU=INTRODUCE_LAYOUT;
            String dday = "17. 07. 25. (+"+String.valueOf(caldate(2017,6,25)+")");
            ddobokAge.setText(dday);
            setVisibilityOfLayout();
            actionOfMenu(CLICKED_MENU);

        } else if (id == R.id.nav_camera) {
            CLICKED_MENU=CAMERA_LAYOUT;
            showWebViewIfWifi();
        } else if (id == R.id.nav_slideshow) {
            CLICKED_MENU=GALLARY_LAYOUT;
            setVisibilityOfLayout();
            actionOfMenu(CLICKED_MENU);
        } else if (id == R.id.nav_manage) {
            CLICKED_MENU=SCHEDULE_LAYOUT;

            String dday_50day = "17. 09. 12. (-"+String.valueOf(caldate(2017,8,12)+")");
            String dday_100day = "17. 11. 01. (-"+String.valueOf(caldate(2017,10,01)+")");
            String dday_hospital = "17. 09. 12. (-"+String.valueOf(caldate(2017,8,12)+")");
            String dday_birthday = "18. 07. 25. (-"+String.valueOf(caldate(2018,6,25)+")");


            ddobok50day.setText(dday_50day);
            ddobok100day.setText(dday_100day);
            ddobokHospital.setText(dday_hospital);
            ddobokBirthday.setText(dday_birthday);
            setVisibilityOfLayout();
            actionOfMenu(CLICKED_MENU);
        } else if (id == R.id.nav_share) {
            CLICKED_MENU=SHARE_LAYOUT;
            actionOfMenu(CLICKED_MENU);

            Intent msg = new Intent(Intent.ACTION_SEND);

            msg.addCategory(Intent.CATEGORY_DEFAULT);

            msg.putExtra(Intent.EXTRA_SUBJECT, "또복이 만나러 오세요 ^^ "+"\n"+"(반드시 와이파이가 연결된 상태에서 접속해 주세요.)"+"\n");

            msg.putExtra(Intent.EXTRA_TEXT, "http://220.70.162.125:8080");

            msg.putExtra(Intent.EXTRA_TITLE, "또복이 만나러 가기");



            msg.setType("text/plain");

            startActivity(Intent.createChooser(msg, "공유"));


        } else if (id == R.id.nav_send) {
            CLICKED_MENU=PHONEBOOK_LAYOUT;
            actionOfMenu(CLICKED_MENU);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void actionOfMenu(int clicked_menu) {

        switch (clicked_menu){
            case INTRODUCE_LAYOUT:
                introduce_Layout.setVisibility(View.VISIBLE);
                webView.stopLoading();
                break;
            case CAMERA_LAYOUT:
                camera_Layout.setVisibility(View.VISIBLE);
                break;
            case GALLARY_LAYOUT:
                gallary_Layout.setVisibility(View.VISIBLE);
                webView.stopLoading();
                break;
            case SCHEDULE_LAYOUT:
                schedule_Layout.setVisibility(View.VISIBLE);
                webView.stopLoading();
                break;
            case SHARE_LAYOUT:
                webView.stopLoading();
                break;
            case PHONEBOOK_LAYOUT:

                webView.stopLoading();
                break;

        }
    }
    private void setVisibilityOfLayout(){
        introduce_Layout.setVisibility(View.INVISIBLE);
        camera_Layout.setVisibility(View.INVISIBLE);
        gallary_Layout.setVisibility(View.INVISIBLE);
        schedule_Layout.setVisibility(View.INVISIBLE);
        share_Layout.setVisibility(View.INVISIBLE);
        phonebook_Layout.setVisibility(View.INVISIBLE);

    }


    public void init(){
        introduce_Layout = (FrameLayout)findViewById(R.id.layout_introduce);
        camera_Layout = (FrameLayout)findViewById(R.id.layout_camera);
        gallary_Layout = (FrameLayout)findViewById(R.id.layout_gallary);
        schedule_Layout = (FrameLayout)findViewById(R.id.layout_scheduel);
        share_Layout = (FrameLayout)findViewById(R.id.layout_share);
        phonebook_Layout = (FrameLayout)findViewById(R.id.layout_phonebook);

        webView = (WebView)findViewById(R.id.webView1);
        webView.setPadding(0,0,0,0);
        //webView.setInitialScale(100);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        //url주소가 변동 가능하도록 바꿔놓자.
        url ="http://220.70.162.125:8080/stream";

        //언제 url을 저장할 것인지, 언제 불러올 것인지, 언제 초기화할 것인지 확인.
        //json으로 불러온 데이터를 어떻게 string으로 변환할 것인지 다시 확인.
        //그래서 admin이 입력해 놓은 주소로 자동으로 들어가도록 체크합시다.
        //uv4l_streaming_url = getSharedPreferences("streaming_url", MODE_PRIVATE);
//        editor = uv4l_streaming_url.edit();
//        editor.putString("url", url);
//        editor.commit();
        //현재 어디위치인지 체크
        CLICKED_MENU = 0;

        isCheckedWifiState = false;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        //이미지뷰 또복이 프로필
        imageView = (ImageView)findViewById(R.id.ddobok_profile_imageview);

        //프로필 dday관련
        ddobokAge = (TextView)findViewById(R.id.ddobok_age_textview);
        ddobok50day = (TextView)findViewById(R.id.ddobok_50day);
        ddobok100day = (TextView)findViewById(R.id.ddobok_100day);
        ddobokHospital = (TextView)findViewById(R.id.ddobok_hospital);
        ddobokBirthday = (TextView)findViewById(R.id.ddobok_birthday);




        //일단 네트워크연결상태를 확인
        mNetworkState = getNetworkInfo();

        alertDialogBuilder_exit = new AlertDialog.Builder(
                this);

        // 제목셋팅
        alertDialogBuilder_exit.setTitle("또복ing 종료");

        // AlertDialog 셋팅
        alertDialogBuilder_exit
                .setMessage("프로그램을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("종료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {

                                // 프로그램을 종료한다
                                MainActivity.this.finish();
                            }
                        });

        // 다이얼로그 생성
        alertDialog_exit = alertDialogBuilder_exit.create();
        ddobokabba="#시환이 아빠 제작"+'\n'+"#초등학교 교사"+'\n'+"#취미로 어플개발";
        //proflie 다이얼로그
        alertDialogBuilder_profile = new AlertDialog.Builder(
                this);
        // 제목셋팅
        alertDialogBuilder_profile.setTitle("developer");
        // AlertDialog 셋팅
        alertDialogBuilder_profile
                .setMessage("또복ing 개발자 : 박상준"+'\n'+'\n'+"#시환이 아빠"+'\n'+"#초등학교 교사"+'\n'+"#초보 개발자")
                .setCancelable(false)
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {

                                // 프로그램을 종료한다
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        alertDialog_profile = alertDialogBuilder_profile.create();
    }

    private NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;

    }

    private boolean checkWifiState(){
        if(mNetworkState != null && mNetworkState.isConnected()){

            //무엇이든 네트워크에 연결되어 있을 때

            if(mNetworkState.getType() == ConnectivityManager.TYPE_WIFI){

                //Wifi연결되었을 때 할 일
                Toast.makeText(getApplicationContext(), "wifi 연결됨", Toast.LENGTH_SHORT).show();
                return true;


            }else if(mNetworkState.getType()==ConnectivityManager.TYPE_MOBILE){

                //lte 또는 3g로 연결 되었을 때
                Toast.makeText(getApplicationContext(), "3g + lte 연결됨", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else{

            //네트워크가 연결되지 않았을 때
            Toast.makeText(getApplicationContext(), "네트워크에 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;

    }

    private void showWebViewIfWifi(){

        mNetworkState = getNetworkInfo();
        if(checkWifiState()){

            setVisibilityOfLayout();
            actionOfMenu(CLICKED_MENU);
            webView.loadUrl(url);



        }else{


            Toast.makeText(getApplicationContext(), "와이파이를 연결해야만 또복이를 만날 수 있어요 ^^", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        webView.stopLoading();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        webView.stopLoading();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public int caldate(int myear, int mmonth, int mday) {
        try {
            Calendar today = Calendar.getInstance(); //현재 오늘 날짜
            Calendar dday = Calendar.getInstance();


            dday.set(myear,mmonth,mday);// D-day의 날짜를 입력합니다.


            long day = dday.getTimeInMillis()/86400000;
            // 각각 날의 시간 값을 얻어온 다음
            //( 1일의 값(86400000 = 24시간 * 60분 * 60초 * 1000(1초값) ) )


            long tday = today.getTimeInMillis()/86400000;
            long count = tday - day; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count+1; // 날짜는 하루 + 시켜줘야합니다.
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }





}
