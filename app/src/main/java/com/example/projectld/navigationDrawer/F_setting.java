package com.example.projectld.navigationDrawer;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.Export_Import.Export_Import;
import com.example.projectld.Export_Import.Export_adapter;
import com.example.projectld.R;
import com.example.projectld.exercise2.st_ex2_adapter.st_ex2_menu;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_menu;
import com.example.projectld.exercise3.st_hard.st_ex3_hard_menu;
import com.example.projectld.exercise3.st_nomal.st_ex3_normal_menu;

import java.util.ArrayList;

public class F_setting extends AppCompatActivity {

    Button ex2,ex3,ex4,ex5,Add_Word,Add_Sentence,import_db,export_ex2,export_ex3,export_ex4,export_ex5;

    Export_adapter export_adapter;
    ArrayList<String> GroupName = new ArrayList<>();
    ArrayList<String> Select_Group = new ArrayList<>();
    DatabaseHelper databaseHelper;
    EditText export_name;
    TextView textView;
    Dialog dialog;
    Export_Import export_import;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("ตั้งค่า");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        ImageView show_menu = toolbar.findViewById(R.id.show_menu);
        show_menu.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ex2 = findViewById(R.id.ex2);
        ex3 = findViewById(R.id.ex3);
        ex4 = findViewById(R.id.ex4);
        ex5 = findViewById(R.id.ex5);
        Add_Word = findViewById(R.id.Add_Word);
        Add_Sentence = findViewById(R.id.Add_Sentence);
        import_db = findViewById(R.id.Import_db);
        export_ex2 = findViewById(R.id.Export_ex2);
        export_ex3 = findViewById(R.id.Export_ex3);
        export_ex4 = findViewById(R.id.Export_ex4);
        export_ex5 = findViewById(R.id.Export_ex5);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        dialog = new Dialog(this);
        export_import = new Export_Import(this);

        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex2_menu.class);
                startActivity(intent);
            }
        });


        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_easy_menu.class);
                startActivity(intent);
            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_normal_menu.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), st_ex3_hard_menu.class);
                startActivity(intent);
            }
        });

        Add_Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.projectld.Add_Word.class);
                startActivity(intent);
            }
        });

        Add_Sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.projectld.Add_Sentence.class);
                startActivity(intent);
            }
        });

        export_ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                dialog.setContentView(R.layout.export_popup);

                GroupName = databaseHelper.GetGroupname("Setting_ex2","st_ex2_id");
                export_adapter = new Export_adapter(getApplicationContext(),GroupName);

                RecyclerView recyclerView = dialog.findViewById(R.id.Export_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(export_adapter);

                export_name = dialog.findViewById(R.id.export_name);
                textView = dialog.findViewById(R.id.text);
                Button close = dialog.findViewById(R.id.close);
                Button export = dialog.findViewById(R.id.export);

                if (GroupName.size() == 0){
                    export_name.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.GONE);
                }

                export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (export_adapter.Check_Group.size() != 0) {
                            if (export_name.getText().length() > 0) {
                                Select_Group.addAll(export_adapter.Check_Group);
                                export_import.export_ex2(Select_Group, export_name.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ชื่อนำออกข้อมูล",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                        }
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

        export_ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                dialog.setContentView(R.layout.export_popup);

                GroupName = databaseHelper.GetGroupname("Setting_ex3_easy","st_ex3_easy_id");
                export_adapter = new Export_adapter(getApplicationContext(),GroupName);

                RecyclerView recyclerView = dialog.findViewById(R.id.Export_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(export_adapter);

                export_name = dialog.findViewById(R.id.export_name);
                textView = dialog.findViewById(R.id.text);
                Button close = dialog.findViewById(R.id.close);
                Button export = dialog.findViewById(R.id.export);

                if (GroupName.size() == 0){
                    export_name.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.GONE);
                }

                export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (export_adapter.Check_Group.size() != 0) {
                            if (export_name.getText().length() > 0) {
                                Select_Group.addAll(export_adapter.Check_Group);
                                export_import.export_ex3(Select_Group, export_name.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ชื่อนำออกข้อมูล",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                        }
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

        export_ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                dialog.setContentView(R.layout.export_popup);

                GroupName = databaseHelper.GetGroupname("Setting_ex3_normal","st_ex3_normal_id");
                export_adapter = new Export_adapter(getApplicationContext(),GroupName);

                RecyclerView recyclerView = dialog.findViewById(R.id.Export_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(export_adapter);

                export_name = dialog.findViewById(R.id.export_name);
                textView = dialog.findViewById(R.id.text);
                Button close = dialog.findViewById(R.id.close);
                Button export = dialog.findViewById(R.id.export);

                if (GroupName.size() == 0){
                    export_name.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.GONE);
                }

                export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (export_adapter.Check_Group.size() != 0) {
                            if (export_name.getText().length() > 0) {
                                Select_Group.addAll(export_adapter.Check_Group);
                                export_import.export_ex4(Select_Group, export_name.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ชื่อนำออกข้อมูล",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                        }
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

        export_ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.layout_radius_while);
                dialog.setContentView(R.layout.export_popup);

                GroupName = databaseHelper.GetGroupname("Setting_ex3_hard","st_ex3_hard_id");
                export_adapter = new Export_adapter(getApplicationContext(),GroupName);

                RecyclerView recyclerView = dialog.findViewById(R.id.Export_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(export_adapter);

                export_name = dialog.findViewById(R.id.export_name);
                textView = dialog.findViewById(R.id.text);
                Button close = dialog.findViewById(R.id.close);
                Button export = dialog.findViewById(R.id.export);

                if (GroupName.size() == 0){
                    export_name.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.GONE);
                }

                export.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (export_adapter.Check_Group.size() != 0) {
                            if (export_name.getText().length() > 0) {
                                Select_Group.addAll(export_adapter.Check_Group);
                                export_import.export_ex5(Select_Group, export_name.getText().toString());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(),"กรุณาพิมพ์ชื่อนำออกข้อมูล",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"กรุณาเลือกแบบทดสอบ",Toast.LENGTH_SHORT).show();
                        }
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

        import_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/*");
                startActivityForResult(intent,123);
            }
        });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            assert data != null;
            String path = String.valueOf(data.getData());
            Log.e("Pathb",path);
            export_import.import_csv(Uri.parse(path));
        }
    }

