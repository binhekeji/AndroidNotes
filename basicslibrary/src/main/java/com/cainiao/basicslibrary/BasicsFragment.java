package com.cainiao.basicslibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cainiao.baselibrary.base.BaseFragment;
import com.cainiao.baselibrary.listener.OnItemClickListener;
import com.cainiao.basicslibrary.chapter_eight.ChapterEightActivity;

import butterknife.BindView;


/**
 * @author liangtao
 * @date 2018/11/9 17:40
 * @describe 基础知识页面
 */
public class BasicsFragment extends BaseFragment implements OnItemClickListener {


    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    private ChapterEightAdapter adapter;
    private String[] data;

    @Override
    protected View addView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basics, container, false);
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

        data = getResources().getStringArray(R.array.basics_contents);
        adapter = new ChapterEightAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直布局，这个是默认的
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
            //第一章 Android应用和开发环境
            case 0:

                break;
             //第二章 Android应用的界面编程
            case 1:
                break;
            //第三章 Android的事件处理
            case 2:

                break;
            //第四章 Android深入理解Activity和Fragment
            case 3:
                break;
            //第五章 Android使用Intent和IntentFilter进行通信
            case 4:

                break;
            //第六章 Android应用的资源
            case 5:
                break;
            //第七章 Android图形与图像处理
            case 6:

                break;
            //第八章 Android数据存储与IO
            case 7:
                intent.setClass(getActivity(),ChapterEightActivity.class);
                startActivity(intent);
                break;
            //第九章 Android线程和线程池
            case 8:

                break;
            //第十章 Android使用ContentProvider实现数据共享
            case 9:
                break;
            //第十一章 Android Service与BroadcastReceiver
            case 10:

                break;
            //第十二章 Android多媒体应用开发
            case 11:
                break;
            //第十三章 Android OpenGL与3D开发
            case 12:

                break;
            //第十四章 Android网络应用
            case 13:
                break;
            //第十五章 Android管理Android手机桌面
            case 14:

                break;
            //第十六章 Android传感器应用开发
            case 15:
                break;
            //第十七章 Android GPS应用开发
            case 16:

                break;
            //第十八章 Android整合高德Map服务
            case 17:
                break;
            //第十九章 Android合金弹头
            case 18:

                break;
            //第二十章 Android电子拍卖系统
            case 19:
                break;
            default:
                showToast(data[position]);
                break;
        }
    }
}
