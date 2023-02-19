package com.task.ui.base.view

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.task.ui.base.viewmodel.BaseViewModel

public abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), BaseFragmentView {

    private var mActivity: BaseActivity<*, *>? = null

    /**
     * TAG
     */
    protected open lateinit var mTag: String

    /**
     * My Lib Activity Navigator
     */
    protected open var mActivityNavigator: com.library.navigator.activity.ActivityNavigator? = null

    /**
     * My Lib Fragment Navigator
     */
    protected open var mFragmentNavigator: com.library.navigator.fragment.FragmentNavigator? = null

    /**
     * ViewModel
     */
    protected open var mViewModel: VM? = null

    /**
     * ViewBinding
     */
    protected open var mViewBinding: VB? = null

    /**
     * View
     */
    protected open var rootView: View? = null

    /**
     * You need override this method for fragment Logcat tag
     * Example :
     * override fun getFragmentClassName(): String {
     *   return HomeFragment::class.java.simpleName
     * }
     */
    protected abstract fun getFragmentClassName(): String

    /**
     * You need override this method, you can use it for navigate to another activity, send and receive
     * data (bundle, serializable, parcelable) between activity
     */
    protected abstract fun getActivityNavigator(): com.library.navigator.activity.ActivityNavigator?

    /**
     * You need override this method, you can use it for add or replace fragment in activity or fragment,
     * send and receive data (bundle, serializable, parcelable) between activity
     */
    protected abstract fun getFragmentNavigator(): com.library.navigator.fragment.FragmentNavigator?

    /**
     * You need override this method, you can use it for bundle etc.,
     */
    protected abstract fun doInOnCreate(savedInstanceState: Bundle?)

    /**
     * You need override this method.
     * Example :
     * override fun getViewModel(): HomeViewModel {
     *   val localRepository = LocalRepository(this)
     *   val remoteRepository = RemoteRepository(this)
     *   val factory = HomeViewModelFactory(localRepository, remoteRepository)
     *   return viewModelProvider<HomeViewModel>(HomeViewModel::class.java, factory)
     * }
     */
    protected abstract fun getViewModel(): VM

    /**
     * You need override this method.
     * Example :
     * override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup): FragmentHomeBinding {
     *     return FragmentHomeBinding.inflate(inflater, container, false)
     * }
     */
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun init()
    protected abstract fun initView()
    protected abstract fun addTextChangedListener()
    protected abstract fun setOnFocusChangeListener()
    protected abstract fun setupObservers()
    protected abstract fun setupListeners()

    private fun initTag() {
        mTag = getFragmentClassName()
        if (mTag.length > 23) {
            mTag = mTag.substring(0, 22) // first 22 chars
        }
    }

    private fun initViewModel() {
        mViewModel = getViewModel()
    }

    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mViewBinding = getViewBinding(inflater, container)
        rootView =  mViewBinding?.root
    }

    /*
     * OnAttach(Context context) is not call, If API level of the android version is lower than 23.
     * Because OnAttach(Context context) is added in API level 23.
     */
    @TargetApi(23)
    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        initTag()
        Log.i(mTag, "onAttach(@NonNull Context context)");

        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = context
            mActivity = activity
        }
    }

    /*
     * OnAttach(Activity activity) is not call, If API level of the android version is greater than 22.
     * Because OnAttach(Activity activity) is deprecated in API level 23, but must remain to allow compatibility with api<23
     */
    @CallSuper
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        initTag()
        Log.i(mTag, "onAttach(@NonNull Activity activity)")

        if (activity is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = activity
            mActivity = activity
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(mTag, "onCreate(Bundle savedInstanceState)")

        mActivityNavigator = getActivityNavigator()
        mFragmentNavigator = getFragmentNavigator()

        doInOnCreate(savedInstanceState)

        initViewModel()
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(mTag, "onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)")
        initViewBinding(inflater, container)
        return rootView
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(mTag, "onViewCreated(@NonNull View view, Bundle savedInstanceState)")

        init()
        initView()
        addTextChangedListener()
        setOnFocusChangeListener()
        setupObservers()
        setupListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(mTag, "onActivityCreated(@Nullable Bundle savedInstanceState)")
    }

    override fun onStart() {
        super.onStart()
        Log.i(mTag, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(mTag, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(mTag, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(mTag, "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView();
        Log.i(mTag, "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(mTag, "onDestroy()")
    }

    override fun onDetach() {
        if (null != mViewModel) {
            mViewModel = null
        }
        if (null != mViewBinding) {
            mViewBinding = null
        }
        if (null != rootView) {
            rootView = null
        }
        if (null != mActivity) {
            mActivity = null
        }
        super.onDetach()
        Log.i(mTag, "onDetach()")
    }
    /***********************************************************************************************
     *************************************Implemented Method****************************************
     **********************************************************************************************/
    override fun showShortToast(message: String) {
        Log.i(mTag, message)
        getBaseActivity()?.showShortToast(message)
    }

    override fun showShortToast(messageResId: Int) {
        Log.i(mTag, getString(messageResId))
        getBaseActivity()?.showShortToast(messageResId)
    }

    override fun showLongToast(message: String) {
        Log.i(mTag, message)
        getBaseActivity()?.showLongToast(message)
    }

    override fun showLongToast(messageResId: Int) {
        Log.i(mTag, getString(messageResId))
        getBaseActivity()?.showLongToast(messageResId)
    }

    override fun showIndefiniteSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, message)
        getBaseActivity()?.showIndefiniteSnackBar(parent, message, actionText, onClickListener)
    }

    override fun showIndefiniteSnackBar(parent: View, messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, getString(messageResId))
        getBaseActivity()?.showIndefiniteSnackBar(parent, messageResId, actionText, onClickListener)
    }

    override fun showShortSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, message)
        getBaseActivity()?.showShortSnackBar(parent, message, actionText, onClickListener)
    }

    override fun showShortSnackBar(parent: View, messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, getString(messageResId))
        getBaseActivity()?.showShortSnackBar(parent, messageResId, actionText, onClickListener)
    }

    override fun showLongSnackBar(parent: View, message: String, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, message)
        getBaseActivity()?.showLongSnackBar(parent, message, actionText, onClickListener);
    }

    override fun showLongSnackBar(parent: View, messageResId: Int, actionText: String?, onClickListener: View.OnClickListener?) {
        Log.i(mTag, getString(messageResId))
        getBaseActivity()?.showLongSnackBar(parent, messageResId, actionText, onClickListener)
    }

    override fun setVisible(vararg views: View) {
        getBaseActivity()?.setVisible(*views)
    }

    override fun setInVisible(vararg views: View) {
        getBaseActivity()?.setInVisible(*views)
    }

    override fun setGone(vararg views: View) {
        getBaseActivity()?.setGone(*views)
    }

    override fun hideKeyboard() {
        getBaseActivity()?.hideKeyboard()
    }
    /***********************************************************************************************
     ********************************************Helper*********************************************
     **********************************************************************************************/
    private fun getBaseActivity(): BaseActivity<*, *>? {
        return mActivity
    }
}
