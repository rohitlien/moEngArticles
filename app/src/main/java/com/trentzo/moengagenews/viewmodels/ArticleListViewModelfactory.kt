package com.trentzo.moengagenews.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trentzo.moengagenews.network.NetworkClient

class ArticleListViewModelfactory(
        private val repository: ArticleRepository,
        private val application: Application
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ArticleListViewModel(repository, application) as T
}