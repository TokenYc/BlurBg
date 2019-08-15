package com.qianfanyun.blurlib

import android.view.View

/**
 * @date on 2019-08-15  18:53
 * @author ArcherYc
 * @mail  247067345@qq.com
 */
class BlurConfig @JvmOverloads constructor(
    val view: View,
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
        var blurRadius: Int = 25
        var blurScale: Float = 0.5f
        var bgCorner: Float = 0.0f
        var normalCoverColor: Int = 0
        var pressedCoverColor: Int = 0

        fun setBlurRadius(blurRadius: Int): Builder {
            this.blurRadius = blurRadius
            return this
        }

        fun setBlurScale(blurScale: Float): Builder {
            this.blurScale = blurScale
            return this
        }

        fun setBgCorner(bgCorner: Float): Builder {
            this.bgCorner = bgCorner
            return this
        }

        fun setNormalCoverColor(normalCoverColor: Int): Builder {
            this.normalCoverColor = normalCoverColor
            return this
        }

        fun setPressedCoverColor(pressedCoverColor: Int): Builder {
            this.pressedCoverColor = pressedCoverColor
            return this
        }

        fun build(): BlurConfig {
            return BlurConfig(view, blurRadius, blurScale, bgCorner, normalCoverColor, pressedCoverColor)
        }
    }
}