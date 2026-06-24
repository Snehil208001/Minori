package com.snehil.minori.mainui.addressdetailscreen.ui

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.snehil.minori.mainui.profilescreen.ui.MinoriStateDropdown
import com.snehil.minori.mainui.addressdetailscreen.viewmodel.AddressDetailsEffect
import com.snehil.minori.mainui.addressdetailscreen.viewmodel.AddressDetailsViewModel
import com.snehil.minori.mainui.cartscreen.ui.OrderSuccessOverlay
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun AddressDetailsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToOrderPreview: () -> Unit,
    viewModel: AddressDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AddressDetailsEffect.NavigateBack -> onNavigateBack()
                AddressDetailsEffect.NavigateToHome -> onNavigateToHome()
                AddressDetailsEffect.NavigateToOrderPreview -> onNavigateToOrderPreview()
                is AddressDetailsEffect.ShowToast -> {
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

    val states = listOf(
        "Delhi", "Maharashtra", "Karnataka", "Tamil Nadu", "Uttar Pradesh", "West Bengal", "Gujarat", "Rajasthan"
    )

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
                // Header Bar
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
                        text = "Delivery Address",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Scrollable Form Screen
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    SpacerHeight(12)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Section 1: Contact Info
                            Text(
                                text = "Contact Information",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                color = accentColor
                            )
                            SpacerHeight(4)
                            HorizontalDivider(color = dividerColor)
                            SpacerHeight(12)

                            MinoriTextField(
                                value = uiState.name,
                                onValueChange = { viewModel.updateName(it) },
                                placeholder = "Full Name",
                                leadingIcon = Icons.Default.Person,
                                isError = uiState.nameError
                            )

                            SpacerHeight(12)

                            MinoriTextField(
                                value = uiState.phone,
                                onValueChange = { viewModel.updatePhone(it) },
                                placeholder = "Phone Number (10 digits)",
                                leadingIcon = Icons.Default.Phone,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                isError = uiState.phoneError
                            )

                            SpacerHeight(24)

                            // Section 2: Address Info
                            Text(
                                text = "Shipping Address",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                color = accentColor
                            )
                            SpacerHeight(4)
                            HorizontalDivider(color = dividerColor)
                            SpacerHeight(12)

                            MinoriTextField(
                                value = uiState.addressLine1,
                                onValueChange = { viewModel.updateAddressLine1(it) },
                                placeholder = "Flat / House No. / Building / Apt",
                                leadingIcon = Icons.Default.Home,
                                isError = uiState.addressLine1Error
                            )

                            SpacerHeight(12)

                            MinoriTextField(
                                value = uiState.addressLine2,
                                onValueChange = { viewModel.updateAddressLine2(it) },
                                placeholder = "Area / Street / Sector / Village",
                                leadingIcon = Icons.Default.Home,
                                isError = uiState.addressLine2Error
                            )

                            SpacerHeight(12)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    MinoriTextField(
                                        value = uiState.city,
                                        onValueChange = { viewModel.updateCity(it) },
                                        placeholder = "City",
                                        leadingIcon = Icons.Default.Home,
                                        isError = uiState.cityError
                                    )
                                }

                                Box(modifier = Modifier.weight(1f)) {
                                    MinoriTextField(
                                        value = uiState.pincode,
                                        onValueChange = { viewModel.updatePincode(it) },
                                        placeholder = "Pincode (6 digits)",
                                        leadingIcon = Icons.Default.Info,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        isError = uiState.pincodeError
                                    )
                                }
                            }

                            SpacerHeight(12)

                            MinoriStateDropdown(
                                selectedState = uiState.state,
                                onStateSelected = { viewModel.updateStateSelected(it) },
                                states = states
                            )

                            SpacerHeight(24)

                            // Section 3: Address Type
                            Text(
                                text = "Address Type",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                color = accentColor
                            )
                            SpacerHeight(4)
                            HorizontalDivider(color = dividerColor)
                            SpacerHeight(12)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                listOf("Home", "Work", "Other").forEach { type ->
                                    val isSelected = uiState.addressType == type
                                    val chipBgColor = if (isSelected) accentColor else if (isDark) Color(0xFF332F2E) else Color(0xFFF3F2EE)
                                    val chipTextColor = if (isSelected) buttonContentColor else textColor
                                    val chipBorderColor = if (isSelected) accentColor else if (isDark) Color(0xFF44403C) else Color(0xFFD1D5DB)

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(44.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(chipBgColor)
                                            .border(1.dp, chipBorderColor, RoundedCornerShape(12.dp))
                                            .clickable { viewModel.updateAddressType(type) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = type,
                                            color = chipTextColor,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
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

                            SpacerHeight(28)

                            // Proceed to preview button inside card
                            Button(
                                onClick = { viewModel.saveAddressAndProceed() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(25.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = buttonBgColor),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                            ) {
                                Text(
                                    text = "Proceed to Order Preview",
                                    color = buttonContentColor,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    SpacerHeight(24)
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

            // Order Success dialog overlay has been moved to Payment Screen
        }
    }
}
