package com.nloyko.interviewtesttask.di

import android.content.Context
import androidx.room.Room
import com.nloyko.interviewtesttask.database.PointsDao
import com.nloyko.interviewtesttask.database.PointsDatabase
import com.nloyko.interviewtesttask.network.BASE_URL
import com.nloyko.interviewtesttask.network.PointsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * As these types are scoped to the application lifecycle using @Singleton, they're installed
 * in Hilt's ApplicationComponent.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePointsDao(@ApplicationContext context: Context): PointsDao {
        return Room.databaseBuilder(
            context.applicationContext,
            PointsDatabase::class.java,
            "Points.db"
        ).build().pointsDao()
    }

    @Singleton
    @Provides
    fun providePointsService(): PointsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PointsService::class.java)
    }
}
