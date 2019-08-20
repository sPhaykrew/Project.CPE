package com.example.projectld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.navigationDrawer.F_Sentence_data;
import com.example.projectld.navigationDrawer.F_Word_data;
import com.example.projectld.navigationDrawer.F_data;
import com.example.projectld.navigationDrawer.F_profile;
import com.example.projectld.navigationDrawer.F_setting;

public class menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    TextView Username,Fullname;

    SharedPreferences user;

    static menu menu;//ปิดหน้าเมนูในหน้าแก้ไขโปรไฟล์


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        menu = this; //ปิดหน้าเมนูในหน้าแก้ไขโปรไฟล์

        user = getSharedPreferences("User", Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (!user.getString("Permission", null).equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.setting).setVisible(false);
            menu.findItem(R.id.EditUser).setVisible(false);
            menu.findItem(R.id.addPerson).setVisible(false);
        }

        //Text Profile
        View header = navigationView.getHeaderView(0);
        Username = header.findViewById(R.id.Username);
        Fullname = header.findViewById(R.id.Fullname);
        Username.setText(user.getString("Username",null));
        Fullname.setText(user.getString("Fullname",null));

        //Profile header
        ImageView imageView = header.findViewById(R.id.Profile);
        if (user.getString("Picture",null) != null){
        byte[] bytes = Base64.decode(user.getString("Picture",null), Base64.DEFAULT); //แปลง String เป็น byte
            Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            imageView.setImageBitmap(bmp); }


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new F_menu_home())
                    .commit();
            //navigationView.setCheckedItem(R.id.setting);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_menu_home()).addToBackStack(null).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_setting()).addToBackStack(null).commit();
                break;
            case R.id.edit:
                Intent editProfile = new Intent(getApplicationContext(), F_profile.class);
                startActivity(editProfile);
                break;
            case R.id.data:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_data()).addToBackStack(null).commit();
                break;

            case R.id.WordData:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_Word_data()).addToBackStack(null).commit();
                break;

            case R.id.SentenceData:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_Sentence_data()).addToBackStack(null).commit();
                break;

            case R.id.EditUser:
                Intent editUser = new Intent(getApplicationContext(),Call_GridAdapter_User.class);
                startActivity(editUser);
                break;
            case R.id.addPerson:
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                break;
            case R.id.logout:
                finish();
                SharedPreferences.Editor editor = user.edit();
                editor.clear();
                editor.apply();
                Intent Logout = new Intent(getApplicationContext(),login.class);
                startActivity(Logout);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static menu getInstance(){ //ปิดหน้าเมนูในหน้าแก้ไขโปรไฟล์
        return   menu;
    }
}
