package kz.aviata.ui.top_headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.aviata.base.BaseViewModel
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.ArticleDvo
import kz.butik.domain.entities.Article
import kz.butik.domain.map
import kz.butik.domain.usecase.GetTopHeadlinesNewsUseCase
import kz.butik.domain.usecase.SaveArticlesUseCase
import org.koin.core.component.inject


class TopHeadlinesViewModel(
    private val mapper: ArticleMapper = ArticleMapper()
) : BaseViewModel() {

    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase by inject()
    private val saveArticleUseCase: SaveArticlesUseCase by inject()
    private val setNews = MutableLiveData<List<ArticleAdapterItem>>()
    private val addNews = MutableLiveData<List<ArticleAdapterItem>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private var isActive = true
    private var isLastPage = false
    private var listItems = mutableListOf<ArticleAdapterItem>()
    private var canObserve: Boolean = true
    var currentPage: Int = 1


    companion object {
        internal const val NEWS_PAGE_SIZE = 15
        internal const val DELAY_INTERVAL = 5000L
    }

    init {
        getAllNews()
    }

    fun loadNextHistoryPage(page: Int) {
        if (isLastPage) return

        currentPage = page
        canObserve = true

        viewModelScope.launch(Dispatchers.IO) {
            fetchArticlesAsync(page).await().doOnSuccess {
                if (it.size < NEWS_PAGE_SIZE) {
                    isLastPage = true
                }
                addNews.postValue(mapper.mapToDvo(it))
                saveArticlesToDb(it)
            }.doOnError {
                error.value = it
            }
        }
    }

    fun getAllNews(currentPage: Int = 1) {
        isActive = true
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
//            while (isActive) {

            fetchArticlesAsync(currentPage).await()
                .doOnSuccess(Dispatchers.IO) {
                    setNews.postValue(mapper.mapToDvo(it))
                    saveArticlesToDb(it)
                }
                .doOnError {
                    error.value = it
                    isActive = false
                }
                .doOnComplete {
                    isLoading.value = false
                }
//                delay(DELAY_INTERVAL)
        }
//        }
    }

    private fun fetchArticlesAsync(page: Int) = viewModelScope.async(Dispatchers.IO) {
        getTopHeadlinesNewsUseCase.execute(page, NEWS_PAGE_SIZE)
    }

    private suspend fun saveArticlesToDb(articles: List<Article>) =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            saveArticleUseCase.execute(articles)
        }

    fun getSavedRVItems() = listItems

    fun saveRVItems(rvItems: List<ArticleAdapterItem>) {
        listItems = rvItems.toMutableList()
    }

    fun getObserveState() = canObserve

    fun setObserveState(observeState: Boolean) {
        canObserve = observeState
    }

    override fun onCleared() {
        super.onCleared()
        listItems.removeAll(listItems)
    }

    fun onStop() {
        isActive = false
        canObserve = false
    }

    fun setNews() = setNews as LiveData<List<ArticleAdapterItem>>
    fun addNews() = addNews as LiveData<List<ArticleAdapterItem>>
    fun getError() = error as LiveData<String>
    fun isLoading() = isLoading as LiveData<Boolean>
}