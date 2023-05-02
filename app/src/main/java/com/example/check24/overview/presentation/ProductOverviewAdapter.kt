package com.example.check24.overview.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.check24.R
import com.example.check24.databinding.ItemAvailableProductBinding
import com.example.check24.databinding.ItemFooterBinding
import com.example.check24.databinding.ItemUnavailableProductBinding
import com.example.check24.loadImageFromWeb
import com.example.check24.overview.domain.entity.ProductEntity

class ProductOverviewAdapter(val listener: OnProductClickListener) :
    ListAdapter<ProductEntity, RecyclerView.ViewHolder>(ProductOverviewDiffCallback()) {


    private val VIEW_AVAILABLE = 0
    private val VIEW_NOT_AVAILABLE = 1
    private val VIEW_FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_AVAILABLE -> ProductAvailableVH(
                ItemAvailableProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_NOT_AVAILABLE -> ProductUnavailableVH(
                ItemUnavailableProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_FOOTER -> ProductFooterVH(
                ItemFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ProductAvailableVH(
                ItemAvailableProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ProductAvailableVH) {
            holder.bind(getItem(position), listener)

        } else if (holder is ProductUnavailableVH) {
            holder.bind(getItem(position), listener)
        } else if (holder is ProductFooterVH) {
            holder.bind(listener)
        }

    }

    override fun getItemViewType(position: Int): Int {

        val product = getItem(position)
        return if (product.isFooter) {
            VIEW_FOOTER
        } else {
            when (product.available) {
                true -> VIEW_AVAILABLE
                false -> VIEW_NOT_AVAILABLE
            }
        }
    }
}


class ProductAvailableVH(val binding: ItemAvailableProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(data: ProductEntity, listener: OnProductClickListener) {
        with(binding) {
            if (data.isLiked) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.fav_color))
            } else {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.white))
            }
            ivImage.loadImageFromWeb(data.imageURL)
            tvName.text = data.name
            tvDescription.text = data.description
            tvDate.text = data.releaseDate
            ratingBar.rating = data.rating
            tvPrice.text =
                "${tvPrice.context.resources.getString(R.string.text_price)} ${data.price} ${data.currency}"
            root.setOnClickListener {
                listener.onClick(data, false)
            }
        }
    }

}

class ProductUnavailableVH(val binding: ItemUnavailableProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(data: ProductEntity, listener: OnProductClickListener) {
        with(binding) {
            if (data.isLiked) {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.fav_color))
            } else {
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.white))
            }
            ivImage.loadImageFromWeb(data.imageURL)
            tvName.text = data.name
            tvDescription.text = data.description
            tvDate.text = data.releaseDate
            ratingBar.rating = data.rating
            tvPrice.text =
                "${tvPrice.context.resources.getString(R.string.text_price)} ${data.price} ${data.currency}"
            root.setOnClickListener {
                listener.onClick(data, false)
            }
        }
    }

}

class ProductFooterVH(val binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(listener: OnProductClickListener) {
        with(binding) {
            root.setOnClickListener {
                listener.onClick(null, true)
            }
        }

    }
}


class ProductOverviewDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity) = oldItem == newItem

}

interface OnProductClickListener {
    fun onClick(data: ProductEntity?, isFooterClicked: Boolean)
}