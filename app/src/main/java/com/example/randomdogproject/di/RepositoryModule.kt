package com.example.randomdogproject.di

import com.example.randomdogproject.data.DogRepository
import com.example.randomdogproject.data.impl.DogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDogRepository(repository: DogRepositoryImpl): DogRepository {
        return repository
    }
}