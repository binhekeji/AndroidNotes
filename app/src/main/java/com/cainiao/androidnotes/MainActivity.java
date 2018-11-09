package com.cainiao.androidnotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cainiao.androidnotes.base.BaseActivity;

/**
 * @author liangtao
 * @date 2018/9/5 11:05
 * @describe 主页面
 */
public class MainActivity extends BaseActivity {


    @Override
    protected View addView() {
        return View.inflate(mContext,R.layout.activity_main,null);
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
