package kz.aviata.ui.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import kz.aviata.R
import kz.aviata.base.BaseFragment
import kz.aviata.base.viewBinding
import kz.aviata.databinding.FragmentSavedNewsBinding
import kz.aviata.ui.recycler.NewsAdapter
import kz.aviata.ui.recycler.makeArticleHeaderSticky
import kz.aviata.ui.top_headlines.dvo.ArticleDvo
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedNewsFragment : BaseFragment(R.layout.fragment_saved_news) {

    private val binding by viewBinding(FragmentSavedNewsBinding::bind)
    private val viewModel: SavedNewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchArticles()
        setupRV()
        setupVm()
    }

    private fun setupRV() {
        binding.rvNews.adapter = NewsAdapter { articleDvo, view ->
            openDetails(articleDvo, view)
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun setupVm() {
        viewModel.getArticles().subscribe {
            Log.d("MSG", "${it.size}")
            getAdapter().setItems(it)
            binding.rvNews.makeArticleHeaderSticky(it, requireContext())
        }
    }

    private fun getAdapter() = (binding.rvNews.adapter as NewsAdapter)


    private fun openDetails(articleDvo: ArticleDvo, view: View) {
        val extras = FragmentNavigatorExtras(
            view to articleDvo.urlToImage
        )

        val action =
            SavedNewsFragmentDirections.actionFromSavedNewsFragmentToDetailsFragment(articleTitle = articleDvo.title)
        findNavController().navigate(action, extras)
    }

}