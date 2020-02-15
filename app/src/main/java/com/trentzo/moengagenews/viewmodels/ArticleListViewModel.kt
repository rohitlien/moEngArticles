package com.trentzo.moengagenews.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.trentzo.moengagenews.network.NetworkClient

class ArticleListViewModel (private val repository: ArticleRepository,
                            application: Application
) : AndroidViewModel(application)