package com.dwiki.githubapp.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.databinding.ListUserBinding

class ListUserAdapter(private val listUser:ArrayList<ListUserResponse>):RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

   @SuppressLint("NotifyDataSetChanged")
   fun getItem(item:ArrayList<ListUserResponse>){
       listUser.apply {
           clear()
           addAll(item)
       }
       notifyDataSetChanged()
   }

    inner class ViewHolder( val binding: ListUserBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user:ListUserResponse){

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(binding.imgUser)
                tvName.text = user.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data:ListUserResponse)
    }
}