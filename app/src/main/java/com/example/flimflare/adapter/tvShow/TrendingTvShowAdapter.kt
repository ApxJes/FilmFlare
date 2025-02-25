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

class TrendingTvShowAdapter: RecyclerView.Adapter<TrendingTvShowAdapter.TrendingViewHolder>() {

    inner class TrendingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var trendingPoster: ImageView
    private lateinit var trendingTitle: TextView
    private lateinit var trendingReleaseDate: TextView

    private val differCallBack = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.now_playing_movie_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val trendingTvShow = differ.currentList[position]

        trendingPoster = holder.itemView.findViewById(R.id.imvMoviePoster)
        trendingTitle = holder.itemView.findViewById(R.id.txvMovieTitle)
        trendingReleaseDate = holder.itemView.findViewById(R.id.txvReleaseDate)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL+ trendingTvShow.poster_path).into(trendingPoster)
            trendingTitle.text = trendingTvShow.name
            trendingReleaseDate.text = trendingTvShow.first_air_date

            setOnClickListener {
                onClick?.let {
                    it(trendingTvShow)
                }
            }
        }
    }

    private var onClick: ((Result) -> Unit)? = null
    fun onClickListener(listener: (Result) -> Unit) {
        onClick = listener
    }
}