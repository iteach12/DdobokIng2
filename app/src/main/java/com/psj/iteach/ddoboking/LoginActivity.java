package com.psj.iteach.ddoboking;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText login_edit_text;
    NetworkInfo mNetworkState;
    Boolean isCheckedWifiState;
    AlertDialog.Builder alert_confirm;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init(){
        mNetworkState = getNetworkInfo();
        login_btn = (Button)findViewById(R.id.login_btn);
        login_edit_text = (EditText)findViewById(R.id.login_edit_text);
        login_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    mNetworkState = getNetworkInfo();
                    alert.show();
                    return true;
                }
                return false;
            }
        });
        isCheckedWifiState=false;

// 다이얼로그 바디
        alert_confirm = new AlertDialog.Builder(this);
        // 메세지
        alert_confirm.setMessage("또복이를 만나실 때에는 와이파이 연결을 유지해 주시기 바랍니다. 실시간 스트리밍은 많은 양의 모바일데이터를 소모합니다.");
        // 확인 버튼 리스너
        alert_confirm.setPositiveButton("오키도키", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String checkLogin = String.valueOf(login_edit_text.getText());
                Log.d("BUTTON", "CLicked");
                if(checkLogin.equals("170725")){
                    if(checkWifiState()){
                        Intent myintent = new Intent(LoginActivity.this, MainActivity.class);
                        myintent.putExtra("MODE", true);
                        startActivity(myintent);
                    }else{
                        Toast.makeText(getApplicationContext(), "와이파이를 연결해 주세요.", Toast.LENGTH_LONG).show();
                    }
                }else if(checkLogin.equals("159357")){

                    Intent myintent = new Intent(LoginActivity.this, MainActivity.class);
                    myintent.putExtra("MODE", false);
                    startActivity(myintent);

                }else{
                    Toast.makeText(getApplicationContext(), "정확한 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

                login_edit_text.setText("");
            }
        });

        // 다이얼로그 생성
        alert = alert_confirm.create();

        // 아이콘
        alert.setIcon(R.drawable.ic_menu_manage);
        // 다이얼로그 타이틀
        alert.setTitle("반드시 와이파이 연결 확인!!");
        // 다이얼로그 보기


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetworkState = getNetworkInfo();
                alert.show();
            }
        });
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

                return true;


            }else if(mNetworkState.getType()==ConnectivityManager.TYPE_MOBILE){

                //lte 또는 3g로 연결 되었을 때
                return false;
            }

        }else{

            //네트워크가 연결되지 않았을 때
            Toast.makeText(getApplicationContext(), "네트워크에 연결되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
