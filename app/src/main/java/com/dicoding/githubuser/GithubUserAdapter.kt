package com.dicoding.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.entity.UserEntity
import com.dicoding.githubuser.databinding.ItemUserBinding

class GithubUserAdapter(private val listUser: MutableList<UserEntity>) :
    RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }

    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity, onItemClickCallback: OnItemClickCallback) {
            Glide.with(itemView.context)
                .load(user.userProfile)
                .into(binding.ivUser)
            binding.tvUsername.text = user.username

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    user
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user, onItemClickCallback)
    }

    fun clearData() {
        listUser.clear()
    }

}


