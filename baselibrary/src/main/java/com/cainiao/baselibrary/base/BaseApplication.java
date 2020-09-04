package com.cainiao.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;


/**
 * @author liangtao
 * @date 2018/5/28 14:50
 * @describe 全局
 */
public class BaseApplication extends Application {
    private static List<Activity> activityList = new LinkedList<>();
    private static String TAG="Logger";
    private static BaseApplication instance;
    public static BaseApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化打印日志的TAG,并设置是否打印日志
         * LogLevel.FULL为打印全部日志
         * LogLevel.NONE不打印日志
         * .hideThreadInfo() 隐藏线程信息，默认显示
         */
       /* Logger.init(TAG)
                // 方法栈打印的个数，默认是 2
                .setMethodCount(3)
                .setLogLevel(LogLevel.FULL);*/

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                //是否打印日志 true打印
                //return BuildConfig.DEBUG;
                return true;
            }
        });

        instance=this;

    }

    /**
     * 获取全局上下文
     * */
    public static Context getContext() {
        return instance;
    }

    /**
     * 通过资源id 获取资源内容
     * @param resourcesId
     * @return
     */
    public static String getStr(int resourcesId) {
        String msg = BaseApplication.getInstance().getResources().getString(resourcesId);
        return msg;
    }

    /**
     * 将activity添加到容器中
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有的Activity并finish
     */
    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        // System.exit(0);
    }
}
