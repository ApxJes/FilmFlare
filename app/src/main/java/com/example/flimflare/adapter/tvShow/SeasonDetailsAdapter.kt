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
import com.example.flimflare.model.tvShow.each_season_details.Episode
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class SeasonDetailsAdapter: RecyclerView.Adapter<SeasonDetailsAdapter.SeasonDetailsViewHolder>() {

    inner class SeasonDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var episodePoster: ImageView
    private lateinit var episodeNumber: TextView
    private lateinit var episodeName: TextView
    private lateinit var episodeAirDate: TextView
    private lateinit var episodeRunTime: TextView

    private val differCallBack = object: DiffUtil.ItemCallback<Episode>(){
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.episode_per_season, parent, false)
        return SeasonDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeasonDetailsViewHolder, position: Int) {
        val episode = differ.currentList[position]

        episodePoster = holder.itemView.findViewById(R.id.imvEpisodePoster)
        episodeNumber = holder.itemView.findViewById(R.id.txvEpisode)
        episodeName = holder.itemView.findViewById(R.id.txvEpisodeName)
        episodeAirDate = holder.itemView.findViewById(R.id.txvEpisodeAirDate)
        episodeRunTime = holder.itemView.findViewById(R.id.txvEpisodeRunTime)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + episode.still_path).into(episodePoster)
            episodeNumber.text = "${episode.episode_number}"
            episodeName.text = " ${episode.name}"
            episodeAirDate.text = "${episode.air_date}"
            episodeRunTime.text = "${episode.runtime} min"

        }
    }
}