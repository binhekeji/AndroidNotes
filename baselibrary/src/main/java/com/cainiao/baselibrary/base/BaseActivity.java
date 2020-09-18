package com.cainiao.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.cainiao.baselibrary.listener.OnErrorHintDialogClickListener;
import com.cainiao.baselibrary.R;
import com.cainiao.baselibrary.custom.MyTitleBar;
import com.cainiao.baselibrary.dialog.LoadingDialog;
import com.cainiao.baselibrary.utils.DialogUtils;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * @author liangtao
 * @date 2018/5/24 15:07
 * @describe activity基类
 */
public abstract class BaseActivity extends FragmentActivity {

    protected MyTitleBar mTitleBar;
    /**
     * 使用子类包名+类名打印日志,如果只用类名getClass().getSimpleName()
     */
    protected String TAG = getClass().getName();
    protected Context mContext;
    protected View view;
    protected LinearLayout.LayoutParams layoutParams;
    protected Toast toast;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.addActivity(this);
        mContext=this;
        view=addView();
        mTitleBar=new MyTitleBar(this);
        layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(view);
        ((ViewGroup)view).addView(mTitleBar,0,layoutParams);
        ButterKnife.bind(this,view);
        initImmersionStatus();//沉浸式状态栏
        initView();
        initData();
        initListener();
    }
    /**
     * 添加View布局
     * @return
     */
    protected abstract View addView();

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
     * 显示提示对话框Toast
     * @param text
     */
    public void showToast(CharSequence text){
        if (!TextUtils.isEmpty(text)){
            if (toast==null){
                toast= Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG);
            }else{
                toast.setText(text);
            }
            toast.show();
        }
    }

    /**
     * 提示
     */
    protected void showToast(Context mContext,String msg) {
        if (!TextUtils.isEmpty(msg)){
            if (toast==null){
                toast= Toast.makeText(mContext,msg, Toast.LENGTH_LONG);
            }else{
                toast.setText(msg);
            }
            toast.show();
        }
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
     * 是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
     */
    protected boolean useThemestatusBarColor = false;
    /**
     * 是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
     */
    protected boolean useStatusBarColor = true;

    /**
     * 使用沉浸式状态栏
     */
    public void initImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //getWindow().getDecorView().setPadding(0, getStatusBarHeight(this), 0, 0);
        }
    }


    /**
     * 使用沉浸式状态栏
     */
   /* public void initImmersionStatus() {

        mImmersionBar = ImmersionBar.with(this);
        //4.4-5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&& Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP) {
            Logger.i("当前系统4.4以上5.0以下");
            //如果标题栏的背景色是白色的话，那么就设置状态栏不透明
            if (getResources().getColor(R.color.white)==getResources().getColor(R.color.title_bar_bg)){
                mImmersionBar.statusBarAlpha(0.2f).init();
            }else {
                mImmersionBar.init();
            }
        }//5.0及以上
        else if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP&& Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            Logger.i("当前系统5.0以上6.0以下");
            //如果标题栏的背景色是白色的话，那么就设置状态栏不透明
            if (getResources().getColor(R.color.white)==getResources().getColor(R.color.title_bar_bg)){
                Logger.i("当前系统5.0以上6.0以下======");
                mImmersionBar.statusBarAlpha(0.2f).init();
            }else {
                Logger.i("当前系统5.0以上6.0以下!!!!====");
                mImmersionBar.init();
            }

        }
        //android6.0,MIUI6.0以及FLYMEOS4.0以后可以对状态栏文字颜色和图标进行修改
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || OSUtils.isMIUI6More()||OSUtils.isFlymeOS4More()) {
            Logger.i("当前系统6.0，MIUI6.0以及FLYMEOS4.0以上");
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }*/
    /**
     * 获取通知栏高度
     *
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
            } catch (Fragment.InstantiationException e) {
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
            }
        }
        return statusHeight;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUI、Flyme和6.0以上版本其他Android
     */
    public static void setStatusBarLightMode(Window window) {
        int type = getStatusBarLightMode(window);
        Logger.i("type:   "+type);
        if (type == 1) {
            MIUISetStatusBarLightMode(window, true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(window, true);
        } else if (type == 3) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {//5.0

        }
    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int getStatusBarLightMode(Window window) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(window, true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(window, true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            } else {//5.0

            }
        }
        return result;
    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    public static void StatusBarDarkMode(Window window) {
        int type = getStatusBarLightMode(window);
        if (type == 1) {
            MIUISetStatusBarLightMode(window, false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(window, false);
        } else if (type == 3) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    public  void showLoadingDialog(){
        if (loadingDialog==null) {
            loadingDialog = new LoadingDialog(this.mContext, R.style.dialogStyle);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }
    public  void showLoadingDialog(String hint){
        if (loadingDialog==null) {
            loadingDialog = new LoadingDialog(mContext, R.style.dialogStyle);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        if (hint!=null){
            loadingDialog.setHint(hint);
        }
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
     * 关闭对话了
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
    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
        dismissLoadingDialog();
    }
}
