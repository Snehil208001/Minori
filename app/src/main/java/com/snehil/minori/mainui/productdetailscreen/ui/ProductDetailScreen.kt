package com.snehil.minori.mainui.productdetailscreen.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.R
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.productdetailscreen.viewmodel.ProductDetailEffect
import com.snehil.minori.mainui.productdetailscreen.viewmodel.ProductDetailViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyClay
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun ProductDetailScreen(
    onNavigateBack: () -> Unit,
    onViewCart: () -> Unit,
    onNavigateToAddressDetails: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProductDetailEffect.NavigateBack -> onNavigateBack()
                ProductDetailEffect.NavigateToAddressDetails -> onNavigateToAddressDetails()
                is ProductDetailEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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

    Scaffold(
        bottomBar = {
            ProductDetailBottomBar(
                price = uiState.product?.price ?: 0.0,
                onAddToCart = { viewModel.addToCart() },
                onBuyNow = { viewModel.buyNow() },
                isDark = isDark,
                cardBg = cardBg,
                accentColor = accentColor,
                textColor = textColor
            )
        },
        containerColor = backgroundColor,
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            val product = uiState.product
            if (product != null) {
                // 1. Top Image section with custom back button & favorite overlay
                val imgRes = product.drawableId ?: getProductDrawableIdFallback(product.imageUrl ?: "")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .background(if (isDark) Color(0xFF1C1917) else SandCream)
                ) {
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Top navigation overlays
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back Button
                        IconButton(
                            onClick = { viewModel.goBack() },
                            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White.copy(alpha = 0.85f)),
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
                                    color = CharcoalText,
                                    style = Stroke(width = w * 0.12f, cap = StrokeCap.Round)
                                )
                            }
                        }

                        Box(contentAlignment = Alignment.TopEnd) {
                            IconButton(
                                onClick = onViewCart,
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White.copy(alpha = 0.85f)),
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            ) {
                                Canvas(modifier = Modifier.size(20.dp)) {
                                    val w = size.width
                                    val h = size.height
                                    val strokeW = w * 0.08f

                                    val cartPath = Path().apply {
                                        moveTo(w * 0.1f, h * 0.15f)
                                        lineTo(w * 0.28f, h * 0.15f)
                                        lineTo(w * 0.42f, h * 0.62f)
                                        lineTo(w * 0.85f, h * 0.62f)
                                        lineTo(w * 0.95f, h * 0.25f)
                                        lineTo(w * 0.28f, h * 0.25f)
                                    }
                                    drawPath(
                                        path = cartPath,
                                        color = CharcoalText,
                                        style = Stroke(width = strokeW, cap = StrokeCap.Round)
                                    )
                                    drawCircle(
                                        color = CharcoalText,
                                        radius = w * 0.08f,
                                        center = androidx.compose.ui.geometry.Offset(w * 0.45f, h * 0.85f)
                                    )
                                    drawCircle(
                                        color = CharcoalText,
                                        radius = w * 0.08f,
                                        center = androidx.compose.ui.geometry.Offset(w * 0.80f, h * 0.85f)
                                    )
                                }
                            }

                            if (uiState.cartCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(accentColor)
                                        .border(1.5.dp, Color.White, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = uiState.cartCount.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    // Category & Wishlist Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Category Label Chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(accentColor.copy(alpha = 0.15f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = product.categoryLabel ?: "Handcrafted",
                                color = accentColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Heart favorite button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(cardBg)
                                .clickable { viewModel.toggleWishlist() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (uiState.isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (uiState.isWishlisted) Color.Red else descColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    SpacerHeight(12)

                    // Title / Name
                    Text(
                        text = product.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = textColor
                    )

                    SpacerHeight(8)

                    // Rating & Price row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating & Reviews
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Stars",
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(16.dp)
                            )
                            SpacerWidth(4)
                            Text(
                                text = product.rating.toString(),
                                color = textColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            SpacerWidth(6)
                            Text(
                                text = "•  (120+ Reviews)",
                                color = descColor,
                                fontSize = 12.sp
                            )
                        }

                        // Quantity Selector
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(if (isDark) Color(0xFF292524) else Color(0xFFE7E5E4))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isDark) Color(0xFF1C1917) else Color.White)
                                    .clickable { viewModel.decreaseQuantity() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "−", color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                            SpacerWidth(12)
                            Text(
                                text = uiState.quantity.toString(),
                                color = textColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            SpacerWidth(12)
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isDark) Color(0xFF1C1917) else Color.White)
                                    .clickable { viewModel.increaseQuantity() },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "+", color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    SpacerHeight(16)

                    // Pricing Details
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${product.price.toInt()}",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor
                        )
                        if (product.originalPrice != null && product.originalPrice!! > product.price) {
                            SpacerWidth(10)
                            Text(
                                text = "₹${product.originalPrice!!.toInt()}",
                                fontSize = 16.sp,
                                color = descColor,
                                textDecoration = TextDecoration.LineThrough
                            )
                            SpacerWidth(12)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF10B981))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = product.discount ?: "Save",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    SpacerHeight(24)

                    // Tabs navigation: "About", "Details/Specs", "Reviews"
                    var activeTab by remember { mutableIntStateOf(0) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DetailTabItem(title = "About", isActive = activeTab == 0, isDark = isDark, accentColor = accentColor, textColor = textColor) { activeTab = 0 }
                        DetailTabItem(title = "Specs", isActive = activeTab == 1, isDark = isDark, accentColor = accentColor, textColor = textColor) { activeTab = 1 }
                        DetailTabItem(title = "Reviews", isActive = activeTab == 2, isDark = isDark, accentColor = accentColor, textColor = textColor) { activeTab = 2 }
                    }

                    SpacerHeight(16)

                    // Tab Content
                    when (activeTab) {
                        0 -> {
                            Text(
                                text = product.description,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                color = descColor
                            )
                            SpacerHeight(16)
                            Text(
                                text = "This handcrafted piece represents premium regional artistry. Created using sustainable raw materials and meticulous shaping techniques. Each item possesses unique characteristics in surface glaze, wood grain or fiber spacing.",
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                color = descColor.copy(alpha = 0.8f)
                            )
                        }
                        1 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(cardBg)
                                    .padding(16.dp)
                            ) {
                                SpecRow(label = "Artisan / Maker", value = getArtisanName(product.type, product.extraTag), descColor = descColor, textColor = textColor)
                                SpecRow(label = "Material Type", value = product.categoryLabel ?: "Clayware / Natural Fibers", descColor = descColor, textColor = textColor)
                                SpecRow(label = "Production Type", value = product.type, descColor = descColor, textColor = textColor)
                                if (product.extraTag != null) {
                                    SpecRow(label = "Specifications", value = product.extraTag!!, descColor = descColor, textColor = textColor)
                                }
                                SpecRow(label = "Shipping", value = "Standard Delivery (3-5 Business Days)", descColor = descColor, textColor = textColor)
                                SpecRow(label = "Origin", value = "Handcrafted in India", descColor = descColor, textColor = textColor)
                            }
                        }
                        2 -> {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                ReviewCard(
                                    author = "Rohan Sharma",
                                    rating = 5,
                                    date = "2 days ago",
                                    comment = "Absolutely stunning! The texture and glaze are exactly as described. Adds a great organic vibe to my studio shelf.",
                                    cardBg = cardBg,
                                    textColor = textColor,
                                    descColor = descColor
                                )
                                ReviewCard(
                                    author = "Sneha Patil",
                                    rating = 4,
                                    date = "1 week ago",
                                    comment = "Premium build. Felt a bit smaller than expected, but the handcarved details more than make up for it.",
                                    cardBg = cardBg,
                                    textColor = textColor,
                                    descColor = descColor
                                )
                                ReviewCard(
                                    author = "Aditi Verma",
                                    rating = 5,
                                    date = "3 weeks ago",
                                    comment = "Extremely happy with my purchase. Highly recommend supporting these talented regional artisans!",
                                    cardBg = cardBg,
                                    textColor = textColor,
                                    descColor = descColor
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading Product Details...", color = textColor, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun DetailTabItem(
    title: String,
    isActive: Boolean,
    isDark: Boolean,
    accentColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val bottomLineWidth by animateFloatAsState(targetValue = if (isActive) 1f else 0f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
            color = if (isActive) accentColor else if (isDark) Color(0xFF78716C) else Color(0xFF8C8583)
        )
        SpacerHeight(4)
        Box(
            modifier = Modifier
                .height(2.5.dp)
                .width(40.dp)
                .graphicsLayer(scaleX = bottomLineWidth)
                .background(if (isActive) accentColor else Color.Transparent)
        )
    }
}

@Composable
fun SpecRow(
    label: String,
    value: String,
    descColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 13.sp, color = descColor)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
    HorizontalDivider(color = descColor.copy(alpha = 0.15f))
}

@Composable
fun ReviewCard(
    author: String,
    rating: Int,
    date: String,
    comment: String,
    cardBg: Color,
    textColor: Color,
    descColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = author, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textColor)
                Text(text = date, fontSize = 11.sp, color = descColor)
            }
            SpacerHeight(4)
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (index < rating) Color(0xFFFBBF24) else Color(0xFFE5E7EB),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            SpacerHeight(8)
            Text(text = comment, fontSize = 12.sp, color = descColor, lineHeight = 18.sp)
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    price: Double,
    onAddToCart: () -> Unit,
    onBuyNow: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    accentColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(cardBg)
            .border(0.5.dp, if (isDark) Color(0xFF292524) else Color(0xFFE5E7EB))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Add To Cart Button (Outline style)
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                border = borderBrush(isDark = isDark, accentColor = accentColor),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Add to Cart",
                    color = accentColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Buy Now Button (Solid Filled style)
            Button(
                onClick = onBuyNow,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Buy Now",
                    color = if (isDark) CharcoalText else Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun borderBrush(isDark: Boolean, accentColor: Color): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(1.5.dp, accentColor)
}

fun getProductDrawableIdFallback(imageUrl: String): Int {
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

fun getArtisanName(type: String, extraTag: String?): String {
    if (extraTag != null && extraTag.startsWith("Artisan: ")) {
        return extraTag.replace("Artisan: ", "")
    }
    return when (type) {
        "Ceramic" -> "Mahesh Clayworks"
        "Painting" -> "Rohini Das"
        "FineArts" -> "Arjun Mehta"
        "ArtisanProduct" -> "Regional Weaver"
        "PotteryProduct" -> "Master Pottery Studio"
        else -> "Minori Handcrafter"
    }
}
