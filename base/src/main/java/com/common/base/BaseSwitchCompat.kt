package com.common.base

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet
import java.util.*

/**
 * Created by Kevalpatel2106 on 27-Sep-17.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */

class BaseSwitchCompat : SwitchCompat {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        //set type face
        val am = context.applicationContext.assets
        typeface = Typeface.createFromAsset(am, getFont())
    }

    /**
     * Get the font based on the text style.
     *
     * @return font file name.
     */
    private fun getFont(): String {
        val typeface = typeface
        when (typeface?.style ?: Typeface.NORMAL) {
            Typeface.BOLD_ITALIC -> return String.format(Locale.US, "fonts/%s", "OpenSans-BoldItalic.ttf")
            Typeface.ITALIC -> return String.format(Locale.US, "fonts/%s", "OpenSans-Italic.ttf")
            Typeface.BOLD -> return String.format(Locale.US, "fonts/%s", "Poppins-SemiBold.ttf")
            else -> return String.format(Locale.US, "fonts/%s", "Poppins-Regular.ttf")
        }
    }

}
