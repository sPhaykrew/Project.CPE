package com.example.projectld;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter_User extends BaseAdapter {

    Context context;
    private final ArrayList<String> values;
    private final ArrayList<Bitmap> images;
    ArrayList<String> UserID;
    LayoutInflater layoutInflater;

    public GridAdapter_User(Context context, ArrayList<String> values, ArrayList<Bitmap> images,ArrayList<String> UserID) {
        this.context = context;
        this.values = values;
        this.images = images;
        this.UserID = UserID;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Holder holder = new Holder();
        View rowView;

        rowView = layoutInflater.inflate(R.layout.custom_gridview_item_user, null);
        holder.tv =(TextView) rowView.findViewById(R.id.name);
        holder.img = (ImageView) rowView.findViewById(R.id.profile);
        holder.tv.setText(values.get(position));
        try{ //กรณีแอดไม่ได้ใส่รูป defual ไม่มีรูป
            holder.img.setImageBitmap(images.get(position));
        } catch (Exception e){

        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,GridAdapter_User_Modifiled.class);
                intent.putExtra("UserID", UserID.get(position));
                context.startActivity(intent);

            }
        });

        return rowView;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

}