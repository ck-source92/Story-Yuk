package com.dwicandra.storyyukk.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dwicandra.storyyukk.R

class CustomViewEdittextPassword : AppCompatEditText {

    internal lateinit var mClearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mClearButtonImage =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (s?.length!! < 6) {
                    "Your Passwords must match and must be at least 6 characters in length"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }


}