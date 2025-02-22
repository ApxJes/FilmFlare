package com.example.flimflare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.model.details.show.CreatedBy
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class ShowCreatorAdapter: RecyclerView.Adapter<ShowCreatorAdapter.CreatorItemViewHolder>() {

    inner class CreatorItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var creatorProfile: ImageView
    private lateinit var creatorName: TextView

    private val differCallBack = object : DiffUtil.ItemCallback<CreatedBy>() {
        override fun areItemsTheSame(oldItem: CreatedBy, newItem: CreatedBy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreatedBy, newItem: CreatedBy): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_creator, parent, false)
        return CreatorItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CreatorItemViewHolder, position: Int) {
        val creator = differ.currentList[position]

        creatorProfile = holder.itemView.findViewById(R.id.imvCreatorProfile)
        creatorName = holder.itemView.findViewById(R.id.txvShowCreatorName)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + creator.profile_path).into(creatorProfile)
            creatorName.text = creator.name
        }
    }
}