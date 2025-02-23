package com.example.flimflare.adapter.tvShow

import android.annotation.SuppressLint
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
import com.example.flimflare.model.details.show.Season
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class SeasonAdapter: RecyclerView.Adapter<SeasonAdapter.SeasonItemViewHolder>() {
    
    inner class SeasonItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var seasonPoster: ImageView
    private lateinit var seasonNumber: TextView
    private lateinit var seasonFirstAirDate: TextView
    private lateinit var seasonEpisodeNumber: TextView
    
    private val differCallBack = object : DiffUtil.ItemCallback<Season>() {
        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tv_show_season, parent, false)
        return SeasonItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeasonItemViewHolder, position: Int) {
        val season = differ.currentList[position]

        seasonPoster = holder.itemView.findViewById(R.id.imvSeasonPoster)
        seasonNumber = holder.itemView.findViewById(R.id.txvSeasonNumber)
        seasonFirstAirDate = holder.itemView.findViewById(R.id.txvSeasonFirstAirDate)
        seasonEpisodeNumber = holder.itemView.findViewById(R.id.txvSeasonEpisodeNumber)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + season.poster_path).into(seasonPoster)
            seasonNumber.text = "Season ${season.season_number}"
            seasonFirstAirDate.text = "1st air date: ${season.air_date}"
            seasonEpisodeNumber.text = "Season's episode count ${season.episode_count}"

            setOnClickListener { onClick?.let { it(season.season_number) } }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onClickListener(listener: (Int) -> Unit) {
        onClick = listener
    }
}