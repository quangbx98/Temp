package com.fsoc.template.common.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.concurrent.Executors


fun Context.hideKeyboardFrom(view: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard() {
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    this.hideKeyboardFrom(view)
}

fun Fragment.hideKeyboard() {
    context?.hideKeyboardFrom(requireView())
}

/**
 * Hide keyboard when User touch out site input is EditText  or search view
 */
fun Fragment.hideKeyBoardWhenTouchOutside() {
    view?.hideKeyBoardWhenTouchOutside()
}

fun View.hideKeyBoardWhenTouchOutside(viewFocus: View? = null) {
    if (this !is EditText) {
        this.setOnTouchListener { _, _ ->
            hideKeyBoard()
            viewFocus?.requestFocus()
            false
        }
    }
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Fragment.getString(stringId: Int): String {
    return this.resources.getString(stringId)
}


private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun runOnIoThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}