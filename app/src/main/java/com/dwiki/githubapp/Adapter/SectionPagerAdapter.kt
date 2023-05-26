package com.dwiki.githubapp.Adapter
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dwiki.githubapp.R
import com.dwiki.githubapp.UI.FollowerFragment
import com.dwiki.githubapp.UI.FollowingFragment

class SectionPagerAdapter(private val context: Context, fragment: FragmentManager, bundle: Bundle): FragmentPagerAdapter(fragment,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

   private var fragmentBundle:Bundle

   init {
       fragmentBundle = bundle
   }

    private val tabTitles = intArrayOf(
        R.string.followers,
        R.string.following
    )

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence  = context.resources.getString(tabTitles[position])


}