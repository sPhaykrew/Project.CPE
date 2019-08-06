package com.example.projectld;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {

    EditText user,password,confirmPS,name,age;
    Button button,picture;
    String sex = null;
    ImageView imageView;
    static final int SELECT_PICTURE = 100;
    Bitmap image = null;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        imageView = findViewById(R.id.selectPicture);

        user = findViewById(R.id.ID);
        password = findViewById(R.id.password);
        confirmPS = findViewById(R.id.psconfirm);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);

        button = findViewById(R.id.button);
        picture = findViewById(R.id.picture);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() || password.getText().toString().isEmpty() || name.getText().toString().isEmpty()
                || age.getText().toString().isEmpty() || sex == null){

                    Toast.makeText(Register.this,"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();

                } else { if(!password.getText().toString().equals(confirmPS.getText().toString()))
                {
                    Toast.makeText(Register.this,"พาสเวิดไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                } else {
                    try { //แปลงรูปเป็น byte ก่อน insert
                    byte[] inputData = convertBitmapIntoByteArray();
                    //insert table user
                    databaseHelper.insert_user(user.getText().toString(),password.getText().toString(),name.getText().toString(),
                            Integer.parseInt(age.getText().toString()),sex,inputData);
                            finish();
                    Toast.makeText(Register.this,"เพิ่มผู้ใช้งานแล้ว",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("SelectPicture Error", "<saveImageInDB> Error : " + e.getLocalizedMessage());
                        }
                    }
                }
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
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
                imageView.setImageBitmap(image);

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
        image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }
}
