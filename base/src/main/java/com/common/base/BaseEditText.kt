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
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Keval Patel on 04/03/17.
 * This base class is to extend the functionality of [AppCompatEditText]. Use this class instead
 * of [android.widget.EditText] through out the application.

 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */

class BaseEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(@Suppress("UNUSED_PARAMETER") context: Context) {
        //Do what ever you want...
    }

    //This a support library bug for is clickable
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event) && isClickable
    }


    /**
     * Check if the edit text is empty.

     * @return True uf there is no text entered in edit text.
     */
    fun isEmpty() = getTrimmedText().isEmpty()

    override fun setError(error: CharSequence?) {
        requestFocus()
        super.setError(error)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)

        if (text != null) setSelection(text.length)
    }

    /**
     * @return Trimmed text.
     */
    fun getTrimmedText() = text.toString().trim { it <= ' ' }

    fun clear() = setText("")
}
