package com.example.projectld.Export_Import;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Export_ex5 extends AppCompatActivity {

    Export_adapter export_adapter;
    ArrayList<String> GroupName = new ArrayList<>();
    ArrayList<String> Select_Group = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_db);

        databaseHelper = new DatabaseHelper(this);
        GroupName = databaseHelper.GetGroupname("Setting_ex3_hard","st_ex3_hard_id");

        export_adapter = new Export_adapter(this,GroupName);

        RecyclerView recyclerView = findViewById(R.id.Export_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(export_adapter);

        Button button = findViewById(R.id.export);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (export_adapter.Check_Group.size() > 0) {
                    for (String a : export_adapter.Check_Group){
                        Select_Group.add(a);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                }
                export(v,Select_Group);
            }
        });
    }

    public void export(View view,ArrayList<String> GroupName){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<Import_object> objects = new ArrayList<>();

        //generate data
        StringBuilder data = new StringBuilder();
        data.append("ex5");
        data.append("\n"+"SentenceID,GroupName");

        for (int i=0;i<GroupName.size();i++){
            objects = databaseHelper.export_ex5(GroupName.get(i));
            for(int j = 0; j<objects.size(); j++){
                data.append("\n"+objects.get(j).getID()+","+objects.get(j).getGroupname());
            }
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
            startActivity(Intent.createChooser(fileIntent, "นำข้อมูลออกเสร็จสิ้น"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
