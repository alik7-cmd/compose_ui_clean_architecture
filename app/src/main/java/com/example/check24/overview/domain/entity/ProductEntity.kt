package com.example.check24.overview.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.check24.overview.data.dto.Price
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val isFooter : Boolean,
    val available: Boolean,
    val description: String,
    val imageURL: String,
    val longDescription: String,
    val name: String,
    val currency: String,
    val price : String,
    val rating: Float,
    val releaseDate: String,
    val type: String,
    var isLiked : Boolean
) : Parcelable{

    constructor(isFooter : Boolean) : this(0, isFooter, false,  "", "", "", "", "",
        "0.0", 0.0f, "", "", false)
}
