package com.example.projectld.exercise2.st_ex2_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise2.main_select_image;

import java.util.ArrayList;

public class adapter_ex2 extends RecyclerView.Adapter<adapter_ex2.ex2_ViewHolder> {
    private ArrayList<Item_st_ex2> mExampleList;
    private SparseBooleanArray mSelectedItemsIds;
    Context context;
    Button button;
    String Groupname;

    public static class ex2_ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView1;
        ImageView mImageView1,mImageView2,mImageView3;
        CheckBox checkBox1,checkBox2,checkBox3;

        public ex2_ViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textchar);
            mImageView1 = itemView.findViewById(R.id.image_char1);
            mImageView2 = itemView.findViewById(R.id.image_char2);
            mImageView3 = itemView.findViewById(R.id.image_char3);
            checkBox1 = itemView.findViewById(R.id.checkbox1);
            checkBox2 = itemView.findViewById(R.id.checkbox2);
            checkBox3 = itemView.findViewById(R.id.checkbox3);

            //button = itemView.findViewById(R.id.cf_char);
        }
    }

    public adapter_ex2(ArrayList<Item_st_ex2> exampleList,Context context,Button button,String Groupname) {
        this.mExampleList = exampleList;
        this.context = context;
        this.button = button;
        this.Groupname = Groupname;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public ex2_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_st_ex2, parent, false);
        ex2_ViewHolder evh = new ex2_ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ex2_ViewHolder holder, final int position) {

        final Item_st_ex2 currentItem = mExampleList.get(position);

        // แปลงชื่อไฟล์รูปเป็น int
        int char1 = context.getResources().getIdentifier(currentItem.getName_image1() , "drawable", context.getPackageName());
        int char2 = context.getResources().getIdentifier(currentItem.getName_image2() , "drawable", context.getPackageName());
        int char3 = context.getResources().getIdentifier(currentItem.getName_image3() , "drawable", context.getPackageName());

        holder.mTextView1.setText(currentItem.getName_char());

        holder.mImageView1.setImageResource(char1);
        holder.mImageView2.setImageResource(char2);
        holder.mImageView3.setImageResource(char3);

        holder.checkBox1.setChecked(mSelectedItemsIds.get(position));
        holder.checkBox2.setChecked(mSelectedItemsIds.get(position));
        holder.checkBox3.setChecked(mSelectedItemsIds.get(position));


        holder.mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox1.setChecked(!holder.checkBox1.isChecked());
                if (holder.checkBox1.isChecked()){
                    currentItem.setCheck(currentItem.getCheck()+1);
                    String image = String.valueOf(currentItem.getName_image1());
                    currentItem.setCheck1(image);
                } else {
                    currentItem.setCheck(currentItem.getCheck()-1);
                    currentItem.setCheck1(null);
                }

            }
        });

        holder.mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox2.setChecked(!holder.checkBox2.isChecked());
                if (holder.checkBox2.isChecked()){
                    currentItem.setCheck(currentItem.getCheck()+1);
                    String image = String.valueOf(currentItem.getName_image2());
                    currentItem.setCheck2(image);
                } else {
                    currentItem.setCheck(currentItem.getCheck()-1);
                    currentItem.setCheck2(null);
                }
            }
        });

        holder.mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox3.setChecked(!holder.checkBox3.isChecked());
                if (holder.checkBox3.isChecked()){
                    currentItem.setCheck(currentItem.getCheck()+1);
                    String image = String.valueOf(currentItem.getName_image3());
                    currentItem.setCheck3(image);
                } else {
                    currentItem.setCheck(currentItem.getCheck()-1);
                    currentItem.setCheck3(null);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("0", String.valueOf(mExampleList.get(0).getCheck()));
                Log.d("1", String.valueOf(mExampleList.get(1).getCheck()));
                Log.d("2", String.valueOf(mExampleList.get(2).getCheck()));
                Log.d("3", String.valueOf(mExampleList.get(3).getCheck()));
                Log.d("4", String.valueOf(mExampleList.get(4).getCheck()));


                if (mExampleList.get(0).getCheck() == 2 && mExampleList.get(1).getCheck() == 2 &&
                mExampleList.get(2).getCheck() == 2 && mExampleList.get(3).getCheck() == 2 &&
                mExampleList.get(4).getCheck() == 2) {

                    //insert db
                    ArrayList<String> setChar = new ArrayList<>();

                    if (mExampleList.get(0).getCheck1() != null){
                        setChar.add(mExampleList.get(0).getCheck1());
                    }
                    if (mExampleList.get(0).getCheck2() != null){
                        setChar.add(mExampleList.get(0).getCheck2());
                    }
                    if (mExampleList.get(0).getCheck3() != null){
                        setChar.add(mExampleList.get(0).getCheck3());
                    }
                    if (mExampleList.get(1).getCheck1() != null){
                        setChar.add(mExampleList.get(1).getCheck1());
                    }
                    if (mExampleList.get(1).getCheck2() != null){
                        setChar.add(mExampleList.get(1).getCheck2());
                    }
                    if (mExampleList.get(1).getCheck3() != null){
                        setChar.add(mExampleList.get(1).getCheck3());
                    }
                    if (mExampleList.get(2).getCheck1() != null){
                        setChar.add(mExampleList.get(2).getCheck1());
                    }
                    if (mExampleList.get(2).getCheck2() != null){
                        setChar.add(mExampleList.get(2).getCheck2());
                    }
                    if (mExampleList.get(2).getCheck3() != null){
                        setChar.add(mExampleList.get(2).getCheck3());
                    }
                    if (mExampleList.get(3).getCheck1() != null){
                        setChar.add(mExampleList.get(3).getCheck1());
                    }
                    if (mExampleList.get(3).getCheck2() != null){
                        setChar.add(mExampleList.get(3).getCheck2());
                    }
                    if (mExampleList.get(3).getCheck3() != null){
                        setChar.add(mExampleList.get(3).getCheck3());
                    }
                    if (mExampleList.get(4).getCheck1() != null){
                        setChar.add(mExampleList.get(4).getCheck1());
                    }
                    if (mExampleList.get(4).getCheck2() != null){
                        setChar.add(mExampleList.get(4).getCheck2());
                    }
                    if (mExampleList.get(4).getCheck3() != null){
                        setChar.add(mExampleList.get(4).getCheck3());
                    }

                    //Toast.makeText(context,setChar.toString(),Toast.LENGTH_SHORT).show();
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    for (int i=0;i<setChar.size();i++){
                        databaseHelper.insert_char(setChar.get(i),Groupname);
                    }
                    Toast.makeText(context,"เพิ่มข้อมูลแล้ว",Toast.LENGTH_SHORT).show();
                    main_select_image.getInstance().finish();  //ปิด activity จากหน้าอื่น

                } else {
                    Toast.makeText(context,"กรุณาเลือก 2 คำถามในแต่ละข้อ",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}