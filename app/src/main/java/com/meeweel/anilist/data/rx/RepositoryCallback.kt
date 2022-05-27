package com.meeweel.anilist.data.rx

import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

interface RepositoryCallback {
    fun handleResponse(response: Single<MaxIdResponse?>?)
    fun handleError()
}