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
import com.example.flimflare.model.room.MovieEntity
import com.example.flimflare.model.room.TvShowEntity
import com.example.flimflare.ui.save.SaveFragmentDirections
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class SaveAdapter(
    private val savedMovieList: List<MovieEntity>
): RecyclerView.Adapter<SaveAdapter.SaveItemViewHolder>() {

    inner class SaveItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_item, parent, false)
        return SaveItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedMovieList.size
    }

    override fun onBindViewHolder(holder: SaveItemViewHolder, position: Int) {
        val item = savedMovieList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvSavePoster)
        movieTitle = holder.itemView.findViewById(R.id.txvSaveTitle)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + item.moviePoster).into(moviePoster)
            movieTitle.text = item.movieTitle

            setOnClickListener {
                val action = SaveFragmentDirections.actionSaveFragmentToMovieDetailsFragment(item.movieResult)
                findNavController().navigate(action)
            }
        }
    }
}