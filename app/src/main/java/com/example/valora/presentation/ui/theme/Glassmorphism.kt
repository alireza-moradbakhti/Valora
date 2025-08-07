package com.example.valora.presentation.ui.theme


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A custom Modifier that applies a glassmorphism effect to a Composable.
 * This creates a frosted-glass look by blurring the content behind it.
 *
 * @param cornerRadius The radius of the corners for the glass panel.
 * @param blurRadius The intensity of the blur effect.
 * @param color The translucent color of the glass panel.
 * @param borderWidth The width of the optional border.
 * @param borderColor The color of the optional border.
 */
fun Modifier.glassmorphism(
    cornerRadius: Dp = 16.dp,
    blurRadius: Dp = 20.dp,
    color: Color = Color.White.copy(alpha = 0.2f),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.White.copy(alpha = 0.3f)
): Modifier = this.drawBehind {
    // Draw the blurred background
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        frameworkPaint.color = color.toArgb()
        if (blurRadius > 0.dp) {
            frameworkPaint.maskFilter = android.graphics.BlurMaskFilter(
                blurRadius.toPx(),
                android.graphics.BlurMaskFilter.Blur.NORMAL
            )
        }
        canvas.drawRoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            radiusX = cornerRadius.toPx(),
            radiusY = cornerRadius.toPx(),
            paint = paint
        )
    }

    // Draw the optional border on top
    if (borderWidth > 0.dp) {
        drawRoundRect(
            color = borderColor,
            topLeft = androidx.compose.ui.geometry.Offset.Zero,
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius.toPx()),
            style = Stroke(width = borderWidth.toPx())
        )
    }
}
