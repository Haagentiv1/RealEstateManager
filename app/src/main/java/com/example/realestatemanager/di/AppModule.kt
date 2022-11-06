package com.example.realestatemanager.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.realestatemanager.data.db.EstateDatabase
import com.example.realestatemanager.data.repositories.EstateRepository
import com.example.realestatemanager.data.repositories.EstateRepositoryImpl
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
    ) = Glide.with(context)

    @Singleton
    @Provides
    fun provideEstateDatabase(app: Application) : EstateDatabase{
        return Room.databaseBuilder(
            app,
            EstateDatabase::class.java,
            EstateDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideEstateRepository(db: EstateDatabase) : EstateRepository{
        return EstateRepositoryImpl(db.estateDao)
    }


}