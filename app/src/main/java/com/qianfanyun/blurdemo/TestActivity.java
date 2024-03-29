package com.qianfanyun.blurdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.qianfanyun.blurlib.BlurBg;
import com.qianfanyun.blurlib.BlurConfig;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ImageView imvCenter = findViewById(R.id.imv_center);
        BlurBg.Companion.blur(BlurConfig.Companion
                .into(imvCenter)
                .setBgCorner(150)
                .setNormalCoverColor(Color.parseColor("#8A0A0A09"))
                .setBlurScale(0.5f)
                .build());
    }
}
