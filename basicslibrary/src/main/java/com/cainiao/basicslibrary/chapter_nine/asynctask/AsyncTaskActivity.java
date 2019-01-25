package com.cainiao.basicslibrary.chapter_nine.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.basicslibrary.R;
/**
 * @author liangtao
 * @date 2018/12/13 17:41
 * @describe
 */
public class AsyncTaskActivity extends BaseActivity {

    /**
     * AsyncTask:即异步任务,是Android给我们提供的一个处理异步任务的类.通过此类,
     *           可以实现UI线程和后台线程进行通讯,后台线程执行异步任务,并把结果返回给UI线程.
     */

    @Override
    protected View addView() {
        return View.inflate(mContext, R.layout.activity_async_task, null);
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

    /**
     *
     */
    class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
