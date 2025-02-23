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

import com.example.flimflare.model.movie.topRate.TopRateResult
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class TopRateAdapter: RecyclerView.Adapter<TopRateAdapter.TopRateItemViewHolder>() {

    inner class TopRateItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var language: TextView
    private lateinit var movieReleaseDate: TextView

    private val differCall = object : DiffUtil.ItemCallback<TopRateResult>(){
        override fun areItemsTheSame(oldItem: TopRateResult, newItem: TopRateResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopRateResult, newItem: TopRateResult): Boolean {
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

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + topRateMovie.poster_path).into(moviePoster)
            movieTitle.text = topRateMovie.title
            language.text = topRateMovie.original_language
            movieReleaseDate.text = topRateMovie.release_date

            setOnClickListener { onClick?.let { it(topRateMovie.id) } }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onItemClick(listener: (Int) -> Unit) {
        onClick = listener
    }
}