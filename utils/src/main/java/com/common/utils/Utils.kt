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

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.Window


/**
 * Created by Keval on 08-Sep-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object Utils {
    /**
     * Checks whether network (WIFI/mobile) is available or not.
     *
     * @param context application context.
     * @return true if network available,false otherwise.
     */
    @JvmStatic
    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    /**
     * Get current device id.

     * @param context instance of caller.
     * *
     * @return Device unique id.
     */
    @JvmStatic
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String = Settings.Secure.getString(context.contentResolver,
            Settings.Secure.ANDROID_ID)

    /**
     * Get the device model name.

     * @return Device model (e.g. Samsung Galaxy S4)
     */
    @JvmStatic
    val deviceName: String
        get() = String.format("%s-%s", Build.MANUFACTURER, Build.MODEL)

    @JvmStatic
    fun openUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (a: ActivityNotFoundException) {
            a.printStackTrace()
            WebViewActivity.launch(context, url)
        }
    }

    @JvmStatic
    fun getMonthNumber(monthInitials: String) = when (monthInitials.toLowerCase()) {
        "jan" -> 0
        "feb" -> 1
        "mar" -> 2
        "apr" -> 3
        "may" -> 4
        "jun" -> 5
        "jul" -> 6
        "aug" -> 7
        "sep" -> 8
        "oct" -> 9
        "nov" -> 10
        "dec" -> 11
        else -> throw IllegalStateException("Illegal month.")
    }

    @JvmStatic
    fun getMonthName(month: Int) = when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> throw IllegalStateException("Illegal month.")
    }

    /**
     * Launch Selected Music app with song query else navigate to playstore to download app
     */
    @JvmStatic
    fun launchMusicOrPlayStoreApp(context: Context, appPackage: String, albumName: String, songTitle: String) {
        //Check if the is installed or not
        if (isInstalled(context, appPackage)) {
            val intent = Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH)
            // gets the list of intents that can be loaded.
            val queryIntentActivities = context.packageManager.queryIntentActivities(intent, 0)
            if (!queryIntentActivities.isEmpty()) {
                for (item in queryIntentActivities)
                    if (item.activityInfo.packageName.toLowerCase().contains(appPackage) ||
                            item.activityInfo.name.toLowerCase().contains(appPackage)) {

                        intent.putExtra(MediaStore.EXTRA_MEDIA_ARTIST, albumName)
                        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, songTitle)
                        intent.putExtra(SearchManager.QUERY, songTitle)
                        intent.`package` = item.activityInfo.packageName
                        break
                    }
//              startActivity(Intent.createChooser(share, "Select"));
                context.startActivity(intent)
            }
        } else {
            openUrl(context, "https://play.google.com/store/apps/details?id=$appPackage")
        }
    }

    /**
     * Check if app is installed
     */
    @JvmStatic
    fun isInstalled(context: Context, appId: String): Boolean {
        return try {
            val pm = context.packageManager
            pm.getPackageInfo(appId, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }

    }

    @JvmOverloads
    @JvmStatic
    fun showAlertDialog(context: Context, style: Int = 0, title: String = "", message: String) {
        val builder = if (style == 0) {
            AlertDialog.Builder(context)
        } else {
            AlertDialog.Builder(context, style)
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        val dialog = builder.setMessage(message)
                .setPositiveButton(android.R.string.yes, { dialog, which ->
                    // continue with delete
                    dialog.dismiss()
                })
        val alertDialog = dialog.create()

        //Remove title if title is empty of null
        if (TextUtils.isEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        alertDialog.show()
    }

    @JvmStatic
    fun getAppVersion(context: Context): String {
        var pInfo: PackageInfo? = null
        val versionName: String
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        versionName = pInfo!!.versionName
        return versionName
    }
}