package com.example.projectld;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GridAdapter_User_Modifiled extends AppCompatActivity {

    EditText Username,Fullname,Age;
    ImageView profile;
    RadioButton male,female;
    Button update, updatePicture,delete;
    String getsex,sex;
    String getUsername;
    String getFullname;
    String getAge;
    byte[] Picture;
    static final int SELECT_PICTURE = 100;
    Bitmap image = null;
    boolean selectpicture = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridadapter_user_modifiled);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final String UserID_GW = getIntent().getExtras().getString("UserID");
        User user = databaseHelper.ModifileUser(UserID_GW);

        Username = findViewById(R.id.Username);
        Fullname = findViewById(R.id.Fullname);
        Age = findViewById(R.id.Age);
        profile = findViewById(R.id.profile);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        update = findViewById(R.id.update);
        updatePicture = findViewById(R.id.updatePicture);
        delete = findViewById(R.id.delete);

        Picture = user.getPicture();
        getUsername = user.getUsername();
        getFullname = user.getFullname();
        getAge = String.valueOf(user.getAge());
        getsex = user.getSex();

        try{ //กรณีแอดไม่ได้ใส่รูป defual ไม่มีรูป
            Bitmap bmp= BitmapFactory.decodeByteArray(Picture, 0 , Picture.length);
            profile.setImageBitmap(bmp);
        } catch (Exception e){

        }


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
                    byte[] inputData = Picture; //ถ้าไม่ได้อัพรูปใหม่ จะใช้รูปเก่า
                    if (image != null) {
                        inputData = convertBitmapIntoByteArray(); //แปลงรูปเป็น byte
                    }
                    databaseHelper.update_user(Username.getText().toString(),Fullname.getText().toString()
                            ,Integer.parseInt(Age.getText().toString()),sex,inputData, UserID_GW);

                    finish();
                    Toast.makeText(getApplicationContext(),"แก้ไขแล้ว",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GridAdapter_User_Modifiled.this, menu.class);
                    menu.getInstance().finish(); //ปิดหน้าเมนู รีโหลด
                    startActivity(intent);
                }
            }
        });

        updatePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.delete_user(UserID_GW);
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
        image.compress(Bitmap.CompressFormat.JPEG, 50, stream);//ขนาดภาพที่ลดลง
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }
}
