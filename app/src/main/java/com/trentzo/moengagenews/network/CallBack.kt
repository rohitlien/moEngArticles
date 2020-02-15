package com.trentzo.moengagenews.network

import com.trentzo.moengagenews.beans.OfflineArticleData

interface CallBack {
    fun onSuccess( articles : ArrayList<OfflineArticleData>?)
    fun onFailure()
}