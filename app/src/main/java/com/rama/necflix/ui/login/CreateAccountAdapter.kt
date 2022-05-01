package com.rama.necflix.ui.login

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rama.necflix.bindingDrawableToImgSrc
import com.rama.necflix.data.DrawableResourceName
import com.rama.necflix.databinding.AccountImagesRowBinding
import com.rama.necflix.ui.BaseViewHolder

class CreateAccountAdapter(
    private val context: Context,
    private val accountImgs: List<DrawableResourceName>,
    private val itemClickListener: OnResourceClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnResourceClickListener{
        fun onDrawableClick(imgSrc: Drawable)
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

    inner class MainViewHolder(val binding: AccountImagesRowBinding) : BaseViewHolder<DrawableResourceName>(binding.root) {
        override fun bind(item: DrawableResourceName, position: Int) = with(binding) {
            binding.username.text = item.name
            bindingDrawableToImgSrc(item,binding)
            itemView.setOnClickListener{ itemClickListener.onDrawableClick(binding.image.drawable)}
        }
    }
}