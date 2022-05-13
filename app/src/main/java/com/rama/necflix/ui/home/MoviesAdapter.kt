package com.rama.necflix.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rama.necflix.data.resultsDB
import com.rama.necflix.data.topRated
import com.rama.necflix.databinding.ItemRecyclerNowPlayingBinding
import com.rama.necflix.databinding.MoviesAndTvshowsRowBinding
import com.rama.necflix.ui.BaseViewHolder

class MoviesAdapter(
    private val context: Context,
    private val listResultsDB: List<resultsDB>,
    private val onClickListener: OnClickListener,
    private val type: String
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnClickListener {
        fun onNowPlayingClickListener(item: resultsDB, position: Int)
        fun onMoviesTvShowsClickListener(item: resultsDB, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val now = "nowplaying"
        val itemBinding = ItemRecyclerNowPlayingBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        val itemMovies = MoviesAndTvshowsRowBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false)

        if(type == now){
            return PlayViewHolder(itemBinding)
        }else{
            return MoviesTVViewHolder(itemMovies)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PlayViewHolder -> holder.bind(listResultsDB[position], position)
            is MoviesTVViewHolder -> holder.bind(listResultsDB[position], position)
        }
    }


    override fun getItemCount(): Int {
        return listResultsDB.size
    }

    inner class PlayViewHolder(val binding: ItemRecyclerNowPlayingBinding) :
        BaseViewHolder<resultsDB>(binding.root) {
        override fun bind(item: resultsDB, position: Int) = with(binding) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + item.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(movie)
            title.text = item.title
            itemView.setOnClickListener {
                onClickListener.onNowPlayingClickListener(
                    item,
                    position
                )
            }
        }
    }

    inner class MoviesTVViewHolder(val bindingtwo: MoviesAndTvshowsRowBinding) :
        BaseViewHolder<resultsDB>(bindingtwo.root) {
        override fun bind(item: resultsDB, position: Int) = with(bindingtwo) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + item.poster_path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(imgMovietvshows)
            titleMvtv.text = item.original_title
            itemView.setOnClickListener {
                onClickListener.onMoviesTvShowsClickListener(
                    item,
                    position
                )
            }
        }
    }
}