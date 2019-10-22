package com.example.projectld.exercise3.nomal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.MusicBG.HomeWatcher;
import com.example.projectld.MusicBG.MusicService;
import com.example.projectld.R;
import com.example.projectld.Score_ex3_word;
import com.example.projectld.TTS;
import com.example.projectld.exercise3.easy.ex3_easy_game;
import com.example.projectld.exercise3.easy.ex3_easy_game_st;
import com.example.projectld.exercise3.segmentation;
import com.example.projectld.recyclerView_Ranking.Ranking_Adapter;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


@SuppressLint("NewApi")
public class ex3_normal_game_st extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    com.example.projectld.exercise3.segmentation segmentation;
    TTS tts;
    ImageView voice,next,back;

    String Groupname,ArraySet;

    int start = 0; //เช็คว่าเลากไปกี่ตัวแล้ว
    int finish = 0;

    ArrayList<String> wordset = new ArrayList<>();
    int count = 0; // count array wordset for next and back
    SharedPreferences sharedPreferences; //เก็บค่า I ไม่ให้หาย
    Bundle arrayset; //รับค่า array จาก gridview ที่คิวรี่จากฐานข้อมูล

    int first = 0; //เช็คว่าใช้การทำงานครั่งแรกไหม

    Dialog dialog,dialog_rank,dialog_correct; //popup score
    DatabaseHelper databaseHelper;
    SharedPreferences user;

    int Score = 100;

    private RecyclerView RecyclerView;
    private RecyclerView.LayoutManager LayoutManager;
    private RecyclerView.Adapter Adapter;

    String status = null; // เก็บคำจาก view ที่คลิ๊ก
    int status_id;
    LinearLayout answer;

    MediaPlayer correct,incorrect;

    ArrayList<String> cerrent_Char = new ArrayList<>();
    ArrayList<String> cerrent_Score = new ArrayList<>();

    HomeWatcher mHomeWatcher;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3_nomal_game);

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

        ImageView set_Answer = findViewById(R.id.setAnswer);

        incorrect= MediaPlayer.create(getApplicationContext(),R.raw.incorrect);
        correct = MediaPlayer.create(getApplicationContext(),R.raw.correct);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("เแบบฝึกเรียงตัวอักษร");
        Title.setTextSize(20);

        ImageView back_toolbar = toolbar.findViewById(R.id.back);
        ImageView show_menu = toolbar.findViewById(R.id.show_menu);

        back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        show_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                popupMenu.inflate(R.menu.menu_toolbar);
                popupMenu.setOnMenuItemClickListener(ex3_normal_game_st.this);
                popupMenu.show();
            }
        });

        user = getSharedPreferences("User", Context.MODE_PRIVATE);
        dialog = new Dialog(this);
        dialog_rank = new Dialog(this);
        dialog_correct = new Dialog(this);

        voice = findViewById(R.id.voice_tts);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back_this);
        arrayset = getIntent().getExtras();

        tts = new TTS(this);
        databaseHelper = new DatabaseHelper(this);

        LoadInt();

        Groupname = arrayset.getString("Groupname"); //ได้รับชื่อแบบทดสอบ
        if (first == 0) {
            wordset = databaseHelper.ex3_normal_game_st(String.valueOf(Groupname), user.getString("UserID", null)); // เก็บคำ 5 คำ และ insert setting ID
            first++;
            SaveFirst(first);
        } else {
            LoadArray();
            String[] playlists = ArraySet.split(",");
            wordset.addAll(Arrays.asList(playlists));
            Load_Array_Score();
        }

        /**
         * ตัดคำ
         */
        segmentation = new segmentation();
        ArrayList<String> sentence = segmentation.split(segmentation.Break(wordset.get(count))); //ระดับคำศัพท์
        //ArrayList<String> sentence = segmentation.substring(wordset.get(count)); //ระดับตัวอักษร

        /**
         * question set ex3_easy_game listeners
         */
        LinearLayout question = (LinearLayout) findViewById(R.id.question);
        for(int i=0 ; i < sentence.size() ; i++) {
            TextView valueQT = new TextView(this);
            valueQT.setText("__");
            valueQT.setId(i);
            valueQT.setTextSize(32);
            valueQT.setTag(sentence.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMarginStart(40);
            valueQT.setLayoutParams(params);
            valueQT.setOnClickListener(setAnswer(valueQT));
            valueQT.setOnDragListener(new ChoiceDragListener());
            valueQT.setGravity(Gravity.CENTER);
            question.addView(valueQT);

            start++; //เช็คว่าตอบคำถามครบหรือยัง
        }

        /**
         * answer views to ex3_easy_game
         */
        answer = (LinearLayout) findViewById(R.id.answer);
        int loop = sentence.size();
        ArrayList<String> sentenceRD = random(sentence,loop);

        for(int i=0 ; i < loop ; i++){
            TextView answerCH = new TextView(this);
            answerCH.setText(sentenceRD.get(i));
            answerCH.setTextSize(32);
            answerCH.setId(i);
            answerCH.setTag(answerCH.getText());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            //params.setMarginStart(20);
            params.setMargins(60,0,0,0);
            answerCH.setLayoutParams(params);
            answerCH.setOnTouchListener(new ChoiceTouchListener());
            answer.addView(answerCH);
        }

        /**
         * call TTS
         */
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(wordset.get(count));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= wordset.size()){
                    Toast.makeText(ex3_normal_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                    count--;
                } else {

                    StringBuilder Sumwordset = new StringBuilder();
                    for (int i = 0; i < wordset.size(); i++) {
                        Sumwordset.append(wordset.get(i)).append(",");
                    }
                    SaveArray(Sumwordset.toString());

                    SaveInt(count);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(count < 0){
                    Toast.makeText(ex3_normal_game_st.this,"ไม่พบคำก่อนหน้า",Toast.LENGTH_SHORT).show();
                    count++;
                } else {
                    SaveInt(count);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

        set_Answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<cerrent_Char.size();i++) {
                    if (!cerrent_Char.get(i).equals("null")) {
                        String stID = databaseHelper.Find_stID_sentence(cerrent_Char.get(i),Groupname,"Setting_ex3_normal","st_ex3_normal_id");
                        databaseHelper.update_score_ex3_normal(user.getString("UserID", null),
                                Integer.parseInt(cerrent_Score.get(i)), stID);
                    }
                }
                Popup_score();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.close_music :
                mServ.stopMusic();
                return true;

            default: return false;
        }
    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     *
     */
    private final class ChoiceTouchListener implements OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            Runnable rannable = new Runnable() { // include touch and click *ถ้าไม่มีจะทำงานร่วมกันไม่ได้
                @Override
                public void run() {
                    /*
                     * Drag details: we only need default behavior
                     * - clip data could be set to pass data as part of ex3_easy_game
                     * - shadow can be tailored
                     */
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);
                    //start dragging the item touched
                    view.startDrag(data, shadowBuilder, view, 0);
                }
            };

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) { //onClick

                for (int i=0;i<answer.getChildCount();i++){ //setText size all view
                    TextView textView = (TextView) answer.getChildAt(i);
                    textView.setTextSize(32);
                }
                status = String.valueOf(((TextView) view).getTag());
                status_id = ((TextView) view).getId();
                ((TextView) view).setTextSize(60);
                return true;
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { //onTouch
                Handler handler = new Handler();
                handler.postDelayed(rannable,100);
                return true;
            }

            //else {
            return false;
            //}
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the ex3_easy_game process
     *
     */
    @SuppressLint("NewApi")
    private class ChoiceDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;

                    Log.i("droptarget", String.valueOf(dropTarget.getTag()));
                    Log.i("dropped", String.valueOf(dropped.getTag()));

                    //checking whether first character of dropTarget equals first character of dropped
                    if((dropTarget.getTag().equals(dropped.getTag())))
                    {
                        correct.start();
                        finish++; //เช็คว่าตอบคำถามครบหรือยัง
                        view.setVisibility(View.INVISIBLE);
                        dropTarget.setText(dropped.getText().toString());
                        dropTarget.setTag("done");
                        dropTarget.setOnDragListener(null);

                        if (finish == start){
                            //Toast.makeText(ex3_normal_game_st.this,"เสร็จสิ้น",Toast.LENGTH_LONG).show();
                            cerrent_Char.add(wordset.get(count));
                            cerrent_Score.add(String.valueOf(Score));
                            SaveArray_Score(cerrent_Char,cerrent_Score);
                            Popup_Correct();
                        }
                    }
                    else {
                        //displays message if first character of dropTarget is not equal to first character of dropped
                        incorrect.start();
                        Score = Score - 5;
                        //Toast.makeText(ex3_normal_game_st.this, " ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    View.OnClickListener setAnswer(final TextView textView)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (!textView.getTag().equals("done")){
                    if (textView.getTag().equals(status)){
                        correct.start();
                        finish++; //เช็คว่าตอบคำถามครบหรือยัง
                        textView.setText(status);
                        textView.setOnDragListener(null);
                        textView.setTag("done");
                        TextView get_status = (TextView) answer.getChildAt(status_id);
                        get_status.setVisibility(View.INVISIBLE);
                        status = null;

                        if (finish == start){
                            //Toast.makeText(ex3_normal_game_st.this,"เสร็จสิ้น",Toast.LENGTH_LONG).show();
                            cerrent_Char.add(wordset.get(count));
                            cerrent_Score.add(String.valueOf(Score));
                            SaveArray_Score(cerrent_Char,cerrent_Score);
                            Popup_Correct();
                        }

                    } else {
                        if (status != null ){ // เพื่อไม่ได้หลัง click เสร็จไม่สารมารถ click คำอื่นได้ ถ้าไม่มีจะทำให้คลิกคำอื่นหลังคลิกเสร็จขึ้นไม่ถูกต้อง
                            incorrect.start();
                            Score = Score - 5;
                            //Toast.makeText(getApplicationContext(),"ไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
    }


    public ArrayList<String> random (ArrayList<String> result, int i){
        Random rd = new Random();
        ArrayList<String> list = new ArrayList<>();
        for(int j=0;j<i;j++){
            String test = result.get(rd.nextInt(result.size()));
            list.add(test);
            result.remove(test);
        }
        return list;
    }

    public void SaveArray(String array){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("array",array);
        editor.commit();
    }

    public void LoadArray(){ // โหลดค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArraySet = sharedPreferences.getString("array", "Hello!");
    }

    public void SaveInt(int value){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", value);
        editor.commit();
    }

    public void SaveFirst(int value){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("first",value);
        editor.commit();
    }

    public void LoadInt(){ // โหลดค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        count = sharedPreferences.getInt("key", 0);
        first = sharedPreferences.getInt("first",0);
    }

    public void Popup_score(){
        TextView Fullname,Sum_Score,text1,text2,text3,text4,text5,score1,score2,score3,score4,score5;
        Button goRank;
        ImageView goBack;

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
        dialog.setContentView(R.layout.score_popup);

        Score_ex3_word score = databaseHelper.Score_ex3_normal(Groupname,user.getString("UserID",null)
                ,user.getString("Fullname",null));

        Sum_Score = dialog.findViewById(R.id.Sum_Score);
        text1 = dialog.findViewById(R.id.text1);
        text2 = dialog.findViewById(R.id.text2);
        text3 = dialog.findViewById(R.id.text3);
        text4 = dialog.findViewById(R.id.text4);
        text5 = dialog.findViewById(R.id.text5);
        score1 = dialog.findViewById(R.id.Score_text1);
        score2 = dialog.findViewById(R.id.Score_text2);
        score3 = dialog.findViewById(R.id.Score_text3);
        score4 = dialog.findViewById(R.id.Score_text4);
        score5 = dialog.findViewById(R.id.Score_text5);

        goBack = dialog.findViewById(R.id.this_back);
        goRank = dialog.findViewById(R.id.Rank);

        text1.setText(score.getWord().get(0));
        text2.setText(score.getWord().get(1));
        text3.setText(score.getWord().get(2));
        text4.setText(score.getWord().get(3));
        text5.setText(score.getWord().get(4));

        score1.setText(String.valueOf(score.getScore().get(0)));
        score2.setText(String.valueOf(score.getScore().get(1)));
        score3.setText(String.valueOf(score.getScore().get(2)));
        score4.setText(String.valueOf(score.getScore().get(3)));
        score5.setText(String.valueOf(score.getScore().get(4)));

        ArrayList<Integer> averrage = new ArrayList<>(score.getScore());
        int sum_average = Average(averrage);
        Sum_Score.setText("คะแนนรวม  " +String.valueOf(sum_average));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Popup_rank();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void Popup_rank(){
        Button myScore;
        ImageView goBack;

        dialog_rank.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
        dialog_rank.setContentView(R.layout.ranking_popup);

        ArrayList<Ranking_Item> ranking_items = databaseHelper.rank_ex3_normal(Groupname);

        RecyclerView = dialog_rank.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        Adapter = new Ranking_Adapter(ranking_items);

        RecyclerView.setLayoutManager(LayoutManager);
        RecyclerView.setAdapter(Adapter);


        goBack = dialog_rank.findViewById(R.id.this_back);
        myScore = dialog_rank.findViewById(R.id.MyScore);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rank.dismiss();
                Popup_score();
            }
        });

        dialog_rank.setCanceledOnTouchOutside(false);
        dialog_rank.show();
    }

    public int Average (ArrayList<Integer> number){
        int average = 0;
        for (int i=0;i<5;i++) {
            average += number.get(i);
        }
        average = average / 5;
        return  average;
    }

    public void SaveArray_Score(ArrayList<String> Char,ArrayList<String> score){ //เก็บคะแนนไว้ในอาเรย์กับตัวอักษรของคะแนนนั้น แล้วส่ง update ทีเดียว
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        StringBuilder character = new StringBuilder(); // เซฟ arrray Char_set
        for (int i = 0; i < Char.size(); i++) {
            character.append(Char.get(i)).append(",");
        }

        StringBuilder Array_Score = new StringBuilder(); // เซฟ arrray ของคะแนนตัวอักษรปัจจุบัน
        for (int i = 0; i < score.size(); i++) {
            Array_Score.append(score.get(i)).append(",");
        }

        editor.putString("Charecter_Score", String.valueOf(character));
        editor.putString("Score", String.valueOf(Array_Score));

        editor.commit();
    }

    public void Load_Array_Score(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String[] Char = sharedPreferences.getString("Charecter_Score","null").split(",");
        String[] Score = sharedPreferences.getString("Score","null").split(",");
        cerrent_Char.addAll(Arrays.asList(Char));
        cerrent_Score.addAll(Arrays.asList(Score));
    }

    public void Popup_Correct(){
        dialog_correct.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
        dialog_correct.setContentView(R.layout.correct_popup);
        Button button = dialog_correct.findViewById(R.id.go_next);
        ImageView back = dialog_correct.findViewById(R.id.this_back);
        TextView textView = dialog_correct.findViewById(R.id.score_correct);
        textView.setText(String.valueOf(Score+" คะแนน"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= wordset.size()){
                    for (int i=0;i<cerrent_Char.size();i++) { //อัพเดตคะแนน
                        if (!cerrent_Char.get(i).equals("null")) {
                            String stID = databaseHelper.Find_stID_sentence(cerrent_Char.get(i),Groupname,"Setting_ex3_normal","st_ex3_normal_id");
                            databaseHelper.update_score_ex3_normal(user.getString("UserID", null),
                                    Integer.parseInt(cerrent_Score.get(i)), stID);
                        }
                    }
                    dialog_correct.dismiss();
                    Popup_score();
                } else {
                    count--;
                    wordset.remove(wordset.get(count)); // ลบคำที่ทำเสร็จแล้ว
                    StringBuilder Sumwordset = new StringBuilder(); //เก็บคำศัพท์ปัจจุบันที่ลบคำไปแล้ว
                    for (int i = 0; i < wordset.size(); i++) {
                        Sumwordset.append(wordset.get(i)).append(",");
                    }
                    SaveInt(count);
                    SaveArray(Sumwordset.toString());

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    dialog_correct.dismiss();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_correct.dismiss();
                finish();
            }
        });

        dialog_correct.setCanceledOnTouchOutside(false);
        dialog_correct.show();
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