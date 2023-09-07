package com.example.check24.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.check24.R

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

@Composable
fun LoadingScreen(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        CircularProgressIndicator(color = Color.Yellow, strokeWidth = Dp(2.0f))

    }
}

