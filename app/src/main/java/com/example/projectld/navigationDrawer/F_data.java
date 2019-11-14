package com.example.projectld.navigationDrawer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.MusicBG.HomeWatcher;
import com.example.projectld.MusicBG.MusicService;
import com.example.projectld.My_Score.My_Score_main_2;
import com.example.projectld.My_Score.My_Score_main_3;
import com.example.projectld.My_Score.My_Score_main_4;
import com.example.projectld.My_Score.My_Score_main_5;
import com.example.projectld.R;

import java.util.Objects;

public class F_data extends AppCompatActivity {

    Button score2,score3,score4,score5,Word,Sentence;
    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

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

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("ข้อมูลผู้ใช้งาน");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        ImageView show_menu = toolbar.findViewById(R.id.show_menu);
        show_menu.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        score2 = findViewById(R.id.myscore2);
        score3 = findViewById(R.id.myscore3);
        score4 = findViewById(R.id.myscore4);
        score5 = findViewById(R.id.myscore5);
        Word = findViewById(R.id.Word_Data);
        Sentence = findViewById(R.id.Sentence_Data);

        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Score_main_2.class);
                startActivity(intent);
            }
        });

        score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Score_main_3.class);
                startActivity(intent);
            }
        });

        score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Score_main_4.class);
                startActivity(intent);
            }
        });

        score5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Score_main_5.class);
                startActivity(intent);
            }
        });

        Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), F_Word_data.class);
                startActivity(intent);
            }
        });

        Sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), F_Sentence_data.class);
                startActivity(intent);
            }
        });

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
                    Scon, Context.BIND_AUTO_CREATE);
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
                isScreenOn = pm.isInteractive();
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
