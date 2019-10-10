package com.example.projectld.exercise3.st_nomal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.projectld.exercise3.st_easy.grid_view_ex3_st_easy_selectWord;
import com.example.projectld.exercise3.word;

import java.util.ArrayList;

public class grid_view_ex3_st_normal_selectWord extends AppCompatActivity {

    private GridListAdapter_selectWord adapter;
    private ArrayList<String> arrayList;
    private DatabaseHelper databaseHelper;
    ArrayList<word> query_word;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_ex3_st_normal_selectword);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("สร้างแบบทดสอบ");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.relative_layout_radius);
        dialog.setContentView(R.layout.st_confirm_popup);
        dialog.setCanceledOnTouchOutside(false);
        final EditText Groupname = dialog.findViewById(R.id.Groupname);
        Button button = dialog.findViewById(R.id.CF);
        ImageView close = dialog.findViewById(R.id.this_back);

        final SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter

        if(selectedRows.size() != 5 || selectedRows.size() > 5 ) {
//            Groupname.setHint("กรุณาเลือก 5 คำ");
//            Groupname.setHintTextColor(Color.parseColor("#EF5350"));
//            Groupname.setText("");
            Toast.makeText(grid_view_ex3_st_normal_selectWord.this, "กรุณาเลือก 5 คำ", Toast.LENGTH_SHORT).show();
        } else {

            dialog.show();

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Check if item is selected or not via size
                    ArrayList<String> wordid_group = new ArrayList<>();
                    if (selectedRows.size() > 0) {
                        //Loop to all the selected rows array
                        for (int i = 0; i < selectedRows.size(); i++) {

                            //Check if selected rows have value i.e. checked item
                            if (selectedRows.valueAt(i)) {
                                //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                                wordid_group.add(arrayList.get(selectedRows.keyAt(i)));
                            }
                        }

                        if (Groupname.getText().toString().length() < 3){
//                            Groupname.setHint("ชื่อสั้นเกินไป");
//                            Groupname.setHintTextColor(Color.parseColor("#EF5350"));
//                            Groupname.setText("");
                            Toast.makeText(grid_view_ex3_st_normal_selectWord.this, "ชื่อสั้นเกินไป", Toast.LENGTH_SHORT).show();
                        } else if (Groupname.getText().toString().length() > 8){
//                            Groupname.setHint("ชื่อยาวเกินไป");
//                            Groupname.setHintTextColor(Color.parseColor("#EF5350"));
//                            Groupname.setText("");
                            Toast.makeText(grid_view_ex3_st_normal_selectWord.this, "ชื่อยาวเกินไป", Toast.LENGTH_SHORT).show();
                        } else {
                            String GroupName =  String.valueOf(Groupname.getText()); //ตั้งชื่อให้กับ Group
                            String Check_Group = databaseHelper.check_groupname_import(GroupName,"Setting_ex3_normal");
                            if(Check_Group == null) {
                                for (String word : wordid_group) {
                                    String sentence = CheckWordID(word, query_word); //หา word ว่าเท่ากับ wordid ที่เท่าไหร
                                    databaseHelper.insert_group(sentence, GroupName, "Setting_ex3_normal", "sentenceID");
                                }
                                databaseHelper.insert_score_normal(GroupName);
                                Toast.makeText(grid_view_ex3_st_normal_selectWord.this, "เพิ่มข้อมูลแล้ว", Toast.LENGTH_SHORT).show();
                                st_ex3_normal_menu.close_activity.finish();
                                Intent intent = new Intent(grid_view_ex3_st_normal_selectWord.this, st_ex3_normal_menu.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(grid_view_ex3_st_normal_selectWord.this,"ชื่อแบบทดสอบซ่ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบ",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });  }
    }

    public String CheckWordID(String word,ArrayList<word> word_id){ // หา word ว่าเท่ากับ wordid ที่เท่าไหร
        String id = null;
        for(int i=0;i<word_id.size();i++){
            if(word.equals(word_id.get(i).getWord())) {
                id = word_id.get(i).getId();
            }
        }
        return id;
    }
}
