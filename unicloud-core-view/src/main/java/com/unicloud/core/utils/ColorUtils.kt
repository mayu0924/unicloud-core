package com.unicloud.core.utils

/**
 * @author mayu
 * @date 2020/2/17
 */
object ColorUtils {
    /**
     * 给color添加透明度
     * @param alpha 透明度 0f～1f
     * @param baseColor 基本颜色
     * @return
     */
    fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
        val a = Math.min(255, 0.coerceAtLeast((alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and baseColor
        return a + rgb
    }
}