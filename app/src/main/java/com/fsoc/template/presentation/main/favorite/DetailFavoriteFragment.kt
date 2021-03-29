package com.fsoc.template.presentation.main.favorite

import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.show
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainActivity
import com.fsoc.template.presentation.main.MainViewModel
import com.fsoc.template.presentation.main.favorite.adpter.SlideFavoriteAdapter
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_detail_favorite.*

class DetailFavoriteFragment : BaseFragment<MainViewModel>() {

    private lateinit var adapter: SlideFavoriteAdapter

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_detail_favorite
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity ?: return, viewModelFactory)
            .get(MainViewModel::class.java)
    }

    override fun setUpView() {
        hideToolbarMenu(false)
        collapsingToolbar.title = "Detail Favorite"
        (activity as MainActivity).apply {
            hideBottomNavMenu(false)
            setSupportActionBar(toolbarDetail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setUpViewPager()
    }

    private fun setUpViewPager() {
        adapter = SlideFavoriteAdapter(requireContext())
        vpHeader.adapter = adapter
    }

    override fun observable() {
        // no action
    }

    override fun fireData() {
        // no action
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideToolbarMenu(true)
        (activity as MainActivity).hideBottomNavMenu()
        toolbarFragmentIcon.show(false)
    }
}