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

import com.example.projectld.Meaning;
import com.example.projectld.TTS;
import com.example.projectld.exercise3.easy.ex3_easy_game;
import com.example.projectld.exercise3.easy.ex3_easy_game_st;
import com.example.projectld.exercise3.hard.ex3_hard_game;
import com.example.projectld.exercise3.hard.ex3_hard_game_st;
import com.example.projectld.exercise3.nomal.ex3_nomal_game;
import com.example.projectld.exercise3.nomal.ex3_normal_game_st;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_inMenu;
import com.example.projectld.exercise3.st_hard.st_ex3_hard_inMenu;
import com.example.projectld.exercise3.st_nomal.st_ex3_normal_inMenu;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;

public class GridviewAdapter extends BaseAdapter {
    List<String> lstSource;
    Context context;
    ArrayList<String> wordset = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String mode;
    Intent intent;
    int test;

    public GridviewAdapter(List<String> lstSource, Context context,String mode,int test) {
        this.lstSource = lstSource;
        this.context = context;
        this.mode = mode;
        this.test = test;
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

        final TTS tts = new TTS(context);

        button = new Button(context);
        button.setLayoutParams(new GridView.LayoutParams(220, 220));
        button.setPadding(0, 0, 0, 0);
        button.setBackgroundResource(test);
        button.setTextColor(parseColor("#ffffff"));

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
                editor.remove("array").apply();

                int count = (int) finalButton.getTag(); // count array wordset for next and back
                wordset = (ArrayList<String>) lstSource;

                switch (mode){
                    case "easy" :
                        intent =  new Intent(context, ex3_easy_game.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "nomal" :
                        intent =  new Intent(context, ex3_nomal_game.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "hard" :
                        intent =  new Intent(context, ex3_hard_game.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_easy" :
                        intent = new Intent(context, st_ex3_easy_inMenu.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_normal" :
                        intent = new Intent(context, st_ex3_normal_inMenu.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_hard" :
                        intent = new Intent(context, st_ex3_hard_inMenu.class);
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "ex3_easy_game_st" :
                        intent = new Intent(context, ex3_easy_game_st.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "ex3_normal_game_st" :
                        intent = new Intent(context, ex3_normal_game_st.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "ex3_hard_game_st" :
                        intent = new Intent(context, ex3_hard_game_st.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("countarray",count);
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "Sentence_Data" :
                        tts.speak(String.valueOf(finalButton.getText()));
                        break;
                    case "Word_data" :
                        intent = new Intent(context, Meaning.class);
                        intent.putExtra("Word_data",String.valueOf(finalButton.getText()));
                        context.startActivity(intent);
                        break;
                }
            }
        }); return button;
    }
}
