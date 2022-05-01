package com.rama.necflix.data

import android.graphics.drawable.Drawable
import com.rama.necflix.domain.Datasource

class DatasourceImpl(val itemsDao: ItemsDao): Datasource {
    override suspend fun insertDrawableName(list: List<DrawableResourceName>) {
        var count = 0
        for(i in list){
            itemsDao.insertDrawableName(list[count])
            count++
        }

    }

    override suspend fun insertAccountToRoom(account: Accounts) {
        itemsDao.insertAccount(account)
    }

}
