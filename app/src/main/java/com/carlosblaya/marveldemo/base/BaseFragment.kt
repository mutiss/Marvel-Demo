package com.carlosblaya.marveldemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.carlosblaya.marveldemo.util.extensions.showMessage

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    lateinit var fragmentBinding: VB
    abstract val viewModel: VM

    abstract fun getViewBinding(): VB

    abstract fun init()

    open fun setUpViews() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = getViewBinding()
        fragmentBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun showProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity?)?.showProgress()
        }
    }

    fun hideProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity?)?.hideProgress()
        }
    }

    fun showMessage(message: String?) {
        if (activity is BaseActivity) {
            (activity as BaseActivity?)?.showMessage(message.toString())
        }
    }
}