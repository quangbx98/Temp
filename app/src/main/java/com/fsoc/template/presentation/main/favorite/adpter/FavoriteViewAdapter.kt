package com.fsoc.template.presentation.main.favorite.adpter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fsoc.template.R
import com.fsoc.template.presentation.base.adapter.BaseViewHolder
import com.fsoc.template.presentation.main.favorite.holder.FavoriteViewHolder

class FavoriteViewAdapter(
    private val context: Context,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    private var listFavorite: ArrayList<String> = arrayListOf()

    fun setData(list: ArrayList<String>) {
        listFavorite.clear()
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return FavoriteViewHolder(parent, R.layout.item_base_select, onItemClick)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val data = listFavorite[position]
        if (holder is FavoriteViewHolder) holder.bindData(data, context)
    }
}
