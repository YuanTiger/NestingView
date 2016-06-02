#效果

<img src="http://upload-images.jianshu.io/upload_images/2153401-8b634e6232192dcd.gif?imageMogr2/auto-orient/strip" />

#原理
RecyclerView嵌套RecyclerView的条目，项目中可能会经常有这样的需求，但是我们将子条目设置为RecyclerView之后，却显示不出来。自己试了很久，终于找到了原因：**必须先设置子RecylcerView的高度**。你要花精力确定出子RecyclerView里面条目的高度，然后从而确定子RecyclerView的高度，设置给子RecylcerView，这样做RecyclerView就可以正确显示出子ReclyclerView。

#代码
**为了方便大家阅读，外层RecyclerView我就不加任何修饰，就是RecyclerView，RecyclerView条目我会称它为子RecyclerView**

下面开始来看代码：：
首页布局就是一个竖直排列的RecyclerView
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.RecyclerView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/recylcerview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
    
    
接下来在MainActivity对该布局进行初始化,然后制造一些假数据
```    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basicParamInit();
        initData();
        initRecyclerView();
    }


 
    private  void basicParamInit() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        screenWidth = metric.widthPixels;

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
```


接下来看看RecyclerView的Adapter:
```
private class RecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder>{
        private final int HORIZONTAL_VIEW = 1000;
        private final int VERTICAL_VIEW = 1001;
        private final int GRID_VIEW = 1002;

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){
                case HORIZONTAL_VIEW:
                    return new HorizontalViewHolder(R.layout.item_recyclerview,parent,viewType);
                case GRID_VIEW:
                    return new GridViewHolder(R.layout.item_recyclerview,parent,viewType);
                case VERTICAL_VIEW:
                    return new ItemViewHolder(R.layout.item_x2_imageview,parent,viewType);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if(holder instanceof HorizontalViewHolder){
                holder.refreshData(data.horizontalData,position);
            }else if(holder instanceof GridViewHolder){
                holder.refreshData(data.gridData,position);
            }else if(holder instanceof ItemViewHolder){
                holder.refreshData(data.verticalData.get(position - 2),position - 2);
            }

        }

        @Override
        public int getItemCount() {
            return 2 + data.verticalData.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0) return HORIZONTAL_VIEW;
            if(position == 1) return GRID_VIEW;
            return VERTICAL_VIEW;
        }
    }
```

可以看出，我们一共有三种条目类型，第一种是水平滑动的子RecyclerView，第二种是GridView形的子RecyclerView，第三种就是正常的子条目，根据viewType来返回不同的ViewHolder，到这里应该都没什么问题。

接下来就是各个类型的ViewHolder了，在Holder当中，我们要**计算条目的高度然后设置给子RecyclerView**
```
    private class HorizontalViewHolder extends BaseHolder<List<Integer>>{
        private RecyclerView hor_recyclerview;

        private List<Integer> data;

        public HorizontalViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            hor_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
        }

        @Override
        public void refreshData(List<Integer> data, int position) {
            this.data = data;
            ViewGroup.LayoutParams layoutParams = hor_recyclerview.getLayoutParams();
            //高度等于＝条目的高度＋ 10dp的间距 ＋ 10dp（为了让条目居中）
            layoutParams.height = screenWidth/3 + dip2px(20);
            hor_recyclerview.setLayoutParams(layoutParams);
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false));
            hor_recyclerview.setBackgroundResource(R.color.colorAccent);
            hor_recyclerview.setAdapter(new HorizontalAdapter());
        }

        private class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder>{

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_x2_imageview,parent,viewType);
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
    }
```

水平的子RecyclerView的高度还是比较容易计算的，毕竟只有一行，高度相对来说是固定的。但是像GridView的高度是动态的，根据条目数量的不同，可能会有多行，所以我们需要先计算行数，然后每行的高度*行数才是子RecyclerView的高度
```
private class GridViewHolder extends BaseHolder<List<Integer>>{

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
            item_recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this,ONE_LINE_SHOW_NUMBER,LinearLayoutManager.HORIZONTAL,false));

            ViewGroup.LayoutParams layoutParams = item_recyclerview.getLayoutParams();
            //计算行数
            int lineNumber = data.size()%ONE_LINE_SHOW_NUMBER==0? data.size()/ONE_LINE_SHOW_NUMBER:data.size()/ONE_LINE_SHOW_NUMBER +1;
            //计算高度=行数＊每行的高度 ＋(行数－1)＊10dp的margin ＋ 10dp（为了居中）
            //因为每行显示3个条目，为了保持正方形，那么高度应该是也是宽度/3
            //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
            layoutParams.height = lineNumber *(screenWidth/3) + (lineNumber-1)*dip2px(10) + dip2px(10);

            item_recyclerview.setLayoutParams(layoutParams);

            item_recyclerview.setBackgroundResource(R.color.colorPrimary);

            item_recyclerview.setAdapter(new GridAdapter());
        }


        private class GridAdapter extends RecyclerView.Adapter<BaseHolder>{

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.item_x2_imageview,parent,viewType);
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


    }
```

其他代码我就不贴了，想要看源码的可以直接下载。
总体来说，RecyclerView嵌套RecyclerView是很简单的，而且也相当好用，希望这个demo可以给大家一些灵感。





>有任何问题都可以联系我：mengyuanzz@126.com