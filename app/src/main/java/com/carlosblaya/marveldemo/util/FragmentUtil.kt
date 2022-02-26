package com.carlosblaya.marveldemo.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.carlosblaya.marveldemo.R
import com.carlosblaya.marveldemo.ui.characters.list.CharacterListFragment
import com.carlosblaya.marveldemo.ui.main.MainActivity


object FragmentUtil {

    const val TAG_SEARCH: String = "search"
    const val TAG_DETAIL: String = "detail"

    fun changeMainFragment(activity: MainActivity, tag: String, bundle: Bundle?) {
        val fragment = activity.supportFragmentManager.findFragmentByTag(tag)
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        if (fragment == null) {
            getFragmentByTag(
                tag,
                bundle
            )?.let {
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out).add(
                    R.id.fl_container, it, tag
                ).addToBackStack(tag)
            }
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out).show(fragment)
        }
        fragmentTransaction.commit()
    }

    fun getFragmentByTag(tag: String, bundle: Bundle?): Fragment? {
        when (tag) {
            TAG_SEARCH -> return CharacterListFragment()
            TAG_DETAIL -> {
                return null
            }
        }
        return null
    }
}

