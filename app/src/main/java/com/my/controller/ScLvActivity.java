package com.my.controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.my.R;
import com.my.view.MyListView;

import org.w3c.dom.Text;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/6/13/上午10:01
 * DESC: ScrollView嵌套滑动ListView
 */
public class ScLvActivity extends AppCompatActivity {

    private MyListView listView;

    private MyAdapter adapter;


    private int maxCount = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sclv);
        listView = (MyListView) findViewById(R.id.listview);


        initListView();


    }

    private void initListView() {
        if (adapter == null)
            listView.setAdapter(adapter = new MyAdapter());
        else
            adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return maxCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = ScLvActivity.this.getLayoutInflater().inflate(R.layout.item_sc_lv, parent, false);
            }

            TextView textView =  convertView.findViewById(R.id.tv_0);
            textView.setText("position:" + position + position + position + position + position + position);
            return convertView;
        }
    }

    /**
     * unit dip to px
     */
    private int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }

}
