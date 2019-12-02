package com.example.projectld.exercise3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.Add_Sentence;
import com.example.projectld.Add_Word;
import com.example.projectld.DatabaseHelper;
import com.example.projectld.Database_Meaning;
import com.example.projectld.Edit_User_From_Admin.Admin_score_exercise2;
import com.example.projectld.Edit_User_From_Admin.Admin_score_exercise3;
import com.example.projectld.Edit_User_From_Admin.Admin_score_exercise4;
import com.example.projectld.Edit_User_From_Admin.Admin_score_exercise5;
import com.example.projectld.Edit_User_From_Admin.GridAdapter_User_Modifiled;
import com.example.projectld.R;
import com.example.projectld.TTS;
import com.example.projectld.exercise2.ex2_game_st;
import com.example.projectld.exercise2.exercise2_game;
import com.example.projectld.exercise2.st_ex2_adapter.st_ex2_inMenu;
import com.example.projectld.exercise3.easy.ex3_easy_game;
import com.example.projectld.exercise3.easy.ex3_easy_game_st;
import com.example.projectld.exercise3.hard.ex3_hard_game;
import com.example.projectld.exercise3.hard.ex3_hard_game_st;
import com.example.projectld.exercise3.nomal.ex3_nomal_game;
import com.example.projectld.exercise3.nomal.ex3_normal_game_st;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_inMenu;
import com.example.projectld.exercise3.st_hard.st_ex3_hard_inMenu;
import com.example.projectld.exercise3.st_nomal.st_ex3_normal_inMenu;
import com.example.projectld.modify_word;
import com.example.projectld.navigationDrawer.F_Word_data;
import com.example.projectld.word_Image_object;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;

public class GridviewAdapter extends BaseAdapter {
    List<String> lstSource;
    Context context;
    ArrayList<String> wordset = new ArrayList<>();
    ArrayList<String> tagset = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String mode;
    Intent intent;
    int color;
    ArrayList<String> tag = new ArrayList<>();

    public GridviewAdapter(List<String> lstSource, Context context,String mode,int color,ArrayList<String> tag) {
        this.lstSource = lstSource;
        this.context = context;
        this.mode = mode;
        this.color = color;
        this.tag = tag;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        Button button = null;

        final TTS tts = new TTS(context);

        button = new Button(context);
        button.setLayoutParams(new GridView.LayoutParams(220, 220));
        button.setPadding(0, 0, 0, 0);
        button.setBackgroundResource(color);
        button.setTextColor(parseColor("#ffffff"));

//        if (tag != null) {
//            button.setTag(tag.get(position));
//        }
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
                editor.remove("Charecter_Score").apply();
                editor.remove("Score").apply();

                final int count = (int) finalButton.getTag(); // count array wordset for next and back
                wordset = (ArrayList<String>) lstSource;
                tagset = (ArrayList<String>) tag;

                switch (mode){
                    case "easy" :

                        PopupMenu popupMenu_ex3 = new PopupMenu(context,v);
                        popupMenu_ex3.getMenuInflater().inflate(R.menu.game_menu,popupMenu_ex3.getMenu());
                        popupMenu_ex3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case  R.id.voice :
                                        tts.speak(String.valueOf(tag.get(count)));
                                        return true;
                                    case R.id.play:
                                        intent = new Intent(context, ex3_easy_game.class);
                                        intent.putExtra("countarray",count);
                                        intent.putExtra("wordset", tagset); //ส่งค่าไปอีก activity
                                        context.startActivity(intent);
                                        return true;
                                }
                                return true;
                            }
                        });

                        popupMenu_ex3.show();
                        break;

                    case "nomal" :

                        PopupMenu popupMenu_ex4 = new PopupMenu(context,v);
                        popupMenu_ex4.getMenuInflater().inflate(R.menu.game_menu,popupMenu_ex4.getMenu());
                        popupMenu_ex4.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case  R.id.voice :
                                        tts.speak(String.valueOf(tag.get(count)));
                                        return true;
                                    case R.id.play:
                                        intent =  new Intent(context, ex3_nomal_game.class);
                                        intent.putExtra("countarray",count);
                                        intent.putExtra("wordset", tagset); //ส่งค่าไปอีก activity
                                        context.startActivity(intent);
                                        return true;
                                }
                                return true;
                            }
                        });

                        popupMenu_ex4.show();
                        break;

                    case "hard" :

                        PopupMenu popupMenu_ex5 = new PopupMenu(context,v);
                        popupMenu_ex5.getMenuInflater().inflate(R.menu.game_menu,popupMenu_ex5.getMenu());
                        popupMenu_ex5.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case  R.id.voice :
                                        tts.speak(String.valueOf(tag.get(count)));
                                        return true;
                                    case R.id.play:
                                        intent =  new Intent(context, ex3_hard_game.class);
                                        intent.putExtra("countarray",count);
                                        intent.putExtra("wordset", tagset); //ส่งค่าไปอีก activity
                                        context.startActivity(intent);
                                        return true;
                                }
                                return true;
                            }
                        });

                        popupMenu_ex5.show();
                        break;

                    case "exercise2_game" :

                        PopupMenu popupMenu_ex2 = new PopupMenu(context,v);
                        popupMenu_ex2.getMenuInflater().inflate(R.menu.game_menu,popupMenu_ex2.getMenu());
                        popupMenu_ex2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case  R.id.voice :
                                        tts.speak(String.valueOf(tag.get(count)));
                                        return true;
                                    case R.id.play:
                                        intent = new Intent(context, exercise2_game.class);
                                        intent.putExtra("wordset", tagset); //ส่งค่าไปอีก activity
                                        intent.putExtra("countarray",count);
                                        context.startActivity(intent);
                                        return true;
                                }
                                return true;
                            }
                        });

                        popupMenu_ex2.show();
                        break;

                    case "st_ex2" :
                        intent = new Intent(context, st_ex2_inMenu.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_easy" :
                        intent = new Intent(context, st_ex3_easy_inMenu.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_normal" :
                        intent = new Intent(context, st_ex3_normal_inMenu.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("wordset", wordset); //ส่งค่าไปอีก activity
                        context.startActivity(intent);
                        break;
                    case "st_hard" :
                        intent = new Intent(context, st_ex3_hard_inMenu.class);
                        intent.putExtra("Groupname",finalButton.getText());
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
                        final Dialog meaning = new Dialog(context);
                        meaning.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                        meaning.setContentView(R.layout.meaning_popup);
                        ImageView close = meaning.findViewById(R.id.this_back);
                        ImageView word_Image = meaning.findViewById(R.id.word_Image);
                        ImageView voice_word = meaning.findViewById(R.id.voice_word);
                        ImageView voice_mean = meaning.findViewById(R.id.voice_mean);
                        TextView word = meaning.findViewById(R.id.word);
                        TextView mean = meaning.findViewById(R.id.meaning);

                        final TTS tts = new TTS(context);
                        DatabaseHelper db = new DatabaseHelper(context);
                        Database_Meaning db_mean = new Database_Meaning(context);

                        final String get_word = String.valueOf(finalButton.getText());
                        final String get_mean = db_mean.Meaning(get_word);
                        word.setText(get_word);
                        mean.setText(get_mean);

                        ArrayList<word_Image_object> path_image = db.get_Image_word(get_word);

                        path_image.get(0).setDefualt_Image(null); //เอารูปออกชั่วคราว

                        if (path_image.get(0).getDefualt_Image() != null) {
                            int set_image = context.getResources().getIdentifier(path_image.get(0).getDefualt_Image(), "drawable", context.getPackageName());
                            word_Image.setImageResource(set_image);

                            if (path_image.get(0).getDefualt_Image().equals("null")){
                                File file = new File(path_image.get(0).getPath_Image());
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                word_Image.setImageBitmap(myBitmap);
                            }

                        } else if (path_image.get(0).getPath_Image() != null) {
                            File file = new File(path_image.get(0).getPath_Image());
                            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            word_Image.setImageBitmap(myBitmap);
                        } else {
                            word_Image.setVisibility(View.GONE);
                        }

                        voice_word.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tts.speak(get_word);
                            }
                        });

                        voice_mean.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tts.speak(get_mean);
                            }
                        });

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                meaning.dismiss();
                            }
                        });

                        meaning.show();
                        break;

                    case "Delete_Mod_Word" :
                        intent = new Intent(context, modify_word.class);
                        intent.putExtra("word",finalButton.getText());
                        context.startActivity(intent);
                        break;


                    case "Delete_Mod_Sentence" :
                        final Dialog dialog_sentence = new Dialog(context);
                        dialog_sentence.setCanceledOnTouchOutside(false);
                        dialog_sentence.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                        dialog_sentence.setContentView(R.layout.modify_delete_sentence_popup);
                        final DatabaseHelper databaseHelper_sentence = new DatabaseHelper(context);

                        final EditText editText_sentence = dialog_sentence.findViewById(R.id.Edit_Word);
                        Button delete_sentence = dialog_sentence.findViewById(R.id.Delete);
                        Button modify_sentence = dialog_sentence.findViewById(R.id.modify);

                        editText_sentence.setText(String.valueOf(finalButton.getText()));
                        dialog_sentence.show();

                        modify_sentence.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (editText_sentence.getText().length() == 0) {
                                    Toast.makeText(context,"กรุณาพิมพ์ข้อความ",Toast.LENGTH_SHORT).show();
                                } else if (editText_sentence.getText().length() <= 5){
                                    Toast.makeText(context,"ข้อความสั่นเกินไป",Toast.LENGTH_SHORT).show();
                                } else if (editText_sentence.getText().length() > 22){
                                    Toast.makeText(context,"ข้อความยาวกินไป",Toast.LENGTH_SHORT).show();
                                } else {
                                    String Word_Mod = String.valueOf(editText_sentence.getText());
                                    databaseHelper_sentence.update_sentence(String.valueOf(finalButton.getText()), Word_Mod);

                                    Add_Sentence.close_activity.finish();
                                    intent = new Intent(context, Add_Sentence.class);
                                    context.startActivity(intent);
                                    dialog_sentence.dismiss();
                                }
                            }
                        });

                        delete_sentence.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean delete = databaseHelper_sentence.delete_sentence(String.valueOf(finalButton.getText()));
                                if (delete) {
                                    Add_Sentence.close_activity.finish();
                                    intent = new Intent(context, Add_Sentence.class);
                                    context.startActivity(intent);
                                    dialog_sentence.dismiss();
                                }
                            }
                        });

                        ImageView goBack_sentence = dialog_sentence.findViewById(R.id.this_back);
                        goBack_sentence.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_sentence.dismiss();
                            }
                        });

                        break;

                    case "exercise2_game_st" :
                        intent = new Intent(context, ex2_game_st.class);
                        intent.putExtra("Groupname",finalButton.getText());
                        intent.putExtra("countarray",count);
                        context.startActivity(intent);
                        break;

                }
            }
        }); return button;
    }

}
