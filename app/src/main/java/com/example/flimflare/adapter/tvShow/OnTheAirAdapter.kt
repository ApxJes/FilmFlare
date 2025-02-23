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
import com.example.flimflare.model.tvShow.onTheAir.Result
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class OnTheAirAdapter: RecyclerView.Adapter<OnTheAirAdapter.TopRateItemViewHolder>() {

    inner class TopRateItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var onAirPoster: ImageView
    private lateinit var onAirTitle: TextView
    private lateinit var onAirLanguage: TextView
    private lateinit var onAirReleaseDate: TextView

    private val differCallBack = object: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRateItemViewHolder {
        return TopRateItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.popular_movie_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TopRateItemViewHolder, position: Int) {
        val onAirToday = differ.currentList[position]

        onAirPoster = holder.itemView.findViewById(R.id.imvPopularPoster)
        onAirTitle = holder.itemView.findViewById(R.id.txvPopularTitle)
        onAirLanguage = holder.itemView.findViewById(R.id.txvPopularLanguage)
        onAirReleaseDate = holder.itemView.findViewById(R.id.txvPopularReleaseDate)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + onAirToday.poster_path).into(onAirPoster)
            onAirTitle.text = onAirToday.name
            onAirLanguage.text = onAirToday.original_language
            onAirReleaseDate.text = onAirToday.first_air_date
            setOnClickListener {
                onClick?.let {
                    it(onAirToday.id)
                }
            }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onClickListener(listener: (Int) -> Unit) {
        onClick = listener
    }
}