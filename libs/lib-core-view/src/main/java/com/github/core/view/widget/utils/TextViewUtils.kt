package com.github.core.view.widget.utils

import android.widget.TextView
import java.util.*


class TextViewUtils private constructor() {
    /**
     * 获取Text实际宽度
     * 备注:单行最大宽度
     *
     * @param view                      view
     * @param drawableWidth             drawable宽度
     * @param paddingLeft               内边距-左
     * @param paddingRight              内边距-右
     * @param drawablePaddingHorizontal 水平方向上drawable间距
     * @return
     */
    fun getTextWidth(
        view: TextView?,
        drawableWidth: Int,
        paddingLeft: Int,
        paddingRight: Int,
        drawablePaddingHorizontal: Int
    ): Float {
        if (view == null) return 0f
        var textWidth: Float
        /**
         * 1.是否存在\n换行（获取宽度最大的值）
         * 2.不存在\n获取单行宽度值（判断是否自动换行临界值）
         */
        val originalText = view.text.toString()
        if (originalText.contains("\n")) {
            val originalTextArray: Array<String>
            val widthList: ArrayList<Float>
            originalTextArray = originalText.split("\n").toTypedArray()
            val arrayLen = originalTextArray.size
            widthList = ArrayList(arrayLen)
            for (i in 0 until arrayLen) {
                widthList.add(view.paint.measureText(originalTextArray[i]))
            }
            textWidth = Collections.max(widthList)
        } else {
            textWidth = view.paint.measureText(originalText)
        }
        //计算自动换行临界值，不允许超过换行临界值
        val maxWidth =
            view.width - drawableWidth - paddingLeft - paddingRight - drawablePaddingHorizontal
        if (textWidth > maxWidth) {
            textWidth = maxWidth.toFloat()
        }
        return textWidth
    }

    /**
     * 获取Text实际高度
     * 备注:多行最大高度
     *
     * @param view                    view
     * @param drawableHeight          drawable高度
     * @param paddingTop              内边距-左
     * @param paddingBottom           内边距-右
     * @param drawablePaddingVertical 垂直方向上drawable间距
     * @return
     */
    fun getTextHeight(
        view: TextView?,
        drawableHeight: Int,
        paddingTop: Int,
        paddingBottom: Int,
        drawablePaddingVertical: Int
    ): Float {
        if (view == null) return 0f
        /**
         * 1.单行高度*行数
         * 2.最大高度临界值
         */
        val fontMetrics = view.paint.fontMetrics
        val singleLineHeight =
            Math.abs(fontMetrics.bottom - fontMetrics.top) //单行高度
        var textHeight = singleLineHeight * view.lineCount
        //最大高度临界值，不允许超过最大高度临界值
        val maxHeight =
            view.height - drawableHeight - paddingTop - paddingBottom - drawablePaddingVertical //最大允许的宽度
        if (textHeight > maxHeight) {
            textHeight = maxHeight.toFloat()
        }
        return textHeight
    }

    companion object {
        private var instance: TextViewUtils? = null
        fun get(): TextViewUtils? {
            if (instance == null) {
                synchronized(TextViewUtils::class.java) {
                    if (instance == null) {
                        instance =
                            TextViewUtils()
                    }
                }
            }
            return instance
        }
    }
}