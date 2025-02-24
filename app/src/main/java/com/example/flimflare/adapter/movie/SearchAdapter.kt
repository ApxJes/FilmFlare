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

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>() {

    inner class SearchItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var language: TextView
    private lateinit var movieReleaseDate: TextView

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
        val searchMovie = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvUpcomingPoster)
        movieTitle = holder.itemView.findViewById(R.id.txvUpcomingTitle)
        language = holder.itemView.findViewById(R.id.txvUpcomingLanguage)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvUpcomingReleaseDate)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + searchMovie.poster_path).into(moviePoster)
            movieTitle.text = searchMovie.title
            language.text = searchMovie.original_language
            movieReleaseDate.text = searchMovie.release_date

            setOnClickListener {
                onClick?.let { it(searchMovie) }
            }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onItemClick(listener: (Result) -> Unit) {
        onClick = listener
    }
}