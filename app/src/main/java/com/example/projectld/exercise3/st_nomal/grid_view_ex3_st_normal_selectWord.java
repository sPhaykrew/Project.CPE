package com.example.projectld.exercise3.st_nomal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.GridListAdapter_selectWord;
import com.example.projectld.R;
import com.example.projectld.exercise3.word;

import java.util.ArrayList;

public class grid_view_ex3_st_normal_selectWord extends AppCompatActivity {

    private GridListAdapter_selectWord adapter;
    private ArrayList<String> arrayList;
    private DatabaseHelper databaseHelper;
    private EditText editText;
    ArrayList<word> query_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_ex3_st_normal_selectword);
        editText = (EditText) findViewById(R.id.setGroupName);

        loadGridView();
        onClickEvent();

    }

    private void loadGridView() {
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
        query_word = databaseHelper.query_id_word("Sentence");

        for(word a : query_word){
            arrayList.add(a.getWord());
        }

        adapter = new GridListAdapter_selectWord(this, arrayList, false);
        gridView.setAdapter(adapter);
    }

    private void onClickEvent() {
        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
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
                    if(selectedRows.size() != 5 || selectedRows.size() > 5){
                        Toast.makeText(getApplicationContext(), "กรุณาเลือก 5 คำ", Toast.LENGTH_SHORT).show();
                    } else if (editText.getText().toString().length() < 3){
                        Toast.makeText(getApplicationContext(), "ชื่อสั้นเกินไป", Toast.LENGTH_SHORT).show();
                    } else if (editText.getText().toString().length() > 8){
                        Toast.makeText(getApplicationContext(), "ชื่อยาวเกินไป", Toast.LENGTH_SHORT).show();
                    } else {
                        for (String word : wordid_group){
                            String sentence = CheckWordID(word,query_word); //หา word ว่าเท่ากับ wordid ที่เท่าไหร
                            String GroupName =  String.valueOf(editText.getText()); //ตั้งชื่อให้กับ Group
                            databaseHelper.insert_group(sentence,GroupName,"Setting_ex3_normal","sentenceID");
                        }
                        Intent intent = new Intent(grid_view_ex3_st_normal_selectWord.this,st_ex3_normal_menu.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
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
