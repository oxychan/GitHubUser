package com.dicoding.githubuser.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.githubuser.GithubUserAdapter
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.databinding.FragmentFavouriteBinding
import com.dicoding.githubuser.ui.profile.ProfileFragment

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FavouriteViewModel by viewModels {
        FavouriteViewModelFactory.getInstance(requireActivity())
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root = binding.root

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val layoutManager = GridLayoutManager(root.context, 2)
        binding.rvUserFav.layoutManager = layoutManager

        viewModel.getBookmarkedUsers().observe(viewLifecycleOwner) { result ->
            val adapter = GithubUserAdapter(result)
            adapter.setOnItemCallback(object : GithubUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserEntity) {
                    val fragment = ProfileFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    val dataBundle = Bundle()
                    dataBundle.putString("username", data.username)
                    dataBundle.putBoolean(ProfileFragment.IS_FROM_HOME, true)
                    fragment.arguments = dataBundle
                    transaction.replace(
                        R.id.nav_host_fragment_activity_main,
                        fragment,
                        ProfileFragment::class.java.simpleName
                    )
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

            })

            binding.rvUserFav.adapter = adapter
        }

        return root
    }

}