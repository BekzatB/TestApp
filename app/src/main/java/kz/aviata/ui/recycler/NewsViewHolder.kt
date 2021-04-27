package kz.aviata.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kz.aviata.base.BaseAdapter
import kz.aviata.databinding.ItemNewsBinding
import kz.aviata.ui.top_headlines.dvo.ArticleDvo

class NewsViewHolder(parent: ViewGroup, private val onClick: (ArticleDvo, View) -> Unit) :
    BaseAdapter.ViewHolder<ArticleDvo, ItemNewsBinding>(
        ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) {
    override fun show(data: ArticleDvo) {
        binding.ivArticle.transitionName = data.urlToImage
        setArticleIV(data.urlToImage)
        binding.tvTitle.text = data.title

        itemView.setOnClickListener {
            onClick(data, binding.ivArticle)
        }
    }

    private fun setArticleIV(urlToImage: String) {
        val width = binding.ivArticle.width
        val height = binding.ivArticle.height

        Glide.with(binding.ivArticle)
            .load(urlToImage)
            .override(width, height)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivArticle)
    }
}