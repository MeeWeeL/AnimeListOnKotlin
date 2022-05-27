package com.meeweel.anilist.data.rx

import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

internal interface RepositoryContract {
    fun checkMaxId(
        query: String,
        callback: RepositoryCallback
    )
    fun checkMaxId(
        query: String
    ): Single<MaxIdResponse>
}