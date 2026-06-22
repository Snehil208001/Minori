package com.snehil.minori.mainui.wishlistscreen.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.wishlistscreen.viewmodel.WishlistEffect
import com.snehil.minori.mainui.wishlistscreen.viewmodel.WishlistViewModel
import com.snehil.minori.mainui.homescreen.ui.MinoriBottomNavigation
import com.snehil.minori.mainui.homescreen.ui.getProductDrawableId
import com.snehil.minori.domain.model.WishlistItem
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun WishlistScreen(
    onViewCart: () -> Unit,

    onNavigateBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onProductClick: (String, String) -> Unit,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                WishlistEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1E1716) else SandCream
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFC7BDBB) else EarthyStone
    val accentColor = if (isDark) SoftTerracotta else Terracotta
    val cardBg = if (isDark) Color(0xFF2E2220) else Color.White

    Scaffold(
        bottomBar = {
            MinoriBottomNavigation(
                onCartClick = onViewCart,

                selectedTab = 1,
                onTabSelected = { index ->
                    when (index) {
                        0 -> viewModel.goBack()
                        3 -> onNavigateToProfile()
                    }
                },
                isDark = isDark,
                backgroundColor = cardBg,
                textColor = textColor
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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
                        val col = textColor
                        val path = Path().apply {
                            moveTo(w * 0.8f, h * 0.5f)
                            lineTo(w * 0.2f, h * 0.5f)
                            moveTo(w * 0.5f, h * 0.2f)
                            lineTo(w * 0.2f, h * 0.5f)
                            lineTo(w * 0.5f, h * 0.8f)
                        }
                        drawPath(
                            path = path,
                            color = col,
                            style = Stroke(width = w * 0.12f, cap = StrokeCap.Round)
                        )
                    }
                }

                SpacerWidth(16)

                Text(
                    text = "My Collection",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor
                )
            }

            // Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .height(54.dp),
                placeholder = {
                    Text(
                        text = "Search saved artifacts...",
                        color = if (isDark) Color(0xFF8B7673) else Color(0xFF9CA3AF),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = if (isDark) Color(0xFFC7BDBB) else Color(0xFF6B7280),
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedContainerColor = cardBg,
                    unfocusedContainerColor = cardBg,
                    focusedBorderColor = accentColor,
                    unfocusedBorderColor = if (isDark) Color(0xFF4C3633) else Color(0xFFE5E7EB),
                    cursorColor = accentColor
                )
            )

            SpacerHeight(12)

            // Content List
            if (uiState.products.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        // Procedural Woven Basket Drawing
                        Canvas(modifier = Modifier.size(120.dp)) {
                            val w = size.width
                            val h = size.height
                            val cx = w / 2f
                            val cy = h / 2f
                            val activeColor = if (isDark) SoftTerracotta else Terracotta

                            // Handle
                            drawArc(
                                color = activeColor.copy(alpha = 0.5f),
                                startAngle = 180f,
                                sweepAngle = 180f,
                                useCenter = false,
                                topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.35f, cy - h * 0.45f),
                                size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.7f),
                                style = Stroke(width = w * 0.06f, cap = StrokeCap.Round)
                            )

                            // Basket Body
                            val bodyPath = Path().apply {
                                moveTo(cx - w * 0.38f, cy)
                                lineTo(cx + w * 0.38f, cy)
                                cubicTo(cx + w * 0.35f, cy + h * 0.35f, cx + w * 0.2f, cy + h * 0.42f, cx, cy + h * 0.42f)
                                cubicTo(cx - w * 0.2f, cy + h * 0.42f, cx - w * 0.35f, cy + h * 0.35f, cx - w * 0.38f, cy)
                                close()
                            }
                            drawPath(bodyPath, color = activeColor)

                            // Woven Lines Overlay
                            val lineStroke = w * 0.025f
                            for (i in -3..3) {
                                val xOffset = i * (w * 0.08f)
                                drawLine(
                                    color = Color.White.copy(alpha = 0.3f),
                                    start = androidx.compose.ui.geometry.Offset(cx + xOffset - w * 0.1f, cy + h * 0.05f),
                                    end = androidx.compose.ui.geometry.Offset(cx + xOffset + w * 0.1f, cy + h * 0.4f),
                                    strokeWidth = lineStroke,
                                    cap = StrokeCap.Round
                                )
                            }
                        }

                        SpacerHeight(24)

                        Text(
                            text = "Your collection is empty",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = textColor
                        )

                        SpacerHeight(8)

                        Text(
                            text = "Tap the heart icon on any craft to save it to your wishlist collection.",
                            fontSize = 13.sp,
                            color = descColor,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(horizontal = 24.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        SpacerHeight(24)

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(accentColor)
                                .clickable { viewModel.goBack() }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Discover Artifacts  →",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    val rows = uiState.products.chunked(2)
                    items(rows) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            rowProducts.forEach { item ->
                                Box(modifier = Modifier.weight(1f)) {
                                    WishlistGridCard(
                                        item = item,
                                        onRemove = { viewModel.removeWishlistItem(item) },
                                        cardBg = cardBg,
                                        textColor = textColor,
                                        descColor = descColor,
                                        accentColor = accentColor,
                                        isDark = isDark,
                                        onProductClick = onProductClick
                                    )
                                }
                            }
                            if (rowProducts.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WishlistGridCard(
    item: WishlistItem,
    onRemove: () -> Unit,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    isDark: Boolean,
    onProductClick: (String, String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        modifier = Modifier.fillMaxWidth().clickable { onProductClick(item.id, item.type) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(if (isDark) Color(0xFF1E1716) else SandCream)
            ) {
                // Determine painter
                if (item.drawableId != null) {
                    Image(
                        painter = painterResource(id = item.drawableId),
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (item.imageUrl != null) {
                    Image(
                        painter = painterResource(id = getProductDrawableId(item.imageUrl)),
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Discount tag
                if (item.discount != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(
                                color = accentColor,
                                shape = RoundedCornerShape(bottomEnd = 8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = item.discount,
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Heart wishlist remove button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onRemove() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove Wishlist",
                        tint = Color.Red,
                        modifier = Modifier.size(13.dp)
                    )
                }

                // Screen category type tag
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = item.type.uppercase(),
                        color = Color.White,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.weight(1f)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier.size(10.dp)
                        )
                        SpacerWidth(2)
                        Text(
                            text = item.rating.toString(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }

                SpacerHeight(2)

                Text(
                    text = item.description,
                    fontSize = 9.sp,
                    color = descColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 13.sp,
                    modifier = Modifier.height(26.dp)
                )

                SpacerHeight(6)

                // Extra details tags if present
                if (item.categoryLabel != null || item.extraTag != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (item.categoryLabel != null) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = accentColor.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = item.categoryLabel,
                                    color = accentColor,
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        if (item.extraTag != null) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Gray.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(3.dp)
                                    )
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = item.extraTag,
                                    color = if (isDark) Color.LightGray else Color.DarkGray,
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    SpacerHeight(6)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${item.price.toInt()}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = textColor
                        )
                        if (item.originalPrice != null) {
                            SpacerWidth(4)
                            Text(
                                text = "₹${item.originalPrice.toInt()}",
                                fontSize = 8.sp,
                                color = descColor,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor)
                            .clickable { /* Acquire / View Details */ }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Acquire",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
