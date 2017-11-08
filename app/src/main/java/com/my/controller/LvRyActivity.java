package com.my.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.BaseHolder;
import com.my.R;
import com.my.view.MyListView;
import com.my.view.MyRecyclerView;

/**
 * Author：mengyuan
 * Date  : 2017/11/7下午5:12
 * E-Mail:mengyuanzz@126.com
 * Desc  : ListView 嵌套 RecyclerView，指定滑动到指定条目
 */

public class LvRyActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView list_view;
    private EditText et_listview;
    private EditText et_recyclerview;


    private final int COUNT_LIST_VIEW = 20;//ListView的条目数量
    private final int COUNT_RECYCLER_VIEW = 30;//每个RecyclerView的条目数量

    private int listPosition;//ListView要滚动的下标
    private int recyclerViewPosition; //RecyclerView要滚动到的下标
    private boolean isUserScroll = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv_ry);


        list_view = findViewById(R.id.list_view);
        et_listview = findViewById(R.id.et_listview);
        et_recyclerview = findViewById(R.id.et_recyclerview);

        list_view.setAdapter(new ListViewAdapter());


        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //监听滑动，如果是用户手动滑动，则无需进行监听
                if (isUserScroll) {
                    return;
                }
                //firstVisibleItem:屏幕上最靠前的条目Position
                //当firstVisibleItem == listPosition 时，则代表已经滚动到了我们指定的ListView下标
                //接下来开始滚动ListView中的RecyclerView
                if (firstVisibleItem == listPosition) {
                    //获取ListView在当前屏幕上显示的第一个条目
                    MyRecyclerView recyclerView = (MyRecyclerView) list_view.getChildAt(0);
                    if (recyclerView == null) {
                        return;
                    }
                    /**此处为重点**/
                    //我调用了recylcerView的所有滚动方法，都无法实现recyclerView的滚动
                    //最终没有办法，我决定获取recyclerView需要滚动的距离，然后交给ListView来进行滚动
                    //此处是获取了RecyclerView需要滚动的距离
                    int needPosition = recyclerView.needScrollPosition(recyclerViewPosition);
                    //将Flag置为true，此次滑动不再监听
                    isUserScroll = true;
                    //让ListView进行滚动，700是滚动动画时间，可以更改
                    list_view.smoothScrollBy(needPosition, 700);
                }
            }
        });

        findViewById(R.id.bt_srcoll).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_srcoll://滚动

                if (TextUtils.isEmpty(et_listview.getText().toString())) {
                    Toast.makeText(this, "请输入LisView的跳转下标", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_recyclerview.getText().toString())) {
                    Toast.makeText(this, "请输入RecyclerView的跳转下标", Toast.LENGTH_SHORT).show();
                    return;
                }
                listPosition = Integer.valueOf(et_listview.getText().toString());
                if (listPosition >= COUNT_LIST_VIEW) {
                    Toast.makeText(this, "ListView下标的范围为0~" + (COUNT_LIST_VIEW - 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                recyclerViewPosition = Integer.valueOf(et_recyclerview.getText().toString());
                if (recyclerViewPosition >= COUNT_RECYCLER_VIEW) {
                    Toast.makeText(this, "RecyclerView下标的范围为0~" + (COUNT_RECYCLER_VIEW - 1), Toast.LENGTH_SHORT).show();
                    return;
                }

                //此次滚动是系统滚动，不是用户滚动
                isUserScroll = false;
                //滚动ListView到指定条目,接着交给滚动监听去处理
                list_view.setSelection(listPosition);


                break;
        }
    }

    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    //--------------------------------Adapter--------------------------------
    public class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return COUNT_LIST_VIEW;
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
                convertView = LvRyActivity.this.getLayoutInflater().inflate(R.layout.item_lv_ry, viewGroup, false);
            }

            RecyclerView recyclerView = convertView.findViewById(R.id.item_recyclerview);
            recyclerView.setAdapter(new RecyclerviewItemAdapter(position));

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
            return COUNT_RECYCLER_VIEW;
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
