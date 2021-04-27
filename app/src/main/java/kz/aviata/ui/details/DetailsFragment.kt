package kz.aviata.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kz.aviata.R
import kz.aviata.base.BaseFragment
import kz.aviata.base.viewBinding
import kz.aviata.databinding.FragmentDetailsBinding
import kz.butik.domain.entities.Article
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModel()
    private val args by lazy { DetailsFragmentArgs.fromBundle(requireArguments()) }
    private var articleUri: String = ""
    private var isFav: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionOnEnter()

        postponeEnterTransition()
        viewModel.getArticle(args.articleTitle)
        setupListeners()
        setupVm()

    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.transition)
    }

    private fun setupListeners() {
        with(binding) {
            btnReadFull.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(articleUri)
                startActivity(intent)
            }
            ivFavorite.setOnClickListener {
                viewModel.updateArticle(args.articleTitle, !isFav)
            }
            ivNavBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupVm() {
        viewModel.getArticle().subscribe {
            isFav = it.isFavorite
            articleUri = it.url
            Log.d("MSG", "${it.content}")
            initView(it)
        }
        viewModel.isFav().subscribe {
            isFav = it
            setFavIcon(isFav)
        }
    }


    private fun initView(article: Article) {
        with(article) {

            binding.ivArticleImage.apply {
                val width = this.width
                val height = this.height
                transitionName = urlToImage

                Glide.with(this)
                    .load(urlToImage)
                    .override(width, height)
                    .dontAnimate()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            startPostponedEnterTransition()
                            return false
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this)
            }


            binding.tvTitle.text = title
            binding.tvAuthor.text = getString(R.string.author, author)
            binding.tvContent.text =
                if (content.length > 200) {
                    content.dropLastWhile {
                        it != '…'
                    }
                } else {
                    content
                }
            setFavIcon(isFavorite)
        }
    }

    private fun setFavIcon(isFav: Boolean) {
        binding.ivFavorite.setImageDrawable(
            when (isFav) {
                true -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_red_24)

                }
                false -> {
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_white_24)
                }
            }
        )
    }

}