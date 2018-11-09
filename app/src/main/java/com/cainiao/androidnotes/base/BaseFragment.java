package com.cainiao.androidnotes.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.cainiao.androidnotes.R;
import com.cainiao.androidnotes.custom.dialog.LoadingDialog;
import com.cainiao.androidnotes.listener.OnErrorHintDialogClickListener;
import com.cainiao.androidnotes.utils.DialogUtils;


/**
 * @author liangtao
 * @date 2018/4/17 14:33
 * @describe fragemnt 基类
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 使用子类包名+类名打印日志
     */
    protected String TAG = getClass().getName();
    protected Context mContext;
    private LoadingDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity();
        View view = addView(inflater, container, savedInstanceState);
        initView();
        initListener();
        initData();
        return view;
    }



    /**
     * 添加布局
     */
    protected abstract View addView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 提示
     */
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
    /**
     * dip 转px
     * @param context
     * @param dipValve
     * @return
     */
    public static int dip2px(Context context, float dipValve){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(scale*dipValve+0.5f);
    }

    /**
     * 使用沉浸式状态栏
     */
    public void initImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //getWindow().getDecorView().setPadding(0, getStatusBarHeight(this), 0, 0);
        }
    }
    /**
     * 获取通知栏高度
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName(
                        "com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (android.support.v4.app.Fragment.InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
    public  void showLoadingDialog(){
        if (loadingDialog==null) {
            loadingDialog = new LoadingDialog(mContext, R.style.dialogStyle);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
       /* Window window = loadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager m =window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth()*0.8); // 宽度设置为屏幕
        window.setAttributes(p);*/
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }
    public  void showLoadingDialog(String hint){
        if (loadingDialog==null) {
            loadingDialog = new LoadingDialog(mContext, R.style.dialogStyle);
        }
        if (hint!=null){
            loadingDialog.setHint(hint);
        }
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
       /* Window window = loadingDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager m =window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth()*0.8); // 宽度设置为屏幕
        window.setAttributes(p);*/
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }
    public  void dismissLoadingDialog(){
        if (loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
            loadingDialog=null;
        }
    }

    /**
     * 显示对话框
     * @param content
     */
    protected void showDialog(String content){
        DialogUtils.showErrorHintDialog(mContext,false,null,content,null);
    }
    /**
     * 可以更改对话框标题，显示标题
     * @param title
     * @param content
     */
    protected void showDialog(String title, String content){
        DialogUtils.showErrorHintDialog(mContext,false,title,content,null);
    }

    /**
     * 可以更改对话框标题，是否显示标题
     * @param isShowTitle
     * @param content
     */
    protected void showDialog(boolean isShowTitle,String content){
        DialogUtils.showErrorHintDialog(mContext,isShowTitle,null,content,null);
    }

    /**
     * 可以更改标题，是否显示标题，可以设置按钮监听器
     * @param isShowTitle
     * @param title
     * @param content
     * @param onErrorHintDialogClickListener
     */
    protected void showDialog(boolean isShowTitle, String title, String content, OnErrorHintDialogClickListener onErrorHintDialogClickListener){
        DialogUtils.showErrorHintDialog(mContext,isShowTitle,title,content,onErrorHintDialogClickListener);
    }

    /**
     * 关闭对话框
     */
    protected void dismissDialog(){
        DialogUtils.dismissErrorHintDialog();
    }
    /**
     * 获取版本号
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
