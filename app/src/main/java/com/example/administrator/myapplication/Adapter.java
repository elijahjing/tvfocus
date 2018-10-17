package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends  RecyclerView.Adapter<Adapter.MyHolder> {
    private final List<String> list;
    FocusView focusView;
    Context context;
    public Adapter(Context context, FocusView focusView) {
        this.focusView=focusView;
        this.context=context;
        list = new ArrayList<String>();
        for (int i = 0; i < 300; i++) {
            list.add("" + i);
        }

    }
    //OnCreateViewHolder用来给rv创建缓存的
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.layout, parent, false);

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        holder.name.setText("這是第一個iteam:"+list.get(position));
    }

    public int getItemCount() {
        return list.size();
    }
    int i=0;
    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

}