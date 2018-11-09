package com.cainiao.androidnotes.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cainiao.androidnotes.R;


/**
 * @author liangtao
 * @date 2018/5/28 14:23
 * @describe 进度对话框
 */
public class LoadingDialog extends Dialog {
    private View view;
    private ProgressBar progressBar;
    private TextView mTvHint;
    private String hint;
    public LoadingDialog(Context context) {
        super(context);
        init(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }
    private void init(Context context){
        view= View.inflate(context, R.layout.dialog_loading,null);
        setContentView(view);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        mTvHint=(TextView)view.findViewById(R.id.hint);

    }
    public void setHint(String hint){
        if (mTvHint!=null && hint!=null){
            mTvHint.setVisibility(View.VISIBLE);
            mTvHint.setText(hint);
        }
    }
    public void setMsgColor(int resColor){
        if(mTvHint != null){
            mTvHint.setTextColor(resColor);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return false;
        } else{
            return false;
        }
    }

}
