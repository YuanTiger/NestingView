package com.my.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.my.BaseHolder;
import com.my.DataInfor;
import com.my.R;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView  嵌套  各种样式的RecyclerView
 */
public class RyRyActivity extends AppCompatActivity {


    private RecyclerView recylcerview;//外层recyclerview

    private DataInfor data;//假数据

    private int screenWidth;//屏幕宽度

    private int HORIZONTAL_VIEW_X = 0;//水平RecyclerView滑动的距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ryry);
        basicParamInit();
        initData();
        initRecyclerView();
    }


    /**
     * 计算屏幕的宽度
     */
    private void basicParamInit() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        screenWidth = metric.widthPixels;

    }

    /**
     * 制造一些假数据
     */
    private void initData() {
        data = new DataInfor();
        ArrayList<Integer> resourceList = new ArrayList<>();

        resourceList.add(R.drawable.aaa);
        resourceList.add(R.mipmap.ic_launcher);
        resourceList.add(R.drawable.aaa);
        resourceList.add(R.mipmap.ic_launcher);
        resourceList.add(R.drawable.aaa);
        resourceList.add(R.mipmap.ic_launcher);
        resourceList.add(R.drawable.aaa);
        resourceList.add(R.mipmap.ic_launcher);

        data.gridData = data.horizontalData = data.verticalData = resourceList;

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recylcerview = (RecyclerView) findViewById(R.id.recylcerview);
        //竖直排列、正向排序
        recylcerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //添加了一个灰色背景
        recylcerview.setBackgroundResource(R.color.c_e0e0e2);
        recylcerview.setAdapter(new RecyclerViewAdapter());
    }

    /**
     * 将dp转化为px
     */
    private int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }


    /**
     * 外层RecyclerView的Adapter
     */
    private class RecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
        //条目样式
        private final int HORIZONTAL_VIEW = 1000;
        private final int VERTICAL_VIEW = 1001;
        private final int GRID_VIEW = 1002;

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case HORIZONTAL_VIEW:
                    return new HorizontalViewHolder(R.layout.item_recyclerview, parent, viewType);
                case GRID_VIEW:
                    return new GridViewHolder(R.layout.item_recyclerview, parent, viewType);
                case VERTICAL_VIEW:
                    return new ItemViewHolder(R.layout.item_x2_imageview, parent, viewType);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if (holder instanceof HorizontalViewHolder) {
                holder.refreshData(data.horizontalData, position);
            } else if (holder instanceof GridViewHolder) {
                holder.refreshData(data.gridData, position);
            } else if (holder instanceof ItemViewHolder) {
                holder.refreshData(data.verticalData.get(position - 2), position);
            }

        }

        @Override
        /**
         * 当Item出现时调用此方法
         */
        public void onViewAttachedToWindow(BaseHolder holder) {
            Log.i("mengyuan", "onViewAttachedToWindow:" + holder.getClass().toString());
        }

        @Override
        /**
         * 当Item被回收时调用此方法
         */
        public void onViewDetachedFromWindow(BaseHolder holder) {
            Log.i("mengyuan", "onViewDetachedFromWindow:" + holder.getClass().toString());
            if (holder instanceof HorizontalViewHolder) {
                ((HorizontalViewHolder) holder).saveStateWhenDestory();
            }
        }


        @Override
        public int getItemCount() {
            return 2 + data.verticalData.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return HORIZONTAL_VIEW;
            if (position == 1) return GRID_VIEW;
            return VERTICAL_VIEW;
        }
    }


    //----------------------Holder----------------------------

    /**
     * 嵌套的水平RecyclerView
     * 当条目被回收时，下次加载会重新回到之前的x轴
     */
    private class HorizontalViewHolder extends BaseHolder<List<Integer>> {
        private RecyclerView hor_recyclerview;

        private List<Integer> data;

        private int scrollX;//纪录X移动的距离

        private boolean isLoadLastState = false;//是否加载了之前的状态


        public HorizontalViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            hor_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
            //为了保存移动距离，所以添加滑动监听
            hor_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //每次条目重新加载时，都会滑动到上次的距离
                    if (!isLoadLastState) {
                        isLoadLastState = true;
                        hor_recyclerview.scrollBy(HORIZONTAL_VIEW_X, 0);
                    }
                    //dx为每一次移动的距离，所以我们需要做累加操作
                    scrollX += dx;
                }
            });
        }

        @Override
        public void refreshData(List<Integer> data, int position) {
            this.data = data;
            //设置水平RecyclerView水平显示
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(RyRyActivity.this, LinearLayoutManager.HORIZONTAL, false));
            //设置背景
            hor_recyclerview.setBackgroundResource(R.color.colorAccent);
            //设置Adapter
            hor_recyclerview.setAdapter(new HorizontalAdapter());

        }

        /**
         * 在条目回收时调用，保存X轴滑动的距离
         */
        public void saveStateWhenDestory() {
            HORIZONTAL_VIEW_X = scrollX;
            isLoadLastState = false;
            scrollX = 0;
        }


        private class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder> {

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_x2_imageview, parent, viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data.get(position), position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }
    }


    /**
     * GridView形状的RecyclerView
     */
    private class GridViewHolder extends BaseHolder<List<Integer>> {

        private RecyclerView item_recyclerview;

        private final int ONE_LINE_SHOW_NUMBER = 3;

        private List<Integer> data;

        public GridViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);

        }

        @Override
        public void refreshData(List<Integer> data, int position) {
            super.refreshData(data, position);
            this.data = data;
            //每行显示3个，水平显示
            item_recyclerview.setLayoutManager(new GridLayoutManager(RyRyActivity.this, ONE_LINE_SHOW_NUMBER, LinearLayoutManager.VERTICAL, false));
            //设置个背景色
            item_recyclerview.setBackgroundResource(R.color.colorPrimary);
            //设置Adapter
            item_recyclerview.setAdapter(new GridAdapter());
        }


        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_x2_imageview, parent, viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data.get(position), position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }


    }


    /**
     * 通用子条目hodler
     */
    private class ItemViewHolder extends BaseHolder<Integer> {

        private ImageView imageview_item;

        public ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            imageview_item = (ImageView) itemView.findViewById(R.id.imageview_item);
            ViewGroup.LayoutParams layoutParams = imageview_item.getLayoutParams();
            layoutParams.width = layoutParams.height = screenWidth / 3;
            imageview_item.setLayoutParams(layoutParams);
        }

        @Override
        public void refreshData(Integer data, final int position) {
            imageview_item.setBackgroundResource(data);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RyRyActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
