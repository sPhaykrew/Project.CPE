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

public class Add_Sentence extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    TextView title_add;
    Dialog dialog;

    public static Activity close_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);

        close_activity = this;

        title_add = findViewById(R.id.title_add);
        title_add.setText("ประโยคในคลังข้อมูล");

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("เพิ่มประโยค");
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

        dialog = new Dialog(this);
        databaseHelper = new DatabaseHelper(this);
        gridView = findViewById(R.id.GridView_Sentence);
        ArrayList Sentence = databaseHelper.queryword("Sentence");

        gridviewAdapter = new GridviewAdapter(Sentence,this,"Delete_Mod_Sentence",R.drawable.radius_button_color_ex2,null);
        gridView.setAdapter(gridviewAdapter);

        FloatingActionButton Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Sentence();
            }
        });
    }

    public void Add_Sentence(){
        dialog.setContentView(R.layout.addsentence_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
        final EditText editText = dialog.findViewById(R.id.Add_Word);
        Button button = dialog.findViewById(R.id.CF);
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ข้อความ",Toast.LENGTH_SHORT).show();
                } else if (editText.getText().length() <= 5){
                    Toast.makeText(getApplicationContext(),"ข้อความสั่นเกินไป",Toast.LENGTH_SHORT).show();
                } else if (editText.getText().length() > 22){
                    Toast.makeText(getApplicationContext(),"ข้อความยาวกินไป",Toast.LENGTH_SHORT).show();
                } else {
                    Boolean Check_Sentence = databaseHelper.AddWord(String.valueOf(editText.getText()),"Sentence",null);
                    if (Check_Sentence) {
                        dialog.dismiss();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Add_Sentence.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"มีประโยคในฐานข้อมูลแล้ว",Toast.LENGTH_SHORT).show();
                    }
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
