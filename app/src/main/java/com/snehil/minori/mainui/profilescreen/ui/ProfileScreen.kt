package com.snehil.minori.mainui.profilescreen.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snehil.minori.mainui.authentication.MinoriTextField
import com.snehil.minori.mainui.authentication.PasswordToggleIcon
import com.snehil.minori.mainui.authentication.SpacerHeight
import com.snehil.minori.mainui.authentication.SpacerWidth
import com.snehil.minori.mainui.profilescreen.viewmodel.ProfileEffect
import com.snehil.minori.mainui.profilescreen.viewmodel.ProfileScreenViewModel
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.SoftTerracotta
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var showSuccessDialog by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEffect.SaveSuccess -> {
                    showSuccessDialog = true
                }
                ProfileEffect.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if (isDark) Color(0xFF1C1917) else SandCream
    val textColor = if (isDark) SandCream else CharcoalText
    val descColor = if (isDark) Color(0xFFB4ADAC) else EarthyStone
    val accentColor = if (isDark) SoftTerracotta else Terracotta
    val buttonBgColor = if (isDark) SoftTerracotta else Terracotta
    val buttonContentColor = if (isDark) CharcoalText else Color.White
    val cardBg = if (isDark) Color(0xFF292524) else Color.White
    val dividerColor = if (isDark) Color(0xFF44403C) else Color(0xFFE5E7EB)

    val scrollState = rememberScrollState()

    val states = listOf(
        "Delhi", "Maharashtra", "Karnataka", "Tamil Nadu", "Uttar Pradesh", "West Bengal", "Gujarat", "Rajasthan"
    )

    if (showSuccessDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onNavigateBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("OK", color = buttonContentColor)
                }
            },
            title = {
                Text(
                    "Profile Saved",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            },
            text = {
                Text(
                    "Your profile details, business address, and bank details have been saved successfully.",
                    color = descColor
                )
            },
            containerColor = cardBg,
            shape = RoundedCornerShape(16.dp)
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Top Bar
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
                    // Back arrow custom drawing
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
                    text = "Edit Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = textColor
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp)
            ) {
                SpacerHeight(16)

                // Circular Avatar Box with Black Hair and Bangs
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF44403C) else Color(0xFFFEE2E2))
                    ) {
                        val w = size.width
                        val h = size.height
                        val cx = w / 2f
                        val cy = h / 2f

                        // Neck
                        drawRect(
                            color = Color(0xFFFFD1B3),
                            topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.12f, cy + h * 0.15f),
                            size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.25f)
                        )

                        // Face
                        drawCircle(
                            color = Color(0xFFFFD1B3),
                            radius = w * 0.32f,
                            center = androidx.compose.ui.geometry.Offset(cx, cy)
                        )

                        // Eyes
                        drawCircle(
                            color = Color(0xFF2C2523),
                            radius = w * 0.03f,
                            center = androidx.compose.ui.geometry.Offset(cx - w * 0.12f, cy - h * 0.02f)
                        )
                        drawCircle(
                            color = Color(0xFF2C2523),
                            radius = w * 0.03f,
                            center = androidx.compose.ui.geometry.Offset(cx + w * 0.12f, cy - h * 0.02f)
                        )

                        // Blush cheeks
                        drawCircle(
                            color = Color(0xFFFFB3B3),
                            radius = w * 0.04f,
                            center = androidx.compose.ui.geometry.Offset(cx - w * 0.18f, cy + h * 0.06f),
                            alpha = 0.6f
                        )
                        drawCircle(
                            color = Color(0xFFFFB3B3),
                            radius = w * 0.04f,
                            center = androidx.compose.ui.geometry.Offset(cx + w * 0.18f, cy + h * 0.06f),
                            alpha = 0.6f
                        )

                        // Smile
                        drawArc(
                            color = Color(0xFFB35959),
                            startAngle = 0f,
                            sweepAngle = 180f,
                            useCenter = false,
                            topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.06f, cy + h * 0.04f),
                            size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.08f),
                            style = Stroke(width = w * 0.02f, cap = StrokeCap.Round)
                        )

                        // Black Hair (Back hair & Bangs)
                        val hairColor = Color(0xFF1E1E1E)

                        drawArc(
                            color = hairColor,
                            startAngle = 140f,
                            sweepAngle = 260f,
                            useCenter = true,
                            topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.38f, cy - h * 0.38f),
                            size = androidx.compose.ui.geometry.Size(w * 0.76f, h * 0.76f)
                        )

                        val bangsPath = Path().apply {
                            moveTo(cx - w * 0.35f, cy - h * 0.15f)
                            quadraticTo(cx - w * 0.2f, cy - h * 0.32f, cx, cy - h * 0.3f)
                            quadraticTo(cx + w * 0.2f, cy - h * 0.32f, cx + w * 0.35f, cy - h * 0.15f)
                            lineTo(cx + w * 0.35f, cy - h * 0.12f)
                            lineTo(cx + w * 0.22f, cy - h * 0.12f)
                            lineTo(cx + w * 0.15f, cy - h * 0.18f)
                            lineTo(cx + w * 0.08f, cy - h * 0.12f)
                            lineTo(cx, cy - h * 0.18f)
                            lineTo(cx - w * 0.08f, cy - h * 0.12f)
                            lineTo(cx - w * 0.15f, cy - h * 0.18f)
                            lineTo(cx - w * 0.22f, cy - h * 0.12f)
                            close()
                        }
                        drawPath(path = bangsPath, color = hairColor)
                    }

                    // Overlapping blue edit pencil icon
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xFF3B82F6))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val col = Color.White
                            val w = size.width
                            val h = size.height

                            val pencilPath = Path().apply {
                                moveTo(w * 0.7f, w * 0.1f)
                                lineTo(w * 0.9f, w * 0.3f)
                                lineTo(w * 0.4f, w * 0.8f)
                                lineTo(w * 0.1f, w * 0.9f)
                                lineTo(w * 0.2f, w * 0.6f)
                                close()
                            }
                            drawPath(pencilPath, color = col)
                        }
                    }
                }

                SpacerHeight(24)

                // Error text if present
                errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    SpacerHeight(12)
                }

                // ================= SECTION 1: Personal Details =================
                Text(
                    text = "Personal Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = accentColor
                )
                SpacerHeight(4)
                HorizontalDivider(color = dividerColor)
                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = "Email Address",
                    leadingIcon = Icons.Default.Email,
                    isError = uiState.emailError
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = "Password",
                    leadingIcon = Icons.Default.Lock,
                    isError = uiState.passwordError,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            PasswordToggleIcon(visible = passwordVisible, tint = accentColor, modifier = Modifier.size(20.dp))
                        }
                    }
                )

                SpacerHeight(8)

                Text(
                    text = "Change Password",
                    fontSize = 14.sp,
                    color = accentColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { }
                )

                SpacerHeight(24)

                // ================= SECTION 2: Business Address Details =================
                Text(
                    text = "Business Address Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = accentColor
                )
                SpacerHeight(4)
                HorizontalDivider(color = dividerColor)
                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.pincode,
                    onValueChange = { viewModel.updatePincode(it) },
                    placeholder = "Pincode",
                    leadingIcon = Icons.Default.Info,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.pincodeError
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.address,
                    onValueChange = { viewModel.updateAddress(it) },
                    placeholder = "Address",
                    leadingIcon = Icons.Default.Home,
                    isError = uiState.addressError
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.city,
                    onValueChange = { viewModel.updateCity(it) },
                    placeholder = "City",
                    leadingIcon = Icons.Default.Home,
                    isError = uiState.cityError
                )

                SpacerHeight(12)

                MinoriStateDropdown(
                    selectedState = uiState.state,
                    onStateSelected = { viewModel.updateStateSelected(it) },
                    states = states
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.country,
                    onValueChange = { viewModel.updateCountry(it) },
                    placeholder = "Country",
                    leadingIcon = Icons.Default.Info
                )

                SpacerHeight(24)

                // ================= SECTION 3: Bank Account Details =================
                Text(
                    text = "Bank Account Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = accentColor
                )
                SpacerHeight(4)
                HorizontalDivider(color = dividerColor)
                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.bankAccountNo,
                    onValueChange = { viewModel.updateBankAccountNo(it) },
                    placeholder = "Bank Account Number",
                    leadingIcon = Icons.Default.Lock,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.bankAccountNoError
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.bankHolderName,
                    onValueChange = { viewModel.updateBankHolderName(it) },
                    placeholder = "Account Holder's Name",
                    leadingIcon = Icons.Default.Person,
                    isError = uiState.bankHolderNameError
                )

                SpacerHeight(12)

                MinoriTextField(
                    value = uiState.bankIfsc,
                    onValueChange = { viewModel.updateBankIfsc(it) },
                    placeholder = "IFSC Code",
                    leadingIcon = Icons.Default.Settings,
                    isError = uiState.bankIfscError
                )

                SpacerHeight(32)

                Button(
                    onClick = { viewModel.saveProfile() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonBgColor,
                        contentColor = buttonContentColor
                    )
                ) {
                    Text(
                        text = "Save Profile",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                SpacerHeight(32)
            }
        }
    }
}

@Composable
fun MinoriStateDropdown(
    selectedState: String,
    onStateSelected: (String) -> Unit,
    states: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val fieldBgColor = if (isDark) Color(0xFF292524) else Color(0xFFF5F4F0)
    val unfocusedBorder = if (isDark) Color(0xFF44403C) else Color(0xFFD1D5DB)
    val textColor = if (isDark) SandCream else CharcoalText

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedState,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .clickable { expanded = true },
            enabled = false,
            placeholder = {
                Text(
                    text = "Select State",
                    color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                    fontSize = 15.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "State",
                    tint = if (isDark) Color(0xFFB4ADAC) else Color(0xFF6B7280),
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                Canvas(modifier = Modifier.size(16.dp)) {
                    val w = size.width
                    val h = size.height
                    val col = if (isDark) Color(0xFFB4ADAC) else Color(0xFF6B7280)
                    val path = Path().apply {
                        if (expanded) {
                            moveTo(w * 0.2f, h * 0.65f)
                            lineTo(w * 0.5f, h * 0.35f)
                            lineTo(w * 0.8f, h * 0.65f)
                        } else {
                            moveTo(w * 0.2f, h * 0.35f)
                            lineTo(w * 0.5f, h * 0.65f)
                            lineTo(w * 0.8f, h * 0.35f)
                        }
                    }
                    drawPath(
                        path = path,
                        color = col,
                        style = Stroke(width = w * 0.12f, cap = StrokeCap.Round)
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = textColor,
                disabledBorderColor = unfocusedBorder,
                disabledContainerColor = fieldBgColor,
                disabledPlaceholderColor = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF)
            )
        )

        // Dropdown menu click interceptor layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(if (isDark) Color(0xFF292524) else Color.White)
        ) {
            states.forEach { stateItem ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(text = stateItem, color = textColor) },
                    onClick = {
                        onStateSelected(stateItem)
                        expanded = false
                    }
                )
            }
        }
    }
}
