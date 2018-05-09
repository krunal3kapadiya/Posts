package com.krunal3kapadiya.posts.network

import com.krunal3kapadiya.posts.BuildConfig
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    /**
     * provides post api
     */
    @JvmStatic
    fun providePostsApi(): PostApi {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .build().create(PostApi::class.java)
    }
}