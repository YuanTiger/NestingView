package com.my.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.my.BaseHolder;
import com.my.R;
import com.my.view.MyListView;

/**
 * Author：mengyuan
 * Date  : 2017/11/7下午5:12
 * E-Mail:mengyuanzz@126.com
 * Desc  : ListView 嵌套 RecyclerView，指定滑动到指定条目
 */

public class LvRyActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView list_view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_ry);


        list_view = findViewById(R.id.list_view);

        list_view.setAdapter(new ListViewAdapter());


        findViewById(R.id.bt_srcoll).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_srcoll://滚动到第三个ListView条目中RecyclerView的第20的条目
                list_view.smoothScrollToPosition(2);

                RecyclerView recyclerView = (RecyclerView) list_view.getChildAt(2);

                recyclerView.smoothScrollToPosition(20);

                break;
        }
    }

    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    public class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
//                convertView = LvRyActivity.this.getLayoutInflater().inflate(R.layout.item_lv_ry_item, viewGroup, false);
                convertView = LvRyActivity.this.getLayoutInflater().inflate(R.layout.item_lv_ry, viewGroup, false);
            }

            RecyclerView recyclerView = convertView.findViewById(R.id.item_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(LvRyActivity.this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new RecyclerviewItemAdapter(position));
//            TextView textView = convertView.findViewById(R.id.tv_number);
//            textView.setText(String.valueOf(position));

            return convertView;
        }
    }


    //RecyclerView条目
    public class RecyclerviewItemAdapter extends RecyclerView.Adapter<BaseHolder> {

        private int fatherPosition;

        public RecyclerviewItemAdapter(int position) {
            fatherPosition = position;
        }

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(R.layout.item_lv_ry_item, parent, viewType);
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            holder.refreshData(fatherPosition, position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }


    public class RecyclerViewHolder extends BaseHolder<Integer> {


        public RecyclerViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
        }

        @Override
        public void refreshData(Integer data, int position) {
            ((TextView) itemView.findViewById(R.id.tv_number)).setText(String.valueOf(data) + "-" + String.valueOf(position));
        }
    }


}
