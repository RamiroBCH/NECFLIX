package com.rama.necflix.di

import com.rama.necflix.data.DatasourceNetworkImpl
import com.rama.necflix.network.DatasourceNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceNetworkModule {

    @Binds
    @Singleton
    abstract fun bindDatasourceNetwork(datasourceNetworkImpl: DatasourceNetworkImpl): DatasourceNetwork
}