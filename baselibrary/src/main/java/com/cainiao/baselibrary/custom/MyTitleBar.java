package com.cainiao.baselibrary.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cainiao.baselibrary.R;


/**
 * @author liangtao
 * @date 2018/5/28 14:15
 * @describe
 */
public class MyTitleBar extends LinearLayout {
    private View view;
    /**
     * 返回
     */
    private ImageView titleLeft;
    /**
     * 标题文字
     */
    private TextView title;
    /**
     * 更多
     */
    private TextView more;
    /**
     * 标题布局
     */
    private LinearLayout titleBar;
    private ImageView titleRight;


    /**
     * 这个构造方法决定了 在其他页面能否直接new
     * @param context
     */
    public MyTitleBar(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 这个构造方法决定了 能否在消磨布局中引用,并且可以引用自定义属性
     * @param context
     * @param attrs
     */
    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     *  这个构造方法决定了 能否在消磨布局中引用,不仅可以引用自定义属性,还可以设置主题样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化布局
     */
    private void initView(final Context context) {
        //如果加this(ViewGroup)，就不用添加addView方法将控件加入父控件中
        view= View.inflate(context, R.layout.my_title_bar,this);
        titleLeft=(ImageView)view.findViewById(R.id.title_left);
        title=(TextView)view.findViewById(R.id.title);
        more=(TextView)view.findViewById(R.id.more);
        titleBar=(LinearLayout)view.findViewById(R.id.title_bar);
        titleRight=(ImageView)view.findViewById(R.id.menu);
    }

    /**
     * 设置返回键监听事件
     * @param titltLeftOnClickListener
     */
    public void setTitleLeftOnClickListener(OnClickListener titltLeftOnClickListener){
        titleLeft.setOnClickListener(titltLeftOnClickListener);
    }

    /**
     * 设置是否显示标题栏
     * @param titleBarVisibility
     */
    public void setTitleBarVisibility(int titleBarVisibility){
        titleBar.setVisibility(titleBarVisibility);
    }

    /**
     * 设置标题栏背景颜色
     * @param titleBarBackgroundColor
     */
    public void setTitleBarBackgroundColor(int titleBarBackgroundColor){
        titleBar.setBackgroundColor(titleBarBackgroundColor);
    }

    /**
     * 设置标题栏背景资源
     * @param titleBarBackgroundResource
     */
    public void setTitleBarBackgroundResource(int titleBarBackgroundResource){
        titleBar.setBackgroundResource(titleBarBackgroundResource);
    }

    /**
     * 是否隐藏标题栏右侧按钮
     * @param titleRightVisibility
     */
    public void setTitleRightVisibility(int titleRightVisibility){
        titleRight.setVisibility(titleRightVisibility);
    }
    /**
     * 设置标题栏右侧图片
     * @param drawable
     */
    public  void setTitleRightDrawable(Drawable drawable){
        titleRight.setImageDrawable(drawable);
    }
    /**
     * 设置标题栏右侧监听事件
     * @param titleRightOnClickListener
     */
    public void setTitleRightOnClickListener(OnClickListener titleRightOnClickListener){
        titleRight.setOnClickListener(titleRightOnClickListener);
    }


    /**
     * 是否隐藏返回键
     * @param titltLeftVisibility
     */
    public void setTitleLeftVisibility(int titltLeftVisibility){
        titleLeft.setVisibility(titltLeftVisibility);
    }
    /**
     * 设置返回键图片
     * @param drawable
     */
    public  void setTitleLeftDrawable(Drawable drawable){
        titleLeft.setImageDrawable(drawable);
    }


    /**
     * 设置标题文字
     * @param title
     */
    public void setTitle(CharSequence title){
        this.title.setText(title);
    }

    /**
     * 设置标题文字颜色
     * @param titleColor
     */
    public void setTitleColor(int titleColor){
        title.setTextColor(titleColor);
    }

    /**
     * 设置标题文字大小
     * @param titleSize
     */
    public void setTitleSize(float titleSize){
        title.setTextSize(titleSize);
    }

    /**
     * 设置更多文字
     * @param more
     */
    public void setMore(CharSequence more){
        this.more.setText(more);
    }

    /**
     * 设置更多文字颜色
     * @param moreColor
     */
    public void setMoreColor(int moreColor){
        more.setTextColor(moreColor);
    }

    /**
     * 设置更多文字大小
     * @param moreSize
     */
    public void setMoreSize(float moreSize){
        more.setTextSize(moreSize);
    }

    /**
     * 设置右侧标题是否显示
     * @param moreVisibility
     */
    public void setMoreVisibility(int moreVisibility){
        more.setVisibility(moreVisibility);
    }
    /**
     * 设置更多监听事件
     * @param moreOnClickListener
     */
    public void setMoreOnClickListener(OnClickListener moreOnClickListener){
        more.setOnClickListener(moreOnClickListener);
    }

}
