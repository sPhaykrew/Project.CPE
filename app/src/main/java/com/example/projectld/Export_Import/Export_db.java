package com.example.projectld.Export_Import;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Export_db extends AppCompatActivity {

    public static final int requestcode = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_db);

        Button button = findViewById(R.id.export);
        Button Import = findViewById(R.id.Import);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export(v);
            }
        });

        Import.setOnClickListener(new View.OnClickListener() {
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

    public void export(View view){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<ex2_st_object> objects = new ArrayList<>();

        objects = databaseHelper.export_ex2("test");

        //generate data
        StringBuilder data = new StringBuilder();
        data.append("ex2");
        data.append("\n"+"choiceID,GroupName");
        for(int i = 0; i<objects.size(); i++){
            data.append("\n"+objects.get(i).getChoice()+","+objects.get(i).getGroupname());
        }

        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            //OutputStream out = new FileOutputStream("data");
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.projectld.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void proImportCSV(Uri uri) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        try{
           // ContentValues cv = new ContentValues();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String[] import_data;

            ArrayList<ex2_st_object> object_ex2 = new ArrayList<>();
            ex2_st_object ex2_st_object;

            while((import_data = dataRead.readNext())!=null) { //save data in object array
                ex2_st_object = new ex2_st_object(import_data[0],import_data[1]);
                object_ex2.add(ex2_st_object);
                //cv.clear();
            }

            String check_group = databaseHelper.check_groupname_ex2(object_ex2.get(1).getGroupname()); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่

            if (check_group == null){
                for (int i=1;i<object_ex2.size();i++){
                    String choice = object_ex2.get(i).getChoice();
                    String groupname = object_ex2.get(i).getGroupname();
                    databaseHelper.Import_ex2(choice,groupname);
                    Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
            }
            dataRead.close();
        }
        catch (Exception e) { Log.e("TAG",e.toString());

        }
    }

    public void import_ex2 (Uri uri){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        try{
            // ContentValues cv = new ContentValues();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String[] import_data;
            String get_groupname = null;
            String check_group = null;

            ArrayList<ex2_st_object> object_ex2 = new ArrayList<>();
            ex2_st_object ex2_st_object; int w=0;

            int is_First = 0; //เอาไว้เช็คการทำงานครั่งแรก

            while((import_data = dataRead.readNext())!=null) { //save data in object array

                if (is_First == 0) { //เช็คว่าทำการครั่งแรกใช่หรือไม่ เพื่อเก็บ get_groupname ซึ่งคือ ex2
                    get_groupname = import_data[0];
                    is_First++;
                }

                Log.d("get_groupname",get_groupname);
                switch (get_groupname) {

                    case "ex2" :

                        Log.d("ee", import_data[0]);

                        if (is_First >= 3){ //รอบแรกคือชื่อ ex2 รอบแรกสองคือ ชื่อคอลัม เช่น choiceID,groupname
                            ex2_st_object = new ex2_st_object(import_data[0],import_data[1]);
                            object_ex2.add(ex2_st_object);

                            Log.d("www", import_data[0]);
                            w++;

                            //cv.clear();
                        } else {
                            is_First ++;
                        }
                        break;

                    case "ex3" : break;
                    case "ex4" : break;
                }
            }

            switch (get_groupname){

                case "ex2" :

                    check_group = databaseHelper.check_groupname_ex2(object_ex2.get(1).getGroupname()); //เช็คว่าชื่อ groupname ซ่ำกับในแอปหรือไม่

                    if (check_group == null){
                        for (int i=0;i<object_ex2.size();i++){
                            String choice = object_ex2.get(i).getChoice(); //get choiceID from table setting2
                            String groupname = object_ex2.get(i).getGroupname(); //get groupname from table setting2
                            databaseHelper.Import_ex2(choice,groupname);
                            Toast.makeText(this,"นำข้อมูลเข้าเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"แบบทดสอบชื่อซ้ำกัน กรุณาเปลี่ยนชื่อแบบทดสอบในแอปพลิเคชัน",Toast.LENGTH_SHORT).show();
                    }
                    dataRead.close();
                    break;

                case "ex3" : break;
                case "ex4" : break;

            }
        }
        catch (Exception e) { Log.e("TAG",e.toString());
        }

    }

}
