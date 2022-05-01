package com.rama.necflix

import androidx.core.graphics.drawable.toDrawable
import com.rama.necflix.data.DrawableResourceName
import com.rama.necflix.databinding.AccountImagesRowBinding


val LIST_OF_NAME_RESOURCE_NAME: List<DrawableResourceName> = listOf(
    DrawableResourceName("autito"),
    DrawableResourceName("barquito"),
    DrawableResourceName("dinosaurio"),
    DrawableResourceName("gatito"),
    DrawableResourceName("patito"),
    DrawableResourceName("perrito"),
    DrawableResourceName("pinguinito"),
    DrawableResourceName("sombrerito")
)

fun bindingDrawableToImgSrc(item: DrawableResourceName, binding: AccountImagesRowBinding) {
    when (item.name) {
        "autito" -> binding.image.setImageDrawable(R.drawable.autito.toDrawable())
        "barquito" -> binding.image.setImageDrawable(R.drawable.barquito.toDrawable())
        "dinosaurio" -> binding.image.setImageDrawable(R.drawable.dinosaurio.toDrawable())
        "gatito" -> binding.image.setImageDrawable(R.drawable.gatito.toDrawable())
        "patito" -> binding.image.setImageDrawable(R.drawable.patito.toDrawable())
        "perrito" -> binding.image.setImageDrawable(R.drawable.perrito.toDrawable())
        "pinguinito" -> binding.image.setImageDrawable(R.drawable.pinguinito.toDrawable())
        "sombrerito" -> binding.image.setImageDrawable(R.drawable.sombrerito.toDrawable())
    }
}
