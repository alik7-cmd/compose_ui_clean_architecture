package com.example.check24.overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.check24.R
import com.example.check24.common.LoadImageFromUrl
import com.example.check24.common.Screen
import com.example.check24.common.cast
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity


@Composable
fun ProductOverview(
    navHostController: NavHostController,
    viewModel: ProductOverviewViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel, block = {
        viewModel.getProduceOverview(FilterCategory.ALL)
    })
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is ProductOverviewUiState.Error -> {
            EmptyView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        is ProductOverviewUiState.Init -> {}
        is ProductOverviewUiState.Loading -> {}
        is ProductOverviewUiState.Success -> {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    Button(onClick = { viewModel.getProduceOverview(FilterCategory.ALL) }) {
                        Text(text = stringResource(id = R.string.text_all))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { viewModel.getProduceOverview(FilterCategory.AVAILABLE) }) {
                        Text(text = stringResource(id = R.string.text_available))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { viewModel.getProduceOverview(FilterCategory.FAVOURITE) }) {
                        Text(text = stringResource(id = R.string.text_favorites))
                    }

                }
                if (uiState.cast<ProductOverviewUiState.Success>().list.isNotEmpty()) {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), content = {
                        items(uiState.cast<ProductOverviewUiState.Success>().list) {
                            ProductCard(entity = it, onClick = {
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "data",
                                    it
                                )
                                navHostController.navigate(Screen.Details.route)
                            })
                        }
                        item {
                            Text(
                                text = "This is Footer text",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 12.dp, 0.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    })
                } else {
                    EmptyView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(entity: ProductEntity, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(2.dp), shape = CardDefaults.shape
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            val mod = Modifier
                .size(100.dp, 100.dp)
                .clip(RoundedCornerShape(16.dp))
            if (entity.available) {
                LoadImageFromUrl(url = entity.imageURL, contentDescription = "", modifier = mod)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = entity.name, modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = entity.releaseDate,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }

                Text(
                    text = entity.description, modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                )

            }
            if (!entity.available) {
                Spacer(modifier = Modifier.width(16.dp))
                LoadImageFromUrl(url = entity.imageURL, contentDescription = entity.name, modifier = mod)
            }
        }

    }

}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = "Nothing to show",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }


}


