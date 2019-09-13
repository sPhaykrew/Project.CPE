package com.example.projectld.exercise2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridListAdapter_selectWord;
import com.example.projectld.exercise3.st_easy.grid_view_ex3_st_easy_selectWord;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_menu;
import com.example.projectld.exercise3.word;

import java.util.ArrayList;
import java.util.Arrays;

public class gridview_ex2_select_char extends AppCompatActivity {

    private GridListAdapter_selectWord adapter;
    private ArrayList<String> arrayList;
    private DatabaseHelper databaseHelper;

    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_ex2_select_char);
        editText = findViewById(R.id.setGroupName);

        loadGridView();
        onClickEvent();
    }

    private void loadGridView() {
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();

        String[] Char = {"ก","ข","ฃ","ค","ฅ","ฆ","ง","จ","ฉ","ช"};
        arrayList.addAll(Arrays.asList(Char));

        adapter = new GridListAdapter_selectWord(this, arrayList, false);
        gridView.setAdapter(adapter);
    }

    private void onClickEvent() {
        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                ArrayList<String> Character = new ArrayList<>();
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {
                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            Character.add(arrayList.get(selectedRows.keyAt(i)));
                        }
                    }
                    if(selectedRows.size() != 5 || selectedRows.size() > 5){
                        Toast.makeText(getApplicationContext(), "กรุณาเลือก 5 คำ", Toast.LENGTH_SHORT).show();
                    } else if (editText.getText().toString().length() < 3){
                        Toast.makeText(getApplicationContext(), "ชื่อสั้นเกินไป", Toast.LENGTH_SHORT).show();
                    } else if (editText.getText().toString().length() > 8){
                        Toast.makeText(getApplicationContext(), "ชื่อยาวเกินไป", Toast.LENGTH_SHORT).show();
                    } else {

                        //do something

                        Intent intent = new Intent(gridview_ex2_select_char.this, main_select_image.class);
                        intent.putExtra("Character",Character);
                        intent.putExtra("Groupname",String.valueOf(editText.getText()));
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
