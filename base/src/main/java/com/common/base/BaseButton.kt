/*
 *  Copyright 2017 Keval Patel.
 *
 *  Licensed under the GNU General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.common.base

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.MotionEvent
import java.util.*

/**
 * Created by Keval Patel on 04/03/17.
 * This base class is to extend the functionality of [AppCompatButton]. Use this class instead
 * of [android.widget.Button] through out the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class BaseButton : AppCompatButton {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    //This a support library bug for is clickable
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event) && isClickable
    }

    private fun init(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translationZ = 10f
        }
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
            Typeface.BOLD_ITALIC -> return String.format(Locale.US, "fonts/%s", "Poppins-BoldItalic.ttf")
            Typeface.ITALIC -> return String.format(Locale.US, "fonts/%s", "Poppins-Italic.ttf")
            Typeface.BOLD -> return String.format(Locale.US, "fonts/%s", "Poppins-SemiBold.ttf")
            else -> return String.format(Locale.US, "fonts/%s", "Poppins-Regular.ttf")
        }
    }

}
