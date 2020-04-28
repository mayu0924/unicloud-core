package com.github.core.view.utils

import android.graphics.drawable.GradientDrawable

/**
 * Shape 工具类
 */
object ShapeUtil {

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor 图形填充色
     * @param radius    图形圆角半径
     * @return 圆角矩形
     */
    fun drawRoundRect(fillColor: Int, radius: Int): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(fillColor)
        shape.cornerRadius = radius.toFloat()
        return shape
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radius      图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    fun drawRoundRect(
        fillColor: Int,
        radius: Int,
        strokeWidth: Int,
        strokeColor: Int
    ): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(fillColor)
        shape.cornerRadius = radius.toFloat()
        shape.setStroke(strokeWidth, strokeColor)
        return shape
    }

    /**
     * 绘制圆角矩形 drawable
     *
     * @param fillColor   图形填充色
     * @param radii       图形圆角半径
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆角矩形
     */
    fun drawRoundRect(
        fillColor: Int,
        radii: FloatArray?,
        strokeWidth: Int,
        strokeColor: Int
    ): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(fillColor)
        shape.cornerRadii = radii
        shape.setStroke(strokeWidth, strokeColor)
        return shape
    }

    /**
     * 绘制圆形
     *
     * @param fillColor   图形填充色
     * @param size        图形的大小
     * @param strokeWidth 边框的大小
     * @param strokeColor 边框的颜色
     * @return 圆形
     */
    fun drawCircle(
        fillColor: Int,
        size: Int,
        strokeWidth: Int,
        strokeColor: Int
    ): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setSize(size, size)
        shape.setColor(fillColor)
        shape.setStroke(strokeWidth, strokeColor)
        return shape
    }
}