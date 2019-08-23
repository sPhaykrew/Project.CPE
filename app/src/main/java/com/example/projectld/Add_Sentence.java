package com.example.projectld;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class Add_Sentence extends AppCompatActivity {

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
        ArrayList Sentence = databaseHelper.queryword("Sentence");

        gridviewAdapter = new GridviewAdapter(Sentence,this,"Delete_Mod_Sentence",R.drawable.radius_button_color1);
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

        final EditText editText = dialog.findViewById(R.id.Add_Word);
        Button button = dialog.findViewById(R.id.CF);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 1){
                    databaseHelper.AddWord(String.valueOf(editText.getText()),"Sentence");
                }
            }
        });

        dialog.show();

    }
}
