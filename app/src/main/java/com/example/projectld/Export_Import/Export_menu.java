package com.example.projectld.Export_Import;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.projectld.R;

public class Export_menu extends AppCompatActivity {

    Button ex2,ex3,ex4,ex5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_menu);

        ex2 = findViewById(R.id.ex2);
        ex3 = findViewById(R.id.ex3);
        ex4 = findViewById(R.id.ex4);
        ex5 = findViewById(R.id.ex5);

        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex2.class);
                startActivity(intent);
            }
        });

        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex3.class);
                startActivity(intent);
            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex4.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex5.class);
                startActivity(intent);
            }
        });
    }
}
