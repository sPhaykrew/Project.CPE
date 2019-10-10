package com.example.projectld.navigationDrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.menu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class F_profile extends AppCompatActivity {

    EditText Username,Fullname,Age;
    ImageView profile;
    RadioButton male,female;
    Button update;
    String getsex,sex;
    String getUsername,getFullname,getAge,Picture,UserID;
    static final int SELECT_PICTURE = 100;
    Bitmap image = null;
    boolean selectpicture = true;

    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        SharedPreferences user = getSharedPreferences("User", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = user.edit();
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("แก้ไขโปรไฟล์");
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

        Username = findViewById(R.id.Username);
        Fullname = findViewById(R.id.Fullname);
        Age = findViewById(R.id.Age);
        profile = findViewById(R.id.profile);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        update = findViewById(R.id.update);

        Picture = user.getString("Picture",null);
        getUsername = user.getString("Username",null);
        getFullname = user.getString("Fullname",null);
        getAge = String.valueOf(user.getInt("Age",0));
        getsex = user.getString("sex",null);
        UserID = user.getString("UserID",null);

        if(Picture != null){
            bytes = Base64.decode(Picture,Base64.DEFAULT); //แปลง String เป็น byte
            Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            profile.setImageBitmap(bmp); }

        Username.setText(getUsername);
        Fullname.setText(getFullname);
        Age.setText(getAge);

        if (getsex.equals("ชาย")){
            male.setChecked(true);
            sex = "ชาย";
        } else {
            female.setChecked(true);
            sex = "หญิง";
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Username.getText().toString().equals(getUsername) && Fullname.getText().toString().equals(getFullname) &&
                        Age.getText().toString().equals(getAge) && getsex.equals(sex) && selectpicture){
                    Toast.makeText(getApplicationContext(),"ไม่พบการเปลี่ยนแปลง",Toast.LENGTH_SHORT).show();
                }
                else {
                    byte[] inputData = bytes; //ถ้าไม่ได้อัพรูปใหม่ จะใช้รูปเก่า
                    if (image != null) {
                        inputData = convertBitmapIntoByteArray(); //แปลงรูปเป็น byte
                    }
                    databaseHelper.update_user(Username.getText().toString(),Fullname.getText().toString()
                            ,Integer.parseInt(Age.getText().toString()),sex,inputData, UserID);

                    editor.putString("Username",Username.getText().toString());
                    editor.putString("Fullname",Fullname.getText().toString());
                    editor.putInt("Age", Integer.parseInt(Age.getText().toString()));
                    editor.putString("sex",sex);
                    if(image != null){
                        String saveThis = Base64.encodeToString(inputData, Base64.DEFAULT);//แปลง byte เป็น String
                        editor.putString("Picture",saveThis);}
                    editor.commit();

                    finish();
                    Toast.makeText(getApplicationContext(),"แก้ไขแล้ว",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(F_profile.this, menu.class);
                    menu.close_activity.finish(); //ปิดหน้าเมนู รีโหลด
                    startActivity(intent);
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE)
        {
            if(data == null) return;
            Uri imageUri = data.getData();
            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                profile.setImageBitmap(image);
                selectpicture = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    sex = "ชาย";
                break;
            case R.id.female:
                if (checked)
                    sex = "หญิง";
                break;
        }
    }

    private byte[] convertBitmapIntoByteArray() { //ลดขนาดรูป แปลงรูป
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 10, stream);//ขนาดภาพที่ลดลง
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }
}
