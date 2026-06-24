package com.snehil.minori.mainui.orderpreviewscreen.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.snehil.minori.mainui.orderpreviewscreen.viewmodel.OrderPreviewEffect
import com.snehil.minori.mainui.orderpreviewscreen.viewmodel.OrderPreviewViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun OrderPreviewScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit,
    viewModel: OrderPreviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OrderPreviewEffect.NavigateBack -> onNavigateBack()
                OrderPreviewEffect.NavigateToPayment -> onNavigateToPayment()
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFB4ADAC) else EarthyStone
    val cardBg = if (isDark) Color(0xFF292524) else Color.White
    val accentColor = if (isDark) SoftTerracotta else Terracotta
    val buttonBgColor = if (isDark) SoftTerracotta else Terracotta
    val buttonContentColor = if (isDark) CharcoalText else Color.White
    val dividerColor = if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB)

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
                // Header
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
                        text = "Order Preview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor,
                        fontFamily = FontFamily.Serif
                    )
                }

                // Checkout Progress Indicator
                CheckoutProgressTimeline(
                    currentStep = 2,
                    isDark = isDark,
                    accentColor = accentColor,
                    textColor = textColor,
                    descColor = descColor
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Shipping Address Card
                    item {
                        Text(
                            text = "Shipping Address",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
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
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Address",
                                            tint = accentColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        SpacerWidth(8)
                                        Text(
                                            text = uiState.address?.addressType ?: "Home",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = textColor
                                        )
                                    }
                                    IconButton(
                                        onClick = { viewModel.goBack() },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit Address",
                                            tint = accentColor,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                SpacerHeight(8)
                                uiState.address?.let { addr ->
                                    Text(
                                        text = addr.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = textColor
                                    )
                                    SpacerHeight(4)
                                    Text(
                                        text = "${addr.addressLine1}, ${addr.addressLine2}",
                                        fontSize = 13.sp,
                                        color = descColor
                                    )
                                    Text(
                                        text = "${addr.city}, ${addr.state} - ${addr.pincode}",
                                        fontSize = 13.sp,
                                        color = descColor
                                    )
                                    SpacerHeight(6)
                                    Text(
                                        text = "Phone: ${addr.phone}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = textColor
                                    )
                                } ?: Text(
                                    text = "No address specified.",
                                    color = Color.Red,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    // Shipping Methods
                    item {
                        Text(
                            text = "Shipping Method",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            ShippingMethodCard(
                                title = "Standard Delivery",
                                desc = "3-5 Business Days (Free above ₹1000)",
                                priceLabel = if (uiState.subtotal > 1000.0) "Free" else "₹50",
                                isSelected = uiState.shippingMethod == "Standard",
                                isDark = isDark,
                                cardBg = cardBg,
                                textColor = textColor,
                                descColor = descColor,
                                accentColor = accentColor,
                                onClick = { viewModel.updateShippingMethod("Standard") }
                            )

                            ShippingMethodCard(
                                title = "Express Delivery",
                                desc = "1-2 Business Days",
                                priceLabel = "₹150",
                                isSelected = uiState.shippingMethod == "Express",
                                isDark = isDark,
                                cardBg = cardBg,
                                textColor = textColor,
                                descColor = descColor,
                                accentColor = accentColor,
                                onClick = { viewModel.updateShippingMethod("Express") }
                            )
                        }
                    }

                    // Order Items
                    item {
                        Text(
                            text = "Order Items",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    items(uiState.cartItems) { item ->
                        PreviewItemRow(
                            item = item,
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor
                        )
                    }

                    // Bill Details Summary Card
                    item {
                        SpacerHeight(8)
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.padding(bottom = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Price Summary",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textColor
                                )
                                HorizontalDivider(color = dividerColor)
                                
                                PreviewSummaryRow(label = "Items Subtotal", value = "₹${uiState.subtotal.toInt()}", descColor = descColor, textColor = textColor)
                                PreviewSummaryRow(label = "Shipping Fee", value = if (uiState.shippingFee == 0.0) "Free" else "₹${uiState.shippingFee.toInt()}", descColor = descColor, textColor = textColor)
                                PreviewSummaryRow(label = "GST / Taxes (5%)", value = "₹${uiState.tax.toInt()}", descColor = descColor, textColor = textColor)
                                if (uiState.discount > 0) {
                                    PreviewSummaryRow(label = "Discount", value = "-₹${uiState.discount.toInt()}", descColor = descColor, textColor = Color(0xFF10B981))
                                }
                                
                                HorizontalDivider(color = dividerColor)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total Amount",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor
                                    )
                                    Text(
                                        text = "₹${uiState.total.toInt()}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = accentColor
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom Action Panel
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Total Payable",
                                fontSize = 12.sp,
                                color = descColor
                            )
                            Text(
                                text = "₹${uiState.total.toInt()}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = accentColor
                            )
                        }

                        Button(
                            onClick = { viewModel.proceedToPayment() },
                            modifier = Modifier
                                .width(180.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonBgColor),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = "Go to Payment",
                                color = buttonContentColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CheckoutProgressTimeline(
    currentStep: Int,
    isDark: Boolean,
    accentColor: Color,
    textColor: Color,
    descColor: Color
) {
    val steps = listOf("Address", "Preview", "Payment")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, title ->
            val stepNumber = index + 1
            val isActive = stepNumber == currentStep
            val isCompleted = stepNumber < currentStep

            val circleBg = if (isActive) accentColor else if (isCompleted) Color(0xFF10B981) else (if (isDark) Color(0xFF332F2E) else Color(0xFFE5E7EB))
            val textStyleColor = if (isActive) textColor else descColor
            val numberColor = if (isActive || isCompleted) (if (isDark && isActive) CharcoalText else Color.White) else descColor

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(circleBg),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completed",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Text(
                            text = stepNumber.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = numberColor
                        )
                    }
                }
                SpacerWidth(6)
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                    color = textStyleColor
                )

                if (index < steps.size - 1) {
                    SpacerWidth(12)
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp)
                            .background(if (isCompleted) Color(0xFF10B981) else (if (isDark) Color(0xFF44403C) else Color(0xFFD1D5DB)))
                    )
                    SpacerWidth(12)
                }
            }
        }
    }
}

@Composable
fun ShippingMethodCard(
    title: String,
    desc: String,
    priceLabel: String,
    isSelected: Boolean,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) accentColor else if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = accentColor,
                unselectedColor = descColor
            )
        )
        SpacerWidth(8)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = descColor
            )
        }
        Text(
            text = priceLabel,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (priceLabel == "Free") Color(0xFF10B981) else textColor
        )
    }
}

@Composable
fun PreviewItemRow(
    item: com.snehil.minori.core.cart.CartItem,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    val imgRes = item.product.drawableId ?: getProductDrawableIdFallback(item.product.imageUrl ?: "")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(cardBg)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = item.product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        SpacerWidth(12)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.product.categoryLabel ?: item.product.type,
                fontSize = 11.sp,
                color = descColor
            )
            SpacerHeight(4)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Qty: ${item.quantity}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = descColor
                )
                SpacerWidth(12)
                Text(
                    text = "₹${item.product.price.toInt()} each",
                    fontSize = 11.sp,
                    color = descColor
                )
            }
        }
        SpacerWidth(8)
        Text(
            text = "₹${(item.product.price * item.quantity).toInt()}",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = textColor
        )
    }
}

@Composable
fun PreviewSummaryRow(
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
