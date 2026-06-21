package com.snehil.minori.mainui.splashscreen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.splashscreen.viewmodel.SplashNavigationEvent
import com.snehil.minori.mainui.splashscreen.viewmodel.SplashViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyClay
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.Terracotta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    // Animation targets
    val logoScale = remember { Animatable(0.1f) }
    val logoTransY = remember { Animatable(30f) } // Slide up slightly from below
    val logoAlpha = remember { Animatable(0f) }
    
    // Kinetic Typography animatables
    val textTransY = remember { Animatable(40f) } // Slide up from below logo
    val textAlpha = remember { Animatable(0f) }
    val textLetterSpacing = remember { Animatable(0.3f) } // Starts extremely wide

    LaunchedEffect(key1 = true) {
        // Collect navigation events
        launch {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    SplashNavigationEvent.NavigateToOnboarding -> onNavigateToOnboarding()
                }
            }
        }

        // Phase 1: Logo springs up from slightly below and scales up
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            logoTransY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        // Phase 2: Brand Text slides up and morphs directly below the logo
        delay(400)
        launch {
            textTransY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 900)
            )
        }
        launch {
            textLetterSpacing.animateTo(
                targetValue = 0.04f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream // Clean earthy warm sand background
    val textColor = if (isDark) SandCream else CharcoalText

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CanvasLogo(
                modifier = Modifier
                    .size(120.dp) // Standalone centered size
                    .graphicsLayer(
                        scaleX = logoScale.value,
                        scaleY = logoScale.value,
                        alpha = logoAlpha.value,
                        translationY = logoTransY.value
                    )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Minori",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                color = textColor,
                letterSpacing = textLetterSpacing.value.em,
                modifier = Modifier.graphicsLayer(
                    alpha = textAlpha.value,
                    translationY = textTransY.value
                )
            )
        }
    }
}

@Composable
fun CanvasLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val cx = width / 2f
        val cy = height / 2f

        // Define gradients
        val terracottaGradient = Brush.verticalGradient(
            colors = listOf(Terracotta, EarthyClay),
            startY = cy - height * 0.25f,
            endY = cy + height * 0.25f
        )
        val clayGradient = Brush.verticalGradient(
            colors = listOf(EarthyClay, Color(0xFF78350F)),
            startY = cy - height * 0.25f,
            endY = cy + height * 0.25f
        )
        val greenLeafColor = Color(0xFF606C38) // Earthy Sage Green

        // Organic shapes (Pebble 1, Pebble 2, Leaf)
        // Pebble 1: Left clay shape
        val pebble1Path = Path().apply {
            moveTo(cx - width * 0.15f, cy + height * 0.15f)
            cubicTo(
                cx - width * 0.35f, cy + height * 0.12f,
                cx - width * 0.38f, cy - height * 0.18f,
                cx - width * 0.18f, cy - height * 0.22f
            )
            cubicTo(
                cx - width * 0.05f, cy - height * 0.25f,
                cx - width * 0.02f, cy - height * 0.02f,
                cx - width * 0.15f, cy + height * 0.15f
            )
            close()
        }

        // Pebble 2: Right clay shape
        val pebble2Path = Path().apply {
            moveTo(cx + width * 0.18f, cy - height * 0.12f)
            cubicTo(
                cx + width * 0.35f, cy - height * 0.08f,
                cx + width * 0.32f, cy + height * 0.22f,
                cx + width * 0.15f, cy + height * 0.25f
            )
            cubicTo(
                cx + width * 0.02f, cy + height * 0.26f,
                cx + width * 0.05f, cy + height * 0.02f,
                cx + width * 0.18f, cy - height * 0.12f
            )
            close()
        }

        // Sage Leaf shape
        val leafPath = Path().apply {
            moveTo(cx - width * 0.05f, cy - height * 0.15f)
            quadraticTo(cx + width * 0.08f, cy - height * 0.28f, cx + width * 0.02f, cy - height * 0.42f)
            quadraticTo(cx - width * 0.12f, cy - height * 0.28f, cx - width * 0.05f, cy - height * 0.15f)
            close()
        }
        val leafStemPath = Path().apply {
            moveTo(cx - width * 0.05f, cy - height * 0.15f)
            quadraticTo(cx - width * 0.02f, cy + height * 0.05f, cx - width * 0.08f, cy + height * 0.22f)
        }

        // Draw Pebble 1 (Terracotta) - 0.85 opacity for organic overlay
        drawPath(
            path = pebble1Path,
            brush = terracottaGradient,
            alpha = 0.85f
        )

        // Draw Pebble 2 (Clay) - 0.85 opacity for organic overlay
        drawPath(
            path = pebble2Path,
            brush = clayGradient,
            alpha = 0.85f
        )

        // Draw Sage Leaf (SageGreen)
        // Leaf stem
        drawPath(
            path = leafStemPath,
            color = greenLeafColor,
            style = Stroke(width = width * 0.028f, cap = StrokeCap.Round)
        )
        // Leaf body
        drawPath(
            path = leafPath,
            color = greenLeafColor
        )
    }
}
