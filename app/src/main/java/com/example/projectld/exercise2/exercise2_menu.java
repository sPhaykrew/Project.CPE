package com.example.projectld.exercise2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class exercise2_menu extends AppCompatActivity {

    GridView gridView,gridView_admin;
    GridviewAdapter gridviewAdapter,gridviewAdapter_admin;
    ArrayList<String> arrayList,st;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise2_menu);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("เแบบฝึกถามตอบ");
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

        String[] Char = {"ก","ข","ฃ","ค","ฅ","ฆ","ง","จ","ฉ","ช","ซ","ฌ","ญ","ฎ","ฏ","ฐ","ฑ","ฒ","ณ","ด",
                "ต","ถ","ท","ธ","น","บ","ป","ผ","ฝ","พ","ฟ","ภ","ม","ย","ร","ล","ว","ศ","ษ","ส","ห","ฬ","อ","ฮ"};
        arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(Char));

        databaseHelper = new DatabaseHelper(this);

        //create Girdview
        gridView = findViewById(R.id.gridView);
        gridviewAdapter = new GridviewAdapter(arrayList,this,"exercise2_game",R.drawable.radius_button_color_ex2);
        gridView.setAdapter(gridviewAdapter);

        //gridview admin setting
        gridView_admin = findViewById(R.id.gridView_admin);
        st = databaseHelper.GetGroupname("Setting_ex2","st_ex2_id");
        gridviewAdapter_admin = new GridviewAdapter(st,this,"exercise2_game_st",R.drawable.radius_button_color_ex2);
        gridView_admin.setAdapter(gridviewAdapter_admin);

        TextView textHide = findViewById(R.id.noEX);

        if (gridviewAdapter_admin.getCount() != 0){
            textHide.setVisibility(View.INVISIBLE);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,0,0,0);
//            textHide.setLayoutParams(params);
        }

    }
}
