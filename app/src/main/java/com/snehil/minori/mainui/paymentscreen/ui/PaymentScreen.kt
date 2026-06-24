package com.snehil.minori.mainui.paymentscreen.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.MinoriTextField
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.cartscreen.ui.OrderSuccessOverlay
import com.snehil.minori.mainui.orderpreviewscreen.ui.CheckoutProgressTimeline
import com.snehil.minori.mainui.paymentscreen.viewmodel.PaymentEffect
import com.snehil.minori.mainui.paymentscreen.viewmodel.PaymentState
import com.snehil.minori.mainui.paymentscreen.viewmodel.PaymentViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun PaymentScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                PaymentEffect.NavigateBack -> onNavigateBack()
                PaymentEffect.NavigateToHome -> onNavigateToHome()
                is PaymentEffect.ShowToast -> {
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
                        text = "Payment Method",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor,
                        fontFamily = FontFamily.Serif
                    )
                }

                // Timeline Step 3
                CheckoutProgressTimeline(
                    currentStep = 3,
                    isDark = isDark,
                    accentColor = accentColor,
                    textColor = textColor,
                    descColor = descColor
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Select Payment Option",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Payment Method Options list
                    val methods = listOf(
                        "UPI" to "Pay using UPI Apps (GPay, PhonePe, etc.)",
                        "Card" to "Credit or Debit Card",
                        "NetBanking" to "Net Banking",
                        "COD" to "Cash / Pay on Delivery"
                    )

                    methods.forEach { (type, description) ->
                        PaymentOptionRow(
                            type = type,
                            description = description,
                            isSelected = uiState.selectedMethod == type,
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor,
                            onClick = { viewModel.updatePaymentMethod(type) }
                        )
                        SpacerHeight(10)
                    }

                    SpacerHeight(16)

                    // Conditional Form rendering
                    AnimatedVisibility(
                        visible = uiState.selectedMethod == "Card",
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CardDetailsForm(
                            uiState = uiState,
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor,
                            dividerColor = dividerColor,
                            onCardNumberChange = { viewModel.updateCardNumber(it) },
                            onExpiryChange = { viewModel.updateExpiryDate(it) },
                            onCvvChange = { viewModel.updateCvv(it) },
                            onNameChange = { viewModel.updateCardholderName(it) }
                        )
                    }

                    AnimatedVisibility(
                        visible = uiState.selectedMethod == "UPI",
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        UPIDetailsView(
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor
                        )
                    }

                    AnimatedVisibility(
                        visible = uiState.selectedMethod == "NetBanking",
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        NetBankingDetailsView(
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor
                        )
                    }

                    AnimatedVisibility(
                        visible = uiState.selectedMethod == "COD",
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CODDetailsView(
                            isDark = isDark,
                            cardBg = cardBg,
                            textColor = textColor,
                            descColor = descColor,
                            accentColor = accentColor
                        )
                    }

                    errorMessage?.let { error ->
                        SpacerHeight(16)
                        Text(
                            text = error,
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    SpacerHeight(24)
                }

                // Bottom Panel
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
                                text = "Amount to Pay",
                                fontSize = 12.sp,
                                color = descColor
                            )
                            Text(
                                text = "₹${uiState.totalPayable.toInt()}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = accentColor
                            )
                        }

                        Button(
                            onClick = { viewModel.payAndPlaceOrder() },
                            modifier = Modifier
                                .width(180.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonBgColor),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Secure",
                                    tint = buttonContentColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                SpacerWidth(6)
                                Text(
                                    text = if (uiState.selectedMethod == "COD") "Place Order" else "Pay & Place Order",
                                    color = buttonContentColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = accentColor)
                        SpacerHeight(12)
                        Text(
                            text = "Authorizing transaction...",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Order Success overlay
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
fun PaymentOptionRow(
    type: String,
    description: String,
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
        Column {
            Text(
                text = when(type) {
                    "UPI" -> "UPI (GPay / PhonePe / Paytm)"
                    "Card" -> "Credit / Debit Card"
                    "NetBanking" -> "Net Banking"
                    "COD" -> "Cash on Delivery"
                    else -> type
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = descColor
            )
        }
    }
}

@Composable
fun CardDetailsForm(
    uiState: PaymentState,
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color,
    dividerColor: Color,
    onCardNumberChange: (String) -> Unit,
    onExpiryChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onNameChange: (String) -> Unit
) {
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
            Text(
                text = "Card Details",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Credit Card Preview Mockup
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isDark) Color(0xFF44403C) else Color(0xFF332F2E))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MINORI BANK",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandCream,
                            fontFamily = FontFamily.Monospace
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Chip",
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Format card number with spaces
                    val numRaw = uiState.cardNumber.take(16).padEnd(16, '•')
                    val formattedNum = numRaw.chunked(4).joinToString("  ")
                    Text(
                        text = formattedNum,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SandCream,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "CARDHOLDER",
                                fontSize = 8.sp,
                                color = Color.LightGray
                            )
                            Text(
                                text = uiState.cardholderName.uppercase().ifEmpty { "YOUR NAME" },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SandCream,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "EXPIRES",
                                fontSize = 8.sp,
                                color = Color.LightGray
                            )
                            Text(
                                text = uiState.expiryDate.ifEmpty { "MM/YY" },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SandCream,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            SpacerHeight(16)

            MinoriTextField(
                value = uiState.cardNumber,
                onValueChange = { if (it.length <= 16 && it.all { c -> c.isDigit() }) onCardNumberChange(it) },
                placeholder = "Card Number (16 digits)",
                leadingIcon = Icons.Default.Lock,
                isError = uiState.cardNumberError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            SpacerHeight(12)

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    MinoriTextField(
                        value = uiState.expiryDate,
                        onValueChange = {
                            val clean = it.take(5)
                            onExpiryChange(clean)
                        },
                        placeholder = "Expiry Date (MM/YY)",
                        leadingIcon = Icons.Default.Info,
                        isError = uiState.expiryError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                SpacerWidth(12)
                Box(modifier = Modifier.weight(1f)) {
                    MinoriTextField(
                        value = uiState.cvv,
                        onValueChange = { if (it.length <= 3 && it.all { c -> c.isDigit() }) onCvvChange(it) },
                        placeholder = "CVV (3 digits)",
                        leadingIcon = Icons.Default.Lock,
                        isError = uiState.cvvError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            SpacerHeight(12)

            MinoriTextField(
                value = uiState.cardholderName,
                onValueChange = onNameChange,
                placeholder = "Cardholder Name",
                leadingIcon = Icons.Default.Person,
                isError = uiState.cardholderNameError
            )
        }
    }
}

@Composable
fun UPIDetailsView(
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
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
            Text(
                text = "Select UPI App",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            SpacerHeight(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("Google Pay", "PhonePe", "Paytm", "BHIM UPI").forEach { app ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                            .clickable { }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = app,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            SpacerHeight(12)
            Text(
                text = "You will be redirected to the selected app to complete authorization.",
                fontSize = 11.sp,
                color = descColor
            )
        }
    }
}

@Composable
fun NetBankingDetailsView(
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
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
            Text(
                text = "Popular Banks",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            SpacerHeight(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("SBI", "HDFC", "ICICI", "Axis").forEach { bank ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                            .clickable { }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = bank,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }
            }
            SpacerHeight(12)
            Text(
                text = "Please select your bank. Clicking submit will redirect to your bank's secure login screen.",
                fontSize = 11.sp,
                color = descColor
            )
        }
    }
}

@Composable
fun CODDetailsView(
    isDark: Boolean,
    cardBg: Color,
    textColor: Color,
    descColor: Color,
    accentColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "COD Info",
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
            SpacerWidth(12)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Cash on Delivery Guidelines",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "Pay using cash, card or UPI directly to the delivery partner upon arrival. No additional COD handling fees apply.",
                    fontSize = 12.sp,
                    color = descColor
                )
            }
        }
    }
}
