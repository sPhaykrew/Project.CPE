package com.example.projectld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projectld.exercise3.word;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteAssetHelper {
    public DatabaseHelper(Context context) {
        super(context, "project.db", null,3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + "User");
        // Create tables again
        onCreate(db);
    }

    public User queryUser(String email, String password) {// check login first

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query("User", new String[]{"UserID",
                        "Username", "Password","Fullname","Age","sex","Permission","Picture"}, "Username" + "=? and " + "Password" + "=?",
                new String[]{email, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2),cursor.getString(0)
            ,cursor.getString(3),cursor.getInt(4),cursor.getString(5)
            ,cursor.getString(6),cursor.getBlob(7));
        }
        db.close();
        return user;
    }

    public void insert_user (String user,String password,String name,int age,String sex,byte[] Picture){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> words = new ArrayList<>();
        ContentValues Val = new ContentValues();
        Val.put("Username",user);
        Val.put("Password", password);
        Val.put("Fullname",name);
        Val.put("Age",age);
        Val.put("sex",sex);
        Val.put("Permission","User");
        Val.put("Picture",Picture);
        long rows = db.insert("User", null, Val);
        db.close();
    }

    public ArrayList<String> queryword (String table){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * From "+table, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(1));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> queryset (String offset){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * From Word limit 5 offset " + offset,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(1));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> query_wordid_fromST(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Word.word From Setting_ex3_easy INNER join Word ON Setting_ex3_easy.wordID = Word.wordID",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> GetGroupname(String table,String Order){ //เช็ค group โดยการลบคำซ่ำในช่อง group
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT GroupName , COUNT(*) count FROM " +table+ " GROUP BY [GroupName] Having COUNT(*) > 1 ORDER BY "+Order+ "", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> group_st_easy (String group){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Word.word From Setting_ex3_easy INNER join Word " +
                "ON Setting_ex3_easy.wordID = Word.wordID where" + "\"group\"" +" ="+group,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> group_st_normal (String group,String table){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence From "+table+" INNER join Sentence " +
                "ON "+table+".sentenceID = Sentence.sentenceID where" + "\"group\"" +" ="+group,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public void insert_group (String ID ,String group,String GroupName,String table,String tableFK){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> words = new ArrayList<>();
        ContentValues Val = new ContentValues();
        Val.put(tableFK,ID);
        Val.put("\"group\"", group);
        Val.put("GroupName",GroupName);
        long rows = db.insert(table, null, Val);
        db.close();
    }

    public ArrayList<word> query_id_word(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<word> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * From "+table, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word word = new word(cursor.getString(0),cursor.getString(1));
            words.add(word);
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> Check_Group_db(String table){ //เช็ค group โดยการลบคำซ่ำในช่อง group
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT \"group\" , COUNT(*) count\n" +
                "\n" +
                "FROM "+table+ "\n" +
                "\n" +
                "GROUP BY [group] \n" +
                "\n" +
                "Having COUNT(*) > 1 \n", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }
    public ArrayList<String> ex3_easy_game_st(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Word.word From Setting_ex3_easy INNER join Word ON Setting_ex3_easy.wordID = Word.wordID where Setting_ex3_easy.GroupName like '"+name+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> ex3_normal_game_st(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence From Setting_ex3_normal INNER join Sentence ON Setting_ex3_normal.sentenceID = Sentence.sentenceID where Setting_ex3_normal.GroupName like '"+name+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> ex3_hard_game_st(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence From Setting_ex3_hard INNER join Sentence ON Setting_ex3_hard.sentenceID = Sentence.sentenceID where Setting_ex3_hard.GroupName like '"+name+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

}
