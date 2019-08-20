package com.example.projectld;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class Add_Word extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);

        dialog = new Dialog(this);
        databaseHelper = new DatabaseHelper(this);
        gridView = findViewById(R.id.GridView_Sentence);
        ArrayList Sentence = databaseHelper.queryword("Word");

        gridviewAdapter = new GridviewAdapter(Sentence,this,"Word_data",R.drawable.radius_button_color1);
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
        dialog.show();

    }
}