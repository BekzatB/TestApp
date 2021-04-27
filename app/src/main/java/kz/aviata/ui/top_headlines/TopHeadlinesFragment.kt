package kz.aviata.ui.top_headlines

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kz.aviata.R
import kz.aviata.base.BaseFragment
import kz.aviata.base.EndlessScrollListener
import kz.aviata.base.viewBinding
import kz.aviata.databinding.FragmentTopHeadlinesBinding
import kz.aviata.ui.recycler.NewsAdapter
import kz.aviata.ui.recycler.makeArticleHeaderSticky
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.ArticleDvo
import kz.aviata.utils.showOkDialog
import kz.aviata.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopHeadlinesFragment : BaseFragment(R.layout.fragment_top_headlines) {

    private val binding by viewBinding(FragmentTopHeadlinesBinding::bind)
    private val viewModel: TopHeadlinesViewModel by viewModel()
    private lateinit var scrollListener: EndlessScrollListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVm()
        setupRV()
        setupListeners()
    }

    private fun setupRV() {
        binding.rvNews.adapter = NewsAdapter { articleDvo, view ->
            openDetails(article = articleDvo, view)
        }

        val savedRVItems = viewModel.getSavedRVItems()
        if (savedRVItems.isNotEmpty()) {
            getAdapter().setItems(savedRVItems)
            makeNewsDateSticky(savedRVItems)
        }
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        scrollListener = EndlessScrollListener(
            layoutManager = binding.rvNews.layoutManager as LinearLayoutManager,
            currentPage = viewModel.currentPage,
            viewModel::loadNextHistoryPage
        )
        binding.rvNews.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            addOnScrollListener(scrollListener)
        }
    }


    private fun setupVm() {
        viewModel.setNews().observe(viewLifecycleOwner) {
            if (viewModel.getObserveState()) {
                getAdapter().setItems(it)
                makeNewsDateSticky(it)
            }
        }
        viewModel.addNews().observe(viewLifecycleOwner) {
            if (viewModel.getObserveState()) {
                getAdapter().addItems(it)
                makeNewsDateSticky(getAdapter().getRVItems())
            }
        }
        viewModel.getError().observe(viewLifecycleOwner) {
            requireActivity().showOkDialog(it)
        }
        viewModel.isLoading().subscribe {
            binding.progressBar.visible(it, View.GONE)
        }
    }

    private fun setupListeners() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getAllNews()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun getAdapter() = (binding.rvNews.adapter as NewsAdapter)

    private fun makeNewsDateSticky(articles: List<ArticleAdapterItem>) {

        binding.rvNews.makeArticleHeaderSticky(articles, requireContext())
    }

    private fun openDetails(article: ArticleDvo, view: View) {
        val extras = FragmentNavigatorExtras(
            view to article.urlToImage
        )
        val action =
            TopHeadlinesFragmentDirections.actionFromTopHeadlinesFragmentToDetailsFragment(
                article.title
            )
        findNavController().navigate(action, extras)
    }

    override fun onStop() {
        binding.rvNews.removeOnScrollListener(scrollListener)
        viewModel.saveRVItems(getAdapter().getRVItems())
        viewModel.onStop()
        super.onStop()
    }
}