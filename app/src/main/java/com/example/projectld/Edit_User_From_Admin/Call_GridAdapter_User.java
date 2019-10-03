package com.example.projectld.Edit_User_From_Admin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;

import java.util.ArrayList;
import java.util.Objects;

public class Call_GridAdapter_User extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_gridview_users);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("ข้อมูลผู้ใช้งาน");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        ArrayList<String> values = databaseHelper.getAll_User("Fullname");
        ArrayList<String> UserID = databaseHelper.getAll_User("UserID");
        Log.d("Userid",UserID.toString());
        ArrayList<Bitmap> images = databaseHelper.getAll_User_Picture();

        gridView = (GridView) findViewById(R.id.griview);

        GridAdapter_User gridAdapter = new GridAdapter_User(this, values, images,UserID);

        gridView.setAdapter(gridAdapter);

    }
}