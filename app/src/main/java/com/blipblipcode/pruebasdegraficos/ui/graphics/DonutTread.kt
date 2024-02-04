package com.blipblipcode.pruebasdegraficos.ui.graphics

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DonutTread(
    pressureValue: Float,
    minPressure: Float,
    maxPressure: Float,
    chartSize: Dp = 400.dp,
    strokeLine: Float = 2f,
    circularColor: Color = Color.Yellow,
    centerColor: Color = Color(147, 165, 182),
    backgroundColor: Color = Color.White,
    donutColorIndicator: Color = Color(204, 237, 220),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onClickAction: (() -> Unit)? = null
) {
    val sweep = remember { Animatable(0f) }
    LaunchedEffect(pressureValue) {
        sweep.animateTo(pressureValue, animationSpec = tween(1000))
    }


    // Aplica el modificador clip para la sombra

    Box(
        modifier
            .graphicsLayer(
                shape = CircleShape,
                clip = true,
            )
    ) {

        Canvas(
            modifier = Modifier
                .size(chartSize)
                .clickable(enabled = onClickAction != null, role = Role.RadioButton, onClick = {
                    onClickAction?.invoke()
                })
        ) {
            val radius = chartSize.toPx().div(2f).minus(strokeLine.dp.toPx())
            val centerX = size.width.div(2f)
            val centerY = size.height.div(2f)


            drawCircle(
                color = circularColor,
                radius = radius,
                center = Offset(centerX, centerY)
            )
            // Dibuja el fondo del gráfico
            drawCircle(
                color = backgroundColor,
                radius = radius - strokeLine.dp.toPx(),
                center = Offset(centerX, centerY)
            )

            // Dibuja los Grafico

            val sweepAngle = sweep.value / (maxPressure - minPressure) * 360f
            drawArc(
                color = donutColorIndicator,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    centerX - radius.minus(2.dp.toPx() + strokeLine.dp.toPx()),
                    centerY - radius.minus(2.dp.toPx() + strokeLine.dp.toPx())
                ),
                size = Size(
                    radius.minus(2.dp.toPx() + strokeLine.dp.toPx()).times(2f),
                    radius.minus(2.dp.toPx() + strokeLine.dp.toPx()).times(2f)
                )
            )

            // Dibuja el círculo central
            drawCircle(
                color = backgroundColor,
                radius = radius.plus(2.dp.toPx()).times(0.5f),
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = centerColor,
                radius = radius.times(0.5f),
                center = Offset(centerX, centerY)
            )

            // Dibuja los pequeños círculos alrededor del círculo central
            val smallCircleRadius = radius.times(0.07f)
            drawCircle(
                color = backgroundColor,
                radius = smallCircleRadius,
                center = Offset(centerX + radius.times(0.32f), centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = smallCircleRadius,
                center = Offset(centerX - radius.times(0.32f), centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = smallCircleRadius,
                center = Offset(centerX, centerY - radius.times(0.32f))
            )
            drawCircle(
                color = backgroundColor,
                radius = smallCircleRadius,
                center = Offset(centerX, centerY + radius.times(0.32f))
            )


        }
    }
}
