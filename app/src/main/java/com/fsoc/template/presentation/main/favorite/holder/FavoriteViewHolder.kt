package com.fsoc.template.presentation.main.favorite.holder

import android.content.Context
import android.view.ViewGroup
import com.fsoc.template.presentation.base.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_base_select.view.*

class FavoriteViewHolder(
    parent: ViewGroup,
    layoutID: Int,
    onItemClick: (position: Int) -> Unit
) : BaseViewHolder<String>(parent, layoutID) {

    init {
        itemView.lnBaseSelected.setOnClickListener {
            onItemClick.invoke(adapterPosition)
        }
    }

    override fun bindData(model: String, context: Context) {
        itemView.tvContentSelect.text = model
    }

}