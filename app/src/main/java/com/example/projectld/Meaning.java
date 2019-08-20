package com.example.projectld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Meaning extends AppCompatActivity {

    TextView Word_Header,Meaning_Sub;
    String Word,Meaning;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meaning);

        Word_Header = findViewById(R.id.Word);
        Meaning_Sub = findViewById(R.id.Meaning);
        Database_Meaning database_meaning = new Database_Meaning(this);

        bundle = getIntent().getExtras();
        Word = bundle.getString("Word_data");
        Meaning = database_meaning.Meaning(Word);

        Word_Header.setText(Word);
        Meaning_Sub.setText(Meaning);

    }
}
