package com.cainiao.basicslibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author liangtao
 * @date 2018/11/9 17:40
 * @describe 基础知识页面
 */
public class BasicsFragment extends Fragment {


    public BasicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basics, container, false);
    }

}
