package com.example.podejscie69
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(
    private val userList: MutableList<User>,
    private val userItemClickListener: UserItemClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface UserItemClickListener {
        fun onUserItemClick(user: User)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.tvUserName)
        val userEmail: TextView = itemView.findViewById(R.id.tvUserEmail)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    userItemClickListener.onUserItemClick(userList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.name
        holder.userEmail.text = currentUser.email
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    fun updateUserList(newList: List<User>) {
        userList.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }


}
