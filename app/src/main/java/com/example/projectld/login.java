package com.example.projectld;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectld.MusicBG.HomeWatcher;
import com.example.projectld.MusicBG.MusicService;

public class login extends AppCompatActivity {

    Button login;
    EditText ID,PW;

    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ////service Music Start!
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        ////service Music Start!
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

        login = (Button) findViewById(R.id.Login);
        ID = (EditText) findViewById(R.id.textID);
        PW = (EditText) findViewById(R.id.textPW);

        SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        final SharedPreferences.Editor editor = sp.edit();

        editor.clear();
        editor.apply();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    User user = dbHelper.queryUser(ID.getText().toString(), PW.getText().toString());
                    if (user != null) {
                        editor.putString("UserID",user.getUserID());
                        editor.putString("Username",user.getUsername());
                        editor.putString("Password",user.getPassword());
                        editor.putString("Fullname",user.getFullname());
                        editor.putInt("Age",user.getAge());
                        editor.putString("sex",user.getSex());
                        editor.putString("Permission",user.getPermission());
                        if(user.getPicture() != null){
                        String saveThis = Base64.encodeToString(user.getPicture(), Base64.DEFAULT);//แปลง byte เป็น String
                        editor.putString("Picture",saveThis);}
                        editor.commit();

                        Intent intent = new Intent(login.this, menu.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(login.this, "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                        PW.setText("");
                    }
                }else{
                    Toast.makeText(login.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(ID.getText().toString()) || TextUtils.isEmpty(PW.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }

    ////service Music Start!
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }

}
