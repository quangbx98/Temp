package com.fsoc.template.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.fsoc.template.R
import com.fsoc.template.common.extension.show
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_base.*

abstract class BaseActivity : AppCompatActivity() {

    private var disableBack = false

    protected val mNavController: NavController by lazy {
        findNavController(getNavControllerId())
    }

    /**
     * layout res of activity
     */
    abstract fun layoutRes(): Int

    /**
     * navigation controller id in layout
     */
    abstract fun getNavControllerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)
        View.inflate(this, layoutRes(), findViewById(R.id.activityContent))

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(mNavController)
    }

    override fun onSupportNavigateUp() = mNavController.navigateUp()

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransitionEnter()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun onBackPressed() {
        if (!disableBack) {
            super.onBackPressed()
        }
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading && activityError.isVisible) {
            activityError.show(false)
        }
        activityLoading.show(isLoading)
    }

    fun showError(msg: String) {
        activityError.show(true)
        activityError.text = msg
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun currentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(getNavControllerId())
        navHostFragment?.childFragmentManager?.apply {
            return fragments[0]
        }
        return null
    }

}