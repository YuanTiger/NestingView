package com.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recylcerview;

    private DataInfor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initRecyclerView();
    }


    private void initData() {
        data = new DataInfor();
        ArrayList<Integer> resourceList =new ArrayList<>();

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


    private void initRecyclerView() {
        recylcerview = (RecyclerView) findViewById(R.id.recylcerview);

        recylcerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        recylcerview.setBackgroundResource(R.color.c_e0e0e2);

        recylcerview.setAdapter(new RecyclerViewAdapter());
    }



    private class RecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder>{
        private final int HORIZONTAL_VIEW = 1000;
        private final int VERTICAL_VIEW = 1001;
        private final int GRID_VIEW = 1002;

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){
                case HORIZONTAL_VIEW:
                    return new HorizontalViewHolder(R.layout.item_horizontal,parent,viewType);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if(holder instanceof HorizontalViewHolder){
                holder.refreshData(data.horizontalData,position);
            }

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0) return HORIZONTAL_VIEW;
            if(position == 1) return GRID_VIEW;
            if(position == 2) return VERTICAL_VIEW;
            return 0;
        }
    }

    /**
     * unit dip to px
     */
    private  int dip2px(float dip) {
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,getResources().getDisplayMetrics());
        return (int) (v + 0.5f);
    }



    //----------------------Holder----------------------------

    /**
     * 嵌套的水平RecyclerView
     */
    private class HorizontalViewHolder extends BaseHolder<List<Integer>>{
        private RecyclerView hor_recyclerview;

        private List<Integer> data;

        public HorizontalViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            hor_recyclerview = (RecyclerView) itemView.findViewById(R.id.hor_recyclerview);
        }

        @Override
        public void refreshData(List<Integer> data, int position) {
            this.data = data;
            ViewGroup.LayoutParams layoutParams = hor_recyclerview.getLayoutParams();
            layoutParams.height = dip2px(120);
            hor_recyclerview.setLayoutParams(layoutParams);
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false));
            hor_recyclerview.setAdapter(new HorizontalAdapter());
        }

        private class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder>{

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new HorizontalItemViewHolder(R.layout.item_x2_imageview,parent,viewType);
            }

            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data.get(position),position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }

        private class HorizontalItemViewHolder extends BaseHolder<Integer>{

            private ImageView imageview_item;

            public HorizontalItemViewHolder(int viewId, ViewGroup parent, int viewType) {
                super(viewId, parent, viewType);
                imageview_item = (ImageView) itemView.findViewById(R.id.imageview_item);
            }

            @Override
            public void refreshData(Integer data, int position) {
                super.refreshData(data, position);
                imageview_item.setBackgroundResource(data);
            }
        }

    }

}
