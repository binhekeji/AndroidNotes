package com.cainiao.framelibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author liangtao
 * @date 2018/11/9 17:53
 * @describe 框架页面
 */
public class FrameFragment extends Fragment {


    public FrameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frame, container, false);
    }

}
