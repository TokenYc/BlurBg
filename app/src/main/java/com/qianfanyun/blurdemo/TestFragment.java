package com.qianfanyun.blurdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.qianfanyun.blurlib.BlurBg;
import com.qianfanyun.blurlib.BlurConfig;


public class TestFragment extends Fragment {

    ImageView imvCenter;
    ImageView imvSecond;
    ImageView imvThird;
    ImageView imvBg;
    Bitmap bitmapHolder;

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
        imvBg = view.findViewById(R.id.imv_bg);
        imvCenter = view.findViewById(R.id.imv_center);
        imvSecond = view.findViewById(R.id.imv_second);
        imvThird = view.findViewById(R.id.imv_third);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imvBg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imvBg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                bitmapHolder = BlurBg.Companion.getBgBitmap(imvBg);
                BlurBg.Companion.blur(BlurConfig.Companion
                        .into(imvCenter)
                        .setBgView(imvBg)
                        .setBgBitmapHolder(bitmapHolder)
                        .build());

                BlurBg.Companion.blur(BlurConfig.Companion
                        .into(imvSecond)
                        .setBgView(imvBg)
                        .setBgBitmapHolder(bitmapHolder)
                        .build());

                BlurBg.Companion.blur(BlurConfig.Companion
                        .into(imvThird)
                        .setBgView(imvBg)
                        .setBgBitmapHolder(bitmapHolder)
                        .build());
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmapHolder!=null){
            if (!bitmapHolder.isRecycled()){
                bitmapHolder.recycle();
            }
        }
    }
}
