package com.my;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/5/24/下午4:28
 * DESC:
 */
public class BaseHolder<T> extends RecyclerView.ViewHolder {


    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(((LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE)).inflate(viewId, parent,false));
    }

    public void refreshData(T data, int position) {

    }
}
