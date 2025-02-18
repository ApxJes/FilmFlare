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
import com.example.flimflare.model.details.credits.Cast
import com.example.flimflare.util.ConstantsURL.IMAGE_URL

class CastAdapter: RecyclerView.Adapter<CastAdapter.CastItemViewHolder>() {

    inner class CastItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var castProfile: ImageView
    private lateinit var castName: TextView
    private lateinit var castCharacterName: TextView

    private val differCallBack = object : DiffUtil.ItemCallback<Cast> (){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastItemViewHolder {
        return CastItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cast, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CastItemViewHolder, position: Int) {
        val cast = differ.currentList[position]

        castProfile = holder.itemView.findViewById(R.id.imvCastProfile)
        castName = holder.itemView.findViewById(R.id.txvCastName)
        castCharacterName = holder.itemView.findViewById(R.id.txvCastCharacter)

        holder.itemView.apply {
            Glide.with(this).load(IMAGE_URL + cast.profile_path).into(castProfile)
            castName.text = cast.name
            castCharacterName.text = "( ${cast.character} )"
            setOnClickListener {
                onClick?.let {
                    it(cast.id)
                }
            }
        }
    }

    private var onClick: ((Int) -> Unit)? = null
    fun onClickListener(listener: (Int) -> Unit) {
        onClick = listener
    }
}