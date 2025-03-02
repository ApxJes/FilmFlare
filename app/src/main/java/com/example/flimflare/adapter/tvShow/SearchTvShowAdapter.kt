package com.example.flimflare.adapter.tvShow

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
import com.example.flimflare.util.ConstantsURL.IMAGE_URL
import com.example.flimflare.model.tvShow.Result

class SearchTvShowAdapter: RecyclerView.Adapter<SearchTvShowAdapter.SearchItemViewHolder>() {

    inner class SearchItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var language: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var rating: TextView

    private val differCall = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result,
            newItem: Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCall)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.upcoming_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val searchTvShow = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvUpcomingPoster)
        movieTitle = holder.itemView.findViewById(R.id.txvUpcomingTitle)
        language = holder.itemView.findViewById(R.id.txvUpcomingLanguage)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvUpcomingReleaseDate)
        rating = holder.itemView.findViewById(R.id.txvUpcomingMovieRating)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + searchTvShow.poster_path).into(moviePoster)
            movieTitle.text = searchTvShow.name
            language.text = searchTvShow.original_language
            movieReleaseDate.text = searchTvShow.first_air_date
            rating.text = "Rating: ★ ${searchTvShow.vote_average}"

            setOnClickListener {
                onClick?.let { it(searchTvShow) }
            }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onItemClick(listener: (Result) -> Unit) {
        onClick = listener
    }
}