package com.trentzo.moengagenews.utils

import android.app.Application
import com.trentzo.moengagenews.viewmodels.ArticleListViewModelfactory
import com.trentzo.moengagenews.viewmodels.ArticleRepository

object InjectorUtils {
    fun provideEcgListModelfactory(application: Application):ArticleListViewModelfactory {
        val repository = getEcgListRepository(application)
        return ArticleListViewModelfactory(repository, application)
    }

    private fun getEcgListRepository(application: Application): ArticleRepository = ArticleRepository(application)
}