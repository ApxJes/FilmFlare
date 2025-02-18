package com.example.flimflare.adapter

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
import com.example.flimflare.model.details.credits.Crew
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class CrewAdapter: RecyclerView.Adapter<CrewAdapter.CrewItemViewHolder>() {

    inner class CrewItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var castProfile: ImageView
    private lateinit var castName: TextView
    private lateinit var castCharacterName: TextView

    private val differCallBack = object : DiffUtil.ItemCallback<Crew>(){
        override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewItemViewHolder {
        return CrewItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.crew, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CrewItemViewHolder, position: Int) {
        val crew = differ.currentList[position]

        castProfile = holder.itemView.findViewById(R.id.imvCrewProfile)
        castName = holder.itemView.findViewById(R.id.txvCrewName)
        castCharacterName = holder.itemView.findViewById(R.id.txvCrewJob)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + crew.profile_path).into(castProfile)
            castName.text = crew.name
            castCharacterName.text = "( ${crew.job} )"

            setOnClickListener {
                onClick?.let { it(crew.id) }
            }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onClickListener(listener: (Int) -> Unit) {
        onClick = listener
    }
}