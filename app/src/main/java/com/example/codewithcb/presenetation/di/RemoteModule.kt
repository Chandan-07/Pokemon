package com.example.codewithcb.presenetation.di

import com.example.codewithcb.data.GetPokemonListRepoImpl
import com.example.codewithcb.domain.GetPokemonListRepository
import com.example.codewithcb.remote.ApiService
import com.example.codewithcb.remote.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideBlogService(): ApiService {
        return RetrofitBuilder.apiService
    }

    @Provides
    @Singleton
    fun provideCharacterRemote(characterRemote: GetPokemonListRepoImpl): GetPokemonListRepository {
        return characterRemote
    }
}
