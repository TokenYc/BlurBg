package com.qianfanyun.blurlib

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.StateListDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import java.lang.Exception

/**
 * @date on 2019-08-15  18:48
 * @author ArcherYc
 * @mail  247067345@qq.com
 */

class BlurBg {

    companion object {
        fun blur(blurConfig: BlurConfig) {

            val targetView = blurConfig.view

//            targetView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    Log.d("test", System.currentTimeMillis().toString() + "")
//
//                    targetView.viewTreeObserver.removeOnGlobalLayoutListener(this)

            val bgBitmap: Bitmap?
            if (blurConfig.bgBitmapHolder != null) {
                bgBitmap = blurConfig.bgBitmapHolder!!
            } else {
                bgBitmap = getBgBitmap(targetView, blurConfig.bgView)
                if (bgBitmap == null) {
                    return
                }
                blurConfig.bgBitmapHolder = bgBitmap
            }
            val cropBitmap = cropBgBitmap(bgBitmap, targetView) ?: return
            val blurBitmap = blur(
                blurConfig.view.context, cropBitmap,
                blurConfig.blurRadius, blurConfig.blurScale
            )
            if (!cropBitmap.isRecycled) {
                cropBitmap.recycle()
            }
            if (blurConfig.normalCoverColor == 0 && blurConfig.pressedCoverColor == 0) {
                val bitmap =
                    bitmapRound(blurBitmap, blurConfig.bgCorner, blurConfig.normalCoverColor)
                targetView.background = BitmapDrawable(targetView.resources, bitmap)
            } else {
                val stateListDrawable = StateListDrawable()
                val pressed: Int = android.R.attr.state_pressed

                if (blurConfig.normalCoverColor != 0) {
                    val bitmapNormal =
                        bitmapRound(blurBitmap, blurConfig.bgCorner, blurConfig.normalCoverColor)
                    stateListDrawable.addState(
                        IntArray(1) { -pressed },
                        BitmapDrawable(targetView.resources, bitmapNormal)
                    )
                }
                if (blurConfig.pressedCoverColor != 0) {
                    val bitmapPressed =
                        bitmapRound(blurBitmap, blurConfig.bgCorner, blurConfig.pressedCoverColor)
                    stateListDrawable.addState(
                        IntArray(1) { pressed },
                        BitmapDrawable(targetView.resources, bitmapPressed)
                    )
                }
                targetView.background = stateListDrawable
            }
            if (!blurBitmap.isRecycled) {
                blurBitmap.recycle()
            }
            Log.d("test", System.currentTimeMillis().toString() + "")
//                }
//            })
        }

        /**
         * 获取ViewGroup的截图
         */
        fun getBgBitmap(view: View, bgView: View?): Bitmap? {
            val bitmap: Bitmap?
            var parentView: ViewGroup = view.parent as ViewGroup
            while (parentView.parent != null
                && parentView.parent is ViewGroup
            ) {
                parentView = parentView.parent as ViewGroup
                if (parentView.id == R.id.content) {
                    break
                }
            }
            if (bgView == null) {
                view.visibility = View.INVISIBLE
                parentView.buildDrawingCache()
                bitmap = parentView.drawingCache
                view.visibility = View.VISIBLE
            } else {
                bitmap = getBgBitmap(bgView)
            }
            return bitmap
        }

        fun getBgBitmap(bgView: View): Bitmap? {
            if (bgView is ImageView) {
                val imageView = bgView
                val bitmapDrawable: BitmapDrawable = imageView.drawable as BitmapDrawable
                val originBitmap = bitmapDrawable.bitmap

                val matrix = Matrix()
                val ratioView = imageView.height.toFloat() / imageView.width.toFloat()
                val ratioBitmap = originBitmap.height.toFloat() / originBitmap.width.toFloat()

                val x: Int
                val y: Int
                val width: Int
                val height: Int
                val scale: Float
                if (ratioView > ratioBitmap) {
                    width = (originBitmap.height.toFloat() / ratioView).toInt()
                    x = ((originBitmap.width - width) / 2)
                    y = 0
                    height = originBitmap.height
                } else {
                    x = 0
                    width = originBitmap.width
                    height = (originBitmap.width.toFloat() * ratioView).toInt()
                    y = (originBitmap.height - height) / 2
                }
                scale = imageView.width.toFloat() / width.toFloat()
                matrix.preScale(scale, scale)
                try {
                    val bgBitmap =
                        Bitmap.createBitmap(originBitmap, x, y, width, height, matrix, false)
                    return bgBitmap
                } catch (e: Exception) {
                    return null
                }
            } else {
                bgView.buildDrawingCache()
                return bgView.drawingCache
            }

        }

        /**
         * 对父布局进行裁剪,获取控件占据的部分
         */
        private fun cropBgBitmap(bitmap: Bitmap, view: View): Bitmap? {
            val location = IntArray(2)
            view.getLocationInWindow(location)
            try {
                val cropBitmap =
                    Bitmap.createBitmap(bitmap, location[0], location[1], view.width, view.height)
                return cropBitmap
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }


        /**
         * 对bitmap画圆角
         */

        private fun bitmapRound(bitmap: Bitmap, corner: Float, coverColor: Int): Bitmap {
            val targetBitmap: Bitmap
            try {
                targetBitmap =
                    Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            } catch (e: Exception) {
                return bitmap
            }
            val canvas = Canvas(targetBitmap)
            val paint = Paint()
            paint.isAntiAlias = true

            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)

            //相当于清屏
            canvas.drawARGB(0, 0, 0, 0)

            canvas.drawRoundRect(rectF, corner, corner, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            canvas.drawBitmap(bitmap, rect, rect, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            paint.color = coverColor
            canvas.drawRoundRect(rectF, corner, corner, paint)

            return targetBitmap
        }

        private fun blur(context: Context, source: Bitmap, radius: Int, scale: Float): Bitmap {

            val width = Math.round(source.width * scale)
            val height = Math.round(source.height * scale)

            val inputBmp = Bitmap.createScaledBitmap(source, width, height, false)

            val renderScript = RenderScript.create(context)


            // Allocate memory for Renderscript to work with

            val input = Allocation.createFromBitmap(renderScript, inputBmp)
            val output = Allocation.createTyped(renderScript, input.type)

            // Load up an instance of the specific script that we want to use.
            val scriptIntrinsicBlur =
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            scriptIntrinsicBlur.setInput(input)

            // Set the blur radius
            scriptIntrinsicBlur.setRadius(radius.toFloat())

            // Start the ScriptIntrinisicBlur
            scriptIntrinsicBlur.forEach(output)


            // Copy the output to the blurred bitmap
            output.copyTo(inputBmp)


            renderScript.destroy()
            return inputBmp
        }
    }


}