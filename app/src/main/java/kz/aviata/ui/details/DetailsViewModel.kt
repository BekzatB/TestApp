package kz.aviata.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kz.aviata.base.BaseViewModel
import kz.butik.domain.entities.Article
import kz.butik.domain.usecase.GetArticleUseCase
import kz.butik.domain.usecase.UpdateArticleUseCase
import org.koin.core.component.inject

class DetailsViewModel : BaseViewModel() {

    private val updateArticleUseCase: UpdateArticleUseCase by inject()
    private val getArticleUseCase: GetArticleUseCase by inject()
    private val article = MutableLiveData<Article>()
    private val isFavorite = MutableLiveData<Boolean>()

    fun getArticle(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            article.postValue(getArticleUseCase.execute(title))
        }
    }

    fun updateArticle(title: String, isFav: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateArticleUseCase.execute(title, isFav)
            isFavorite.postValue(isFav)
        }
    }

    private fun saveArticleAsync(title: String, isFav: Boolean) = viewModelScope.async(Dispatchers.IO) {
    }

    fun isFav() = isFavorite as LiveData<Boolean>
    fun getArticle() = article as LiveData<Article>
}