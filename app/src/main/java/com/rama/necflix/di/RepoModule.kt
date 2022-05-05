package com.rama.necflix.di

import com.rama.necflix.domain.Repo
import com.rama.necflix.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindRepo(repoImpl: RepoImpl): Repo
}