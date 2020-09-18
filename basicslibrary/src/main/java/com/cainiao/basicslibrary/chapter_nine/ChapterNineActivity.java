package com.cainiao.basicslibrary.chapter_nine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.baselibrary.listener.OnItemClickListener;
import com.cainiao.basicslibrary.ChapterEightAdapter;
import com.cainiao.basicslibrary.R;
import com.cainiao.basicslibrary.R2;
import com.cainiao.basicslibrary.chapter_nine.thread.ThreadActivity;
import com.cainiao.basicslibrary.chapter_nine.thread_pool.ThreadPoolActivity;

import butterknife.BindView;

/**
 * @author liangtao
 * @date 2018/12/3 11:30
 * @describe 第九章 Android线程和线程池
 */
public class ChapterNineActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChapterEightAdapter adapter;
    private String[] data;

    @Override
    protected View addView() {
        view=View.inflate(mContext, R.layout.activity_chapter_nine, null);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        mTitleBar.setTitleRightVisibility(View.INVISIBLE);
        mTitleBar.setTitle(getResources().getStringArray(R.array.basics_contents)[8]);
        data = getResources().getStringArray(R.array.chapter_nine);
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
            //线程
            case 0:
                intent.setClass(mContext, ThreadActivity.class);
                break;
            //线程池
            case 1:
                intent.setClass(mContext, ThreadPoolActivity.class);
                break;

            //只有条件不满足的时候才会执行这个方法
            default:
                showToast(data[position]);
                break;
        }
        showToast(data[position]);
        startActivity(intent);
    }
}
