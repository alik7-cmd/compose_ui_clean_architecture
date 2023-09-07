package com.example.check24.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun LoadImageFromUrl(url: String, contentDescription: String = "", modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url/*"https://www.foodiesfeed.com/wp-content/uploads/2023/05/juicy-cheeseburger.jpg"*/)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        ),
        contentDescription = contentDescription, //category.name,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
    )
}