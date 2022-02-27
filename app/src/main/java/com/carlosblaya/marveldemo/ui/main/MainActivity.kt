package com.carlosblaya.marveldemo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.carlosblaya.marveldemo.R
import com.carlosblaya.marveldemo.base.BaseActivity
import com.carlosblaya.marveldemo.databinding.ActivityMainBinding
import com.carlosblaya.marveldemo.ui.characters.list.CharacterListFragment
import com.carlosblaya.marveldemo.util.FragmentUtil
import com.carlosblaya.marveldemo.util.extensions.fadeOut
import com.carlosblaya.marveldemo.util.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        setupClicks()
    }

    private fun setupClicks() {
        binding.headbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViews() {
        FragmentUtil.changeMainFragment(this, FragmentUtil.TAG_SEARCH, null)
    }

    fun setTitleHeadbar(title: String) {
        binding.headbar.tvTitleHeadbar.text = title
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        fm.let {
            if (fm.backStackEntryCount > 1) {
                fm.popBackStackImmediate()
                val f: Fragment? = fm.findFragmentById(R.id.fl_container)
                f.let {
                    if (f is CharacterListFragment) {
                        setTitleHeadbar(resources.getString(R.string.search))
                        binding.headbar.ivBack.fadeOut()
                    }
                }
            } else {
                finish()
            }
        }
    }
}