package com.example.projectld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    Button login;
    EditText ID,PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.Login);
        ID = (EditText) findViewById(R.id.textID);
        PW = (EditText) findViewById(R.id.textPW);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(login.this,menu.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    User user = dbHelper.queryUser(ID.getText().toString(), PW.getText().toString());
                    if (user != null) {
                        Bundle mBundle = new Bundle();
                        mBundle.putString("user", user.getUsername());
                        Intent intent = new Intent(login.this, menu.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        Toast.makeText(login.this, "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                        PW.setText("");
                    }
                }else{
                    Toast.makeText(login.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(ID.getText().toString()) || TextUtils.isEmpty(PW.getText().toString())) {
            return true;
        }else {
            return false;
        }
    }

}
