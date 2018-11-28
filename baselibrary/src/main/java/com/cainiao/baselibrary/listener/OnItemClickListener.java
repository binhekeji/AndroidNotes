package com.cainiao.baselibrary.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * @author liangtao
 * @date 2018/11/28 11:38
 * @describe item点击事件
 */
public interface OnItemClickListener {
    /**
     * 点击事件
     * @param adapterView
     * @param view
     * @param position
     * @param id
     */
    void onItemClick(AdapterView<?> adapterView, View view, int position, long id);
    /**
     * 点击事件
     * @param view
     */
    void onItemClick(View view);
}
