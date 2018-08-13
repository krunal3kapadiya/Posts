package com.krunal3kapadiya.posts.di.component

import com.krunal3kapadiya.posts.di.module.ActivityModule
import com.krunal3kapadiya.posts.di.module.ApiModule
import com.krunal3kapadiya.posts.network.PostApi
import dagger.Component

@Component(modules = [ApiModule::class])
public interface CoreComponent {

    fun postApi(): PostApi
}