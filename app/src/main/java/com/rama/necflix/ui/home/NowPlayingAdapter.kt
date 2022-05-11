package com.rama.necflix.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rama.necflix.data.NowPlayingDB
import com.rama.necflix.databinding.ItemRecyclerNowPlayingBinding
import com.rama.necflix.ui.BaseViewHolder

class NowPlayingAdapter(
    private val context: Context,
    private val listNowPlayingDB: List<NowPlayingDB>,
    private val onClickListener: OnNowPlayingClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnNowPlayingClickListener {
        fun onNowPlayingClickListener(item: NowPlayingDB, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRecyclerNowPlayingBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return PlayViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PlayViewHolder -> holder.bind(listNowPlayingDB[position], position)
        }
    }

    override fun getItemCount(): Int {
        return listNowPlayingDB.size
    }

    inner class PlayViewHolder(val binding: ItemRecyclerNowPlayingBinding) :
        BaseViewHolder<NowPlayingDB>(binding.root) {
        override fun bind(item: NowPlayingDB, position: Int) = with(binding) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + item.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(movie)
            title.text = item.title
            details.text = item.overview
            itemView.setOnClickListener {
                onClickListener.onNowPlayingClickListener(
                    item,
                    position
                )
            }
        }

    }
}