package com.cainiao.baselibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cainiao.baselibrary.R;
import com.cainiao.baselibrary.dialog.ErrorHintDialog;
import com.cainiao.baselibrary.listener.OnErrorHintDialogClickListener;


/**
 * @author liangtao
 * @date 2018/5/28 14:46
 * @describe 对话框工具类
 */
public class DialogUtils {
    private static ErrorHintDialog errorHintDialog;


    /**
     * 用户显示错误码等其他的一些重要提示
     * @param context
     * @param title
     * @param content
     * @param onErrorHintDialogClickListener
     */
    public static void showErrorHintDialog(Context context, boolean isShowTitle, String title, String content, OnErrorHintDialogClickListener onErrorHintDialogClickListener){
       // if (errorHintDialog==null) {
            errorHintDialog = new ErrorHintDialog(context, R.style.dialogStyle);
       // }
        errorHintDialog.setOnErrorHintDialogClickListener(onErrorHintDialogClickListener);
        if (isShowTitle){
            errorHintDialog.setTitleVisibility(View.GONE);
            errorHintDialog.setContentPadding(100);
            errorHintDialog.setContentTextSize(20);
        }/*else {
            errorHintDialog.setTitleVisibility(View.VISIBLE);
            errorHintDialog.setContentPadding(50);
            errorHintDialog.setContentTextSize(16);
        }*/
        if (!TextUtils.isEmpty(title)){
            errorHintDialog.setTitle(title);
        }/*else {
            errorHintDialog.setTitle("提示");
        }*/
        errorHintDialog.setContent(content);
    	 /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        Window window = errorHintDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager m =window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        //p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth()*0.8); // 宽度设置为屏幕
        window.setAttributes(p);
        if (!errorHintDialog.isShowing()) {
            errorHintDialog.show();
        }
    }
    public static void dismissErrorHintDialog(){
        if (errorHintDialog!=null&&errorHintDialog.isShowing()){
            errorHintDialog.dismiss();
            errorHintDialog=null;
        }
    }



}
