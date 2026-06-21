package com.snehil.minori.mainui.onboardingscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snehil.minori.R
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta
import kotlinx.coroutines.launch

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.onboardingscreen.viewmodel.OnboardingEffect
import com.snehil.minori.mainui.onboardingscreen.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    onNavigateToHome: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingEffect.NavigateToLogin -> onNavigateToHome()
            }
        }
    }
    val pages = listOf(
        OnboardingPageData(
            imageResId = R.drawable.onboarding_choose,
            title = "Discover Unique Crafts",
            description = "Explore a curated gallery of one-of-a-kind, handmade creations made with love and creative passion by independent studios."
        ),
        OnboardingPageData(
            imageResId = R.drawable.onboarding_payment,
            title = "Support Local Artisans",
            description = "Connect directly with painters, ceramicists, and weavers, keeping traditional craftsmanship and human connection alive."
        ),
        OnboardingPageData(
            imageResId = R.drawable.onboarding_delivery,
            title = "Acquire Handcrafted Art",
            description = "Bring organic textures, warmth, and character into your home with authentic art pieces shipped safely to your space."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onPageChanged(pagerState.currentPage)
    }
    val isDark = isSystemInDarkTheme()

    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val titleColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFB4ADAC) else EarthyStone
    
    // Page indicator dot colors
    val activeDotColor = if (isDark) SoftTerracotta else Terracotta
    val inactiveDotColor = if (isDark) Color(0xFF44403C) else Color(0xFFE7E5E4) // Stone 700 vs Stone 200
    val accentColor = if (isDark) SoftTerracotta else Terracotta

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            // Header Page Indicators & Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Page Indicator (e.g. 2/3)
                Row {
                    Text(
                        text = "${pagerState.currentPage + 1}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = titleColor
                    )
                    Text(
                        text = "/${pages.size}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = descColor
                    )
                }

                // Skip Button
                Text(
                    text = "Skip",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                    modifier = Modifier.clickable {
                        viewModel.onSkipClicked()
                    }
                )
            }

            // Pager content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val pageData = pages[page]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = pageData.imageResId),
                        contentDescription = pageData.title,
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(290.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = pageData.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = titleColor,
                        fontFamily = FontFamily.Serif, // Serif font for an artistic, editorial vibe
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = pageData.description,
                        fontSize = 15.sp,
                        color = descColor,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            // Bottom Navigation Panel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Prev button (only visible on Page 2 and 3)
                Box(modifier = Modifier.width(80.dp)) {
                    if (pagerState.currentPage > 0) {
                        Text(
                            text = "Prev",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = descColor,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                }
                        )
                    }
                }

                // Dot progress indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isActive = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isActive) 24.dp else 8.dp)
                                .clip(if (isActive) RoundedCornerShape(4.dp) else CircleShape)
                                .background(if (isActive) activeDotColor else inactiveDotColor)
                        )
                    }
                }

                // Next or Get Started button
                Box(
                    modifier = Modifier.width(110.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    val isLastPage = pagerState.currentPage == pages.size - 1
                    Text(
                        text = if (isLastPage) "Get Started" else "Next",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.clickable {
                            if (isLastPage) {
                                viewModel.onGetStartedClicked()
                            } else {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

data class OnboardingPageData(
    val imageResId: Int,
    val title: String,
    val description: String
)
