package com.rama.necflix.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rama.necflix.data.DrawableResourceUrl
import com.rama.necflix.databinding.AccountImagesRowBinding
import com.rama.necflix.ui.BaseViewHolder

class CreateAccountAdapter(
    private val context: Context,
    private val accountImgs: List<DrawableResourceUrl>,
    private val itemClickListener: OnDrawableClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnDrawableClickListener{
        fun onDrawableClick(imgSrc: String,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = AccountImagesRowBinding.inflate(LayoutInflater.from(context), parent,false)
        val holder = MainViewHolder(itemBinding)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(accountImgs[position], position)
        }
    }

    override fun getItemCount(): Int {
        return accountImgs.size
    }

    inner class MainViewHolder(val binding: AccountImagesRowBinding) : BaseViewHolder<DrawableResourceUrl>(binding.root) {
        override fun bind(item: DrawableResourceUrl, position: Int) = with(binding) {
            Glide.with(context)
                .load(item.url)
                .centerCrop()
                .into(image)
            itemView.setOnClickListener{ itemClickListener.onDrawableClick(item.url,position)}
        }
    }
}

