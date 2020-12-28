package com.fsoc.template.presentation.main

import android.os.Bundle
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fsoc.template.R
import com.fsoc.template.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun getNavControllerId(): Int {
        return R.id.mainNavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // override setup action bar vs navigation
        // Create an AppBarConfiguration with the correct top-level destinations
        setupActionBarWithNavController(
            mNavController, AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.favoriteFragment,
                    R.id.settingFragment
                )
            )
        )
        setupBottomNavMenu()
    }

    private fun setupBottomNavMenu() {
        mainBottomNavView.setupWithNavController(mNavController)
    }

    fun selectBottomNavMenu(itemId: Int) {
        mainBottomNavView.selectedItemId = itemId
    }
}
