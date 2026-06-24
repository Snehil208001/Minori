package com.snehil.minori.mainui.cartscreen.ui


import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.R
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.cartscreen.viewmodel.CartEffect
import com.snehil.minori.mainui.cartscreen.viewmodel.CartViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun CartScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddressDetails: () -> Unit,
    onProductClick: (String, String) -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CartEffect.NavigateBack -> onNavigateBack()
                CartEffect.NavigateToHome -> onNavigateToHome()
                CartEffect.NavigateToAddressDetails -> onNavigateToAddressDetails()
                is CartEffect.ShowToast -> {
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
        containerColor = backgroundColor,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 1. Top Header Bar
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

                    SpacerWidth(16)

                    Text(
                        text = "Shopping Cart",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )

                    if (uiState.cartCount > 0) {
                        Text(
                            text = "${uiState.cartCount} items",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = descColor
                        )
                    }
                }

                if (uiState.cartItems.isEmpty()) {
                    // Empty Cart Screen State
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Drawing an empty basket on Canvas
                        Canvas(modifier = Modifier.size(100.dp)) {
                            val w = size.width
                            val h = size.height
                            val strokeW = w * 0.06f
                            val color = if (isDark) SoftTerracotta.copy(alpha = 0.6f) else Terracotta.copy(alpha = 0.5f)

                            val basketPath = Path().apply {
                                moveTo(w * 0.15f, h * 0.45f)
                                lineTo(w * 0.85f, h * 0.45f)
                                lineTo(w * 0.75f, h * 0.85f)
                                lineTo(w * 0.25f, h * 0.85f)
                                close()

                                // Handle
                                moveTo(w * 0.2f, h * 0.45f)
                                cubicTo(w * 0.2f, h * 0.1f, w * 0.8f, h * 0.1f, w * 0.8f, h * 0.45f)
                            }

                            drawPath(
                                path = basketPath,
                                color = color,
                                style = Stroke(width = strokeW, cap = StrokeCap.Round)
                            )
                        }

                        SpacerHeight(24)

                        Text(
                            text = "Your Cart is Empty",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = textColor
                        )

                        SpacerHeight(8)

                        Text(
                            text = "Looks like you haven't added anything to your cart yet. Explore our handcrafted collections to find something beautiful!",
                            fontSize = 13.sp,
                            color = descColor,
                            textAlign = TextAlign.Center,
                            lineHeight = 19.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        SpacerHeight(32)

                        Button(
                            onClick = { viewModel.goHome() },
                            colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text(text = "Start Shopping", color = if (isDark) CharcoalText else Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // Cart listing
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(uiState.cartItems) { cartItem ->
                            CartItemRow(
                                item = cartItem,
                                onIncrease = { viewModel.increaseQuantity(cartItem) },
                                onDecrease = { viewModel.decreaseQuantity(cartItem) },
                                onRemove = { viewModel.removeItem(cartItem) },
                                isDark = isDark,
                                cardBg = cardBg,
                                textColor = textColor,
                                descColor = descColor,
                                accentColor = accentColor
                            )
                        }
                    }

                    // Billing section summary card at the bottom
                    CheckoutSummaryCard(
                        subtotal = uiState.cartTotal,
                        shipping = uiState.shippingCharge,
                        taxRate = uiState.taxRate,
                        onCheckout = { viewModel.checkout() },
                        isDark = isDark,
                        cardBg = cardBg,
                        textColor = textColor,
                        descColor = descColor,
                        accentColor = accentColor
                    )
                }
            }

            // Loading overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = accentColor)
                }
            }

            // Place Order Success Dialog Screen Overlay
            AnimatedVisibility(
                visible = uiState.checkoutSuccess,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OrderSuccessOverlay(
                    onDismiss = {
                        viewModel.resetCheckout()
                        viewModel.goHome()
                    },
                    isDark = isDark,
                    cardBg = cardBg,
                    textColor = textColor,
                    descColor = descColor,
                    accentColor = accentColor
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: com.snehil.minori.core.cart.CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    val imgRes = item.product.drawableId ?: getProductDrawableIdFallback(item.product.imageUrl ?: "")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail image
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = item.product.name,
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            SpacerWidth(12)

            // Mid Section details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.product.categoryLabel != null) {
                    Text(
                        text = item.product.categoryLabel!!,
                        fontSize = 11.sp,
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                SpacerHeight(4)

                // Item Price
                Text(
                    text = "₹${item.product.price.toInt()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }

            SpacerWidth(8)

            // Right side Quantity Controller and Delete button
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Delete button
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val c = if (isDark) Color(0xFFEF4444) else Color(0xFFEF4444)
                        val strokeW = w * 0.12f

                        val trashPath = Path().apply {
                            // Top Bar lid
                            moveTo(w * 0.2f, h * 0.25f)
                            lineTo(w * 0.8f, h * 0.25f)
                            // Handle
                            moveTo(w * 0.4f, h * 0.25f)
                            lineTo(w * 0.4f, h * 0.1f)
                            lineTo(w * 0.6f, h * 0.1f)
                            lineTo(w * 0.6f, h * 0.25f)
                            // Bin frame
                            moveTo(w * 0.28f, h * 0.25f)
                            lineTo(w * 0.32f, h * 0.85f)
                            lineTo(w * 0.68f, h * 0.85f)
                            lineTo(w * 0.72f, h * 0.25f)
                        }
                        drawPath(path = trashPath, color = c, style = Stroke(width = strokeW, cap = StrokeCap.Round))
                    }
                }

                // Small pill quantity controller
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isDark) Color(0xFF1C1917) else Color(0xFFE7E5E4))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF292524) else Color.White)
                            .clickable { onDecrease() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "−", color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    SpacerWidth(8)
                    Text(
                        text = item.quantity.toString(),
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    SpacerWidth(8)
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF292524) else Color.White)
                            .clickable { onIncrease() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+", color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CheckoutSummaryCard(
    subtotal: Double,
    shipping: Double,
    taxRate: Double,
    onCheckout: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    val tax = subtotal * taxRate
    val grandTotal = subtotal + shipping + tax

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Order Summary",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = textColor
            )

            SpacerHeight(12)

            // Subtotal
            SummaryRow(label = "Subtotal", value = "₹${subtotal.toInt()}", descColor = descColor, textColor = textColor)
            SpacerHeight(6)
            // Shipping
            SummaryRow(label = "Delivery Charge", value = "₹${shipping.toInt()}", descColor = descColor, textColor = textColor)
            SpacerHeight(6)
            // Tax
            SummaryRow(label = "GST (5%)", value = "₹${tax.toInt()}", descColor = descColor, textColor = textColor)

            SpacerHeight(12)
            HorizontalDivider(color = descColor.copy(alpha = 0.15f))
            SpacerHeight(12)

            // Grand Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Grand Total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "₹${grandTotal.toInt()}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = accentColor
                )
            }

            SpacerHeight(20)

            // Checkout Button
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Place Order",
                    color = if (isDark) CharcoalText else Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: String,
    descColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = descColor)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = textColor)
    }
}

@Composable
fun OrderSuccessOverlay(
    onDismiss: () -> Unit,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable(enabled = false) {}, // Consume clicks
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circle animated Checkmark icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)), // Soft green background
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(36.dp)) {
                        val w = size.width
                        val h = size.height
                        val path = Path().apply {
                            moveTo(w * 0.15f, h * 0.5f)
                            lineTo(w * 0.42f, h * 0.75f)
                            lineTo(w * 0.85f, h * 0.22f)
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF2E7D32), // Dark green stroke
                            style = Stroke(width = w * 0.12f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
                        )
                    }
                }

                SpacerHeight(20)

                Text(
                    text = "Order Placed!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor
                )

                SpacerHeight(8)

                Text(
                    text = "Thank you for shopping with Minori. Your handcrafted pieces will soon begin their journey from master studios to your doorstep.",
                    fontSize = 13.sp,
                    color = descColor,
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp
                )

                SpacerHeight(12)

                Text(
                    text = "Order Reference ID: #MN-${(100000..999999).random()}",
                    fontSize = 11.sp,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                SpacerHeight(24)

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Continue Shopping", color = if (isDark) CharcoalText else Color.White, fontWeight = FontWeight.Bold)
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
