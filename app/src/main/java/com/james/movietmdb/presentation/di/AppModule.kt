package com.james.movietmdb.presentation.di

import com.james.movietmdb.data.api.DetailApiInterface
import com.james.movietmdb.data.api.ListApiInterface
import com.james.movietmdb.domain.repository.DetailMovieRepository
import com.james.movietmdb.data.repository.DetailMovieRepositoryImpl
import com.james.movietmdb.domain.repository.MovieRepository
import com.james.movietmdb.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideListRepository(api: ListApiInterface):MovieRepository{
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(api: DetailApiInterface):DetailMovieRepository{
        return DetailMovieRepositoryImpl(api)
    }
}