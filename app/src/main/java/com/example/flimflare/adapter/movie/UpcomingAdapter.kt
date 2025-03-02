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

class UpcomingAdapter: RecyclerView.Adapter<UpcomingAdapter.UpcomingItemViewHolder>() {

    inner class UpcomingItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingItemViewHolder {
        return UpcomingItemViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.upcoming_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UpcomingItemViewHolder, position: Int) {
        val upcomingMovie = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvUpcomingPoster)
        movieTitle = holder.itemView.findViewById(R.id.txvUpcomingTitle)
        language = holder.itemView.findViewById(R.id.txvUpcomingLanguage)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvUpcomingReleaseDate)
        movieRating = holder.itemView.findViewById(R.id.txvUpcomingMovieRating)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + upcomingMovie.poster_path).into(moviePoster)
            movieTitle.text = upcomingMovie.title
            language.text = upcomingMovie.original_language
            movieReleaseDate.text = upcomingMovie.release_date
            movieRating.text = "Rating: ★ ${upcomingMovie.vote_average}"

            setOnClickListener {
                onClick?.let { it(upcomingMovie) }
            }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onItemClick(listener: (Result) -> Unit) {
        onClick = listener
    }
}