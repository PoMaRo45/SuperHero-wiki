package com.example.listapi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listapi.OnClickListener
import com.example.listapi.R
import com.example.listapi.databinding.ItemRecycleBinding
import com.example.listapi.model.DataAllItem

class UserAdapter(private var users: MutableList<DataAllItem>, private val listener: OnClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemRecycleBinding.bind(view)
        fun setListener(user: DataAllItem){
            binding.root.setOnClickListener {
                listener.onClick(user)
            }
        }
    }
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycle, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList : MutableList<DataAllItem>){
        users = newList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        with(holder){

            val sprites = mutableListOf(R.drawable.sprite1,R.drawable.sprite2, R.drawable.sprite3, R.drawable.sprite4, R.drawable.sprite5, R.drawable.sprite6, R.drawable.sprite7)
            binding.constraint.setBackgroundResource(sprites[(user.id%7)])
            setListener(user)
            binding.name.text = user.name

            if (user.name.startsWith("Spider")) binding.constraint.setBackgroundResource(R.drawable.sprite_spiderman)
            else if (user.name.uppercase().contains("SUPER",)) {
                binding.constraint.setBackgroundResource(R.drawable.sprite_superman)
                binding.name.setTextColor(Color.WHITE)
            }
            else if (user.name.uppercase().contains("BAT",)){
                binding.constraint.setBackgroundResource(R.drawable.sprite_batman)
                binding.name.setTextColor(Color.WHITE)
            }
            else binding.name.setTextColor(Color.BLACK)
            Glide.with(context)
                .load(user.images.md)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.ivSprite)
        }
    }
    override fun getItemCount(): Int {
        return users.size
    }


}
