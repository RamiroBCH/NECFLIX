package com.rama.necflix.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rama.necflix.data.PosterDBMovieSelected
import com.rama.necflix.data.VideosDBMovieSelected
import com.rama.necflix.databinding.PostersRowBinding
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
        fun onVideoClickListener()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val baseType = "posters"
        val postersBinding = PostersRowBinding.inflate(
            LayoutInflater.from(context),parent,false)

        if(type == baseType){
            return PosterViewHolder(postersBinding)
        }else{
            return PosterViewHolder(postersBinding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PosterViewHolder -> holder.bind(posters[position], position)
        }
    }

    override fun getItemCount(): Int {
        return if(type == "posters"){
            posters.size
        }else{
            videos.size
        }
    }

    inner class PosterViewHolder(val binding: PostersRowBinding):
            BaseViewHolder<PosterDBMovieSelected>(binding.root){
        override fun bind(item: PosterDBMovieSelected, position: Int) = with(binding) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(posterimg)
            idposter.text = item.id.toString()
            itemView.setOnClickListener { clickListener.onPosterClickListener(item, position) }
        }

    }
}