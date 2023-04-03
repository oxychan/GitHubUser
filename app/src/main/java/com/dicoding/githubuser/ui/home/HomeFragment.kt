package com.dicoding.githubuser.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchView = binding.svUser
        val layoutManager = GridLayoutManager(root.context, 2)
        binding.rvUser.layoutManager = layoutManager

        homeViewModel.listUsers.observe(viewLifecycleOwner) { listUsers ->
            setUserData(listUsers)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(root.context, it, Toast.LENGTH_LONG).show()
            }
        }

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                (binding.rvUser.adapter as GithubUserAdapter).clearData()
                (binding.rvUser.adapter as GithubUserAdapter).notifyDataSetChanged()
                homeViewModel.getUsers(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return root
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

    private fun showLoading(isLoading: Boolean) {
        binding.pbUser.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}