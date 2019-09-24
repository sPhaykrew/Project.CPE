package com.example.projectld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.projectld.Export_Import.ex2_st_object;
import com.example.projectld.My_Score.Score.HorizontalModel;
import com.example.projectld.exercise2.Character;
import com.example.projectld.exercise2.st_ex2_adapter.Item_st_ex2;
import com.example.projectld.exercise3.word;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteAssetHelper {

    Context context;

    public DatabaseHelper(Context context) {
        super(context, "project.db", null,3);
        this.context = context;
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

    public void update_user (String user,String name,int age,String sex,byte[] Picture,String UserID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        Val.put("Username",user);
        Val.put("Fullname",name);
        Val.put("Age",age);
        Val.put("sex",sex);
        Val.put("Picture",Picture);
        long rows = db.update("User",Val, "UserID=" + UserID, null);
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
    public ArrayList<String> ex3_easy_game_st(String GroupName,String UserID){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Word.word,Setting_ex3_easy.st_ex3_easy_id From Setting_ex3_easy INNER join Word ON Setting_ex3_easy.wordID = Word.wordID where Setting_ex3_easy.GroupName like '"+GroupName+"'",null);
        cursor.moveToFirst();

        Cursor Check = db.rawQuery("SELECT Score_ex3_easy.UserID,Setting_ex3_easy.Groupname " +
                "from Score_ex3_easy LEFT JOIN Setting_ex3_easy on (Score_ex3_easy.st_ex3_easy_id = Setting_ex3_easy.st_ex3_easy_id) " +
                "where Score_ex3_easy.UserID = '"+ UserID +"' AND Setting_ex3_easy.Groupname = '"+ GroupName +"'",null); //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง

        ContentValues Val = new ContentValues();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));

            if (Check.getCount() == 0) { //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง
                Val.put("st_ex3_easy_id", cursor.getString(1));
                Val.put("Score", 0);
                Val.put("UserID", UserID);
                long rows = db.insert("Score_ex3_easy", null, Val);
            }
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> ex3_normal_game_st(String GroupName,String UserID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Setting_ex3_normal.st_ex3_normal_id From Setting_ex3_normal INNER join Sentence ON Setting_ex3_normal.sentenceID = Sentence.sentenceID where Setting_ex3_normal.GroupName like '"+GroupName+"'",null);
        cursor.moveToFirst();

        Cursor Check = db.rawQuery("SELECT Score_ex3_normal.UserID,Setting_ex3_normal.Groupname " +
                "from Score_ex3_normal LEFT JOIN Setting_ex3_normal on (Score_ex3_normal.st_ex3_normal_id = Setting_ex3_normal.st_ex3_normal_id) " +
                "where Score_ex3_normal.UserID = '"+ UserID +"' AND Setting_ex3_normal.Groupname = '"+ GroupName +"'",null); //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง

        ContentValues Val = new ContentValues();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));

            if (Check.getCount() == 0) { //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง
                Val.put("st_ex3_normal_id", cursor.getString(1));
                Val.put("Score", 0);
                Val.put("UserID", UserID);
                long rows = db.insert("Score_ex3_normal", null, Val);
            }
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> ex3_hard_game_st(String GroupName,String UserID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> words = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Setting_ex3_hard.st_ex3_hard_id From Setting_ex3_hard " +
                "INNER join Sentence ON Setting_ex3_hard.sentenceID = Sentence.sentenceID " +
                "where Setting_ex3_hard.GroupName like '"+GroupName+"'",null);
        cursor.moveToFirst();

        Cursor Check = db.rawQuery("SELECT Score_ex3_hard.UserID,Setting_ex3_hard.Groupname " +
                "from Score_ex3_hard LEFT JOIN Setting_ex3_hard on (Score_ex3_hard.st_ex3_hard_id = Setting_ex3_hard.st_ex3_hard_id) " +
                "where Score_ex3_hard.UserID = '"+ UserID +"' AND Setting_ex3_hard.Groupname = '"+ GroupName +"'",null); //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง

        ContentValues Val = new ContentValues();
        while (!cursor.isAfterLast()){
            words.add(cursor.getString(0));

            if (Check.getCount() == 0) { //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง
                Val.put("st_ex3_hard_id", cursor.getString(1));
                Val.put("Score", 0);
                Val.put("UserID", UserID);
                long rows = db.insert("Score_ex3_hard", null, Val);
            }
            cursor.moveToNext();
        }
        db.close();
        return words;
    }

    public ArrayList<String> get_Groupname_ex2_st(String Groupname,String UserID){

        int yes = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> get_groupname = new ArrayList<>();
        Cursor cursor = db.rawQuery("select Character_ex2.Char,Setting_ex2.st_ex2_id from Setting_ex2\n" +
                "inner join Character_ex2 on Setting_ex2.choiceID = Character_ex2.choiceID\n" +
                "where Setting_ex2.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();

        Cursor Check = db.rawQuery("SELECT Score_ex2.UserID,Setting_ex2.GroupName " +
                "from Score_ex2 LEFT JOIN Setting_ex2 on (Score_ex2.st_ex2_id = Setting_ex2.st_ex2_id) " +
                "where Score_ex2.UserID = '"+ UserID +"' AND Setting_ex2.GroupName = '"+ Groupname +"'",null); //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง

        ContentValues Val = new ContentValues();
        while (!cursor.isAfterLast()){
            get_groupname.add(cursor.getString(0));
            yes++;
            if (Check.getCount() == 0){ //เช็ค user ได้ทำการเพิ่มคำศัพท์เข้าไปหรือยัง
                Val.put("st_ex2_id", cursor.getString(1));
                Val.put("Score", 0);
                Val.put("UserID", UserID);
                if(yes % 2 == 0) {
                    //is Even
                } else {
                    Val.put("this_Score","yes"); //odd
                }
                long rows = db.insert("Score_ex2", null, Val);
            }
            cursor.moveToNext();
        }
        db.close();
        return get_groupname;
    }

    public ArrayList<String> getAll_User(String select){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> User = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " +select+ " From User ORDER BY UserID",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            User.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return User;
    }

    public ArrayList<Bitmap> getAll_User_Picture(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Bitmap> Picture = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT Picture From User ORDER BY UserID",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            byte[] bytes = cursor.getBlob(0);
            if (bytes != null) {
                Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                Picture.add(bmp);
            }
            cursor.moveToNext();
        }
        db.close();
        return Picture;
    }

    public User ModifileUser(String UserID) {// check login first

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query("User", new String[]{"UserID",
                        "Username", "Password","Fullname","Age","sex","Permission","Picture"}, "UserID = " + UserID, null, null, null, "1");
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

    public void delete_user (String UserID){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("User","UserID = " + UserID,null);
        db.close();
    }

    public Score_ex3_word Score_ex3_easy(String Groupname, String UserID, String Fullname){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> Word = new ArrayList<>();
        ArrayList<Integer> Score = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT Word.word,Score_ex3_easy.Score From Score_ex3_easy \n" +
                "LEFT JOIN Setting_ex3_easy on (Score_ex3_easy.st_ex3_easy_id = Setting_ex3_easy.st_ex3_easy_id)\n" +
                "LEFT JOIN Word on (Setting_ex3_easy.wordID = Word.wordID)\n" +
                "LEFT JOIN User on (Score_ex3_easy.UserID = User.UserID)\n" +
                "where Setting_ex3_easy.GroupName = '"+Groupname+"' and  Score_ex3_easy.UserID = '"+UserID +"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Word.add(cursor.getString(0));
            Score.add(cursor.getInt(1));
            cursor.moveToNext();
        }
        Score_ex3_word Score_Word = new Score_ex3_word(Fullname,Word,Score,UserID);
        db.close();
        return Score_Word;
    }

    public Score_ex3_word Score_ex3_normal (String Groupname,String UserID,String Fullname){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> Word = new ArrayList<>();
        ArrayList<Integer> Score = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Score_ex3_normal.Score From Score_ex3_normal \n" +
                "LEFT JOIN Setting_ex3_normal on (Score_ex3_normal.st_ex3_normal_id = Setting_ex3_normal.st_ex3_normal_id)\n" +
                "LEFT JOIN Sentence on (Setting_ex3_normal.sentenceID = Sentence.sentenceID)\n" +
                "LEFT JOIN User on (Score_ex3_normal.UserID = User.UserID)\n" +
                "where Setting_ex3_normal.GroupName = '"+Groupname+"' and  Score_ex3_normal.UserID = '"+UserID +"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Word.add(cursor.getString(0));
            Score.add(cursor.getInt(1));
            cursor.moveToNext();
        }
        Score_ex3_word Score_Word = new Score_ex3_word(Fullname,Word,Score,UserID);
        db.close();
        return Score_Word;
    }

    public Score_ex3_word Score_ex3_hard (String Groupname,String UserID,String Fullname){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> Word = new ArrayList<>();
        ArrayList<Integer> Score = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Score_ex3_hard.Score From Score_ex3_hard \n" +
                "LEFT JOIN Setting_ex3_hard on (Score_ex3_hard.st_ex3_hard_id = Setting_ex3_hard.st_ex3_hard_id)\n" +
                "LEFT JOIN Sentence on (Setting_ex3_hard.sentenceID = Sentence.sentenceID)\n" +
                "LEFT JOIN User on (Score_ex3_hard.UserID = User.UserID)\n" +
                "where Setting_ex3_hard.GroupName = '"+Groupname+"' and  Score_ex3_hard.UserID = '"+UserID +"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Word.add(cursor.getString(0));
            Score.add(cursor.getInt(1));
            cursor.moveToNext();
        }
        Score_ex3_word Score_Word = new Score_ex3_word(Fullname,Word,Score,UserID);
        db.close();
        return Score_Word;
    }

    public Score_ex3_word Score_ex2 (String Groupname,String UserID,String Fullname){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> Word = new ArrayList<>();
        ArrayList<Integer> Score = new ArrayList<>();

        Cursor cursor = db.rawQuery("select Character_ex2.Char,Score_ex2.Score From Score_ex2 \n" +
                "LEFT JOIN Setting_ex2 on (Score_ex2.st_ex2_id = Setting_ex2.st_ex2_id)\n" +
                "LEFT JOIN Character_ex2 on (Setting_ex2.choiceID = Character_ex2.choiceID)\n" +
                "LEFT JOIN User on (Score_ex2.UserID = User.UserID)\n" +
                "where Setting_ex2.GroupName = '"+Groupname+"' and  Score_ex2.UserID = '"+UserID+"' and Score_ex2.this_Score = 'yes'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Word.add(cursor.getString(0));
            Score.add(cursor.getInt(1));
            cursor.moveToNext();
        }
        Score_ex3_word Score_Word = new Score_ex3_word(Fullname,Word,Score,UserID);
        db.close();
        return Score_Word;
    }

    public void update_score_ex2(String UserID, int Score, String stID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        //Val.put("UserID",UserID);
        Val.put("Score",Score);
        long rows = db.update("Score_ex2",Val, "st_ex2_id='" + stID + "' and UserID = '"+ UserID + "'", null);
        db.close();
    }

    public String Find_stID_Char(String Char,String Groupname){
        SQLiteDatabase db = this.getReadableDatabase();
        String stID = null;
        Cursor cursor = db.rawQuery("SELECT\n" +
                "    Setting_ex2.st_ex2_id\n" +
                "FROM\n" +
                "    Setting_ex2\n" +
                "INNER JOIN \n" +
                "\tCharacter_ex2 ON Setting_ex2.choiceID=Character_ex2.choiceID\n" +
                "where Character_ex2.char = '" +Char+"' and Setting_ex2.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        stID = cursor.getString(0);

        db.close();
        return stID;
    }

    public String Find_stID_word(String word,String Groupname){
        SQLiteDatabase db = this.getReadableDatabase();
        String stID = null;
        Cursor cursor = db.rawQuery("SELECT\n" +
                "    Setting_ex3_easy.st_ex3_easy_id\n" +
                "FROM\n" +
                "    Setting_ex3_easy\n" +
                "INNER JOIN \n" +
                "\tWord ON Setting_ex3_easy.wordID=Word.wordID\n" +
                "where Word.word = '" +word+"' and Setting_ex3_easy.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
            stID = cursor.getString(0);

        db.close();
        return stID;
    }

    public String Find_stID_sentence(String word,String Groupname,String Table,String ID){
        SQLiteDatabase db = this.getReadableDatabase();
        String stID = null;
        Cursor cursor = db.rawQuery("SELECT\n" +
                "    "+Table+"."+ID+"\n" +
                "FROM\n" +
                "    "+Table+"\n" +
                "INNER JOIN \n" +
                "\tSentence ON "+Table+".sentenceID=Sentence.sentenceID\n" +
                "where Sentence.sentence = '" +word+"' and "+Table+".GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        stID = cursor.getString(0);

        db.close();
        return stID;
    }



    public void update_score_ex3_easy(String UserID, int Score, String stID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        //Val.put("UserID",UserID);
        Val.put("Score",Score);
        long rows = db.update("Score_ex3_easy",Val, "st_ex3_easy_id=" + stID + " and UserID = "+ UserID, null);
        db.close();
    }

    public void update_score_ex3_normal (String UserID,int Score,String stID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        //Val.put("UserID",UserID);
        Val.put("Score",Score);
        long rows = db.update("Score_ex3_normal",Val, "st_ex3_normal_id=" + stID + " and UserID = "+ UserID, null);
        db.close();
    }

    public void update_score_ex3_hard (String UserID,int Score,String stID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        //Val.put("UserID",UserID);
        Val.put("Score",Score);
        long rows = db.update("Score_ex3_hard",Val, "st_ex3_hard_id=" + stID + " and UserID = "+ UserID, null);
        db.close();
    }

    public void delete_word (String word,String table,String column){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(table,column + " = '" + word +"'",null);
        db.close();
    }

    public void update_word (String word,String mod,String table,String column){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        Val.put("word",mod);
        try {
            long rows = db.update(table,Val, column + " = '" + word +"'", null);
        } catch (Exception e){
            Toast.makeText(context,"มีคำในฐานข้อมูลแล้ว",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void AddWord (String word,String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Val = new ContentValues();
        Val.put("word",word);
        try {
            long rows = db.insertOrThrow(table, null, Val);
        } catch (Exception e) {
            Toast.makeText(context,"มีคำในฐานข้อมูลแล้ว",Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public ArrayList<Ranking_Item> rank_ex2 (String group){
        Ranking_Item rankingItem = null;
        ArrayList<Ranking_Item> ranking_item = new ArrayList<>();
        int rank=1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select User.Fullname,avg(Score) from Score_ex2 \n" +
                "LEFT JOIN Setting_ex2 on (Score_ex2.st_ex2_id = Setting_ex2.st_ex2_id)\n" +
                "LEFT JOIN User on (Score_ex2.UserID = User.UserID)\n" +
                "where Setting_ex2.GroupName = '" + group +"' and Score_ex2.this_Score = 'yes'\n" +
                "Group by Score_ex2.UserID ORDER by sum(Score) desc\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            String a = cursor.getString(0);
            String[] arrB = a.split(" ");
            ArrayList<String> Firstname = new ArrayList(Arrays.asList(arrB));

            rankingItem = new Ranking_Item(String.valueOf(rank),Firstname.get(0),cursor.getString(1));
            ranking_item.add(rankingItem);
            rank++;
            cursor.moveToNext();
        }
        db.close();
        return ranking_item;
    }

    public ArrayList<Ranking_Item> rank_ex3_easy (String group){
        Ranking_Item rankingItem = null;
        ArrayList<Ranking_Item> ranking_item = new ArrayList<>();
        int rank=1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select User.Fullname,avg(Score) from Score_ex3_easy \n" +
                "LEFT JOIN Setting_ex3_easy on (Score_ex3_easy.st_ex3_easy_id = Setting_ex3_easy.st_ex3_easy_id)\n" +
                "LEFT JOIN User on (Score_ex3_easy.UserID = User.UserID)\n" +
                "where Setting_ex3_easy.GroupName = '" + group +"'\n" +
                "Group by Score_ex3_easy.UserID ORDER by sum(Score) desc\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            String a = cursor.getString(0);
            String[] arrB = a.split(" ");
            ArrayList<String> Firstname = new ArrayList(Arrays.asList(arrB));

            rankingItem = new Ranking_Item(String.valueOf(rank),Firstname.get(0),cursor.getString(1));
            ranking_item.add(rankingItem);
            rank++;
            cursor.moveToNext();
        }
        db.close();
        return ranking_item;
    }

    public ArrayList<Ranking_Item> rank_ex3_normal (String group){
        Ranking_Item rankingItem = null;
        ArrayList<Ranking_Item> ranking_item = new ArrayList<>();
        int rank=1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select User.Fullname,avg(Score) from Score_ex3_normal \n" +
                "LEFT JOIN Setting_ex3_normal on (Score_ex3_normal.st_ex3_normal_id = Setting_ex3_normal.st_ex3_normal_id)\n" +
                "LEFT JOIN User on (Score_ex3_normal.UserID = User.UserID)\n" +
                "where Setting_ex3_normal.GroupName = '" + group +"'\n" +
                "Group by Score_ex3_normal.UserID ORDER by sum(Score) desc\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            String a = cursor.getString(0);
            String[] arrB = a.split(" ");
            ArrayList<String> Firstname = new ArrayList(Arrays.asList(arrB));

            rankingItem = new Ranking_Item(String.valueOf(rank),Firstname.get(0),cursor.getString(1));
            ranking_item.add(rankingItem);
            rank++;
            cursor.moveToNext();
        }
        db.close();
        return ranking_item;
    }

    public ArrayList<Ranking_Item> rank_ex3_hard (String group){
        Ranking_Item rankingItem = null;
        ArrayList<Ranking_Item> ranking_item = new ArrayList<>();
        int rank=1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select User.Fullname,avg(Score) from Score_ex3_hard \n" +
                "LEFT JOIN Setting_ex3_hard on (Score_ex3_hard.st_ex3_hard_id = Setting_ex3_hard.st_ex3_hard_id)\n" +
                "LEFT JOIN User on (Score_ex3_hard.UserID = User.UserID)\n" +
                "where Setting_ex3_hard.GroupName = '" + group +"'\n" +
                "Group by Score_ex3_hard.UserID ORDER by sum(Score) desc\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            rankingItem = new Ranking_Item(String.valueOf(rank),cursor.getString(0),cursor.getString(1));
            ranking_item.add(rankingItem);
            rank++;
            cursor.moveToNext();
        }
        db.close();
        return ranking_item;
    }

    public ArrayList<String> SearchGroupName_ex2(String UserID){
        ArrayList Groupname = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Setting_ex2.GroupName , COUNT(*) count FROM Score_ex2 \n" +
                "LEFT JOIN Setting_ex2 on (Score_ex2.st_ex2_id = Setting_ex2.st_ex2_id)\n" +
                "where Score_ex2.UserID = '"+UserID+"' GROUP BY [GroupName] Having COUNT(*) > 1\n" +
                "ORDER by Setting_ex2.st_ex2_id\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Groupname.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return Groupname;
    }

    public ArrayList<String> SearchGroupName_easy(String UserID){
        ArrayList Groupname = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Setting_ex3_easy.GroupName , COUNT(*) count FROM Score_ex3_easy \n" +
                "LEFT JOIN Setting_ex3_easy on (Score_ex3_easy.st_ex3_easy_id = Setting_ex3_easy.st_ex3_easy_id)\n" +
                "where Score_ex3_easy.UserID = '"+UserID+"' GROUP BY [GroupName] Having COUNT(*) > 1\n" +
                "ORDER by Setting_ex3_easy.st_ex3_easy_id\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Groupname.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return Groupname;
    }

    public ArrayList<String> SearchGroupName_normal(String UserID){
        ArrayList Groupname = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Setting_ex3_normal.GroupName , COUNT(*) count FROM Score_ex3_normal \n" +
                "LEFT JOIN Setting_ex3_normal on (Score_ex3_normal.st_ex3_normal_id = Setting_ex3_normal.st_ex3_normal_id)\n" +
                "where Score_ex3_normal.UserID = '"+UserID+"' GROUP BY [GroupName] Having COUNT(*) > 1\n" +
                "ORDER by Setting_ex3_normal.st_ex3_normal_id\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Groupname.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return Groupname;
    }

    public ArrayList<String> SearchGroupName_hard(String UserID){
        ArrayList Groupname = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Setting_ex3_hard.GroupName , COUNT(*) count FROM Score_ex3_hard \n" +
                "LEFT JOIN Setting_ex3_hard on (Score_ex3_hard.st_ex3_hard_id = Setting_ex3_hard.st_ex3_hard_id)\n" +
                "where Score_ex3_hard.UserID = '"+UserID+"' GROUP BY [GroupName] Having COUNT(*) > 1\n" +
                "ORDER by Setting_ex3_hard.st_ex3_hard_id\n",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Groupname.add(cursor.getString(0));
            cursor.moveToNext();
        }
        db.close();
        return Groupname;
    }

    public ArrayList<HorizontalModel> item_word_Ranking_ex2(String userID, String Groupname){
        ArrayList<HorizontalModel> horizontalModel_return = new ArrayList<>();
        HorizontalModel horizontalModel = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Character_ex2.Char,Score_ex2.Score\n" +
                "From Score_ex2\n" +
                "LEFT JOIN Setting_ex2 on (Score_ex2.st_ex2_id = Setting_ex2.st_ex2_id)\n" +
                "LEFT JOIN Character_ex2 on (Setting_ex2.choiceID = Character_ex2.choiceID)\n" +
                "where Score_ex2.UserID = '"+userID+"' and Setting_ex2.GroupName = '"+Groupname+"' and Score_ex2.this_Score = 'yes'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            horizontalModel = new HorizontalModel(cursor.getString(0),cursor.getString(1));
            horizontalModel_return.add(horizontalModel);
            cursor.moveToNext();
        }
        db.close();
        return horizontalModel_return;
    }

    public ArrayList<HorizontalModel> item_word_Ranking_easy(String userID, String Groupname){
        ArrayList<HorizontalModel> horizontalModel_return = new ArrayList<>();
        HorizontalModel horizontalModel = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Word.word,Score_ex3_easy.Score \n" +
                "From Score_ex3_easy\n" +
                "LEFT JOIN Setting_ex3_easy on (Score_ex3_easy.st_ex3_easy_id = Setting_ex3_easy.st_ex3_easy_id)\n" +
                "LEFT JOIN Word on (Setting_ex3_easy.wordID = Word.wordID)\n" +
                "where Score_ex3_easy.UserID = "+userID+" and Setting_ex3_easy.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            horizontalModel = new HorizontalModel(cursor.getString(0),cursor.getString(1));
            horizontalModel_return.add(horizontalModel);
            cursor.moveToNext();
        }

        db.close();
        return horizontalModel_return;
    }

    public ArrayList<HorizontalModel> item_word_Ranking_normal(String userID, String Groupname){
        ArrayList<HorizontalModel> horizontalModel_return = new ArrayList<>();
        HorizontalModel horizontalModel = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Score_ex3_normal.Score \n" +
                "From Score_ex3_normal\n" +
                "LEFT JOIN Setting_ex3_normal on (Score_ex3_normal.st_ex3_normal_id = Setting_ex3_normal.st_ex3_normal_id)\n" +
                "LEFT JOIN Sentence on (Setting_ex3_normal.sentenceID = Sentence.sentenceID)\n" +
                "where Score_ex3_normal.UserID = "+userID+" and Setting_ex3_normal.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            horizontalModel = new HorizontalModel(cursor.getString(0),cursor.getString(1));
            horizontalModel_return.add(horizontalModel);
            cursor.moveToNext();
        }

        db.close();
        return horizontalModel_return;
    }

    public ArrayList<HorizontalModel> item_word_Ranking_hard(String userID, String Groupname){
        ArrayList<HorizontalModel> horizontalModel_return = new ArrayList<>();
        HorizontalModel horizontalModel = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Sentence.sentence,Score_ex3_hard.Score \n" +
                "From Score_ex3_hard\n" +
                "LEFT JOIN Setting_ex3_hard on (Score_ex3_hard.st_ex3_hard_id = Setting_ex3_hard.st_ex3_hard_id)\n" +
                "LEFT JOIN Sentence on (Setting_ex3_hard.sentenceID = Sentence.sentenceID)\n" +
                "where Score_ex3_hard.UserID = "+userID+" and Setting_ex3_hard.GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            horizontalModel = new HorizontalModel(cursor.getString(0),cursor.getString(1));
            horizontalModel_return.add(horizontalModel);
            cursor.moveToNext();
        }

        db.close();
        return horizontalModel_return;
    }

    public Character character(String Character){
        Character Char = null;
        String Image = null;
        String Correct = null;
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //ดึงข้อมูลรูปภาพกับคำถามที่ผิด
        Cursor cursor = db.rawQuery("select Image,Image_char,Anser FROM Character_ex2\n" +
                "where Char = '"+Character+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Image = cursor.getString(0);
            arrayList.add(cursor.getString(1));
            Correct = cursor.getString(2);
            cursor.moveToNext();
        }

        db.close();
        Char = new Character(Image,arrayList,Correct);
        return Char;
    }

    public Item_st_ex2 item_st_ex2(String Char){
        ArrayList<String> get_char = new ArrayList<>();
        String Character = null;
        Item_st_ex2 item_st_ex2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT char,Image_char from Character_ex2 where char = '"+Char+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            get_char.add(cursor.getString(1));
            Character = cursor.getString(0);
            cursor.moveToNext();
        }
        db.close();
        item_st_ex2 = new Item_st_ex2(Character,get_char.get(0),get_char.get(1),get_char.get(2));
        return item_st_ex2;
    }

    public void insert_char(String image_name,String Groupname){
        SQLiteDatabase db = this.getWritableDatabase();
        String choiceID = null;

        Cursor cursor = db.rawQuery("SELECT choiceID from Character_ex2 where Image_char = '"+image_name+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            choiceID = cursor.getString(0);
            cursor.moveToNext();
        }
        ContentValues Val = new ContentValues();
        Val.put("choiceID",choiceID);
        Val.put("GroupName", Groupname);
        long rows = db.insert("Setting_ex2", null, Val);
        db.close();
    }

    public ArrayList<ex2_st_object> export_ex2(String Groupname){
        ArrayList<ex2_st_object> return_object = new ArrayList<>();
        ex2_st_object object = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select choiceID,GroupName from Setting_ex2 where GroupName = '"+Groupname+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            object = new ex2_st_object(cursor.getString(0),cursor.getString(1));
            return_object.add(object);
            cursor.moveToNext();
        }
        db.close();
        return return_object;
    }

    public void Import_ex2(String choiceID,String GroupName){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Val = new ContentValues(); //insert data
            Val.put("choiceID", choiceID);
            Val.put("GroupName", GroupName);
            long rows = db.insert("Setting_ex2", null, Val);
            db.close();
    }

    public String check_groupname_ex2(String GroupName){
        SQLiteDatabase db = this.getReadableDatabase();
        String check_group = null;
        Cursor cursor = db.rawQuery("select GroupName from Setting_ex2 where GroupName = '"+GroupName+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            check_group = cursor.getString(0);
            cursor.moveToNext();
        }
        return check_group;
    }
}
