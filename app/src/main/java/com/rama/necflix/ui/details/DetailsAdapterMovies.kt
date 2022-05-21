package com.rama.necflix.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rama.necflix.data.PosterDBMovieSelected
import com.rama.necflix.data.VideosDBMovieSelected
import com.rama.necflix.databinding.DetailsPostersRowBinding
import com.rama.necflix.databinding.DetailsVideosRowBinding
import com.rama.necflix.ui.BaseViewHolder

class DetailsAdapterMovies(
    private val context: Context,
    private val posters: List<PosterDBMovieSelected>,
    private val videos: List<VideosDBMovieSelected>,
    private val type: String,
    private val clickListener: OnMovieDataClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnMovieDataClickListener{
        fun onPosterClickListener(item: PosterDBMovieSelected, position: Int)
        fun onVideoClickListener(item: VideosDBMovieSelected, position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val baseType = "posters"
        val postersBinding = DetailsPostersRowBinding.inflate(
            LayoutInflater.from(context),parent,false
        )
        val videosBinding = DetailsVideosRowBinding.inflate(
            LayoutInflater.from(context),parent, false
        )

        if(type == baseType){
            return PosterViewHolder(postersBinding)
        }else{
            return VideosViewHolder(videosBinding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PosterViewHolder -> holder.bind(posters[position], position)
            is VideosViewHolder -> holder.bind(videos[position], position)
        }
    }

    override fun getItemCount(): Int {
        return if(type == "posters"){
            posters.size
        }else{
            videos.size
        }
    }

    inner class PosterViewHolder(val binding: DetailsPostersRowBinding):
            BaseViewHolder<PosterDBMovieSelected>(binding.root){
        override fun bind(item: PosterDBMovieSelected, position: Int) = with(binding) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(posterimg)
            idposter.text = ""
            itemView.setOnClickListener { clickListener.onPosterClickListener(item, position) }
        }

    }
    inner class VideosViewHolder(val bindingTwo: DetailsVideosRowBinding):
            BaseViewHolder<VideosDBMovieSelected>(bindingTwo.root){
        override fun bind(item: VideosDBMovieSelected, position: Int) = with(bindingTwo) {
            linkUrl.text = ("https://www.youtube.com/watch?v=" + item.url)
            itemView.setOnClickListener { clickListener.onVideoClickListener(item, position) }
        }

    }

}