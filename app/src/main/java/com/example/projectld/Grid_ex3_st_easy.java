package com.example.projectld;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Grid_ex3_st_easy extends AppCompatActivity {

    private Context context;
    private GridListAdapter adapter;
    private ArrayList<String> arrayList;
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_fragment);
        selectButton = (Button) findViewById(R.id.select_button);
        loadGridView();
        onClickEvent();
    }

    private void loadGridView() {
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        arrayList = new ArrayList<>();
        for (int i = 1; i <= 50; i++)
            arrayList.add("GridView Items " + i);

        adapter = new GridListAdapter(this, arrayList, false);
        gridView.setAdapter(adapter);
    }

    private void onClickEvent() {
        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            String selectedRowLabel = arrayList.get(selectedRows.keyAt(i));

                            //append the row label text
                            stringBuilder.append(selectedRowLabel + "\n");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray selectedRows = adapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    //Loop to all the selected rows array
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //remove the checked item
                            arrayList.remove(selectedRows.keyAt(i));
                        }
                    }

                    //notify the adapter and remove all checked selection
                    adapter.removeSelection();
                }
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check the current text of Select Button
                if (selectButton.getText().toString().equals(getResources().getString(R.string.select_all))) {

                    //If Text is Select All then loop to all array List items and check all of them
                    for (int i = 0; i < arrayList.size(); i++)
                        adapter.checkCheckBox(i, true);

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.deselect_all));
                } else {
                    //If button text is Deselect All remove check from all items
                    adapter.removeSelection();

                    //After checking all items change button text
                    selectButton.setText(getResources().getString(R.string.select_all));
                }
            }
        });

    }
}
