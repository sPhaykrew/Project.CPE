package com.example.projectld.Export_Import;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Import_db extends AppCompatActivity {

    public static final int requestcode = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_db);

        Button import_db = findViewById(R.id.import_db);

        import_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/*");
                startActivityForResult(intent,requestcode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert data != null;
        String path = String.valueOf(data.getData());
        Log.e("Pathb",path);
        import_ex2(Uri.parse(path));
    }

    public void import_ex2 (Uri uri){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<String> GroupName = new ArrayList<>();

        try{
            // ContentValues cv = new ContentValues();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String[] import_data;
            String Ex = null;
            String get_Groupname = null;

            ArrayList<Import_object> object_ex2 = new ArrayList<>();
            Import_object Import_object;

            int is_First = 0; //เอาไว้เช็คการทำงานครั่งแรก

            while((import_data = dataRead.readNext())!=null) { //save data in object array

                if (is_First == 0) { //เช็คว่าทำการครั่งแรกใช่หรือไม่ เพื่อเก็บ get_groupname ซึ่งคือ ex2
                    Ex = import_data[0];
                    is_First++;
                }
                if (is_First >= 3){ //รอบแรกคือชื่อ ex2 รอบแรกสองคือ ชื่อคอลัม เช่น choiceID,groupname
                    Import_object = new Import_object(import_data[0],import_data[1]);
                    object_ex2.add(Import_object);

                    GroupName.add(import_data[1]); //เก็บ groupname ไว้ใน array จากนนั้นจะทำการลบคำซ้ำ

                    //cv.clear();
                } else {
                    is_First ++;
                }
            }
            Set<String> set = new HashSet<>(GroupName); //ลบชื่อกลุ่มซ้ำออก
            GroupName.clear();
            GroupName.addAll(set);
            boolean Check_Group = false;

            switch (Ex){

                case "ex2" :

                    for (int i=0;i<GroupName.size();i++){ //ตรวจสอบว่ามีชื่อกลุ่มซ้ำกันไหม
                        get_Groupname = databaseHelper.check_groupname_import(object_ex2.get(1).getGroupname(),"Setting_ex2"); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่
                        if (get_Groupname == null){
                            Check_Group = true;
                        }
                    }

                    if (Check_Group){
                        for (int i=0;i<object_ex2.size();i++){
                            String choice = object_ex2.get(i).getID(); //get choiceID from table setting2
                            String groupname = object_ex2.get(i).getGroupname(); //get groupname from table setting2
                            databaseHelper.Import_ex2(choice,groupname);
                            Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
                    }
                    dataRead.close();
                    break;

                case "ex3" :

                    for (int i=0;i<GroupName.size();i++){ //ตรวจสอบว่ามีชื่อกลุ่มซ้ำกันไหม
                        get_Groupname = databaseHelper.check_groupname_import(object_ex2.get(1).getGroupname(),"Setting_ex3_easy"); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่
                        if (get_Groupname == null){
                            Check_Group = true;
                        }
                    }

                    if (Check_Group){
                        for (int i=0;i<object_ex2.size();i++){
                            String choice = object_ex2.get(i).getID(); //get choiceID from table setting2
                            String groupname = object_ex2.get(i).getGroupname(); //get groupname from table setting2
                            databaseHelper.Import_ex3(choice,groupname);
                            Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
                    }
                    dataRead.close();
                    break;

                case "ex4" :

                    for (int i=0;i<GroupName.size();i++){ //ตรวจสอบว่ามีชื่อกลุ่มซ้ำกันไหม
                        get_Groupname = databaseHelper.check_groupname_import(object_ex2.get(1).getGroupname(),"Setting_ex3_normal"); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่
                        if (get_Groupname == null){
                            Check_Group = true;
                        }
                    }

                    if (Check_Group){
                        for (int i=0;i<object_ex2.size();i++){
                            String choice = object_ex2.get(i).getID(); //get choiceID from table setting2
                            String groupname = object_ex2.get(i).getGroupname(); //get groupname from table setting2
                            databaseHelper.Import_ex4(choice,groupname);
                            Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
                    }
                    dataRead.close();
                    break;

                case "ex5" :

                    for (int i=0;i<GroupName.size();i++){ //ตรวจสอบว่ามีชื่อกลุ่มซ้ำกันไหม
                        get_Groupname = databaseHelper.check_groupname_import(object_ex2.get(1).getGroupname(),"Setting_ex3_hard"); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่
                        if (get_Groupname == null){
                            Check_Group = true;
                        }
                    }

                    if (Check_Group){
                        for (int i=0;i<object_ex2.size();i++){
                            String choice = object_ex2.get(i).getID(); //get choiceID from table setting2
                            String groupname = object_ex2.get(i).getGroupname(); //get groupname from table setting2
                            databaseHelper.Import_ex5(choice,groupname);
                            Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
                    }
                    dataRead.close();
                    break;
            }
        }
        catch (Exception e) { Log.e("TAG",e.toString());
        }

    }

}
