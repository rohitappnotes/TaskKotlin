package com.task.ui.base.view;

import android.view.View
import androidx.annotation.StringRes

interface BaseFragmentView {
    fun showProgressBar()
    fun hideProgressBar()

    fun showProgressDialog()
    fun hideProgressDialog()

    fun showShortToast(message: String)
    fun showShortToast(@StringRes messageResId: Int)
    fun showLongToast(message: String)
    fun showLongToast(@StringRes messageResId: Int)

    fun showIndefiniteSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?)
    fun showIndefiniteSnackBar(parent: View, @StringRes messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?)
    fun showShortSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?)
    fun showShortSnackBar(parent: View, @StringRes messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?)
    fun showLongSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?)
    fun showLongSnackBar(parent: View, @StringRes messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?)

    fun setVisible(vararg views: View)
    fun setInVisible(vararg views: View)
    fun setGone(vararg views: View)

    fun hideKeyboard()
}