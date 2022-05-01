package com.rama.necflix.domain

import android.graphics.drawable.Drawable
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceName


interface Repo {
    suspend fun insertDrawableName(list: List<DrawableResourceName>)
    suspend fun insertAccountToRoom(account: Accounts)
}