package com.example.projectld.Export_Import;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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

public class Export_menu extends AppCompatActivity {

    Button ex2,ex3,ex4,ex5;

    Export_adapter export_adapter;
    ArrayList<String> GroupName = new ArrayList<>();
    ArrayList<String> Select_Group = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_menu);

        final Dialog dialog = new Dialog(this);

        ex2 = findViewById(R.id.ex2);
        ex3 = findViewById(R.id.ex3);
        ex4 = findViewById(R.id.ex4);
        ex5 = findViewById(R.id.ex5);

        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex2.class);
                startActivity(intent);
            }
        });

        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Export_ex3.class);
//                startActivity(intent);

                dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                dialog.setContentView(R.layout.export_popup);

                databaseHelper = new DatabaseHelper(getApplicationContext());
                GroupName = databaseHelper.GetGroupname("Setting_ex3_easy","st_ex3_easy_id");

                export_adapter = new Export_adapter(getApplicationContext(),GroupName);

                RecyclerView recyclerView = dialog.findViewById(R.id.Export_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(export_adapter);

                Button close = dialog.findViewById(R.id.close);
                Button export = dialog.findViewById(R.id.export);
                export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (export_adapter.Check_Group.size() > 0) {
                            Select_Group.addAll(export_adapter.Check_Group);
                        } else {
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                        }
                        export_ex3(v,Select_Group);
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex4.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Export_ex5.class);
                startActivity(intent);
            }
        });
    }

    public void export_ex3(View view, ArrayList<String> GroupName){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<Import_object> objects = new ArrayList<>();

        //generate data
        StringBuilder data = new StringBuilder();
        data.append("ex3");
        data.append("\n"+"WordID,GroupName");

        for (int i=0;i<GroupName.size();i++){
            objects = databaseHelper.export_ex3(GroupName.get(i));
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
