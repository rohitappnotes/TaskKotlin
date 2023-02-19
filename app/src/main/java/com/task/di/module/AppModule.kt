package com.task.di.module

import com.task.AppConstants
import com.task.di.qualifier.isDebug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @isDebug
    fun provideIsDebug(): Boolean {
        return AppConstants.AppConfig.IS_DEBUG
    }
}

