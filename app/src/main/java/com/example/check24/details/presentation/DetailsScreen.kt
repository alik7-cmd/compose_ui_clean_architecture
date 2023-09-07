package com.example.check24.details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.check24.R
import com.example.check24.common.LoadImageFromUrl
import com.example.check24.overview.domain.entity.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {

    val entity =
        navHostController.previousBackStackEntry?.savedStateHandle?.get<ProductEntity>("data")

    var isFav by remember { mutableStateOf(entity?.isLiked) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            val modifier = Modifier
                .size(100.dp, 100.dp)
                .clip(RoundedCornerShape(16.dp))
            LoadImageFromUrl(url = entity?.imageURL ?: "", modifier = modifier)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row {
                    Text(
                        text = entity?.name ?: "",
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        color = Color.Blue,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = entity?.releaseDate ?: "",
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }

                Text(
                    text = "Price ${entity?.price}",
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = entity?.description ?: "",
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )

            }

        }
        Button(
            onClick = {
                isFav = !isFav!!
                entity?.isLiked = isFav!!
                coroutineScope.launch {
                    viewModel.insert(entity!!)
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp, 0.dp, 12.dp, 12.dp)
        ) {
            Text(
                text = when (isFav) {
                    true -> stringResource(id = R.string.text_unselect_fav)
                    false -> stringResource(id = R.string.text_select_fav)
                    null -> TODO()
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Long Description",
            modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 12.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = entity?.longDescription ?: "",
            modifier = Modifier.padding(12.dp, 0.dp, 12.dp, 12.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
        )

    }

}


