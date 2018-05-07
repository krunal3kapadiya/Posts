package com.common.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.text.TextUtils

/**
 * Created by Kevalpatel2106 on 30-Sep-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object LocationUtils {

    /**
     * Check if the location service is enabled or not?
     *
     * @param context instance of the caller.
     * @return true if the location is enabled else false.
     * @see <a href="https://stackoverflow.com/a/22980843/4690731">https://stackoverflow.com/a/22980843/4690731</a>
     */
    @JvmStatic
    fun isLocationEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                false
            }
        } else {
            @Suppress("DEPRECATION")
            val locationProviders = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            !TextUtils.isEmpty(locationProviders)
        }
    }

    /**
     * Check if the location permission is granted?
     *
     * @param context Context of caller
     * @return true if the location permission is available.
     */
    @JvmStatic
    fun isLocationPermissionAvailable(context: Context) = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}