package com.example.projectld.exercise3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.easy.ex3_easy_game;
import com.example.projectld.exercise3.hard.ex3_hard_game;
import com.example.projectld.exercise3.nomal.ex3_nomal_game;

import java.util.ArrayList;
import java.util.List;

public class GridviewAdapter extends BaseAdapter {
    List<String> lstSource;
    Context context;
    ArrayList<String> wordset = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String mode;
    Intent intent;

    public GridviewAdapter(List<String> lstSource, Context context,String mode) {
        this.lstSource = lstSource;
        this.context = context;
        this.mode = mode;
    }

    @Override
    public int getCount() {
        return lstSource.size();
    }

    @Override
    public Object getItem(int position) {
        return lstSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Button button = null;
        final DatabaseHelper databaseHelper = new DatabaseHelper(context);

        if(button == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(300, 300));
            button.setPadding(0, 0, 0, 0);
        }

        button.setText(lstSource.get(position));
        button.setTag(position);
        final Button finalButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); // ลบค่า I ในหน้าต่อไป
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("key").apply();
                editor.remove("first").apply();

                int count = (int) finalButton.getTag(); // count array wordset for next and back

                Toast.makeText(context, finalButton.getTag().toString(),Toast.LENGTH_SHORT).show();
                wordset = (ArrayList<String>) lstSource;

                switch (mode){
                    case "easy" : intent =  new Intent(context, ex3_easy_game.class); break;
                    case "nomal" : intent =  new Intent(context, ex3_nomal_game.class); break;
                    case "hard" : intent =  new Intent(context, ex3_hard_game.class); break;

                }

                intent.putExtra("countarray",count);
                intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                context.startActivity(intent);
            }
        }); return button;
    }
}
