package com.dicoding.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubuser.ui.components.FollowingFollowerFragment
import com.dicoding.githubuser.ui.profile.ProfileFragment

class SectionsPagerAdapter(activity: ProfileFragment) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingFollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingFollowerFragment.ARG_SECTION_NUMBER, position)
        }

        return fragment
    }
}