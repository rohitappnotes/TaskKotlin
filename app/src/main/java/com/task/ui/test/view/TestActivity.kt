package com.task.ui.test.view;

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.task.databinding.ActivityTestBinding
import com.task.ui.base.view.BaseActivity
import com.task.ui.test.viewmodel.TestViewModel
import com.library.navigator.activity.ActivityNavigator
import com.library.navigator.fragment.FragmentNavigator
import com.task.data.remote.server.utils.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>() {

    override fun getActivityClassName(): String {
        return TestActivity::class.java.simpleName
    }

    override fun getActivityNavigator(): ActivityNavigator? {
        //return ActivityNavigator(this)
        return null
    }

    override fun getFragmentNavigator(): FragmentNavigator? {
        return null
    }

    override fun doInOnCreate(savedInstanceState: Bundle?) {
    }

    override fun getViewModel(): TestViewModel {
        val testViewModel: TestViewModel by viewModels()
        return testViewModel
    }

    override fun doBeforeSetContentView() {
    }

    override fun getViewBinding(inflater: LayoutInflater): ActivityTestBinding {
        return ActivityTestBinding.inflate(inflater)
    }

    override fun setupStatusBar() {
    }

    override fun setupAppBar() {
    }

    override fun init() {
    }

    override fun initView() {
    }

    override fun addTextChangedListener() {
    }

    override fun setOnFocusChangeListener() {
    }

    override fun setupObservers() {
        mViewModel?.dataSuccess?.observe(this) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Loading -> {
                    showProgressBar()
                    Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                }
                is ApiResponse.Success -> {
                    hideProgressBar()
                    apiResponse.value.let { baseResponseObjectFormat ->
                        baseResponseObjectFormat?.data?.let {
                            mViewBinding?.localDataTextView?.text = it.job
                        }
                    }
                }
                is ApiResponse.Failure -> {
                    hideProgressBar()
                    Toast.makeText(this, apiResponse.message, Toast.LENGTH_LONG).show()
                    Log.e(mTag, "Success response: ${apiResponse.message}")
                }
                is ApiResponse.NullBody -> {
                    hideProgressBar()
                    Toast.makeText(this, "Null Body", Toast.LENGTH_LONG).show()
                }
            }
        }
        mViewModel?.employee1()
    }

    override fun setupListeners() {
        mViewBinding?.submitMaterialButton?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
            }
        })
    }

    override fun setupNavigationBar() {
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showProgressBar() {
        mViewBinding?.progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        mViewBinding?.progressBar?.visibility = View.GONE
    }

    override fun showProgressDialog() {
    }

    override fun hideProgressDialog() {
    }
    /***********************************************************************************************
     ********************************************Permission*****************************************
     **********************************************************************************************/

    /***********************************************************************************************
     ********************************************Validations****************************************
     **********************************************************************************************/

    /***********************************************************************************************
     ********************************************Open Activity**************************************
     **********************************************************************************************/

    /***********************************************************************************************
     **********************************************Helper*******************************************
     **********************************************************************************************/
    fun setupCategoryRecyclerView() {
    }
}