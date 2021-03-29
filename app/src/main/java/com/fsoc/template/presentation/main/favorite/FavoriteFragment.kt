package com.fsoc.template.presentation.main.favorite

import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel
import com.fsoc.template.presentation.main.favorite.adpter.FavoriteViewAdapter
import com.fsoc.template.presentation.widget.WrapContentLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment<MainViewModel>() {

    private lateinit var adapter: FavoriteViewAdapter
    private lateinit var wrapLayoutManager: WrapContentLinearLayoutManager

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_favorite
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun setUpView() {
        bindRecyclerView()
        viewModel.getData()
        adapter.setData(viewModel.listFavorite)
    }

    private fun bindRecyclerView() {
        adapter = FavoriteViewAdapter(requireContext(), this::onItemClick)
        wrapLayoutManager = WrapContentLinearLayoutManager(requireContext())
        rcvListFavorite.apply {
            setHasFixedSize(true)
            layoutManager = wrapLayoutManager
            adapter = this@FavoriteFragment.adapter
        }
    }

    override fun observable() {
    }

    override fun fireData() {
//        viewModel.checkAppExpire()
    }

    private fun onItemClick(position: Int) {
        navigate(R.id.detailFavoriteFragment)
    }
}