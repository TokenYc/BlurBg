package com.qianfanyun.blurdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.qianfanyun.blurlib.BlurBg;
import com.qianfanyun.blurlib.BlurConfig;


public class TestFragment extends Fragment {

    ImageView imvCenter;

    public TestFragment() {
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        imvCenter = view.findViewById(R.id.imv_center);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BlurBg.Companion.blur(BlurConfig.Companion
                .into(imvCenter)
                .build());
    }
}
