package com.rama.necflix.data

import com.rama.necflix.domain.Datasource
import com.rama.necflix.vo.Resource
import javax.inject.Inject

class DatasourceImpl @Inject constructor(val itemsDao: ItemsDao): Datasource {
    override suspend fun insertDrawableName(list: List<DrawableResourceUrl>) {
        var count = 0
        for(i in 0 .. 7){
            itemsDao.insertDrawableName(list[count])
            count++
        }

    }

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

    override suspend fun getDrawableSrc(): List<DrawableResourceUrl> {
        return itemsDao.getDrawableSrc()
    }

    override suspend fun getAccountsFromDatabase(): Resource<List<Accounts>> {
        return Resource.Success(itemsDao.getAccountsFromDatabase())
    }

}
