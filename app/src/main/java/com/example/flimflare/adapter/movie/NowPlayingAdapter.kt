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
import com.example.flimflare.model.movie.nowPlaying.Result
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class NowPlayingAdapter: RecyclerView.Adapter<NowPlayingAdapter.NowPlayingItemViewHolder>() {

    inner class NowPlayingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieReleaseDate: TextView

    private val differCall = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCall)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingItemViewHolder {
        return NowPlayingItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.now_playing_movie_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NowPlayingItemViewHolder, position: Int) {
        val nowPlayingMovie = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvMoviePoster)
        movieTitle = holder.itemView.findViewById(R.id.txvMovieTitle)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvReleaseDate)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + nowPlayingMovie.poster_path).into(moviePoster)
            movieTitle.text = nowPlayingMovie.title
            movieReleaseDate.text = nowPlayingMovie.release_date

            setOnClickListener {
                onClick?.let {
                    it(nowPlayingMovie.id)
                }
            }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onItemClick(listener: (Int) -> Unit) {
        onClick = listener
    }
}