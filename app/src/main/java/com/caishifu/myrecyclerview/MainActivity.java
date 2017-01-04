package com.caishifu.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mContentRv ;
    private MainAdapter mAdapter ;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentRv = (RecyclerView) findViewById(R.id.id_scroll_content) ;
        initData();
        mAdapter = new MainAdapter(this , mData) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentRv.setLayoutManager(linearLayoutManager);

        mContentRv.setAdapter(mAdapter);

    }

    private void  initData(){
        for(char a = 'a' ; a < 'z' ; a ++){
            String title = "我是 " + a ;
            mData.add(title);
        }
    }
}
