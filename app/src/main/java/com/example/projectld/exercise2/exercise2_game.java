package com.example.projectld.exercise2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.TTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class exercise2_game extends AppCompatActivity {

    ImageView imageView,ch1,ch2,ch3;
    ImageView next,back,voice;

    int first = 0 ; //เช็คการทำงานรอบแรก
    int count; //เช็คเริ่มเข้ามาที่ตัวอักษรไหน

    SharedPreferences sharedPreferences; //เก็บค่า I ไม่ให้หาย
    ArrayList<String> char_array = new ArrayList<>();

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

        final TTS tts = new TTS(this);

        Bundle bundle = getIntent().getExtras();
        char_array = bundle.getStringArrayList("wordset");

        LoadInt();

        if (first == 0){
            count = bundle.getInt("countarray");
            first++;
            checkFIrst(first);
        }

        imageView = findViewById(R.id.picture);
        ch1 = findViewById(R.id.ch1);
        ch2 = findViewById(R.id.ch2);
        ch3 = findViewById(R.id.ch3);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back_this);
        voice = findViewById(R.id.voice);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Character character = databaseHelper.character(char_array.get(count));

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

                    count++; //เมื่อตอบถูกจะไปพยัญชนะถัดไปทันที
                    if(count >= char_array.size()){
                        Toast.makeText(exercise2_game.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        count--;
                    } else {
                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random.get(1) == cha1){
                    Toast.makeText(getApplicationContext(),"คำตอบถูกต้อง",Toast.LENGTH_SHORT).show();

                    count++; //เมื่อตอบถูกจะไปพยัญชนะถัดไปทันที
                    if(count >= char_array.size()){
                        Toast.makeText(exercise2_game.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        count--;
                    } else {
                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (random.get(2) == cha1){
                    Toast.makeText(getApplicationContext(),"คำตอบถูกต้อง",Toast.LENGTH_SHORT).show();

                    count++; //เมื่อตอบถูกจะไปพยัญชนะถัดไปทันที
                    if(count >= char_array.size()){
                        Toast.makeText(exercise2_game.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                        count--;
                    } else {
                        SaveInt(count);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"คำตอบผิด",Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= char_array.size()){
                    Toast.makeText(exercise2_game.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                    count--;
                } else {
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
                    Toast.makeText(exercise2_game.this,"ไม่พบคำก่อนหน้า",Toast.LENGTH_SHORT).show();
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
                tts.speak(char_array.get(count));
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

}
