package com.my.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/6/13/上午10:07
 * DESC:
 */
public class MyListView extends ListView {

    private boolean isCanScroll = true;

    private int downY;
    private int moveY;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("mengyuan", " this.getFirstVisiblePosition()::" + this.getFirstVisiblePosition());
        Log.i("mengyuan", " this.getLastVisiblePosition()::" + this.getLastVisiblePosition());
        Log.i("mengyuan", " this.getChildCount()::" + this.getChildCount());
        Log.i("mengyuan", " this.getCount()::" + this.getCount());
        Log.i("mengyuan", " ev.getAction()::" + ev.getAction());
        Log.i("mengyuan", " ---------------------------------------");


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                isCanScroll = true;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) ev.getRawY();
                //首先明白 moveY - downY > 0  是上滑，否则是下滑
                //上滑的时候 在ListView达到顶部的时候放行；下滑的时候，在ListView达到底部时放行
                //那么就有2种情况需要放行事件：
                //1、上滑 && 达到顶部
                //2、下滑 && 达到底部
                //连在一起就是  ((上滑 && 达到顶部) || (下滑 && 达到底部))
                if ((moveY - downY > 0 && this.getChildCount() - this.getLastVisiblePosition() == 1) || moveY - downY < 0 && this.getLastVisiblePosition() + 1 == this.getCount())
                    isCanScroll = false;

                if (isCanScroll) {
                    requestDisallowInterceptTouchEvent(true);
                }
                break;

        }
        return super.onTouchEvent(ev);
    }


}
