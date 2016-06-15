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
 * DESC:
 */
public class ScLvActivity extends AppCompatActivity {

    private MyListView listView;

    private MyAdapter adapter;

    private int lastPosition = 0;

    private final int HANDLER_MESSAGE = 888;

    private boolean userIsScroll = false;

    private int maxCount = 20;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

//            listView.scrollTo(0, dip2px(300));
            if (lastPosition < maxCount - 1) {
                //条目向下移动1个单位
                listView.setSelection(++lastPosition);
                //发送一个3秒后的新handler
                handler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 3000);
            }

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sclv);
        listView = (MyListView) findViewById(R.id.listview);


        initListView();

        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 3000);

    }

    private void initListView() {
        if (adapter == null)
            listView.setAdapter(adapter = new MyAdapter());
        else
            adapter.notifyDataSetChanged();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL://用户滑动状态
                        //当用户滑动时，取消handler
                        userIsScroll = true;
                        handler.removeMessages(HANDLER_MESSAGE);
                        break;
                    case SCROLL_STATE_IDLE://用户手指离开屏幕
                        //当用户手指离开屏幕时，重新开启自动播放
                        userIsScroll = false;
                        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 3000);
                        break;
                }
//                Log.i("mengyuan", "scrollState" + scrollState);

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.i("mengyuan", "firstVisibleItem" + firstVisibleItem + "::::visibleItemCount" + visibleItemCount + "::::totalItemCount" + totalItemCount);
                //如果用户自己切换条目，保存条目下标
                if (userIsScroll) lastPosition = firstVisibleItem;
            }
        });

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
            View inflate = ScLvActivity.this.getLayoutInflater().inflate(R.layout.item_sc_lv, parent, false);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_0);
            textView.setText("position:" + position + position + position + position + position + position);
            return inflate;
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
