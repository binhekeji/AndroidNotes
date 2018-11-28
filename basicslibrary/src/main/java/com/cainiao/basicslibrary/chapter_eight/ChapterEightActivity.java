package com.cainiao.basicslibrary.chapter_eight;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.baselibrary.listener.OnItemClickListener;
import com.cainiao.basicslibrary.ChapterEightAdapter;
import com.cainiao.basicslibrary.R;
import com.cainiao.basicslibrary.R2;

import butterknife.BindView;

/**
 * @author liangtao
 * @date 2018/11/28 15:03
 * @describe 第八章 Android数据存储与IO
 */
public class ChapterEightActivity extends BaseActivity implements OnItemClickListener {


    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChapterEightAdapter adapter;
    private String[] data;

    @Override
    protected View addView() {
        view=View.inflate(mContext, R.layout.activity_chapter_eight, null);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        mTitleBar.setTitleRightVisibility(View.INVISIBLE);
        mTitleBar.setTitle(getResources().getStringArray(R.array.basics_contents)[7]);
        data = getResources().getStringArray(R.array.chapter_eight);
        adapter = new ChapterEightAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //设置为垂直布局，这个是默认的
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置增加或者删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onItemClick(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);
        Intent intent=new Intent();
        switch (position) {
            //使用SharedPreferences
            case 0:
                showToast(data[position]);
                break;
            //File存储
            case 1:
                break;
            //SQLite数据库
            case 2:

                break;
            //手势(Gesture)
            case 3:
                break;
            default:
                showToast(data[position]);
                break;
        }
    }
}
