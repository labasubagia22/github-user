package com.example.githubsearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.R
import com.example.githubsearch.model.User
import kotlinx.android.synthetic.main.list_user.view.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    private val users = ArrayList<User>()

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // load data in layout
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(img_avatar)
                tv_name.text = user.login
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    // setter
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    // set found users
    fun setUsers(users: ArrayList<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}