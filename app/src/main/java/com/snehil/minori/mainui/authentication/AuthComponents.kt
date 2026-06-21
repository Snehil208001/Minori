package com.snehil.minori.mainui.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snehil.minori.ui.theme.CharcoalText
import com.snehil.minori.ui.theme.EarthyStone
import com.snehil.minori.ui.theme.SandCream
import com.snehil.minori.ui.theme.Terracotta

@Composable
fun MinoriTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false
) {
    val isDark = isSystemInDarkTheme()
    val fieldBgColor = if (isDark) Color(0xFF292524) else Color(0xFFF5F4F0)
    val unfocusedBorder = if (isDark) Color(0xFF44403C) else Color(0xFFD1D5DB)
    val focusedBorder = if (isError) Color.Red else Terracotta
    val textColor = if (isDark) SandCream else CharcoalText
    val iconColor = if (isDark) Color(0xFFB4ADAC) else Color(0xFF6B7280)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        placeholder = { 
            Text(
                text = placeholder, 
                color = if (isDark) Color(0xFF78716C) else Color(0xFF9CA3AF),
                fontSize = 15.sp
            ) 
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = placeholder,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedContainerColor = fieldBgColor,
            unfocusedContainerColor = fieldBgColor,
            focusedBorderColor = focusedBorder,
            unfocusedBorderColor = unfocusedBorder,
            errorBorderColor = Color.Red,
            cursorColor = focusedBorder
        )
    )
}

@Composable
fun SocialLoginRow(
    onGoogleClick: () -> Unit = {},
    onAppleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialButton(type = SocialType.GOOGLE, onClick = onGoogleClick)
        SpacerWidth(20)
        SocialButton(type = SocialType.APPLE, onClick = onAppleClick)
        SpacerWidth(20)
        SocialButton(type = SocialType.FACEBOOK, onClick = onFacebookClick)
    }
}

enum class SocialType {
    GOOGLE, APPLE, FACEBOOK
}

@Composable
fun SocialButton(
    type: SocialType,
    onClick: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val containerBg = if (isDark) Color(0xFF292524) else Color(0xFFFFF5F5)
    val borderCol = if (isDark) Color(0xFF44403C) else Color(0xFFFEE2E2)

    Card(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = containerBg),
        border = BorderStroke(1.2.dp, borderCol),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (type) {
                SocialType.GOOGLE -> GoogleLogoDraw()
                SocialType.APPLE -> AppleLogoDraw(isDark = isDark)
                SocialType.FACEBOOK -> FacebookLogoDraw()
            }
        }
    }
}

@Composable
fun GoogleLogoDraw() {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val strokeW = w * 0.16f

        drawArc(
            color = Color(0xFFEA4335),
            startAngle = 180f,
            sweepAngle = 135f,
            useCenter = false,
            style = Stroke(width = strokeW, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = 120f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeW, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color(0xFF34A853),
            startAngle = 0f,
            sweepAngle = 120f,
            useCenter = false,
            style = Stroke(width = strokeW, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = 315f,
            sweepAngle = 45f,
            useCenter = false,
            style = Stroke(width = strokeW, cap = StrokeCap.Round)
        )
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(cx, cy),
            end = Offset(cx + w * 0.45f, cy),
            strokeWidth = strokeW,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun AppleLogoDraw(isDark: Boolean) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val color = if (isDark) Color.White else Color.Black

        val applePath = Path().apply {
            moveTo(cx, cy + h * 0.35f)
            cubicTo(
                cx - w * 0.2f, cy + h * 0.38f,
                cx - w * 0.35f, cy + h * 0.2f,
                cx - w * 0.35f, cy - h * 0.1f
            )
            cubicTo(
                cx - w * 0.35f, cy - h * 0.3f,
                cx - w * 0.15f, cy - h * 0.35f,
                cx, cy - h * 0.22f
            )
            cubicTo(
                cx + w * 0.15f, cy - h * 0.35f,
                cx + w * 0.35f, cy - h * 0.3f,
                cx + w * 0.35f, cy - h * 0.1f
            )
            cubicTo(
                cx + w * 0.35f, cy - h * 0.05f,
                cx + w * 0.22f, cy - h * 0.02f,
                cx + w * 0.22f, cy - h * 0.02f
            )
            cubicTo(
                cx + w * 0.22f, cy + h * 0.15f,
                cx + w * 0.35f, cy + h * 0.12f,
                cx + w * 0.35f, cy + h * 0.12f
            )
            cubicTo(
                cx + w * 0.35f, cy + h * 0.28f,
                cx + w * 0.2f, cy + h * 0.38f,
                cx, cy + h * 0.35f
            )
            close()
        }

        val leafPath = Path().apply {
            moveTo(cx, cy - h * 0.25f)
            quadraticTo(cx + w * 0.08f, cy - h * 0.42f, cx + w * 0.15f, cy - h * 0.45f)
            quadraticTo(cx + w * 0.18f, cy - h * 0.32f, cx + w * 0.04f, cy - h * 0.25f)
            close()
        }

        drawPath(path = applePath, color = color)
        drawPath(path = leafPath, color = color)
    }
}

@Composable
fun FacebookLogoDraw() {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        drawCircle(
            color = Color(0xFF1877F2),
            radius = w / 2f
        )

        val fPath = Path().apply {
            moveTo(cx + w * 0.2f, h)
            lineTo(cx + w * 0.2f, cy + h * 0.12f)
            lineTo(cx + w * 0.35f, cy + h * 0.12f)
            lineTo(cx + w * 0.38f, cy - h * 0.08f)
            lineTo(cx + w * 0.2f, cy - h * 0.08f)
            lineTo(cx + w * 0.2f, cy - h * 0.22f)
            cubicTo(
                cx + w * 0.2f, cy - h * 0.32f,
                cx + w * 0.25f, cy - h * 0.38f,
                cx + w * 0.38f, cy - h * 0.38f
            )
            lineTo(cx + w * 0.45f, cy - h * 0.38f)
            lineTo(cx + w * 0.45f, cy - h * 0.52f)
            lineTo(cx + w * 0.32f, cy - h * 0.52f)
            cubicTo(
                cx + w * 0.08f, cy - h * 0.52f,
                cx - w * 0.02f, cy - h * 0.38f,
                cx - w * 0.02f, cy - h * 0.18f
            )
            lineTo(cx - w * 0.02f, cy - h * 0.08f)
            lineTo(cx - w * 0.15f, cy - h * 0.08f)
            lineTo(cx - w * 0.15f, cy + h * 0.12f)
            lineTo(cx - w * 0.02f, cy + h * 0.12f)
            lineTo(cx - w * 0.02f, h)
            close()
        }

        drawPath(path = fPath, color = Color.White)
    }
}

@Composable
fun PasswordToggleIcon(
    visible: Boolean,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Draw Eye shape outer almond
        val eyePath = Path().apply {
            moveTo(w * 0.1f, cy)
            quadraticTo(cx, cy - h * 0.35f, w * 0.9f, cy)
            quadraticTo(cx, cy + h * 0.35f, w * 0.1f, cy)
            close()
        }
        drawPath(
            path = eyePath,
            color = tint,
            style = Stroke(width = w * 0.08f, cap = StrokeCap.Round)
        )

        // Draw Pupil
        drawCircle(
            color = tint,
            radius = w * 0.16f,
            center = Offset(cx, cy)
        )

        // Draw diagonal slash if invisible
        if (!visible) {
            drawLine(
                color = tint,
                start = Offset(w * 0.2f, cy - h * 0.3f),
                end = Offset(w * 0.8f, cy + h * 0.3f),
                strokeWidth = w * 0.08f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun SpacerWidth(width: Int) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(width.dp))
}

@Composable
fun SpacerHeight(height: Int) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height.dp))
}
