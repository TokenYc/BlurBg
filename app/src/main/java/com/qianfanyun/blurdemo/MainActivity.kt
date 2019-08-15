package com.qianfanyun.blurdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.qianfanyun.blurlib.BlurBg
import com.qianfanyun.blurlib.BlurConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        BlurBg.blur(
            BlurConfig.into(imv_center)
                .setBgCorner(50.0f)
                .setBlurRadius(25)
                .setBlurScale(0.5f)
                .setNormalCoverColor(Color.parseColor("#22000000"))
                .setPressedCoverColor(Color.parseColor("#99000000"))
                .build()
        )

        imv_center.setOnClickListener {
            Toast.makeText(this, "哈哈", Toast.LENGTH_LONG).show()
        }

    }

}
