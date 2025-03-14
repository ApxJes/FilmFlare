package com.example.flimflare.adapter.movie

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
import com.example.flimflare.model.movie.Result

import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class TopRateAdapter: RecyclerView.Adapter<TopRateAdapter.TopRateItemViewHolder>() {

    inner class TopRateItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var language: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var movieRating: TextView

    private val differCall = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCall)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRateItemViewHolder {
        return TopRateItemViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.top_rate_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TopRateItemViewHolder, position: Int) {
        val topRateMovie = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvTopRatePoster)
        movieTitle = holder.itemView.findViewById(R.id.txvTopRateTitle)
        language = holder.itemView.findViewById(R.id.txvTopRateLanguage)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvTopRateReleaseDate)
        movieRating = holder.itemView.findViewById(R.id.txvTopRatingMovieRating)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + topRateMovie.poster_path).into(moviePoster)
            movieTitle.text = topRateMovie.title
            language.text = topRateMovie.original_language
            movieReleaseDate.text = topRateMovie.release_date
            movieRating.text = "Rating: ★ ${topRateMovie.vote_average}"

            setOnClickListener { onClick?.let { it(topRateMovie) } }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onItemClick(listener: (Result) -> Unit) {
        onClick = listener
    }
}