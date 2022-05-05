package com.rama.necflix.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rama.necflix.R
import com.rama.necflix.data.Accounts
import com.rama.necflix.databinding.AccountImagesRowBinding
import com.rama.necflix.ui.BaseViewHolder

class AccountsImagesAdapter(
    private val context: Context,
    private val accounts: List<Accounts>,
    private val itemClickListener: OnPhotoClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnPhotoClickListener {
        fun onPhotoClick(account: Accounts)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val imgAccount = AccountImagesRowBinding.inflate(LayoutInflater.from(context),parent,false)
        val holder = ImgAccontViewHolder(imgAccount)
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is AccountsImagesAdapter.ImgAccontViewHolder -> holder.bind(accounts[position], position)
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    inner class ImgAccontViewHolder(val binding: AccountImagesRowBinding): BaseViewHolder<Accounts>(binding.root) {
        override fun bind(item: Accounts, position: Int) = with(binding) {
            username.text = item.username
            Glide.with(context).load(item.imgSrc).circleCrop().into(image)
            itemView.setOnClickListener{ itemClickListener.onPhotoClick(item)}
        }
    }
}