package com.example.realestatemanager.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.realestatemanager.NetworkConnectivityObserver
import com.example.realestatemanager.data.local.db.PropertyDatabase
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepository
import com.example.realestatemanager.data.local.repositories.PointOfInterestRepositoryImpl
import com.example.realestatemanager.data.local.repositories.PropertyRepository
import com.example.realestatemanager.data.local.repositories.PropertyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) : RequestManager = Glide.with(context)

    @Singleton
    @Provides
    fun providePropertyDatabase(app: Application): PropertyDatabase {
        return Room.databaseBuilder(
            app,
            PropertyDatabase::class.java,
            PropertyDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providePropertyRepository(db: PropertyDatabase): PropertyRepository {
        return PropertyRepositoryImpl(db.propertyDao)
    }

    @Singleton
    @Provides
    fun providePointOfInterestRepository(db : PropertyDatabase) : PointOfInterestRepository{
        return PointOfInterestRepositoryImpl(db.pointOfInterestDao)
    }

    @Singleton
    @Provides
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }



}