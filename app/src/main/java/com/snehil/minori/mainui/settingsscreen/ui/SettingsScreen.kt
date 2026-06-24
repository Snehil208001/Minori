package com.snehil.minori.mainui.settingsscreen.ui

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.settingsscreen.viewmodel.SettingsEffect
import com.snehil.minori.mainui.settingsscreen.viewmodel.SettingsViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onNavigateBack()
                SettingsEffect.NavigateToProfile -> onNavigateToProfile()
                SettingsEffect.NavigateToLogin -> onNavigateToLogin()
                is SettingsEffect.ShowToast -> {
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

    // Logout Confirmation Dialog
    if (uiState.isLogoutDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.hideLogoutDialog() },
            title = {
                Text(
                    text = "Sign Out",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to sign out of your Minori account?",
                    color = descColor,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.logout() },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Logout", color = buttonContentColor, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.hideLogoutDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text("Cancel", color = accentColor, fontWeight = FontWeight.Bold)
                }
            },
            containerColor = cardBg,
            shape = RoundedCornerShape(16.dp)
        )
    }

    Scaffold(
        containerColor = backgroundColor,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                    text = "Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor,
                    fontFamily = FontFamily.Serif
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section 1: Preferences Settings
                Text(
                    text = "Preferences",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        // Dark Mode Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Dark Mode",
                                    tint = textColor,
                                    modifier = Modifier.size(20.dp)
                                )
                                SpacerWidth(12)
                                Text(
                                    text = "Dark Theme",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                            }
                            Switch(
                                checked = uiState.isDarkMode,
                                onCheckedChange = { viewModel.toggleDarkMode(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = buttonContentColor,
                                    checkedTrackColor = accentColor,
                                    uncheckedThumbColor = descColor,
                                    uncheckedTrackColor = dividerColor
                                )
                            )
                        }

                        HorizontalDivider(color = dividerColor, modifier = Modifier.padding(vertical = 12.dp))

                        // Push Notifications Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = textColor,
                                    modifier = Modifier.size(20.dp)
                                )
                                SpacerWidth(12)
                                Text(
                                    text = "Push Notifications",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = textColor
                                )
                            }
                            Switch(
                                checked = uiState.isNotificationsEnabled,
                                onCheckedChange = { viewModel.toggleNotifications(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = buttonContentColor,
                                    checkedTrackColor = accentColor,
                                    uncheckedThumbColor = descColor,
                                    uncheckedTrackColor = dividerColor
                                )
                            )
                        }
                    }
                }

                // Section 2: Regional Settings
                Text(
                    text = "Regional",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        SettingsClickableRow(
                            title = "App Language",
                            value = uiState.selectedLanguage,
                            textColor = textColor,
                            descColor = descColor,
                            onClick = {
                                viewModel.updateLanguage(if (uiState.selectedLanguage == "English") "Hindi" else "English")
                                Toast.makeText(context, "Language switched!", Toast.LENGTH_SHORT).show()
                            }
                        )
                        HorizontalDivider(color = dividerColor, modifier = Modifier.padding(vertical = 12.dp))
                        SettingsClickableRow(
                            title = "Default Currency",
                            value = uiState.selectedCurrency,
                            textColor = textColor,
                            descColor = descColor,
                            onClick = {
                                viewModel.updateCurrency(if (uiState.selectedCurrency == "INR (₹)") "USD ($)" else "INR (₹)")
                                Toast.makeText(context, "Currency preference updated!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }

                // Section 3: Account & Support
                Text(
                    text = "Account & Support",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                        AccountNavigationItem(
                            icon = Icons.Default.Person,
                            title = "Edit Profile & Bank details",
                            textColor = textColor,
                            accentColor = accentColor,
                            onClick = { viewModel.goToProfile() }
                        )
                        AccountNavigationItem(
                            icon = Icons.Default.Info,
                            title = "Privacy Policy",
                            textColor = textColor,
                            accentColor = accentColor,
                            onClick = { Toast.makeText(context, "Privacy Policy screen is under construction", Toast.LENGTH_SHORT).show() }
                        )
                        AccountNavigationItem(
                            icon = Icons.Default.Info,
                            title = "Terms of Service",
                            textColor = textColor,
                            accentColor = accentColor,
                            onClick = { Toast.makeText(context, "Terms of Service screen is under construction", Toast.LENGTH_SHORT).show() }
                        )
                    }
                }

                // Section 4: Danger Zone
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.showLogoutDialog() }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Red,
                            modifier = Modifier.size(22.dp)
                        )
                        SpacerWidth(12)
                        Text(
                            text = "Sign Out",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }

                SpacerHeight(8)
                // App Version info
                Text(
                    text = "Minori App Version ${uiState.appVersion}",
                    fontSize = 11.sp,
                    color = descColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsClickableRow(
    title: String,
    value: String,
    textColor: Color,
    descColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = descColor
        )
    }
}

@Composable
fun AccountNavigationItem(
    icon: ImageVector,
    title: String,
    textColor: Color,
    accentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = accentColor,
            modifier = Modifier.size(20.dp)
        )
        SpacerWidth(12)
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
