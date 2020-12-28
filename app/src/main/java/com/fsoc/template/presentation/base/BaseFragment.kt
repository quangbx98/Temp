package com.fsoc.template.presentation.base

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.fsoc.template.R
import com.fsoc.template.common.AppCommon
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.extension.*
import com.fsoc.template.common.preferences.SharedPrefsHelper
import com.fsoc.template.data.api.ApiConfig
import com.fsoc.template.data.api.entity.BaseDto
import com.fsoc.template.presentation.MyApplication
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.toolbar_base.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: T
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var errorHandler: BaseErrorHandler

    // animation for switch fragment
    private val navOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    private val notificationBroadcastReceiver = BaseBroadcastReceiver { data, body ->
        Logger.d("data: $data, body: $body")
    }

    private var LOADING_FULL_MODE = true
    private var ERROR_DIALOG_MODE = true

    /**
     * inject fragment
     */
    abstract fun inject(appComponent: AppComponent)

    /**
     * res id of layout
     */
    abstract fun layoutRes(): Int

    /**
     * init or get view model
     */
    abstract fun initViewModel()

    /**
     * setup view, fill data for view
     */
    abstract fun setUpView()

    /**
     * listener update data for view
     */
    abstract fun observable()

    /**
     * fire to get data
     */
    abstract fun fireData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject((activity?.application as MyApplication).getAppComponent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // notification registerReceiver
        val filter = IntentFilter(AppCommon.ACTION_NOTIFICATION_FILTER)
        requireActivity().registerReceiver(notificationBroadcastReceiver, filter)

    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(notificationBroadcastReceiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        View.inflate(context, layoutRes(), view.findViewById(R.id.content))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyBoardWhenTouchOutside()
        hideKeyboard()

        setUpToolbar()

        initViewModel()
        setUpView()

        fireData()
        observable()

        observe(viewModel.mLoading) { loading ->
            showLoading(loading == LOADING.START)
        }
        observe(viewModel.mError) { t ->
            showErrorMsg(t)
            // reset error
            viewModel.mError.value = null
        }

        observe(viewModel.checkAppExpireLiveData) {
            viewModel.checkAppExpireLiveData.value = null
        }
        observe(viewModel.checkMaintenanceModeLiveData) { maintenance ->
            fireData()
            viewModel.checkMaintenanceModeLiveData.value = null
        }

        loading.show(false)
        error.show(false)
    }

    private fun setUpToolbar() {
        (requireActivity() as BaseActivity).toolbar?.apply {
            title?.let {
                toolbarFragmentTitle.text = title
            }
            toolbarFragmentIcon.show(navigationIcon != null)
        }

        toolbarFragmentIcon.click {
            activity?.onBackPressed()
        }
    }

    protected fun hideBackButton() {
        toolbarFragmentIcon.show(false)
    }

    protected fun updateTitle(title: String) {
        toolbarFragmentTitle.text = title
    }

    protected fun showToolbar(b: Boolean) {
        toolbarFragment.show(b)
    }

    open fun showLoading(isLoading: Boolean) {
        //Logger.d("Loading: $isLoading")
        if (LOADING_FULL_MODE) {
            showLoadingFull(isLoading)
        } else {
            // fragment
            showLoadingInside(isLoading)
        }
    }

    open fun showErrorMsg(err: Throwable) {
        if (ERROR_DIALOG_MODE) {
            // fragment dialog
            showErrorDialog(err)
        } else {
            if (LOADING_FULL_MODE) {
                // activity
                showErrorBottom(err)
            } else {
                // fragment msg
                showErrorInside(err)
            }
        }
    }

    private fun showLoadingFull(isLoading: Boolean) {
        // activity
        (activity as BaseActivity).showLoading(isLoading)
    }

    private fun showLoadingInside(isLoading: Boolean) {
        if (isLoading && error.isVisible) {
            error.show(false)
        }
        loading.show(isLoading)
    }

    private fun showErrorBottom(err: Throwable) {
        val msg = errorHandler.handleMsg(err)
        // activity
        (activity as BaseActivity).showError(msg)
    }

    private fun showErrorInside(err: Throwable) {
        val msg = errorHandler.handleMsg(err)
        error.show(true)
        error.text = msg
    }

    // MARK HANDLE ERROR
    private fun showErrorDialog(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                processHttpException(throwable)
            }
            is UnknownHostException -> {
                Logger.d("NO INTERNET -----------------------")
                showErrorDialog("NO INTERNET")
            }
            is SocketTimeoutException -> {
                Logger.d("SocketTimeoutException -----------------------")
                showErrorDialog("Socket Timeout")
            }
            else -> throwable.printStackTrace()
        }
    }

    private fun processHttpException(err: HttpException) {
        val bodyErr = err.response()?.errorBody()?.string()
        try {
            val httpError = SharedPrefsHelper.gson.fromJson<BaseDto>(bodyErr, BaseDto::class.java)
            if (httpError.errors == null) {
                val errMsg = httpError.message ?: "Error"
                showErrorDialog(errMsg)
            } else {
                catchHttpError(httpError.errors[0], httpError.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun catchHttpError(name: String, message: String?) {
        when (name) {
            ApiConfig.TOKEN_DELETED -> {
                viewModel.unregisterFirebase()
            }
            ApiConfig.TOKEN_INVALID -> {
                viewModel.unregisterFirebase()
            }
            ApiConfig.TOKEN_EXPIRED -> {
            }
            ApiConfig.FORCE_UPDATE -> {
            }
            ApiConfig.SERVER_MAINTENANCE -> {
            }
            ApiConfig.UPDATE_TOKEN_FAIL -> {
            }
            ApiConfig.TOKEN_NOT_EXIST -> {
            }

            else -> {
            }
        }
        showErrorDialog(message ?: "Error", name)
    }

    private fun showErrorDialog(errStr: String, name: String? = null) {
        if (showErrorDialogStt) {
            // skip show if showing
            return
        }

        AlertNotice.show(context, errStr) {
            showErrorDialogStt = false
        }
        showErrorDialogStt = true
    }

    private var showErrorDialogStt = false


    /**
     * Navigate with animation param
     */
    fun navigate(resId: Int, args: Bundle? = null, navOptions: NavOptions = this.navOptions) {
        findNavController().navigate(resId, args, navOptions)
    }

    fun navigatePopUpTo(resId: Int, args: Bundle? = null, resIdPopUpTo: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(resIdPopUpTo, true)
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        findNavController().navigate(resId, args, navOptions)
    }

    /**
     * Navigate listener
     */
    fun navigateListener(resId: Int, args: Bundle? = null): View.OnClickListener {
        return Navigation.createNavigateOnClickListener(resId, args)
    }

    fun addBackPress(fragment: Fragment, handleBack: () -> Unit) {
        // This callback will only be called when MyFragment is at least Started.
        val callback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                handleBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(fragment, callback)
    }


    private fun isCurrent(): Boolean {
        try {
            //val lst = parentFragmentManager.fragments.last()
            val lst = fragmentManager?.fragments?.last()
            return lst == this
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}