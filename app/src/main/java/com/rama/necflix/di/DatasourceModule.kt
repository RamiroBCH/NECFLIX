package com.rama.necflix.di

import com.rama.necflix.data.DatasourceImpl
import com.rama.necflix.domain.Datasource
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
    abstract fun bindDatasource(datasourceImpl: DatasourceImpl): Datasource
}