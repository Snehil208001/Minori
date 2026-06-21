package com.snehil.minori.mainui.specialoffersscreen.ui

import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.specialoffersscreen.model.OfferProduct
import com.snehil.minori.mainui.specialoffersscreen.viewmodel.SpecialOffersEffect
import com.snehil.minori.mainui.specialoffersscreen.viewmodel.SpecialOffersViewModel
import com.snehil.minori.mainui.homescreen.ui.MinoriBottomNavigation
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream

@Composable
fun SpecialOffersScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: SpecialOffersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SpecialOffersEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1E1A11) else Color(0xFFFFFDF7) // Soft amber white background
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFC7BEB2) else EarthyStone
    val accentColor = if (isDark) Color(0xFFFBBF24) else Color(0xFFD97706) // Golden/amber accent
    val cardBg = if (isDark) Color(0xFF2E2619) else Color.White

    var sortExpanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            MinoriBottomNavigation(
                selectedTab = 0,
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
                .statusBarsPadding()
                .padding(paddingValues)
        ) {
            // 1. Header
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
                    text = "Special Offers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor
                )
            }

            // 2. Search
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .height(54.dp),
                placeholder = {
                    Text(
                        text = "Search combo bundle deals...",
                        color = if (isDark) Color(0xFF7A6B56) else Color(0xFF9CA3AF),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = if (isDark) Color(0xFFC7BEB2) else Color(0xFF6B7280),
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
                    unfocusedBorderColor = if (isDark) Color(0xFF4C3E29) else Color(0xFFE5E7EB),
                    cursorColor = accentColor
                )
            )

            // 3. Sort & Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${uiState.products.size} Bundles Available",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Row {
                    // Sort Box
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(cardBg)
                                .clickable { sortExpanded = true }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sort: ${uiState.selectedSort}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            SpacerWidth(4)
                            Canvas(modifier = Modifier.size(10.dp)) {
                                val w = size.width
                                val h = size.height
                                val path = Path().apply {
                                    moveTo(w * 0.15f, h * 0.35f)
                                    lineTo(w * 0.5f, h * 0.7f)
                                    lineTo(w * 0.85f, h * 0.35f)
                                }
                                drawPath(path, color = textColor, style = Stroke(width = w * 0.15f, cap = StrokeCap.Round))
                            }
                        }

                        DropdownMenu(
                            expanded = sortExpanded,
                            onDismissRequest = { sortExpanded = false },
                            modifier = Modifier.background(cardBg)
                        ) {
                            uiState.sortOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(text = option, color = textColor, fontSize = 13.sp) },
                                    onClick = {
                                        viewModel.updateSort(option)
                                        sortExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    SpacerWidth(8)

                    // Filter Box
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(cardBg)
                                .clickable { filterExpanded = true }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Type: ${uiState.selectedCategory}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            SpacerWidth(4)
                            Canvas(modifier = Modifier.size(10.dp)) {
                                val w = size.width
                                val h = size.height
                                val path = Path().apply {
                                    moveTo(w * 0.15f, h * 0.35f)
                                    lineTo(w * 0.5f, h * 0.7f)
                                    lineTo(w * 0.85f, h * 0.35f)
                                }
                                drawPath(path, color = textColor, style = Stroke(width = w * 0.15f, cap = StrokeCap.Round))
                            }
                        }

                        DropdownMenu(
                            expanded = filterExpanded,
                            onDismissRequest = { filterExpanded = false },
                            modifier = Modifier.background(cardBg)
                        ) {
                            uiState.categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(text = category, color = textColor, fontSize = 13.sp) },
                                    onClick = {
                                        viewModel.updateCategory(category)
                                        filterExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            SpacerHeight(4)

            // 4. Products Grid with Coupon Banner at top
            if (uiState.products.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No special bundles found.",
                        color = descColor,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    // Coupon Banner
                    item(span = { GridItemSpan(2) }) {
                        CouponOffersBanner(isDark = isDark, accentColor = accentColor)
                    }

                    items(uiState.products, key = { it.id }) { product ->
                        OfferGridCard(
                            product = product,
                            isWishlisted = uiState.wishlistedIds.contains(product.id),
                            onWishlistToggle = { viewModel.toggleWishlist(product.id) },
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CouponOffersBanner(isDark: Boolean, accentColor: Color) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val code = "MINORI40"

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF382D1D) else Color(0xFFFEF3C7)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "EXTRA DISCOUNT APPLIED 🏷️",
                color = accentColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            SpacerHeight(4)
            Text(
                text = "Copy and apply the coupon code at checkout for flat 40% extra discount on all artisan bundles.",
                color = if (isDark) Color(0xFFD4C9BD) else CharcoalText,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
            SpacerHeight(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(if (isDark) Color(0xFF2E2619) else Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = code,
                        color = if (isDark) Color.White else CharcoalText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(accentColor)
                        .clickable {
                            clipboard.setText(AnnotatedString(code))
                            Toast
                                .makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Copy Code",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun OfferGridCard(
    product: OfferProduct,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
            ) {
                Image(
                    painter = painterResource(id = product.drawableId),
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Favorite button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onWishlistToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color.Red else Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                }

                // Golden Offer Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(
                            color = accentColor,
                            shape = RoundedCornerShape(topEnd = 6.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = product.offerTag,
                        color = Color.White,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = product.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                SpacerHeight(2)

                Text(
                    text = product.description,
                    fontSize = 10.sp,
                    color = descColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(28.dp)
                )

                SpacerHeight(6)

                // Render List of included bundle items
                Column(modifier = Modifier.fillMaxWidth()) {
                    product.bundleItems.forEach { item ->
                        Text(
                            text = "• $item",
                            fontSize = 8.sp,
                            color = descColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                SpacerHeight(8)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "₹${product.price}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = accentColor
                        )
                        Text(
                            text = "₹${product.originalPrice}",
                            fontSize = 10.sp,
                            color = descColor,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor)
                            .clickable { /* Get Combo */ }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Get",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
