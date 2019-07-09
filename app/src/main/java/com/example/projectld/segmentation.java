package com.example.projectld;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class segmentation {
    private String replace;
    private static String word1 = null;
    private String word;

    private String[] symbol = {"ะ","ั","็","ิ","่","ํ","ุ","ู","้","๊","๋"};

    public  String Break(String i){

        Locale u = new Locale("th");
        java.text.BreakIterator boundary = java.text.BreakIterator.getWordInstance(u);
        try {
            replace = i.replace(" ","");
            boundary.setText(replace);
            StringBuffer th = new StringBuffer();
            int start = boundary.first();
            for (int next = boundary.next(); next != java.text.BreakIterator.DONE; start = next, next = boundary.next()) {

                    th.append(replace.substring(start, next) + ",");
                    word = th.toString();
                    word1 = word; }
            }
        catch(Exception e){
            System.out.println("Error !" + e.getMessage());
        }
        return word1;
    }

    public ArrayList<String> split (String sentence){

        String[] words = sentence.split(",");
        ArrayList<String> segment = new ArrayList<>(Arrays.asList(words));

        return  segment;
    }

    public ArrayList<String> substring (String sentence){
        int start = 0;
        int next = 1;
     //ทดสอบ
        return null;
    }
}
