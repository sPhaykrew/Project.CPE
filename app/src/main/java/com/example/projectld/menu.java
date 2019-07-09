package com.example.projectld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class menu extends AppCompatActivity {
    Button exercise1,exercise2,exercise3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        exercise1 = (Button) findViewById(R.id.Exercise1);
        exercise2 = (Button) findViewById(R.id.Exercise2);
        exercise3 = (Button) findViewById(R.id.Exercise3);

        /**
         * test hello !
         */

        exercise3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this,drag.class);
                startActivity(intent);
            }
        });

    }
}
