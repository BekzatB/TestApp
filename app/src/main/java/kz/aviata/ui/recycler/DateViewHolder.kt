package kz.aviata.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import kz.aviata.base.BaseAdapter
import kz.aviata.databinding.ItemDateBinding
import kz.aviata.ui.top_headlines.dvo.DateDvo

class DateViewHolder(parent: ViewGroup) : BaseAdapter.ViewHolder<DateDvo, ItemDateBinding>(
    ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
)  {

    override fun show(data: DateDvo) {
        binding.tvDate.text = data.date
    }

}