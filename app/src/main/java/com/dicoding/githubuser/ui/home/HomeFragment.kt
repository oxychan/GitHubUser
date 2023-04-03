package com.dicoding.githubuser.ui.home

import android.annotation.SuppressLint
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
import com.dicoding.githubuser.GithubUserAdapter
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.FragmentHomeBinding
import com.dicoding.githubuser.model.User
import com.dicoding.githubuser.response.ItemsItem
import com.dicoding.githubuser.ui.profile.ProfileFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.svUser.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchUser(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val layoutManager = GridLayoutManager(root.context, 2)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = GithubUserAdapter(ArrayList())

        viewModel.usersDefault.observe(viewLifecycleOwner) { result ->
            result?.let { handleUsersResult(it) }
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun searchUser(username: String) {
        viewModel.getUsers(username).observe(viewLifecycleOwner) { result ->
            result?.let { handleUsersResult(it) }
        }
    }

    private fun handleUsersResult(result: UserResult<List<ItemsItem>>) {
        when (result) {
            is UserResult.Loading -> showLoading(result.isLoading)
            is UserResult.Success -> setUserData(result.data)
            is UserResult.Error -> showError(result.error)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserData(listItem: List<ItemsItem>) {
        val listUser = ArrayList<User>()
        for (item in listItem) {
            listUser.add(User(username = item.login, userProfile = item.avatarUrl))
        }
        val adapter = GithubUserAdapter(listUser)
        binding.rvUser.adapter = adapter

        adapter.setOnItemCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
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
    }

    private fun showError(error: String) {
        showLoading(false)
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}