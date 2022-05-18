package com.rama.necflix.di

import com.rama.necflix.data.DatasourceLocalImpl
import com.rama.necflix.domain.DatasourceLocal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    abstract fun bindDatasource(datasourceImpl: DatasourceLocalImpl): DatasourceLocal
}