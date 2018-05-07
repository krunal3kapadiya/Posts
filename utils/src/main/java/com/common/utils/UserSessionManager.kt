package com.common.utils

import android.content.Context

/**
 * Created by Keval on 24-Oct-16.
 * This class manages user login session.
 *
 * @author [&#39;https://github.com/kevalpatel2106&#39;]['https://github.com/kevalpatel2106']
 */

class UserSessionManager(context: Context) {
    private val mSharedPrefsProvider: SharedPrefsProvider = SharedPrefsProvider(context)

    /**
     * Set user session detail.
     *
     * @param userId id
     * @param token  Token of the session
     * @param username   Name of the user
     * @param email  Email address of the user
     */
    @JvmOverloads
    fun setNewSession(userId: String,
                      username: String,
                      email: String,
                      name: String? = null,
                      avatarUrl: String? = null,
                      coverUrl: String? = null,
                      isPasswordSet: Boolean = true,
                      token: String = "") {
        //Save to the share prefs.
        mSharedPrefsProvider.savePreferences(USER_ID, userId)
        mSharedPrefsProvider.savePreferences(USER_NAME, username)
        mSharedPrefsProvider.savePreferences(TOKEN, token)
        mSharedPrefsProvider.savePreferences(USER_EMAIL, email)

        updateUserDetail(
                name = name,
                avatarUrl = avatarUrl,
                coverUrl = coverUrl,
                country = "",
                isPasswordSet = isPasswordSet)
    }

    /**
     * Update the user session detail.
     *
     * @param name   Name of the user
     */
    fun updateUserDetail(name: String?,
                         avatarUrl: String?,
                         coverUrl: String?,
                         country: String? = null,
                         isPasswordSet: Boolean = false) {
        //Save to the share prefs.
        mSharedPrefsProvider.savePreferences(USER_DISPLAY_NAME, name)
        mSharedPrefsProvider.savePreferences(AVATAR_URL, avatarUrl)
        mSharedPrefsProvider.savePreferences(COVER_IMAGE, coverUrl)
        // mSharedPrefsProvider.savePreferences(USER_LOCATION, country)
        mSharedPrefsProvider.savePreferences(USER_IS_PASSWORD_SET, isPasswordSet)
    }

    /**
     * Get the user latlong of current user.
     *
     * @return user latlong.
     */
    val userId: String?
        get() = mSharedPrefsProvider.getStringFromPreferences(USER_ID)

    /**
     * Get the name of current user.
     */
    var userName: String
        get() = mSharedPrefsProvider.getStringFromPreferences(USER_NAME)!!
        set(userName) = mSharedPrefsProvider.savePreferences(USER_NAME, userName)

    /**
     * Get the display name of current user.
     */
    val displayName: String
        get() = mSharedPrefsProvider.getStringFromPreferences(USER_DISPLAY_NAME)!!

    /**
     * Get the avatar of current user.
     **/
    val avatar: String?
        get() = mSharedPrefsProvider.getStringFromPreferences(AVATAR_URL)

    /**
     * Get the sporify token of current user.
     */
    var isPasswordSet: Boolean
        get() = mSharedPrefsProvider.getBoolFromPreferences(USER_IS_PASSWORD_SET)
        set(isSet) = mSharedPrefsProvider.savePreferences(USER_IS_PASSWORD_SET, isSet)

    /**
     * Get the cover of current user.
     *
     * @return user latlong.
     */
    val coverUrl: String?
        get() = mSharedPrefsProvider.getStringFromPreferences(COVER_IMAGE)

    /**
     * Get the email of current user.
     *
     * @return user lat long.
     */
    var userEmail: String?
        get() = mSharedPrefsProvider.getStringFromPreferences(USER_EMAIL)
        set(userEmail) = mSharedPrefsProvider.savePreferences(USER_EMAIL, userEmail)

    /**
     * Get current authentication token
     *
     * @return token
     */
    val token: String?
        get() = mSharedPrefsProvider.getStringFromPreferences(TOKEN)

    /**
     * Clear user session.
     */
    fun clearUserSession() {
        mSharedPrefsProvider.removePreferences(USER_ID)
        mSharedPrefsProvider.removePreferences(USER_EMAIL)
        mSharedPrefsProvider.removePreferences(USER_DISPLAY_NAME)
        mSharedPrefsProvider.removePreferences(USER_NAME)
        mSharedPrefsProvider.removePreferences(AVATAR_URL)
        mSharedPrefsProvider.removePreferences(COVER_IMAGE)
        mSharedPrefsProvider.removePreferences(USER_IS_PASSWORD_SET)
        mSharedPrefsProvider.removePreferences(TOKEN)
    }

    /**
     * Check if the user is currently logged in?
     *
     * @return true if the user is currently logged in.
     */
    val isUserLoggedIn: Boolean
        get() = mSharedPrefsProvider.getStringFromPreferences(USER_ID) != null
                || mSharedPrefsProvider.getStringFromPreferences(TOKEN) != null

    companion object {
        private val TAG = "UserSessionManager"

        //User preference keys.
        private val USER_ID = "USER_ID"
        private val USER_NAME = "USER_NAME"
        private val USER_DISPLAY_NAME = "USER_DISPLAY_NAME"
        private val USER_IS_PASSWORD_SET = "USER_IS_PASSWORD_SET"
        private val AVATAR_URL = "avatar"
        private val COVER_IMAGE = "cover_image"
        private val USER_EMAIL = "USER_EMAIL"
        private val TOKEN = "USER_TOKEN"                       //Authentication token
    }
}
