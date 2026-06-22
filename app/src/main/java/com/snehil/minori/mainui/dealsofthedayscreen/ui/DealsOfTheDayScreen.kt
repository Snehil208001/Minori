package com.snehil.minori.mainui.dealsofthedayscreen.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.dealsofthedayscreen.model.Deal
import com.snehil.minori.mainui.dealsofthedayscreen.viewmodel.DealsEffect
import com.snehil.minori.mainui.dealsofthedayscreen.viewmodel.DealsOfTheDayScreenViewModel
import com.snehil.minori.mainui.homescreen.ui.MinoriBottomNavigation
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta

@Composable
fun DealsOfTheDayScreen(
    onViewCart: () -> Unit,

    onNavigateBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onViewWishlist: () -> Unit,
    onProductClick: (String, String) -> Unit,
    viewModel: DealsOfTheDayScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                DealsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    // Peach-blush custom background theme for Deals of the Day
    val backgroundColor = if (isDark) Color(0xFF251917) else Color(0xFFFFF6F4)
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFC7BDBB) else EarthyStone
    val accentColor = if (isDark) Color(0xFFFCA5A5) else Color(0xFFDC2626) // Vivid red/coral accent
    val cardBg = if (isDark) Color(0xFF332321) else Color.White

    var sortExpanded by remember { mutableStateOf(false) }
    var filterExpanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            MinoriBottomNavigation(
                onCartClick = onViewCart,

                selectedTab = 0,
                onTabSelected = { index ->
                    when (index) {
                        0 -> viewModel.goBack()
                        1 -> onViewWishlist()
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
            // 1. Top Bar Header
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
                    text = "Deals of the Day",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor
                )
            }

            // 2. Search Box
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .height(54.dp),
                placeholder = {
                    Text(
                        text = "Search flash sales...",
                        color = if (isDark) Color(0xFF9E8F8D) else Color(0xFF9CA3AF),
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
                    unfocusedBorderColor = if (isDark) Color(0xFF4C3836) else Color(0xFFE5E7EB),
                    cursorColor = accentColor
                )
            )

            // 3. Segmented Urgency Timer Header
            SegmentedTimerHeader(
                secondsRemaining = uiState.secondsRemaining,
                isDark = isDark,
                accentColor = accentColor
            )

            // 4. Sort & Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${uiState.deals.size} Deals Available",
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
                                text = "Category: ${uiState.selectedCategory}",
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

            // 5. Scrollable Layout with Featured Hero Card at Top & 2-column Grid below
            if (uiState.deals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No deals found matching your filters.",
                        color = descColor,
                        fontSize = 14.sp
                    )
                }
            } else {
                val featuredDeal = remember(uiState.deals) { uiState.deals.firstOrNull() }
                val gridDeals = remember(uiState.deals) { uiState.deals.drop(1) }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    // Span full width for Featured Deal card
                    if (featuredDeal != null) {
                        item(span = { GridItemSpan(2) }) {
                            FeaturedDealCard(
                                deal = featuredDeal,
                                isWishlisted = uiState.wishlistedIds.contains(featuredDeal.id),
                                onWishlistToggle = { viewModel.toggleWishlist(featuredDeal.id) },
                                isDark = isDark,
                                cardBg = cardBg,
                                textColor = textColor,
                                descColor = descColor,
                                accentColor = accentColor,
                                onProductClick = onProductClick
                            )
                        }
                    }

                    // Display rest of deals in 2-column grid
                    items(gridDeals, key = { it.id }) { deal ->
                        DealFlashCard(
                            deal = deal,
                            isWishlisted = uiState.wishlistedIds.contains(deal.id),
                            onWishlistToggle = { viewModel.toggleWishlist(deal.id) },
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor,
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SegmentedTimerHeader(
    secondsRemaining: Int,
    isDark: Boolean,
    accentColor: Color
) {
    val total = secondsRemaining
    val h = total / 3600
    val m = (total % 3600) / 60
    val s = total % 60

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.horizontalGradient(
                    colors = if (isDark) {
                        listOf(Color(0xFF4C1E1A), Color(0xFF6B2D25))
                    } else {
                        listOf(Color(0xFFFEF2F2), Color(0xFFFEE2E2))
                    }
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "FLASH SALE ENDING IN:",
                    color = if (isDark) Color(0xFFFCA5A5) else Color(0xFFDC2626),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                SpacerHeight(4)
                Text(
                    text = "Claim local pottery & art up to 50% off",
                    color = if (isDark) Color(0xFFF3E8E6) else CharcoalText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Segmented HH : MM : SS blocks
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimerBlock(value = String.format("%02d", h), isDark = isDark)
                TimerDivider(isDark = isDark)
                TimerBlock(value = String.format("%02d", m), isDark = isDark)
                TimerDivider(isDark = isDark)
                TimerBlock(value = String.format("%02d", s), isDark = isDark)
            }
        }
    }
}

@Composable
fun TimerBlock(value: String, isDark: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isDark) Color(0xFFEF4444) else Color(0xFFDC2626))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TimerDivider(isDark: Boolean) {
    Text(
        text = ":",
        color = if (isDark) Color(0xFFFCA5A5) else Color(0xFFDC2626),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 3.dp)
    )
}

@Composable
fun FeaturedDealCard(
    deal: Deal,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    onProductClick: (String, String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth().clickable { onProductClick(deal.id.toString(), "Deal") }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Image(
                    painter = painterResource(id = deal.drawableId),
                    contentDescription = deal.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Featured Deal Ribbon tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(
                            color = Color(0xFFDC2626),
                            shape = RoundedCornerShape(bottomEnd = 12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "DEAL OF THE HOUR • ${deal.discount}",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Wishlist Toggle button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onWishlistToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color.Red else Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = deal.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        fontFamily = FontFamily.Serif
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier.size(14.dp)
                        )
                        SpacerWidth(2)
                        Text(
                            text = deal.rating.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }

                SpacerHeight(4)

                Text(
                    text = deal.description,
                    fontSize = 12.sp,
                    color = descColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                SpacerHeight(12)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "₹${deal.price}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )
                            SpacerWidth(6)
                            Text(
                                text = "₹${deal.originalPrice}",
                                fontSize = 12.sp,
                                color = descColor,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = "Includes free shipping",
                            fontSize = 10.sp,
                            color = Color(0xFF16A34A),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { /* Claim Deal */ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text(
                            text = "Claim Deal",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DealFlashCard(
    deal: Deal,
    isWishlisted: Boolean,
    onWishlistToggle: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    onProductClick: (String, String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        modifier = Modifier.fillMaxWidth().clickable { onProductClick(deal.id.toString(), "Deal") },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                Image(
                    painter = painterResource(id = deal.drawableId),
                    contentDescription = deal.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Diagonal discount badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(
                            color = Color(0xFFDC2626),
                            shape = RoundedCornerShape(bottomEnd = 8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = deal.discount,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Favorite overlay button
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
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = deal.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                SpacerHeight(2)

                Text(
                    text = deal.description,
                    fontSize = 10.sp,
                    color = descColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(28.dp)
                )

                SpacerHeight(4)

                // Star Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFBBF24),
                        modifier = Modifier.size(11.dp)
                    )
                    SpacerWidth(2)
                    Text(
                        text = "${deal.rating} (${deal.reviewCount})",
                        fontSize = 9.sp,
                        color = descColor
                    )
                }

                SpacerHeight(6)

                // Price structure
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "₹${deal.price}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                        Text(
                            text = "₹${deal.originalPrice}",
                            fontSize = 10.sp,
                            color = descColor,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    // Card Action CTA Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor)
                            .clickable { /* Buy Now action */ }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Buy",
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
