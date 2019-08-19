package com.qianfanyun.blurdemo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.widget.Toast
import com.qianfanyun.blurlib.BlurBg
import com.qianfanyun.blurlib.BlurConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imv_center.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                imv_center.viewTreeObserver.removeOnGlobalLayoutListener(this)
                BlurBg.blur(
                    BlurConfig.into(imv_center)
                        .setBlurRadius(25)
                        .setBlurScale(0.5f)
                        .setBgCorner(100.0f)
                        .setNormalCoverColor(Color.parseColor("#8A0A0A09"))
                        .build()
                )
            }
        })



        imv_center.setOnClickListener {
            val testFragment = TestFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fl_container, testFragment)
                .commit()
        }


    }

}
