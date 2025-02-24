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

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.PopularItemViewHolder>() {

    inner class PopularItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var language: TextView
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.popular_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        val popularMovie = differ.currentList[position]

        moviePoster = holder.itemView.findViewById(R.id.imvPopularPoster)
        movieTitle = holder.itemView.findViewById(R.id.txvPopularTitle)
        movieReleaseDate = holder.itemView.findViewById(R.id.txvPopularReleaseDate)
        language = holder.itemView.findViewById(R.id.txvPopularLanguage)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + popularMovie.poster_path).into(moviePoster)
            movieTitle.text = popularMovie.title
            movieReleaseDate.text = popularMovie.release_date
            language.text = popularMovie.original_language

            setOnClickListener {
                onClick?.let {it(popularMovie)}
            }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onItemClick(listener: (Result) -> Unit) {
        onClick = listener
    }
}