package com.krunal3kapadiya.posts

import android.app.Application
import com.facebook.stetho.Stetho

class PostApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }
}