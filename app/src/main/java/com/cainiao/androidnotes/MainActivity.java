package com.cainiao.androidnotes;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cainiao.advancedlibrary.AdvancedFragment;
import com.cainiao.baselibrary.base.BaseActivity;
import com.cainiao.basicslibrary.BasicsFragment;
import com.cainiao.framelibrary.FrameFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liangtao
 * @date 2018/9/5 11:05
 * @describe 主页面
 */
public class MainActivity extends BaseActivity {
    /**
     * 基础知识
     */
    @BindView(R.id.basic_knowledge)
    TextView mBasicKnowledge;
    /**
     * 进阶
     */
    @BindView(R.id.advance)
    TextView mAdvance;
    /**
     * 框架
     */
    @BindView(R.id.frame)
    TextView mFrame;
    /**
     * 我的
     */
    @BindView(R.id.mine)
    TextView mMine;

    /**
     * 申明fragment对象
     */
    private Fragment currentFragment;
    private final String BASIC_KNOWLEDGE = "basic_knowledge";
    private final String ADVANCE = "advance";
    private final String FRAME = "frame";
    private final String MINE = "mine";

    @Override
    protected View addView() {
        return View.inflate(mContext, R.layout.activity_main, null);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mTitleBar.setTitle(getResources().getString(R.string.basic_knowledge));
        mTitleBar.setTitleLeftVisibility(View.INVISIBLE);
        mTitleBar.setTitleRightVisibility(View.INVISIBLE);
        replaceFragment(BASIC_KNOWLEDGE);
    }

    /**
     * 绑定点击事件
     *
     * @param view
     */
    @OnClick({R.id.basic_knowledge, R.id.advance, R.id.frame, R.id.mine})
    public void onClick(View view) {
        switch (view.getId()) {
            //基础知识
            case R.id.basic_knowledge:
                mTitleBar.setTitle(getResources().getString(R.string.basic_knowledge));
                replaceFragment(BASIC_KNOWLEDGE);
                replaceTextAttribute(BASIC_KNOWLEDGE);
                break;
            //进阶
            case R.id.advance:
                mTitleBar.setTitle(getResources().getString(R.string.advance));
                replaceFragment(ADVANCE);
                replaceTextAttribute(ADVANCE);
                break;
            //框架
            case R.id.frame:
                mTitleBar.setTitle(getResources().getString(R.string.frame));
                replaceFragment(FRAME);
                replaceTextAttribute(FRAME);
                break;
            //我的
            case R.id.mine:
                mTitleBar.setTitle(getResources().getString(R.string.basic_knowledge));
                replaceFragment(MINE);
                replaceTextAttribute(MINE);
                break;
            default:
                break;
        }
    }

    /**
     * 切换fragment
     *
     * @param tag
     */
    private void replaceFragment(String tag) {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        }
        currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment == null) {
            switch (tag) {
                case BASIC_KNOWLEDGE:
                    currentFragment = new BasicsFragment();
                    break;
                case ADVANCE:
                    currentFragment = new AdvancedFragment();
                    break;
                case FRAME:
                    currentFragment = new FrameFragment();
                    break;
                case MINE:
                    currentFragment = new BasicsFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, currentFragment, tag).commit();
        } else {

            getSupportFragmentManager().beginTransaction().show(currentFragment).commit();
        }
    }

    /**
     * 替换文本属性
     *
     * @param main
     */
    private void replaceTextAttribute(String main) {
        switch (main) {
            case BASIC_KNOWLEDGE:
                mBasicKnowledge.setTextColor(getResources().getColor(R.color.select));
                mBasicKnowledge.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.main_true), null, null);
                mBasicKnowledge.setBackgroundResource(R.drawable.shape_light_pink_rectangle);
                mAdvance.setTextColor(getResources().getColor(R.color.normal));
                mAdvance.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.store_false), null, null);
                mAdvance.setBackgroundResource(R.drawable.shape_white_rectangle);
                mFrame.setTextColor(getResources().getColor(R.color.normal));
                mFrame.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.consult_false), null, null);
                mFrame.setBackgroundResource(R.drawable.shape_white_rectangle);
                mMine.setTextColor(getResources().getColor(R.color.normal));
                mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.mine_false), null, null);
                mMine.setBackgroundResource(R.drawable.shape_white_rectangle);
                break;
            case ADVANCE:
                mBasicKnowledge.setTextColor(getResources().getColor(R.color.normal));
                mBasicKnowledge.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.main_false), null, null);
                mBasicKnowledge.setBackgroundResource(R.drawable.shape_white_rectangle);
                mAdvance.setTextColor(getResources().getColor(R.color.select));
                mAdvance.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.store_true), null, null);
                mAdvance.setBackgroundResource(R.drawable.shape_light_pink_rectangle);
                mFrame.setTextColor(getResources().getColor(R.color.normal));
                mFrame.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.consult_false), null, null);
                mFrame.setBackgroundResource(R.drawable.shape_white_rectangle);
                mMine.setTextColor(getResources().getColor(R.color.normal));
                mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.mine_false), null, null);
                mMine.setBackgroundResource(R.drawable.shape_white_rectangle);
                break;
            case FRAME:
                mBasicKnowledge.setTextColor(getResources().getColor(R.color.normal));
                mBasicKnowledge.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.main_false), null, null);
                mBasicKnowledge.setBackgroundResource(R.drawable.shape_white_rectangle);
                mAdvance.setTextColor(getResources().getColor(R.color.normal));
                mAdvance.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.store_false), null, null);
                mAdvance.setBackgroundResource(R.drawable.shape_white_rectangle);
                mFrame.setTextColor(getResources().getColor(R.color.select));
                mFrame.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.consult_true), null, null);
                mFrame.setBackgroundResource(R.drawable.shape_light_pink_rectangle);
                mMine.setTextColor(getResources().getColor(R.color.normal));
                mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.mine_false), null, null);
                mMine.setBackgroundResource(R.drawable.shape_white_rectangle);
                break;
            case MINE:
                mBasicKnowledge.setTextColor(getResources().getColor(R.color.normal));
                mBasicKnowledge.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.main_false), null, null);
                mBasicKnowledge.setBackgroundResource(R.drawable.shape_white_rectangle);
                mAdvance.setTextColor(getResources().getColor(R.color.normal));
                mAdvance.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.store_false), null, null);
                mAdvance.setBackgroundResource(R.drawable.shape_white_rectangle);
                mFrame.setTextColor(getResources().getColor(R.color.normal));
                mFrame.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.consult_false), null, null);
                mFrame.setBackgroundResource(R.drawable.shape_white_rectangle);
                mMine.setTextColor(getResources().getColor(R.color.select));
                mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.mine_true), null, null);
                mMine.setBackgroundResource(R.drawable.shape_light_pink_rectangle);
                break;
            default:
                break;
        }
    }
}
