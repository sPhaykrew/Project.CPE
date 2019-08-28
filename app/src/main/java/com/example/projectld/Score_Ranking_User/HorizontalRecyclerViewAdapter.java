package com.example.projectld.Score_Ranking_User;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectld.R;

import java.util.ArrayList;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.HorizonViewHolder> {

    Context context;
    ArrayList<HorizontalModel> arrayList;

    public HorizontalRecyclerViewAdapter (Context context,ArrayList<HorizontalModel> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HorizonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal,viewGroup,false);
        return new HorizonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizonViewHolder holder, int positon) {
        final HorizontalModel horizontalModel =arrayList.get(positon);
        holder.name.setText(horizontalModel.getWord());
        holder.score.setText(horizontalModel.getScore());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HorizonViewHolder extends RecyclerView.ViewHolder {

        TextView name,score;

        public HorizonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.word);
            score = itemView.findViewById(R.id.sumScore);
        }
    }
}
