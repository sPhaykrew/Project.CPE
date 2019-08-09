package com.example.projectld;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class Call_GridAdapter_User extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_gridview_users);

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