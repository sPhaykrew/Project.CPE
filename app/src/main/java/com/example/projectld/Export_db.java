package com.example.projectld;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectld.Export_Import.ex2_st_object;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        String path = String.valueOf(data.getData());
        Log.e("Pathb",path);
        proImportCSV(Uri.parse(path));
    }

    public void export(View view){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<ex2_st_object> objects = new ArrayList<>();

        objects = databaseHelper.export_ex2("test");

        //generate data
        StringBuilder data = new StringBuilder();
        data.append("ex2");
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
            ContentValues cv = new ContentValues();
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String gg = null;
            String[] import_data;
            int Check_First_Loop = 1;

            while((import_data = dataRead.readNext())!=null) {
            String groupname = databaseHelper.check_groupname_ex2(import_data[1]);
                if (Check_First_Loop != 1) { //รอบแรกคือชื่อ choice,groupname เลยไม่ต้องเพิ่ม
                    if (!import_data[1].equals(groupname)) {
                        databaseHelper.Import_ex2(import_data[0], import_data[1]);
                    } else {
                        Toast.makeText(this,"ชื่อแบบทดสอบเหมือนกัน กรุณาเปลี่ยนชื่อแบบทดสอบในเครื่อง",Toast.LENGTH_SHORT).show();
                    }
                }
                Check_First_Loop++;
                cv.clear();
            }

            dataRead.close();
        }
        catch (Exception e) { Log.e("TAG",e.toString());

        }
    }

}
