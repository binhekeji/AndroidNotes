package com.cainiao.androidnotes.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cainiao.androidnotes.R;
import com.cainiao.androidnotes.listener.OnErrorHintDialogClickListener;


public class ErrorHintDialog extends Dialog implements View.OnClickListener{
	
	private View view;
	private TextView title;
	private TextView content;
	private TextView cancel;
	private OnErrorHintDialogClickListener onErrorHintDialogClickListener;
	public ErrorHintDialog(Context context) {
		super(context);
		initView(context);
	}
	public ErrorHintDialog(Context context, int theme) {
		super(context, theme);
		initView(context);
	}
	public ErrorHintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView(context);
	}
	private void initView(Context context) {
		view= View.inflate(context, R.layout.dialog_hint_error,null);
		setContentView(view);
		title=(TextView)view.findViewById(R.id.title);
		content=(TextView)view.findViewById(R.id.content);
		cancel=(TextView)view.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.cancel:
			if (onErrorHintDialogClickListener!=null){
				onErrorHintDialogClickListener.ensure();
			}else {
				dismiss();
			}
			break;
		}
	}

	/**
	 * 设置标题
	 * @param title
     */
	public void setTitle(String title){
		this.title.setText(title);
	}

	/**
	 * 设置标题颜色
	 * @param titleColor
     */
	public void setTitleColor(int titleColor){
		title.setTextColor(titleColor);
	}
	/**
	 * 是否隐藏标题栏
	 */
	public void setTitleVisibility(int titleVisibility){
		title.setVisibility(titleVisibility);
	}

	/**
	 * 设置内容
	 * @param contents
	 */
	 public void setContent(String contents){
		content.setText(contents);
	}
	/**
	 * 设置内容padding
	 * @param padding
	 */
	public void setContentPadding(int padding){
		content.setPadding(padding,padding,padding,padding);
	}
	/**
	 * 设置内容字体大小
	 * @param contentTextSize
	 */
	public void setContentTextSize(int contentTextSize){
		content.setTextSize(contentTextSize);
	}
	/**
	 * 设置监听器
	 * @param onErrorHintDialogClickListener
	 */
	 public void setOnErrorHintDialogClickListener(OnErrorHintDialogClickListener onErrorHintDialogClickListener){
		 this.onErrorHintDialogClickListener=onErrorHintDialogClickListener;
	 }




}
