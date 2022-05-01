package com.rama.necflix.domain

import android.graphics.drawable.Drawable
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceName

class RepoImpl(private val datasource: Datasource): Repo {
    override suspend fun insertDrawableName(list: List<DrawableResourceName>) {
        datasource.insertDrawableName(list)
    }

    override suspend fun insertAccountToRoom(account: Accounts) {
        datasource.insertAccountToRoom(account)
    }

}