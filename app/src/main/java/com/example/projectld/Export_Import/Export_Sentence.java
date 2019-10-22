package com.example.projectld.Export_Import;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.GridListAdapter_selectWord;
import com.example.projectld.R;
import com.example.projectld.exercise3.st_nomal.grid_view_ex3_st_normal_selectWord;
import com.example.projectld.exercise3.word;
import com.example.projectld.navigationDrawer.F_setting;

import java.util.ArrayList;

public class Export_Sentence extends AppCompatActivity {

    private GridListAdapter_selectWord adapter;
    private ArrayList<String> arrayList;
    private DatabaseHelper databaseHelper;
    ArrayList<word> query_word;
    Dialog dialog;
    Export_Import export_import;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_ex3_st_easy_selectword);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("นำออกข้อมูลคำศัพท์");
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

        export_import = new Export_Import(getApplicationContext());

        loadGridView();
        dialog = new Dialog(this);

        Button button = findViewById(R.id.set_Groupanme);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvent();
            }
        });
    }

    private void loadGridView() {
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        query_word = databaseHelper.query_id_word("Sentence");

        for(word a : query_word){
            arrayList.add(a.getWord());
        }

        adapter = new GridListAdapter_selectWord(this, arrayList, false,14);
        gridView.setAdapter(adapter);
    }

    private void onClickEvent() {

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
        dialog.setContentView(R.layout.export_word_popup);
        dialog.setCanceledOnTouchOutside(false);
        final EditText Groupname = dialog.findViewById(R.id.Groupname);
        Button export = dialog.findViewById(R.id.CF);
        Button share = dialog.findViewById(R.id.share);
        ImageView close = dialog.findViewById(R.id.this_back);

        final SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter

        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if item is selected or not via size
                ArrayList<String> word = new ArrayList<>();
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {
                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            word.add(arrayList.get(selectedRows.keyAt(i)));
                        }
                    }

                    if (Groupname.getText().toString().length() < 3){
                        Toast.makeText(Export_Sentence.this, "ชื่อสั้นเกินไป", Toast.LENGTH_SHORT).show();
                    } else if (Groupname.getText().toString().length() > 8){
                        Toast.makeText(Export_Sentence.this, "ชื่อยาวเกินไป", Toast.LENGTH_SHORT).show();
                    } else {
                        export_import.export_Sentence(word,Groupname.getText().toString(),"export");
                        Toast.makeText(Export_Sentence.this, "นำข้อมูลออกแล้ว", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                                Intent intent = new Intent(Export_Word.this, F_setting.class);
//                                startActivity(intent);
                        finish();
                    }
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> word = new ArrayList<>();
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {
                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            word.add(arrayList.get(selectedRows.keyAt(i)));
                        }
                    }

                    if (Groupname.getText().toString().length() < 3){
                        Toast.makeText(Export_Sentence.this, "ชื่อสั้นเกินไป", Toast.LENGTH_SHORT).show();
                    } else if (Groupname.getText().toString().length() > 8){
                        Toast.makeText(Export_Sentence.this, "ชื่อยาวเกินไป", Toast.LENGTH_SHORT).show();
                    } else {
                        export_import.export_Word(word,Groupname.getText().toString(),"share");
                        Toast.makeText(Export_Sentence.this, "นำข้อมูลออกแล้ว", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                                Intent intent = new Intent(Export_Word.this, F_setting.class);
//                                startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}