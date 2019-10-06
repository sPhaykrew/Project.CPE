package com.example.projectld;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class Add_Word extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    Dialog dialog;

    public static Activity close_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);

        close_activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("เพิ่มคำศัพท์");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new Dialog(this);
        databaseHelper = new DatabaseHelper(this);
        gridView = findViewById(R.id.GridView_Sentence);
        ArrayList Word = databaseHelper.queryword("Word");

        gridviewAdapter = new GridviewAdapter(Word,this,"Delete_Mod_Word",R.drawable.radius_button_color1);
        gridView.setAdapter(gridviewAdapter);

        FloatingActionButton Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_word();
            }
        });
    }

    public void Add_word(){
        dialog.setContentView(R.layout.addword_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.relative_layout_radius);
        final EditText editText = dialog.findViewById(R.id.Add_Word);
        Button button = dialog.findViewById(R.id.CF);
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ข้อความ",Toast.LENGTH_SHORT).show();
                } else if (editText.getText().length() <= 2){
                    Toast.makeText(getApplicationContext(),"ข้อความสั่นเกินไป",Toast.LENGTH_SHORT).show();
                } else if (editText.getText().length() > 13){
                    Toast.makeText(getApplicationContext(),"ข้อความยาวกินไป",Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.AddWord(String.valueOf(editText.getText()),"Word");
                    dialog.dismiss();
                    finish();
                    Intent intent = new Intent(getApplicationContext(),Add_Word.class);
                    startActivity(intent);
                }
            }
        });

        ImageView goBack = dialog.findViewById(R.id.this_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}
