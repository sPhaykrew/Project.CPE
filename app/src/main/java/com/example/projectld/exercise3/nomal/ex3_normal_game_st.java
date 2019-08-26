package com.example.projectld.exercise3.nomal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.Score_ex3_word;
import com.example.projectld.TTS;
import com.example.projectld.exercise3.segmentation;
import com.example.projectld.recyclerView_Ranking.Ranking_Adapter;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


@SuppressLint("NewApi")
public class ex3_normal_game_st extends Activity {

    com.example.projectld.exercise3.segmentation segmentation;
    TTS tts;
    Button voice,next,back;

    String Groupname,ArraySet;

    int start = 0; //เช็คว่าเลากไปกี่ตัวแล้ว
    int finish = 0;

    ArrayList<String> wordset = new ArrayList<>();
    int count = 0; // count array wordset for next and back
    SharedPreferences sharedPreferences; //เก็บค่า I ไม่ให้หาย
    Bundle arrayset; //รับค่า array จาก gridview ที่คิวรี่จากฐานข้อมูล

    int first = 0; //เช็คว่าใช้การทำงานครั่งแรกไหม

    Dialog dialog,dialog_rank; //popup score
    DatabaseHelper databaseHelper;
    SharedPreferences user;

    int Score = 100;

    private RecyclerView RecyclerView;
    private RecyclerView.LayoutManager LayoutManager;
    private RecyclerView.Adapter Adapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3_nomal_game);

        user = getSharedPreferences("User", Context.MODE_PRIVATE);
        dialog = new Dialog(this);
        dialog_rank = new Dialog(this);

        next = (Button)  findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);
        arrayset = getIntent().getExtras();

        voice = (Button) findViewById(R.id.voice_tts);
        tts = new TTS(this);
        databaseHelper = new DatabaseHelper(this);

        LoadInt();

        Groupname = arrayset.getString("Groupname"); //ได้รับชื่อแบบทดสอบ
        if (first == 0) {
            wordset = databaseHelper.ex3_normal_game_st(String.valueOf(Groupname), user.getString("UserID", null)); // เก็บคำ 5 คำ และ insert setting ID
            first++;
            SaveFirst(first);
        } else {
            LoadArray();
            String[] playlists = ArraySet.split(",");
            wordset.addAll(Arrays.asList(playlists));
        }

        /**
         * ตัดคำ
         */
        segmentation = new segmentation();
        ArrayList<String> sentence = segmentation.split(segmentation.Break(wordset.get(count))); //ระดับคำศัพท์
        //ArrayList<String> sentence = segmentation.substring(wordset.get(count)); //ระดับตัวอักษร

        /**
         * question set ex3_easy_game listeners
         */
        LinearLayout question = (LinearLayout) findViewById(R.id.question);
        for(int i=0 ; i < sentence.size() ; i++) {
            TextView valueQT = new TextView(this);
            valueQT.setText("__");
            valueQT.setId(i);
            valueQT.setTextSize(30);
            valueQT.setTag(sentence.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMarginStart(40);
            valueQT.setLayoutParams(params);
            valueQT.setOnDragListener(new ChoiceDragListener());
            valueQT.setGravity(Gravity.CENTER);
            question.addView(valueQT);

            start++; //เช็คว่าตอบคำถามครบหรือยัง
        }

        /**
         * answer views to ex3_easy_game
         */
        LinearLayout answer = (LinearLayout) findViewById(R.id.answer);
        int loop = sentence.size();
        ArrayList<String> sentenceRD = random(sentence,loop);

        for(int i=0 ; i < loop ; i++){
            TextView answerCH = new TextView(this);
            answerCH.setText(sentenceRD.get(i));
            answerCH.setTextSize(30);
            answerCH.setId(i);
            answerCH.setTag(answerCH.getText());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginStart(20);
            answerCH.setLayoutParams(params);
            answerCH.setOnTouchListener(new ChoiceTouchListener());
            answer.addView(answerCH);
        }

        /**
         * call TTS
         */
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(wordset.get(count));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= wordset.size()){
                    //Toast.makeText(ex3_easy_game_st.this,"ไม่พบคำถัดไป",Toast.LENGTH_SHORT).show();
                    count--;
                    Popup_score();
                } else {

                    StringBuilder Sumwordset = new StringBuilder();
                    for (int i = 0; i < wordset.size(); i++) {
                        Sumwordset.append(wordset.get(i)).append(",");
                    }
                    SaveArray(Sumwordset.toString());

                    SaveInt(count);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(count < 0){
                    Toast.makeText(ex3_normal_game_st.this,"ไม่พบคำก่อนหน้า",Toast.LENGTH_SHORT).show();
                    count++;
                } else {
                    SaveInt(count);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     *
     */
    private final class ChoiceTouchListener implements OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                /*
                 * Drag details: we only need default behavior
                 * - clip data could be set to pass data as part of ex3_easy_game
                 * - shadow can be tailored
                 */
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the ex3_easy_game process
     *
     */
    @SuppressLint("NewApi")
    private class ChoiceDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;

                    Log.i("droptarget", String.valueOf(dropTarget.getTag()));
                    Log.i("dropped", String.valueOf(dropped.getTag()));

                    //checking whether first character of dropTarget equals first character of dropped
                    if((dropTarget.getTag().equals(dropped.getTag())))
                    {
                        finish++; //เช็คว่าตอบคำถามครบหรือยัง
                        //stop displaying the view where it was before it was dragged
                        view.setVisibility(View.INVISIBLE);
                        //update the text in the target view to reflect the data being dropped
                        dropTarget.setText(dropped.getText().toString());
                        //make it bold to highlight the fact that an item has been dropped
                        dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                        //if an item has already been dropped here, there will be a tag
//                        Object tag = dropTarget.getTag();
                        //if there is already an item here, set it back visible in its original place

//                        if(tag!=null)
//                        {
//                            //the tag is the view id already dropped here
//                            int existingID = (Integer)tag;
//                            //set the original view visible again
//                            findViewById(existingID).setVisibility(View.VISIBLE);
//                        }

                        //set the tag in the target view being dropped on - to the ID of the view being dropped
                        dropTarget.setTag(dropped.getId());
                        //remove setOnDragListener by setting OnDragListener to null, so that no further ex3_easy_game & dropping on this TextView can be done
                        dropTarget.setOnDragListener(null);

                        if (finish == start){
                            String stID = databaseHelper.Find_stID_sentence(wordset.get(count),Groupname,"Setting_ex3_normal","st_ex3_normal_id");
                            databaseHelper.update_score_ex3_normal(user.getString("UserID",null),Score,stID); //update score

                            wordset.remove(wordset.get(count)); // ลบคำที่ทำเสร็จแล้ว
                            //count++;
                            SaveInt(count);

                            StringBuilder Sumwordset = new StringBuilder();
                            for (int i = 0; i < wordset.size(); i++) {
                                Sumwordset.append(wordset.get(i)).append(",");
                            }
                            SaveArray(Sumwordset.toString());

                            if (wordset.size() != 0) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            } else {
                                Popup_score();
                            }

                            //Toast.makeText(ex3_easy_game_st.this,"Finish",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        //displays message if first character of dropTarget is not equal to first character of dropped
                        Score = Score - 5;
                        Toast.makeText(ex3_normal_game_st.this, dropped.getText().toString() + " ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void reset(View view)
    {
//        option1.setVisibility(TextView.VISIBLE);
//        option2.setVisibility(TextView.VISIBLE);
//        option3.setVisibility(TextView.VISIBLE);
//
//        choice1.setText("A for ");
//        choice2.setText("O for ");
//        choice3.setText("B for ");
//
//        choice1.setTag(null);
//        choice2.setTag(null);
//        choice3.setTag(null);
//
//        choice1.setTypeface(Typeface.DEFAULT);
//        choice2.setTypeface(Typeface.DEFAULT);
//        choice3.setTypeface(Typeface.DEFAULT);
//
//        choice1.setOnDragListener(new ChoiceDragListener());
//        choice2.setOnDragListener(new ChoiceDragListener());
//        choice3.setOnDragListener(new ChoiceDragListener());
    }

    public ArrayList<String> random (ArrayList<String> result, int i){
        Random rd = new Random();
        ArrayList<String> list = new ArrayList<>();
        for(int j=0;j<i;j++){
            String test = result.get(rd.nextInt(result.size()));
            list.add(test);
            result.remove(test);
        }
        return list;
    }

    public void SaveArray(String array){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("array",array);
        editor.commit();
    }

    public void LoadArray(){ // โหลดค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArraySet = sharedPreferences.getString("array", "Hello!");
    }

    public void SaveInt(int value){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", value);
        editor.commit();
    }

    public void SaveFirst(int value){ //เซฟค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("first",value);
        editor.commit();
    }

    public void LoadInt(){ // โหลดค่า count
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        count = sharedPreferences.getInt("key", 0);
        first = sharedPreferences.getInt("first",0);
    }

    public void Popup_score(){
        TextView Fullname,Sum_Score,text1,text2,text3,text4,text5,score1,score2,score3,score4,score5;
        Button goBack,goRank;

        dialog.setContentView(R.layout.score_popup);

        Score_ex3_word score = databaseHelper.Score_ex3_normal(Groupname,user.getString("UserID",null)
                ,user.getString("Fullname",null));

        Sum_Score = dialog.findViewById(R.id.Sum_Score);
        text1 = dialog.findViewById(R.id.text1);
        text2 = dialog.findViewById(R.id.text2);
        text3 = dialog.findViewById(R.id.text3);
        text4 = dialog.findViewById(R.id.text4);
        text5 = dialog.findViewById(R.id.text5);
        score1 = dialog.findViewById(R.id.Score_text1);
        score2 = dialog.findViewById(R.id.Score_text2);
        score3 = dialog.findViewById(R.id.Score_text3);
        score4 = dialog.findViewById(R.id.Score_text4);
        score5 = dialog.findViewById(R.id.Score_text5);

        goBack = dialog.findViewById(R.id.Back);
        goRank = dialog.findViewById(R.id.Rank);

        text1.setText(score.getWord().get(0));
        text2.setText(score.getWord().get(1));
        text3.setText(score.getWord().get(2));
        text4.setText(score.getWord().get(3));
        text5.setText(score.getWord().get(4));

        score1.setText(String.valueOf(score.getScore().get(0)));
        score2.setText(String.valueOf(score.getScore().get(1)));
        score3.setText(String.valueOf(score.getScore().get(2)));
        score4.setText(String.valueOf(score.getScore().get(3)));
        score5.setText(String.valueOf(score.getScore().get(4)));

        ArrayList<Integer> averrage = new ArrayList<>(score.getScore());
        int sum_average = Average(averrage);
        Sum_Score.setText("คะแนนรวม  " +String.valueOf(sum_average));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Popup_rank();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void Popup_rank(){
        Button goBack,myScore;

        dialog_rank.setContentView(R.layout.ranking_popup);

        ArrayList<Ranking_Item> ranking_items = databaseHelper.rank_ex3_normal(Groupname);

        RecyclerView = dialog_rank.findViewById(R.id.recyclerView);
        RecyclerView.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        Adapter = new Ranking_Adapter(ranking_items);

        RecyclerView.setLayoutManager(LayoutManager);
        RecyclerView.setAdapter(Adapter);


        goBack = dialog_rank.findViewById(R.id.Back);
        myScore = dialog_rank.findViewById(R.id.MyScore);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rank.dismiss();
                Popup_score();
            }
        });

        dialog_rank.setCanceledOnTouchOutside(false);
        dialog_rank.show();
    }

    public int Average (ArrayList<Integer> number){
        int average = 0;
        for (int i=0;i<5;i++) {
            average += number.get(i);
        }
        average = average / 5;
        return  average;
    }
}