package com.dicoding.githubuser.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.githubuser.databinding.FragmentFollowingFollowerBinding
import com.dicoding.githubuser.model.User
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.ui.profile.ProfileFragment
import com.dicoding.githubuser.ui.profile.ProfileViewModel

class FollowingFollowerFragment : Fragment() {
    private var _binding: FragmentFollowingFollowerBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingFollowerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(root.context, 2)
        binding.rvFollow.layoutManager = layoutManager

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileViewModel =
            ViewModelProvider(requireParentFragment()).get(ProfileViewModel::class.java)
        val index = arguments?.getInt(ARG_SECTION_NUMBER)

        profileViewModel.userDetail.observe(viewLifecycleOwner) {
            if (index == 0) {
                profileViewModel.listFollowings.observe(viewLifecycleOwner) { followings ->
                    getFollowings(followings)
                }
                profileViewModel.isLoadingFollowings.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            } else {
                profileViewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                    getFollowers(followers)
                }
                profileViewModel.isLoadingFollowers.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            }
        }

    }

    private fun getFollowers(listFollowers: List<ItemsItem>) {
        val followers = ArrayList<User>()
        for (item in listFollowers) {
            followers.add(User(userProfile = item.avatarUrl, username = item.login))
        }

        val adapter = (parentFragment as ProfileFragment).selfUpdate(followers)

        binding.rvFollow.adapter = adapter
    }

    private fun getFollowings(listFollowings: List<ItemsItem>) {
        val followings = ArrayList<User>()
        for (item in listFollowings) {
            followings.add(User(userProfile = item.avatarUrl, username = item.login))
        }

        val adapter = (parentFragment as ProfileFragment).selfUpdate(followings)

        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}