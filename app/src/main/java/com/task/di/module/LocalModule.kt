package com.task.di.module

import android.content.Context
import androidx.room.Room
import com.task.AppConstants
import com.task.data.local.room.dao.CartDao
import com.task.data.local.room.dao.ProductDao
import com.task.data.local.room.database.MyRoomDatabase
import com.task.data.local.sharedpreferences.SharedPreferencesHelper
import com.task.di.qualifier.local.RoomDatabaseName
import com.task.di.qualifier.local.SharedPreferencesName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @SharedPreferencesName
    fun provideSharedPreferencesFileName(): String {
        return AppConstants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(@ApplicationContext context: Context, @SharedPreferencesName sharedPreferencesFileName: String): SharedPreferencesHelper {
        return SharedPreferencesHelper(context, sharedPreferencesFileName)
    }

    @Provides
    @RoomDatabaseName
    fun provideRoomDatabaseFileName(): String {
        return AppConstants.Database.ROOM_DATABASE_FILE_NAME
    }

    @Singleton
    @Provides
    fun provideMyRoomDatabase(
        @ApplicationContext context: Context,
        @RoomDatabaseName roomDatabaseFileName: String
    ): MyRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyRoomDatabase::class.java,
            roomDatabaseFileName
        ).build()
    }

    @Singleton
    @Provides
    fun provideCartDao(myRoomDatabase: MyRoomDatabase): CartDao = myRoomDatabase.cartDao()

    @Singleton
    @Provides
    fun provideProductDao(myRoomDatabase: MyRoomDatabase): ProductDao = myRoomDatabase.productDao()
}

