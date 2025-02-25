package com.example.flimflare.adapter.save

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flimflare.R
import com.example.flimflare.model.room.TvShowEntity
import com.example.flimflare.ui.save.SaveFragmentDirections
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class SaveTvShowAdapter(
    private val savedMovieList: List<TvShowEntity>
): RecyclerView.Adapter<SaveTvShowAdapter.SaveItemViewHolder>() {

    inner class SaveItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var showPoster: ImageView
    private lateinit var showTitle: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_item, parent, false)
        return SaveItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedMovieList.size
    }

    override fun onBindViewHolder(holder: SaveItemViewHolder, position: Int) {
        val item = savedMovieList[position]

        showPoster = holder.itemView.findViewById(R.id.imvSavePoster)
        showTitle = holder.itemView.findViewById(R.id.txvSaveTitle)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + item.showPoster).into(showPoster)
            showTitle.text = item.showTitle

            setOnClickListener {
                val action = SaveFragmentDirections.actionSaveFragmentToTvShowDetailsFragment(item.showResult)
                findNavController().navigate(action)
            }
        }
    }
}