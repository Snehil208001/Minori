package com.snehil.minori.mainui.searchscreen.ui

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.R
import com.snehil.minori.domain.model.WishlistItem
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.searchscreen.viewmodel.SearchEffect
import com.snehil.minori.mainui.searchscreen.viewmodel.SearchViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProductDetail: (String, String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SearchEffect.NavigateBack -> onNavigateBack()
                is SearchEffect.NavigateToProductDetail -> {
                    onNavigateToProductDetail(effect.productId, effect.productType)
                }
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFB4ADAC) else EarthyStone
    val cardBg = if (isDark) Color(0xFF292524) else Color.White
    val accentColor = if (isDark) SoftTerracotta else Terracotta
    val dividerColor = if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB)

    Scaffold(
        containerColor = backgroundColor,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.goBack() },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = cardBg),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val path = Path().apply {
                            moveTo(w * 0.8f, h * 0.5f)
                            lineTo(w * 0.2f, h * 0.5f)
                            moveTo(w * 0.5f, h * 0.2f)
                            lineTo(w * 0.2f, h * 0.5f)
                            lineTo(w * 0.5f, h * 0.8f)
                        }
                        drawPath(
                            path = path,
                            color = textColor,
                            style = Stroke(width = w * 0.12f, cap = StrokeCap.Round)
                        )
                    }
                }

                SpacerWidth(12)

                // Search Input Field
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    placeholder = { Text("Search ceramics, paintings...", fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.clearSearch() }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = textColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(26.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = cardBg,
                        unfocusedContainerColor = cardBg,
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = dividerColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        focusedPlaceholderColor = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                        unfocusedPlaceholderColor = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF)
                    )
                )
            }

            // Categories horizontal row selector
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    val chipBg = if (isSelected) accentColor else cardBg
                    val chipTextColor = if (isSelected) (if (isDark) CharcoalText else Color.White) else textColor
                    val chipBorder = if (isSelected) accentColor else dividerColor

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(chipBg)
                            .border(1.dp, chipBorder, RoundedCornerShape(20.dp))
                            .clickable { viewModel.selectCategory(category) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = chipTextColor
                        )
                    }
                }
            }

            // Popular Search tags (When query is empty)
            if (uiState.searchQuery.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Popular Searches",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        fontFamily = FontFamily.Serif
                    )
                    SpacerHeight(12)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.popularSearches.take(3).forEach { query ->
                            PopularSearchChip(query = query, isDark = isDark, cardBg = cardBg, textColor = textColor, onClick = { viewModel.updateSearchQuery(query) })
                        }
                    }
                    SpacerHeight(8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.popularSearches.drop(3).forEach { query ->
                            PopularSearchChip(query = query, isDark = isDark, cardBg = cardBg, textColor = textColor, onClick = { viewModel.updateSearchQuery(query) })
                        }
                    }
                }
            }

            // Results count
            if (uiState.filteredProducts.isNotEmpty()) {
                Text(
                    text = "Results (${uiState.filteredProducts.size})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = descColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Products Vertical Grid
            if (uiState.filteredProducts.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.filteredProducts) { item ->
                        SearchResultItemCard(
                            item = item,
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor,
                            onClick = { viewModel.onProductClick(item.id, item.type) }
                        )
                    }
                }
            } else {
                // Empty state
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "No Results",
                            tint = descColor,
                            modifier = Modifier.size(64.dp)
                        )
                        SpacerHeight(16)
                        Text(
                            text = "No items match your search",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontFamily = FontFamily.Serif
                        )
                        SpacerHeight(8)
                        Text(
                            text = "Try typing something else or check your spelling.",
                            fontSize = 13.sp,
                            color = descColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopularSearchChip(
    query: String,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardBg)
            .border(1.dp, if (isDark) Color(0xFF332F2E) else Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = query,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun SearchResultItemCard(
    item: WishlistItem,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    onClick: () -> Unit
) {
    val imgRes = item.drawableId ?: getProductDrawableIdFallback(item.imageUrl ?: "")
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = imgRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Discount badge
                if (item.discount != null) {
                    val discount = item.discount
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor)
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = discount,
                            color = if (isDark) CharcoalText else Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.categoryLabel ?: item.type,
                    fontSize = 11.sp,
                    color = descColor
                )

                SpacerHeight(4)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${item.price.toInt()}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = accentColor
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(12.dp)
                        )
                        SpacerWidth(2)
                        Text(
                            text = item.rating.toString(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

private fun getProductDrawableIdFallback(imageUrl: String): Int {
    return when (imageUrl) {
        "ceramic_bowl" -> R.drawable.ceramic_bowl
        "clay_pitcher" -> R.drawable.clay_pitcher
        "glass_carafe" -> R.drawable.glass_carafe
        "oak_chest" -> R.drawable.oak_chest
        "rattan_chair" -> R.drawable.rattan_chair
        "wool_rug" -> R.drawable.wool_rug
        "ceramic_mug" -> R.drawable.ceramic_mug
        "boho_candle" -> R.drawable.boho_candle
        "tapestry_wall" -> R.drawable.tapestry_wall
        "woven_basket" -> R.drawable.woven_basket
        else -> R.drawable.ceramic_bowl
    }
}
