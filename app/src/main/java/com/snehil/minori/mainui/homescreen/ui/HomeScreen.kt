package com.snehil.minori.mainui.homescreen.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.homescreen.viewmodel.HomeViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyClay
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit,
    onViewAllTrending: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFB4ADAC) else EarthyStone
    val cardBg = if (isDark) Color(0xFF292524) else Color.White

    var selectedNavTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MinoriBottomNavigation(
                selectedTab = selectedNavTab,
                onTabSelected = {
                    selectedNavTab = it
                    if (it == 3) {
                        onNavigateToProfile()
                    }
                },
                isDark = isDark,
                backgroundColor = cardBg,
                textColor = textColor
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            SpacerHeight(8)
            
            // 1. Top Bar Section
            HomeTopBar(
                isDark = isDark,
                textColor = textColor,
                cardBg = cardBg,
                onNavigateToProfile = onNavigateToProfile
            )

            SpacerHeight(16)

            // 2. Search Field Section
            HomeSearchBar(isDark = isDark, textColor = textColor, cardBg = cardBg)

            SpacerHeight(24)

            // 3. All Featured Header & Sort / Filter controls
            HomeFeaturedHeader(textColor = textColor, isDark = isDark, cardBg = cardBg)

            SpacerHeight(16)

            // 4. Categories horizontal row with custom drawings
            HomeCategoriesRow(textColor = textColor, isDark = isDark, cardBg = cardBg)

            SpacerHeight(24)

            // 5. Horizontal swipeable Pager Banners
            HomeBannersPager(isDark = isDark)

            SpacerHeight(24)

            // 6. Deal of the Day timer header & deal cards carousel
            HomeDealOfTheDay(isDark = isDark, textColor = textColor, cardBg = cardBg)

            SpacerHeight(24)

            // 7. Special Offers card
            HomeSpecialOffersCard(isDark = isDark, cardBg = cardBg, textColor = textColor)

            SpacerHeight(24)

            // 8. Pottery & Tapestry Promo card ("Flat & Heels" equivalent)
            HomePotteryPromoCard(isDark = isDark, textColor = textColor)

            SpacerHeight(24)

            // 9. Trending Products section
            HomeTrendingProducts(
                isDark = isDark,
                textColor = textColor,
                cardBg = cardBg,
                onViewAllTrending = onViewAllTrending
            )

            SpacerHeight(24)

            // 10. New Arrivals / Hot Sale banner
            HomeHotSaleBanner(isDark = isDark)

            SpacerHeight(24)

            // 11. Sponsored section
            HomeSponsoredCard(isDark = isDark, cardBg = cardBg, textColor = textColor)

            SpacerHeight(24)
        }
    }
}

// 1. Top Bar Component
@Composable
fun HomeTopBar(
    isDark: Boolean,
    textColor: Color,
    cardBg: Color,
    onNavigateToProfile: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Menu Button
        IconButton(
            onClick = {},
            colors = IconButtonDefaults.iconButtonColors(containerColor = cardBg),
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = textColor
            )
        }

        // Center Monogram Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            HomeSmallLogo(modifier = Modifier.size(32.dp))
            SpacerWidth(8)
            Text(
                text = "Minori",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = textColor
            )
        }

        // Profile Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (isDark) Color(0xFF44403C) else Color(0xFFFEE2E2))
                .clickable { onNavigateToProfile() },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                // Avatar head
                drawCircle(
                    color = if (isDark) SoftTerracotta else Terracotta,
                    radius = size.width * 0.22f,
                    center = androidx.compose.ui.geometry.Offset(cx, cy - size.height * 0.12f)
                )
                // Avatar shoulders
                val bodyPath = Path().apply {
                    moveTo(cx - size.width * 0.35f, cy + size.height * 0.4f)
                    quadraticTo(
                        cx, cy - size.height * 0.05f,
                        cx + size.width * 0.35f, cy + size.height * 0.4f
                    )
                    close()
                }
                drawPath(
                    path = bodyPath,
                    color = if (isDark) SoftTerracotta else Terracotta
                )
            }
        }
    }
}

// Small procedural logo icon
@Composable
fun HomeSmallLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        val pebble1Path = Path().apply {
            moveTo(cx - w * 0.15f, cy + h * 0.15f)
            cubicTo(
                cx - w * 0.35f, cy + h * 0.12f,
                cx - w * 0.38f, cy - h * 0.18f,
                cx - w * 0.18f, cy - h * 0.22f
            )
            cubicTo(
                cx - w * 0.05f, cy - h * 0.25f,
                cx - w * 0.02f, cy - h * 0.02f,
                cx - w * 0.15f, cy + h * 0.15f
            )
            close()
        }
        val pebble2Path = Path().apply {
            moveTo(cx + w * 0.18f, cy - h * 0.12f)
            cubicTo(
                cx + w * 0.35f, cy - h * 0.08f,
                cx + w * 0.32f, cy + h * 0.22f,
                cx + w * 0.15f, cy + h * 0.25f
            )
            cubicTo(
                cx + w * 0.02f, cy + h * 0.26f,
                cx + w * 0.05f, cy + h * 0.02f,
                cx + w * 0.18f, cy - h * 0.12f
            )
            close()
        }

        drawPath(path = pebble1Path, color = Terracotta, alpha = 0.8f)
        drawPath(path = pebble2Path, color = EarthyClay, alpha = 0.8f)
        
        // Sage Leaf Body
        val leafPath = Path().apply {
            moveTo(cx - w * 0.05f, cy - h * 0.15f)
            quadraticTo(cx + w * 0.08f, cy - h * 0.28f, cx + w * 0.02f, cy - h * 0.42f)
            quadraticTo(cx - w * 0.12f, cy - h * 0.28f, cx - w * 0.05f, cy - h * 0.15f)
            close()
        }
        drawPath(path = leafPath, color = Color(0xFF606C38))
    }
}

// 2. Search Bar Component
@Composable
fun HomeSearchBar(isDark: Boolean, textColor: Color, cardBg: Color) {
    var query by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Search any Product...",
                    color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                    fontSize = 15.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (isDark) Color(0xFFB4ADAC) else Color(0xFF9CA3AF),
                    modifier = Modifier.size(22.dp)
                )
            },
            trailingIcon = {
                // Voice microphone icon outline
                Canvas(modifier = Modifier.size(20.dp)) {
                    val cx = size.width / 2f
                    val cy = size.height / 2f
                    val tint = if (isDark) Color(0xFFB4ADAC) else Color(0xFF9CA3AF)
                    // Mic capsule
                    drawRoundRect(
                        color = tint,
                        size = androidx.compose.ui.geometry.Size(size.width * 0.28f, size.height * 0.5f),
                        topLeft = androidx.compose.ui.geometry.Offset(cx - size.width * 0.14f, cy - size.height * 0.35f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.width * 0.15f),
                        style = Stroke(width = size.width * 0.09f)
                    )
                    // Stand cup
                    drawArc(
                        color = tint,
                        startAngle = 0f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = androidx.compose.ui.geometry.Offset(cx - size.width * 0.28f, cy - size.height * 0.08f),
                        size = androidx.compose.ui.geometry.Size(size.width * 0.56f, size.height * 0.44f),
                        style = Stroke(width = size.width * 0.09f, cap = StrokeCap.Round)
                    )
                    // Stand vertical stem
                    drawLine(
                        color = tint,
                        start = androidx.compose.ui.geometry.Offset(cx, cy + size.height * 0.36f),
                        end = androidx.compose.ui.geometry.Offset(cx, cy + size.height * 0.5f),
                        strokeWidth = size.width * 0.09f,
                        cap = StrokeCap.Round
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = cardBg,
                unfocusedContainerColor = cardBg,
                focusedBorderColor = if (isDark) SoftTerracotta else Terracotta,
                unfocusedBorderColor = if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB),
                cursorColor = if (isDark) SoftTerracotta else Terracotta
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// 3. Featured Header Sort & Filter Section
@Composable
fun HomeFeaturedHeader(textColor: Color, isDark: Boolean, cardBg: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "All Featured",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = FontFamily.Serif
        )

        Row {
            // Sort Button
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(cardBg)
                    .clickable {}
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sort  ", fontSize = 12.sp, color = textColor, fontWeight = FontWeight.SemiBold)
                // Draw sort icon (two bidirectional arrows)
                Canvas(modifier = Modifier.size(12.dp)) {
                    val w = size.width
                    val h = size.height
                    val col = textColor
                    // Left arrow up
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.9f), end = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.1f), strokeWidth = w * 0.14f)
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.05f, h * 0.35f), end = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.1f), strokeWidth = w * 0.14f)
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.45f, h * 0.35f), end = androidx.compose.ui.geometry.Offset(w * 0.25f, h * 0.1f), strokeWidth = w * 0.14f)
                    // Right arrow down
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.75f, h * 0.1f), end = androidx.compose.ui.geometry.Offset(w * 0.75f, h * 0.9f), strokeWidth = w * 0.14f)
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.55f, h * 0.65f), end = androidx.compose.ui.geometry.Offset(w * 0.75f, h * 0.9f), strokeWidth = w * 0.14f)
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.95f, h * 0.65f), end = androidx.compose.ui.geometry.Offset(w * 0.75f, h * 0.9f), strokeWidth = w * 0.14f)
                }
            }

            SpacerWidth(10)

            // Filter Button
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(cardBg)
                    .clickable {}
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Filter  ", fontSize = 12.sp, color = textColor, fontWeight = FontWeight.SemiBold)
                // Draw filter icon (funnel slider lines)
                Canvas(modifier = Modifier.size(12.dp)) {
                    val w = size.width
                    val h = size.height
                    val col = textColor
                    // Funnel top line
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(0f, h * 0.2f), end = androidx.compose.ui.geometry.Offset(w, h * 0.2f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
                    // Funnel mid line
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.55f), end = androidx.compose.ui.geometry.Offset(w * 0.8f, h * 0.55f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
                    // Funnel bottom line
                    drawLine(col, start = androidx.compose.ui.geometry.Offset(w * 0.4f, h * 0.9f), end = androidx.compose.ui.geometry.Offset(w * 0.6f, h * 0.9f), strokeWidth = w * 0.14f, cap = StrokeCap.Round)
                }
            }
        }
    }
}

// 4. Categories Component with vector icons
data class CategoryItem(val id: Int, val label: String)

@Composable
fun HomeCategoriesRow(textColor: Color, isDark: Boolean, cardBg: Color) {
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val categories = listOf(
        CategoryItem(1, "Ceramics"),
        CategoryItem(2, "Paintings"),
        CategoryItem(3, "Fiber Art"),
        CategoryItem(4, "Woodwork"),
        CategoryItem(5, "Glassware")
    )
    
    var selectedCategory by remember { mutableIntStateOf(1) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category.id
            val ringColor = if (isSelected) (if (isDark) SoftTerracotta else Terracotta) else Color.Transparent

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { selectedCategory = category.id }
            ) {
                // Category circular graphic
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(cardBg)
                        .padding(2.dp)
                        .background(backgroundColor, CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(36.dp)) {
                        val w = size.width
                        val h = size.height
                        val cx = w / 2f
                        val cy = h / 2f
                        val strokeW = w * 0.08f
                        val activeColor = if (isDark) SoftTerracotta else Terracotta

                        when (category.id) {
                            1 -> { // Ceramics (Vase)
                                val path = Path().apply {
                                    moveTo(cx - w * 0.15f, cy - h * 0.3f)
                                    lineTo(cx + w * 0.15f, cy - h * 0.3f)
                                    quadraticTo(cx + w * 0.1f, cy - h * 0.15f, cx + w * 0.1f, cy)
                                    cubicTo(cx + w * 0.3f, cy + h * 0.05f, cx + w * 0.3f, cy + h * 0.3f, cx + w * 0.15f, cy + h * 0.4f)
                                    lineTo(cx - w * 0.15f, cy + h * 0.4f)
                                    cubicTo(cx - w * 0.3f, cy + h * 0.3f, cx - w * 0.3f, cy + h * 0.05f, cx - w * 0.1f, cy)
                                    quadraticTo(cx - w * 0.1f, cy - h * 0.15f, cx - w * 0.15f, cy - h * 0.3f)
                                    close()
                                }
                                drawPath(path = path, color = activeColor)
                            }
                            2 -> { // Paintings (Palette)
                                drawCircle(color = activeColor, radius = w * 0.38f)
                                drawCircle(color = backgroundColor, radius = w * 0.1f, center = androidx.compose.ui.geometry.Offset(cx - w * 0.12f, cy - h * 0.12f))
                                drawCircle(color = Color(0xFFFBBF24), radius = w * 0.08f, center = androidx.compose.ui.geometry.Offset(cx + w * 0.14f, cy - h * 0.1f))
                                drawCircle(color = Color(0xFF3B82F6), radius = w * 0.08f, center = androidx.compose.ui.geometry.Offset(cx + w * 0.18f, cy + h * 0.12f))
                            }
                            3 -> { // Fiber Art (Macrame loops)
                                val loopPath = Path().apply {
                                    moveTo(cx, cy - h * 0.35f)
                                    cubicTo(cx - w * 0.4f, cy - h * 0.1f, cx - w * 0.1f, cy + h * 0.35f, cx, cy + h * 0.35f)
                                    cubicTo(cx + w * 0.1f, cy + h * 0.35f, cx + w * 0.4f, cy - h * 0.1f, cx, cy - h * 0.35f)
                                }
                                drawPath(path = loopPath, color = activeColor, style = Stroke(width = strokeW))
                                drawLine(activeColor, start = androidx.compose.ui.geometry.Offset(cx, cy - h * 0.35f), end = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.35f), strokeWidth = strokeW)
                            }
                            4 -> { // Woodwork (Tree rings)
                                drawCircle(color = activeColor, radius = w * 0.4f, style = Stroke(width = strokeW))
                                drawCircle(color = activeColor, radius = w * 0.26f, style = Stroke(width = strokeW))
                                drawCircle(color = activeColor, radius = w * 0.12f)
                            }
                            5 -> { // Glassware (Decanter)
                                val glassPath = Path().apply {
                                    moveTo(cx - w * 0.1f, cy - h * 0.4f)
                                    lineTo(cx + w * 0.1f, cy - h * 0.4f)
                                    lineTo(cx + w * 0.1f, cy - h * 0.1f)
                                    lineTo(cx + w * 0.35f, cy + h * 0.3f)
                                    quadraticTo(cx + w * 0.35f, cy + h * 0.42f, cx, cy + h * 0.42f)
                                    quadraticTo(cx - w * 0.35f, cy + h * 0.42f, cx - w * 0.35f, cy + h * 0.3f)
                                    lineTo(cx - w * 0.1f, cy - h * 0.1f)
                                    close()
                                }
                                drawPath(path = glassPath, color = activeColor)
                            }
                        }
                    }
                }
                SpacerHeight(6)
                Text(
                    text = category.label,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) (if (isDark) SoftTerracotta else Terracotta) else textColor
                )
            }
        }
    }
}

// 5. Swipeable Horizontal Pager Banners
@Composable
fun HomeBannersPager(isDark: Boolean) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % 3
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            val gradient = when (page) {
                0 -> Brush.linearGradient(colors = listOf(Color(0xFFF43F5E), Color(0xFFBE123C))) // Rose / red gradient
                1 -> Brush.linearGradient(colors = listOf(Terracotta, EarthyClay)) // Minori core brand warm gradient
                else -> Brush.linearGradient(colors = listOf(Color(0xFF0D9488), Color(0xFF0F766E))) // Teal/Sage gradient
            }
            
            val bannerTitle = when (page) {
                0 -> "50-40% OFF"
                1 -> "Artisan Spotlight"
                else -> "New In Store"
            }
            
            val bannerSubtitle = when (page) {
                0 -> "Summer Art Sale\nAll Pottery ceramics"
                1 -> "Meet the weavers and painters\nbehind the crafts"
                else -> "Authentic hand-carved oak\nchests and frames"
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(gradient)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = bannerTitle,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif
                    )
                    SpacerHeight(6)
                    Text(
                        text = bannerSubtitle,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )
                    SpacerHeight(12)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .clickable {}
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Shop Now  →",
                            color = CharcoalText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Draw a beautiful abstract outline decoration on the right
                Canvas(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterEnd)
                        .graphicsLayer(alpha = 0.25f)
                ) {
                    drawCircle(Color.White, radius = size.width * 0.45f, style = Stroke(width = size.width * 0.08f))
                    drawCircle(Color.White, radius = size.width * 0.28f, style = Stroke(width = size.width * 0.08f))
                }
            }
        }
        
        SpacerHeight(12)
        
        // Pager Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val isActive = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(if (isActive) 18.dp else 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            if (isActive) (if (isDark) SoftTerracotta else Terracotta)
                            else (if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB))
                        )
                )
                SpacerWidth(6)
            }
        }
    }
}

// 6. Deal of the Day timer header & deal cards carousel
@Composable
fun HomeDealOfTheDay(isDark: Boolean, textColor: Color, cardBg: Color) {
    var totalSecondsRemaining by remember { mutableStateOf(82520) } // Equivalent to 22h 55m 20s

    LaunchedEffect(key1 = true) {
        while (totalSecondsRemaining > 0) {
            delay(1000)
            totalSecondsRemaining--
        }
    }

    val hours = totalSecondsRemaining / 3600
    val minutes = (totalSecondsRemaining % 3600) / 60
    val seconds = totalSecondsRemaining % 60
    val timerString = String.format("%02dh %02dm %02ds remaining", hours, minutes, seconds)

    Column(modifier = Modifier.fillMaxWidth()) {
        // Status Row
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF4285F4)) // Classic blue matching mockup screenshot
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Deal of the Day",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    SpacerHeight(2)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Clock outline drawn on Canvas
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val cx = size.width / 2f
                            val cy = size.height / 2f
                            drawCircle(Color.White, radius = size.width * 0.45f, style = Stroke(width = size.width * 0.12f))
                            drawLine(Color.White, start = androidx.compose.ui.geometry.Offset(cx, cy), end = androidx.compose.ui.geometry.Offset(cx, cy - size.height * 0.28f), strokeWidth = size.width * 0.12f, cap = StrokeCap.Round)
                            drawLine(Color.White, start = androidx.compose.ui.geometry.Offset(cx, cy), end = androidx.compose.ui.geometry.Offset(cx + size.width * 0.2f, cy), strokeWidth = size.width * 0.12f, cap = StrokeCap.Round)
                        }
                        SpacerWidth(4)
                        Text(
                            text = timerString,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable {}
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "View all  →",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SpacerHeight(16)

        // Deal Cards Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            item {
                HomeDealCard(
                    title = "Earthy Ceramic Bowl",
                    subtitle = "Hand-spun clay bowl ideal for organic displays.",
                    price = "₹1,500",
                    originalPrice = "₹2,000",
                    discount = "25% OFF",
                    rating = "4.9",
                    reviews = "3,280",
                    isDark = isDark,
                    cardBg = cardBg,
                    textColor = textColor,
                    isVase = false
                )
            }
            item {
                HomeDealCard(
                    title = "Terracotta Pitcher",
                    subtitle = "Rustic craft piece with Sage-wash finish.",
                    price = "₹2,489",
                    originalPrice = "₹4,000",
                    discount = "37% OFF",
                    rating = "4.8",
                    reviews = "2,460",
                    isDark = isDark,
                    cardBg = cardBg,
                    textColor = textColor,
                    isVase = true
                )
            }
        }
    }
}

@Composable
fun HomeDealCard(
    title: String,
    subtitle: String,
    price: String,
    originalPrice: String,
    discount: String,
    rating: String,
    reviews: String,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    isVase: Boolean
) {
    Card(
        modifier = Modifier.width(185.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Product Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(if (isDark) Color(0xFF1C1917) else SandCream),
                contentAlignment = Alignment.Center
            ) {
                val imgRes = if (isVase) com.snehil.minori.R.drawable.clay_pitcher else com.snehil.minori.R.drawable.ceramic_bowl
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = imgRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            // Product Meta
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = FontFamily.Serif
                )
                SpacerHeight(2)
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (isDark) Color(0xFFB4ADAC) else EarthyStone,
                    maxLines = 2,
                    lineHeight = 15.sp
                )
                SpacerHeight(8)
                
                // Prices
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = originalPrice,
                        fontSize = 11.sp,
                        color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                    )
                    SpacerWidth(6)
                    Text(
                        text = discount,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) SoftTerracotta else Terracotta
                    )
                }

                SpacerHeight(6)

                // Ratings row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = if (index < 4) Color(0xFFFBBF24) else Color(0xFFE5E7EB),
                            modifier = Modifier.size(11.dp)
                        )
                    }
                    SpacerWidth(4)
                    Text(
                        text = reviews,
                        fontSize = 10.sp,
                        color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF)
                    )
                }
            }
        }
    }
}

// 7. Special Offers Card Component
@Composable
fun HomeSpecialOffersCard(isDark: Boolean, cardBg: Color, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Draw a cute procedural discount tag icon
            Canvas(modifier = Modifier.size(36.dp)) {
                val w = size.width
                val h = size.height
                val activeColor = if (isDark) SoftTerracotta else Terracotta
                
                val path = Path().apply {
                    moveTo(w * 0.15f, h * 0.15f)
                    lineTo(w * 0.55f, h * 0.15f)
                    lineTo(w * 0.85f, h * 0.45f)
                    lineTo(w * 0.55f, h * 0.75f)
                    lineTo(w * 0.15f, h * 0.75f)
                    close()
                }
                drawPath(path = path, color = activeColor)
                // Hole inside tag
                drawCircle(cardBg, radius = w * 0.06f, center = androidx.compose.ui.geometry.Offset(w * 0.32f, h * 0.45f))
            }

            SpacerWidth(16)

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Special Offers 🏷️",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                SpacerHeight(2)
                Text(
                    text = "We make sure you get the best price for local organic art collections.",
                    fontSize = 12.sp,
                    color = if (isDark) Color(0xFFB4ADAC) else EarthyStone,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// 8. Pottery & Macrame Promo Card ("Flat & Heels" equivalent)
@Composable
fun HomePotteryPromoCard(isDark: Boolean, textColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFEF08A), // Warm yellow base
                            Color(0xFFFDE047)
                        )
                    )
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Decanter / Vase illustration on left
            Canvas(modifier = Modifier.size(88.dp)) {
                val w = size.width
                val h = size.height
                val cx = w / 2f
                val cy = h / 2f

                // Draw a beautiful bohemian double-handled jar
                val potPath = Path().apply {
                    moveTo(cx - w * 0.18f, cy - h * 0.38f)
                    lineTo(cx + w * 0.18f, cy - h * 0.38f)
                    quadraticTo(cx + w * 0.14f, cy - h * 0.2f, cx + w * 0.14f, cy)
                    cubicTo(cx + w * 0.38f, cy + h * 0.05f, cx + w * 0.38f, cy + h * 0.3f, cx + w * 0.18f, cy + h * 0.4f)
                    lineTo(cx - w * 0.18f, cy + h * 0.4f)
                    cubicTo(cx - w * 0.38f, cy + h * 0.3f, cx - w * 0.38f, cy + h * 0.05f, cx - w * 0.14f, cy)
                    quadraticTo(cx - w * 0.14f, cy - h * 0.2f, cx - w * 0.18f, cy - h * 0.38f)
                    close()
                }
                drawPath(path = potPath, color = Terracotta)
                
                // Outer ring details around jar
                drawCircle(Color.White.copy(alpha = 0.4f), radius = w * 0.45f, style = Stroke(width = w * 0.02f))
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Clay & Tapestry",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalText,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Stand out & feel connected",
                    fontSize = 11.sp,
                    color = EarthyStone
                )
                SpacerHeight(12)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Terracotta)
                        .clickable {}
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Visit now  →",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// 9. Trending Products horizontal row
@Composable
fun HomeTrendingProducts(
    isDark: Boolean,
    textColor: Color,
    cardBg: Color,
    onViewAllTrending: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Red Section Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFD5C63)) // Warm red-pink matching mockup
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Trending Products",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Last date 29/06/2026",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onViewAllTrending() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "View all  →",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SpacerHeight(16)

        // Cards horizontal carousel
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            item {
                HomeTrendingCard(
                    title = "Oak Carved Chest",
                    subtitle = "Handcrafted organic frame drawer with brass keys.",
                    price = "₹6,850",
                    originalPrice = "₹9,500",
                    discount = "28% OFF",
                    isDark = isDark,
                    cardBg = cardBg,
                    textColor = textColor,
                    isChest = true
                )
            }
            item {
                HomeTrendingCard(
                    title = "Glass Vase / Carafe",
                    subtitle = "Blown decanter in warm amber gradient shade.",
                    price = "₹2,650",
                    originalPrice = "₹5,300",
                    discount = "50% OFF",
                    isDark = isDark,
                    cardBg = cardBg,
                    textColor = textColor,
                    isChest = false
                )
            }
        }
    }
}

@Composable
fun HomeTrendingCard(
    title: String,
    subtitle: String,
    price: String,
    originalPrice: String,
    discount: String,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    isChest: Boolean
) {
    Card(
        modifier = Modifier.width(185.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(if (isDark) Color(0xFF1C1917) else SandCream),
                contentAlignment = Alignment.Center
            ) {
                val imgRes = if (isChest) com.snehil.minori.R.drawable.oak_chest else com.snehil.minori.R.drawable.glass_carafe
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = imgRes),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = FontFamily.Serif
                )
                SpacerHeight(2)
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = if (isDark) Color(0xFFB4ADAC) else EarthyStone,
                    maxLines = 2,
                    lineHeight = 15.sp
                )
                SpacerHeight(8)

                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = originalPrice,
                        fontSize = 11.sp,
                        color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                    )
                    SpacerWidth(6)
                    Text(
                        text = discount,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) SoftTerracotta else Terracotta
                    )
                }
            }
        }
    }
}

// 10. Hot Summer Sale / New Arrivals Banner
@Composable
fun HomeHotSaleBanner(isDark: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFedd5), // Peach light
                        Color(0xFFFed7aa)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New Arrivals",
                color = CharcoalText,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif
            )
            SpacerHeight(4)
            Text(
                text = "Summer '25 Collections",
                color = EarthyStone,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            SpacerHeight(16)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFD5C63))
                    .clickable {}
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "View all  →",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Draw hot summer sun elements procedurally on the right side
        Canvas(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.CenterEnd)
                .graphicsLayer(alpha = 0.35f)
        ) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            drawCircle(Color(0xFFF97316), radius = size.width * 0.28f)
            
            // Rays
            val rayCount = 8
            for (i in 0 until rayCount) {
                val angle = (i * 360f / rayCount) * (Math.PI / 180f)
                val startX = cx + Math.cos(angle).toFloat() * (size.width * 0.34f)
                val startY = cy + Math.sin(angle).toFloat() * (size.width * 0.34f)
                val endX = cx + Math.cos(angle).toFloat() * (size.width * 0.48f)
                val endY = cy + Math.sin(angle).toFloat() * (size.width * 0.48f)
                drawLine(
                    color = Color(0xFFF97316),
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX, endY),
                    strokeWidth = size.width * 0.08f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

// 11. Sponsored Section Component
@Composable
fun HomeSponsoredCard(isDark: Boolean, cardBg: Color, textColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Sponsored",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Large pottery class drawing
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(if (isDark) Color(0xFF1C1917) else SandCream),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.snehil.minori.R.drawable.pottery_class),
                        contentDescription = "Pottery Class",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }

                // Text Meta
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
                            text = "up to 50% Off",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        // Arrow pointing right
                        Icon(
                            imageVector = Icons.Default.Settings, // placeholder for a clean chevron or settings
                            contentDescription = "Link",
                            tint = if (isDark) SoftTerracotta else Terracotta,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    SpacerHeight(2)
                    Text(
                        text = "Curated Pottery Classes & Handcrafted Rugs",
                        fontSize = 13.sp,
                        color = if (isDark) Color(0xFFB4ADAC) else EarthyStone,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// 12. Bottom Navigation Component with a prominent floating cart button
@Composable
fun MinoriBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    isDark: Boolean,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        // Bottom Bar Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(backgroundColor)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Tab
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                isDark = isDark,
                textColor = textColor
            )

            // Wishlist Tab
            BottomNavItem(
                icon = Icons.Default.FavoriteBorder,
                label = "Wishlist",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                isDark = isDark,
                textColor = textColor
            )

            // Spacing placeholder for middle floating button
            SpacerWidth(56)

            // Search Tab
            BottomNavItem(
                icon = Icons.Default.Search,
                label = "Search",
                isSelected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                isDark = isDark,
                textColor = textColor
            )

            // Setting Tab
            BottomNavItem(
                icon = Icons.Default.Settings,
                label = "Setting",
                isSelected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                isDark = isDark,
                textColor = textColor
            )
        }

        // Circular prominent Floating Cart Button in the exact center
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer(translationY = -24f)
                .size(62.dp)
                .clip(CircleShape)
                .background(if (isDark) Color(0xFF1C1917) else SandCream) // border outline ring
                .padding(4.dp)
                .clip(CircleShape)
                .background(if (isDark) SoftTerracotta else Terracotta)
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            // Custom drawn shopping cart icon on Canvas
            Canvas(modifier = Modifier.size(24.dp)) {
                val w = size.width
                val h = size.height
                val col = if (isDark) CharcoalText else Color.White
                val strokeW = w * 0.08f

                val cartPath = Path().apply {
                    moveTo(w * 0.1f, h * 0.15f)
                    lineTo(w * 0.28f, h * 0.15f)
                    lineTo(w * 0.42f, h * 0.62f)
                    lineTo(w * 0.85f, h * 0.62f)
                    lineTo(w * 0.95f, h * 0.28f)
                    lineTo(w * 0.32f, h * 0.28f)
                }
                drawPath(path = cartPath, color = col, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                // Wheels
                drawCircle(col, radius = w * 0.1f, center = androidx.compose.ui.geometry.Offset(w * 0.42f, h * 0.78f))
                drawCircle(col, radius = w * 0.1f, center = androidx.compose.ui.geometry.Offset(w * 0.82f, h * 0.78f))
            }
        }
    }
}

@Composable
fun BottomNavItem(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    isDark: Boolean,
    textColor: Color
) {
    val activeColor = if (isDark) SoftTerracotta else Terracotta
    val inactiveColor = if (isDark) Color(0xFF78716C) else EarthyStone

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(60.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) activeColor else inactiveColor,
            modifier = Modifier.size(22.dp)
        )
        SpacerHeight(4)
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}
