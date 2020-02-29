package com.unicloud.core.mvvm.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import com.unicloud.core.mvvm.R
import kotlinx.android.synthetic.main.dialog_confirm.*

/**
 * @author mayu
 * @date 2019/2/2/002
 */
class ConfirmDialog(var ctx: Context) : BaseDialog(ctx) {

    private var mIcon: Int = 0
    private var mTitle: String = "提示"
    private var mContent: String = ""
    private var mCancelTxt: String = "取消"
    private var mConfirmTxt: String = "确定"
    private var mCancel: () -> Unit = {}
    private var mConfirm: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWidthModulus(0.8f)
        setContentView(R.layout.dialog_confirm)
    }

    fun setIcon(icon: Int): ConfirmDialog {
        this.mIcon = icon
        return this
    }

    fun setTitle(titleStr: String): ConfirmDialog {
        this.mTitle = titleStr
        return this
    }

    fun setContent(contentStr: String): ConfirmDialog {
        this.mContent = contentStr
        return this
    }

    fun cancelClick(cancelTxt: String, cancel: () -> Unit): ConfirmDialog {
        this.mCancelTxt = cancelTxt
        this.mCancel = cancel
        return this
    }

    fun confirmClick(confirmTxt: String, confirm: () -> Unit): ConfirmDialog {
        this.mConfirmTxt = confirmTxt
        this.mConfirm = confirm
        return this
    }

    override fun show() {
        super.show()
//        title.setDrawable(ctx.resources.getDrawable(mIcon), ImageText.LEFT)
        title.text = mTitle
        content.text = mContent
        cancelBtn.text = mCancelTxt
        confirmBtn.text = mConfirmTxt
        if (TextUtils.isEmpty(mContent)) {
            content.visibility = View.GONE
            val lp = title.layoutParams as LinearLayout.LayoutParams
            lp.topMargin = SizeUtils.dp2px(30f)
            lp.bottomMargin = SizeUtils.dp2px(30f)
            title.layoutParams = lp
        }
        cancelBtn.setOnClickListener {
            this.mCancel.invoke()
            dismiss()
        }
        confirmBtn.setOnClickListener {
            this.mConfirm.invoke()
            dismiss()
        }
    }
}