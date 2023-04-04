package com.dicoding.githubuser.ui.components

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.FragmentFollowingFollowerBinding
import com.dicoding.githubuser.model.User
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.response.UserResponse
import com.dicoding.githubuser.ui.profile.ProfileFragment
import com.dicoding.githubuser.ui.profile.ProfileResult
import com.dicoding.githubuser.ui.profile.ProfileViewModel
import com.dicoding.githubuser.ui.profile.ProfileViewModelFactory

class FollowingFollowerFragment : Fragment() {
    private var _binding: FragmentFollowingFollowerBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingFollowerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = GridLayoutManager(root.context, 2)
        binding.rvFollow.layoutManager = layoutManager

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val profileFragment = parentFragment as ProfileFragment
        viewModel = profileFragment.getProfileViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER)

        viewModel.currentUser.observe(viewLifecycleOwner) { result ->
            result.let {
                when (it) {
                    is ProfileResult.User -> {
                        if (index == 0) {
                            viewModel.getFollowing(it.data.login)
                                .observe(viewLifecycleOwner) { res ->
                                    res?.let { result ->
                                        if (res is ProfileResult.Following) {
                                            val data = (result as ProfileResult.Following).data
                                            getFollowings(data)
                                        }
                                    }
                                }
                        } else {
                            viewModel.getFollower(it.data.login)
                                .observe(viewLifecycleOwner) { res ->
                                    res?.let { result ->
                                        if (res is ProfileResult.Follower) {
                                            val data = (result as ProfileResult.Follower).data
                                            getFollowers(data)
                                        }
                                    }
                                }
                        }
                        showLoading(false)
                    }
                    is ProfileResult.Loading -> showLoading(true)
                    is ProfileResult.Error -> showError(it.message)
                    else -> {}
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

    private fun showError(error: String) {
        showLoading(false)
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}