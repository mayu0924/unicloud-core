package com.unicloud.core.demo.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.unicloud.core.demo.R

/**
 * <pre>
 *     author : yu.ma
 *     e-mail : yu.ma@quanshi.com
 *     time   : 2020/4/30 16:52
 *     desc   : AnimLayout
 *     version: 1.0
 * </pre>
 */
class AnimLayout(context: Context, attrs: AttributeSet? = null) : MotionLayout(context, attrs) {

    private val imageView: ImageFilterView by lazy {
        ImageFilterView(context).apply {
            id = R.id.anim_image
            setImageResource(R.mipmap.ic_image)
            roundPercent = 50f
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftToLeft = this@AnimLayout.id
                    rightToRight = this@AnimLayout.id
                    topToTop = this@AnimLayout.id
                    bottomToBottom = this@AnimLayout.id
                }
        }
    }

    init {
        id = if (id == View.NO_ID) View.generateViewId() else id
        setBackgroundResource(R.color.colorRed_light)
        addView(imageView)
        val motionScene = MotionScene(this)
        setScene(motionScene)
    }
}