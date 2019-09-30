package com.example.projectld.exercise2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.Score_ex3_word;
import com.example.projectld.TTS;
import com.example.projectld.recyclerView_Ranking.Ranking_Adapter;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ex2_game_st extends AppCompatActivity {

    ImageView imageView,ch1,ch2,ch3;
    ImageView next,back,voice;

    int first = 0 ; //เช็คการทำงานรอบแรก
    int count = 0; //ให้ index array ตัวแรกที่ 0

    SharedPreferences sharedPreferences,user; //เก็บค่า I ไม่ให้หาย
    String Groupname,ArraySet;

    ArrayList<String> Char_set = new ArrayList<>();
    DatabaseHelper databaseHelper;

    Dialog dialog,dialog_rank; //popup score

    private RecyclerView RecyclerView;
    private RecyclerView.LayoutManager LayoutManager;
    private RecyclerView.Adapter Adapter;

    private int Score = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise2_game);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("เแบบฝึกถามตอบ");
        Title.setTextSize(20);

        ImageView back_toolbar = toolbar.findViewById(R.id.back);
        back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new Dialog(this);
        dialog_rank = new Dialog(this);

        final TTS tts = new TTS(this);
        databaseHelper = new DatabaseHelper(this);

        user = getSharedPreferences("User", Context.MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        Groupname = bundle.getString("Groupname");

        LoadInt();

        if (first == 0){
            //count = bundle.getInt("countarray");
            Char_set = databaseHelper.get_Groupname_ex2_st(Groupname,user.getString("UserID",null));

            /**
             * ลบคำซ่ำใน array charset
             */
            Set<String> set = new HashSet<>(Char_set);
            Char_set.clear();
            Char_set.addAll(set);

            first++;
            checkFIrst(first);

        } else {
            LoadArray();
            String[] playlists = ArraySet.split(",");
            Char_set.addAll(Arrays.asList(playlists));
        }

        imageView = findViewById(R.id.picture);
        ch1 = findViewById(R.id.ch1);
        ch2 = findViewById(R.id.ch2);
        ch3 = findViewById(R.id.ch3);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back_this);
        voice = findViewById(R.id.voice);

        Character character = databaseHelper.character(Char_set.get(count));

        //set image
        String get_image = character.getImage();
        int set_image = getResources().getIdentifier(get_image , "drawable", getPackageName());

        String get_char1 = character.getCorrect();
        final int cha1 = getResources().getIdentifier(get_char1 , "drawable", getPackageName());

        String get_char2 = character.getChoice().get(0);
        int cha2 = getResources().getIdentifier(get_char2 , "drawable", getPackageName());

        String get_char3 = character.getChoice().get(1);
        int cha3 = getResources().getIdentifier(get_char3 , "drawable", getPackageName());

        final ArrayList<Integer> random = new ArrayList<>();
        random.add(cha1);
        random.add(cha2);
        random.add(cha3);

        //random in arraylist
        Collections.shuffle(random);

        imageView.setImageResource(set_image);

        ch1.setImageResource(random.get(0));
        ch2.setImageResource(random.get(1));
        ch3.setImageResource(random.get(2));

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random.get(0) == cha1){
                    Toast.makeText(getApplicationContext(),"คำตอบถูกต้อง",Toast.LENGTH_SHORT).show();

                    try { //ถ้าไม่ทำ try catch ไว้ กดถูกไปเรื่อยๆหน้าสุดท้ายจะ error เนื่องจาก index ของ count
                        //update score
                        String stID = databaseHelper.Find_stID_Char(Char_set.get(count),Groupname);
                        databaseHelper.update_score_ex2(user.getString("UserID",null),Score,stID);
                        Char_set.remove(count);
                    } catch (Exception e) {
                        Log.d("exception",e.toString());
                    }

                    if(count >= Char_set.size()){
                        Toast.makeText(ex2_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        //count--;
                    } else {

                        StringBuilder Sumwordset = new StringBuilder(); // เซฟ arrray Char_set
                        for (int i = 0; i < Char_set.size(); i++) {
                            Sumwordset.append(Char_set.get(i)).append(",");
                        }
                        SaveArray(Sumwordset.toString());

                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Score = Score - 5;
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random.get(1) == cha1){
                    Toast.makeText(getApplicationContext(),"คำตอบถูกต้อง",Toast.LENGTH_SHORT).show();

                    try {
                        //update score
                        String stID = databaseHelper.Find_stID_Char(Char_set.get(count),Groupname);
                        databaseHelper.update_score_ex2(user.getString("UserID",null),Score,stID);
                        Char_set.remove(count);
                    } catch (Exception e) {
                        Log.d("exception ",e.toString());
                    }

                    if(count >= Char_set.size()){
                        Toast.makeText(ex2_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        //count--;
                    } else {

                        StringBuilder Sumwordset = new StringBuilder(); // เซฟ arrray Char_set
                        for (int i = 0; i < Char_set.size(); i++) {
                            Sumwordset.append(Char_set.get(i)).append(",");
                        }
                        SaveArray(Sumwordset.toString());

                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Score = Score - 5;
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random.get(2) == cha1){
                    Toast.makeText(getApplicationContext(),"คำตอบถูกต้อง",Toast.LENGTH_SHORT).show();

                    try {
                        //update score
                        String stID = databaseHelper.Find_stID_Char(Char_set.get(count),Groupname);
                        databaseHelper.update_score_ex2(user.getString("UserID",null),Score,stID);
                        Char_set.remove(count);
                    } catch (Exception e) {
                        Log.d("Exception",e.toString());
                    }

                    if(count >= Char_set.size()){
                        Toast.makeText(ex2_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        //count--;
                    } else {

                        StringBuilder Sumwordset = new StringBuilder(); // เซฟ arrray Char_set
                        for (int i = 0; i < Char_set.size(); i++) {
                            Sumwordset.append(Char_set.get(i)).append(",");
                        }
                        SaveArray(Sumwordset.toString());

                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Score = Score - 5;
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= Char_set.size()){
                    Toast.makeText(ex2_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                    count--;
                    Popup_score();
                } else {

                    StringBuilder Sumwordset = new StringBuilder(); // เซฟ arrray Char_set
                    for (int i = 0; i < Char_set.size(); i++) {
                        Sumwordset.append(Char_set.get(i)).append(",");
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
                    Toast.makeText(ex2_game_st.this,"ไม่พบคำก่อนหน้า",Toast.LENGTH_SHORT).show();
                    count++;
                } else {
                    SaveInt(count);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(Char_set.get(count));
            }
        });


    }

    public void SaveInt(int value){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", value);
        editor.commit();
    }
    public void LoadInt(){ // โหลดค่า count and first
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        count = sharedPreferences.getInt("key", 0);
        first = sharedPreferences.getInt("first",0);
    }

    public void checkFIrst(int first){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("first",first);
        editor.commit();
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

    public void Popup_score(){
        TextView Fullname,Sum_Score,text1,text2,text3,text4,text5,score1,score2,score3,score4,score5;
        Button goRank;
        ImageView goBack;

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.relative_layout_radius);
        dialog.setContentView(R.layout.score_popup);

        Score_ex3_word score = databaseHelper.Score_ex2(Groupname,user.getString("UserID",null)
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

        dialog_rank.getWindow().setBackgroundDrawableResource(R.drawable.relative_layout_radius);
        dialog_rank.setContentView(R.layout.ranking_popup);

        ArrayList<Ranking_Item> ranking_items = databaseHelper.rank_ex2(Groupname);

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

}
