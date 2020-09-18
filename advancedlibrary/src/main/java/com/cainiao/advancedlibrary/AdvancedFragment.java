package com.cainiao.advancedlibrary;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * @author liangtao
 * @date 2018/11/9 17:52
 * @describe 进阶页面
 */
public class AdvancedFragment extends Fragment {


    public AdvancedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advanced, container, false);
    }

}
