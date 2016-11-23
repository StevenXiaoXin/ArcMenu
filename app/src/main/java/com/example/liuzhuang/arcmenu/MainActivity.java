package com.example.liuzhuang.arcmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.liuzhuang.arcmenu.view.ArcMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView mListView;
    private ArcMenu mArcMenu;
    private List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mDatas));
        initEvent();

    }

    private void initEvent() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mArcMenu.isOpen())
                    mArcMenu.toggleMenu(400);
            }

        });

        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener()
        {

            @Override
            public void onClick(View view, int pos)
            {
                Toast.makeText(MainActivity.this, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<String>();

        for (int i = 'A'; i < 'Z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }

    private void initView() {
        mArcMenu = (ArcMenu) findViewById(R.id.id_menu);
        mListView = (ListView) findViewById(R.id.id_listview);
    }
}
