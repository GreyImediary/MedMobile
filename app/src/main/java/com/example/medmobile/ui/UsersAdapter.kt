package com.example.medmobile.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medmobile.R
import com.example.medmobile.inflate
import com.example.medmobile.mvvm.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(parent.inflate(R.layout.item_user))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(users[position])

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.user_id.text = user.id.toString()
            itemView.user_name.text = user.name
            itemView.user_login.text = itemView.context.getString(R.string.user_login_text, user.login)
            itemView.user_phone.text = itemView.context.getString(R.string.user_phone_text, user.phone)
            itemView.user_role.text = itemView.context.getString(R.string.user_role_text, user.role)
            itemView.user_status.text = user.status

            itemView.user_show.setOnClickListener {

            }

            itemView.user_edit.setOnClickListener {

            }
        }
    }
}