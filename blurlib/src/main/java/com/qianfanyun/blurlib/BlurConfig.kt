package com.qianfanyun.blurlib

import android.view.View

/**
 * @date on 2019-08-15  18:53
 * @author ArcherYc
 * @mail  247067345@qq.com
 */
class BlurConfig @JvmOverloads constructor(
    val view: View,
    val bgView: View?,
    val blurRadius: Int,
    val blurScale: Float,
    val bgCorner: Float,
    val normalCoverColor: Int,
    val pressedCoverColor: Int
) {


    companion object {
        fun into(view: View): Builder {
            return Builder(view)
        }
    }

    class Builder(view: View) {
        private val view: View = view
        var mBgView: View? = null
        var mBlurRadius: Int = 25
        var mBlurScale: Float = 0.5f
        var mBgCorner: Float = 0.0f
        var mNormalCoverColor: Int = 0
        var mPressedCoverColor: Int = 0

        fun setBlurRadius(blurRadius: Int): Builder {
            this.mBlurRadius = blurRadius
            return this
        }

        fun setBlurScale(blurScale: Float): Builder {
            this.mBlurScale = blurScale
            return this
        }

        fun setBgCorner(bgCorner: Float): Builder {
            this.mBgCorner = bgCorner
            return this
        }

        fun setNormalCoverColor(normalCoverColor: Int): Builder {
            this.mNormalCoverColor = normalCoverColor
            return this
        }

        fun setPressedCoverColor(pressedCoverColor: Int): Builder {
            this.mPressedCoverColor = pressedCoverColor
            return this
        }

        fun setBgView(bgView: View): Builder {
            this.mBgView = bgView
            return this
        }

        fun build(): BlurConfig {
            return BlurConfig(view, mBgView, mBlurRadius, mBlurScale, mBgCorner, mNormalCoverColor, mPressedCoverColor)
        }
    }
}