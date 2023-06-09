package com.dicoding.githubuser.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuser.GithubUserAdapter
import com.dicoding.githubuser.MainActivity
import com.dicoding.githubuser.R
import com.dicoding.githubuser.SectionsPagerAdapter
import com.dicoding.githubuser.databinding.FragmentProfileBinding
import com.dicoding.githubuser.model.User
import com.dicoding.githubuser.model.UserDetail
import com.dicoding.githubuser.response.UserResponse
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.abs

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var appBarProfile: AppBarLayout
    private lateinit var ivTbProfile: CircleImageView
    private lateinit var tvTbName: TextView
    private lateinit var ivProfile: CircleImageView
    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvFollowers: TextView
    private lateinit var tvFollowings: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        appBarProfile = binding.abProfile
        ivTbProfile = binding.ivTbProfile
        tvTbName = binding.tvTbUsername
        ivProfile = binding.ivProfile
        tvName = binding.tvName
        tvUsername = binding.tvUsername
        tvFollowers = binding.tvFollower
        tvFollowings = binding.tvFollowing

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        val tabs = binding.tlFollow

        profileViewModel.userDetail.observe(viewLifecycleOwner) { response ->
            val currentUser = setUserDetailData(response)
            Glide.with(root.context)
                .load(currentUser.userProfile)
                .into(ivTbProfile)

            Glide.with(root.context)
                .load(currentUser.userProfile)
                .into(ivProfile)

            tvTbName.text = currentUser.name
            tvName.text = currentUser.name
            tvUsername.text = currentUser.username
            tvFollowers.text = getString(R.string.count_followers, currentUser.followers)
            tvFollowings.text = getString(R.string.count_followings, currentUser.followings)

        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        ivTbProfile.visibility = View.GONE
        tvTbName.visibility = View.GONE
        var isCollapsed = false

        if (arguments != null) {
            if (arguments?.getBoolean(IS_FROM_HOME) as Boolean) {
                val username = arguments?.getString("username")
                profileViewModel.getUser(username.toString())
            }

            if (arguments?.getBoolean(IS_FROM_FOLLOW) as Boolean) {
                val username = arguments?.getString("username")
                profileViewModel.getUser(username.toString())
            }

        } else {
            profileViewModel.getUser("oxychan")
        }

        appBarProfile.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                if (!isCollapsed) {
                    ivTbProfile.visibility = View.VISIBLE
                    tvTbName.visibility = View.VISIBLE
                    isCollapsed = true
                }
            } else {
                if (isCollapsed) {
                    ivTbProfile.visibility = View.GONE
                    tvTbName.visibility = View.GONE
                    isCollapsed = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as MainActivity).binding.navView.visibility = View.VISIBLE

        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (arguments != null) {
            if (arguments?.getBoolean(IS_FROM_FOLLOW) as Boolean) {
                (activity as AppCompatActivity).supportActionBar?.hide()
                (activity as MainActivity).binding.navView.visibility = View.GONE
            }

            if (arguments?.getBoolean(IS_FROM_HOME) as Boolean) {
                (activity as AppCompatActivity).supportActionBar?.hide()
                (activity as MainActivity).binding.navView.visibility = View.GONE
            }
        }
    }

    private fun setUserDetailData(userData: UserResponse): UserDetail {
        return UserDetail(
            userProfile = userData.avatarUrl,
            username = userData.login,
            name = userData.name,
            followers = userData.followers,
            followings = userData.following
        )
    }

    fun selfUpdate(listUser: ArrayList<User>): GithubUserAdapter {
        val adapter = GithubUserAdapter(listUser)

        adapter.setOnItemCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val fragment = ProfileFragment()
                val transaction = parentFragmentManager.beginTransaction()
                val dataBundle = Bundle()
                dataBundle.putString("username", data.username)
                dataBundle.putBoolean(IS_FROM_FOLLOW, true)
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

        return adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbProfile.visibility = View.VISIBLE
        } else {
            binding.pbProfile.visibility = View.GONE
        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val IS_FROM_HOME = "isFromHome"
        const val IS_FROM_FOLLOW = "isFromFollow"
    }
}




















