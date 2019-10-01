package com.example.projectld.navigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.Export_Import.Export_menu;
import com.example.projectld.Export_Import.Import_db;
import com.example.projectld.R;
import com.example.projectld.exercise2.st_ex2_adapter.st_ex2_menu;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_menu;
import com.example.projectld.exercise3.st_hard.st_ex3_hard_menu;
import com.example.projectld.exercise3.st_nomal.st_ex3_normal_menu;

public class F_setting extends AppCompatActivity {

    Button ex2,ex3,ex4,ex5,Add_Word,Add_Sentence,export_db,import_db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("ตั้งค่า");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ex2 = findViewById(R.id.ex2);
        ex3 = findViewById(R.id.ex3);
        ex4 = findViewById(R.id.ex4);
        ex5 = findViewById(R.id.ex5);
        Add_Word = findViewById(R.id.Add_Word);
        Add_Sentence = findViewById(R.id.Add_Sentence);
        export_db = findViewById(R.id.Export_db);
        import_db = findViewById(R.id.Import_db);

        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex2_menu.class);
                startActivity(intent);
            }
        });


        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_easy_menu.class);
                startActivity(intent);
            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_normal_menu.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_hard_menu.class);
                startActivity(intent);
            }
        });

        Add_Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.projectld.Add_Word.class);
                startActivity(intent);
            }
        });

        Add_Sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.projectld.Add_Sentence.class);
                startActivity(intent);
            }
        });

        export_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_menu.class);
                startActivity(intent);
            }
        });

        import_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Import_db.class);
                startActivity(intent);
            }
        });

        }

    }

