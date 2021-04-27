package kz.aviata.ui.saved

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aviata.base.BaseViewModel
import kz.aviata.ui.top_headlines.ArticleMapper
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.butik.domain.usecase.GetFavArticlesListUseCase
import org.koin.core.component.inject

class SavedNewsViewModel(private val mapper: ArticleMapper = ArticleMapper()) : BaseViewModel() {

    private val getFavFavArticlesListUseCase: GetFavArticlesListUseCase by inject()
    private val articles = MutableLiveData<List<ArticleAdapterItem>>()

    fun fetchArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            articles.postValue(
                mapper.mapToDvo(getFavFavArticlesListUseCase.execute(true))
            )
        }
    }

    fun getArticles() = articles as LiveData<List<ArticleAdapterItem>>
}