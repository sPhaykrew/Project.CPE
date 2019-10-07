package com.example.projectld;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database_Meaning extends SQLiteAssetHelper {

    public Database_Meaning(Context context) {
        super(context, "lexitron_v3.db", null,3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + "User");
        // Create tables again
        onCreate(db);
    }

    public String Meaning (String word){
        SQLiteDatabase db = this.getReadableDatabase();
        String Meaning = null;
        Cursor cursor = db.rawQuery("Select sdef From lexitron where sentry = '"+word+"'" ,null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0 ) {
            Meaning = "-";
        } else {
            Meaning = (cursor.getString(0));
        }
        db.close();
        return Meaning;
    }

}
