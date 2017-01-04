package com.caishifu.myrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wp on 2016/12/21.
 *
 * @description
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Holder> {
    private Context context ;
    private List<String> mDatas ;
    public MainAdapter(Context context , List<String> datas){
        this.context = context ;
        this.mDatas = datas ;
    }

    public class Holder extends RecyclerView.ViewHolder{

        public Holder (View view){
            super(view);
        }

        TextView tv ;
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String title = mDatas.get(position);
        holder.tv.setText(title);
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main , null , false);
        Holder h = new Holder(view);
        h.tv = (TextView) view.findViewById(R.id.tv_main_title);
        return h;
    }
}
