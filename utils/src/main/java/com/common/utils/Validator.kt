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

package com.common.utils

import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Keval on 12-Sep-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object Validator {
    const val PASSWORD_MIN_LENGTH = 6
    private const val PASSWORD_MAX_LENGTH = 50
    private const val EMAIL_MAX_LENGTH = 50
    private const val NAME_MAX_LENGTH = 30
    private const val USERNAME_MAX_LENGTH = 20
    const val USERNAME_MIN_LENGTH = 6

    /**
     * Validate the email address.
     */
    @JvmStatic
    fun isValidEmail(target: String?) = when {
        target == null -> false
        target.length > EMAIL_MAX_LENGTH -> false
        else -> android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * Validate the password address.
     */
    @JvmStatic
    fun isValidPassword(target: String?) = when {
        target == null -> false
        target.isEmpty() -> false
        target.length > PASSWORD_MAX_LENGTH -> false
        target.length < PASSWORD_MIN_LENGTH -> false
        else -> !target.contains(" ")
    }

    /**
     * Validate the username address.
     */
    @JvmStatic
    fun isValidUsername(target: String?) = when {
        target == null -> false
        target.isEmpty() -> false
        target.length > USERNAME_MAX_LENGTH -> false
        target.length < USERNAME_MIN_LENGTH -> false
        else -> !target.contains(" ")
    }

    /**
     * Validate the display name address.
     */
    @JvmStatic
    fun isValidDisplayName(target: String?) = when {
        target == null -> false
        target.isEmpty() -> false
        target.length > NAME_MAX_LENGTH -> false
        else -> true
    }

    /**
     * Validate the url
     */
    @JvmStatic
    fun isValidUrl(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            try {
                URL(target)
                true
            } catch (e: MalformedURLException) {
                Timber.e(e)
                false
            }
        }
    }
}