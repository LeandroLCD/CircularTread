package com.blipblipcode.pruebasdegraficos.ui.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun CircularTread(
    pressureValue: Float,
    minPressure: Float,
    maxPressure: Float,
    chartSize: Dp = 400.dp,
    strokeLine: Float = 2f,
    circularColor: Color = Color.Yellow,
    centerColor: Color = Color(147, 165, 182),
    lineIndicatorColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    backgroundColorIndicator: Color = Color(204, 237, 220)
) {

    Box(Modifier.padding(5.dp)) {

        Canvas(modifier = Modifier.size(chartSize)) {
            val radius = chartSize.toPx().div(2f)
            val centerX = size.width.div(2f)
            val centerY = size.height.div(2f)

            val visualValue = mapValueToVisualRange(pressureValue, minPressure, maxPressure)

            // Calcula las coordenadas x para ambos lados de la línea horizontal
            val xLeft = xLeft(visualValue, centerX, radius)
            val xRight = xRight(visualValue, centerX, radius)
            // Calcula la coordenada y de la línea horizontal (ajustada para desplazarse hacia arriba)
            val yLine = yLine(visualValue, centerY, radius)

            // Dibuja el círculo del Tread
            drawCircle(
                color = circularColor,
                radius = radius + strokeLine.dp.toPx(),
                center = Offset(centerX, centerY)
            )
            drawCircle(color = backgroundColor, radius = radius, center = Offset(centerX, centerY))


            //dibuja el fondo de indicador
            val path = Path().apply {
                val p1 = Offset(xLeft(60f, centerX, radius), yLine(60f, centerY, radius))
                val p2 = Offset(xRight(60f, centerX, radius), yLine(60f, centerY, radius))
                val arcR =
                    Offset(xRight(0f, centerX, radius.times(1.25f)), yLine(0f, centerY, radius))
                val arcL =
                    Offset(xLeft(0f, centerX, radius.times(1.25f)), yLine(0f, centerY, radius))

                val p3 = Offset(xRight(-60f, centerX, radius), yLine(-60f, centerY, radius))
                val p4 = Offset(xLeft(-60f, centerX, radius), yLine(-60f, centerY, radius))
                moveTo(p1.x, p1.y)
                lineTo(p2.x, p2.y)
                cubicTo(p2.x, p2.y, arcR.x, arcR.y, p3.x, p3.y)
                lineTo(p4.x, p4.y)
                cubicTo(p4.x, p4.y, arcL.x, arcL.y, p1.x, p1.y)
                close()

            }

            drawPath(path, backgroundColorIndicator)

            //dibuja las lineas segmentadas
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f)
            for (it in 1 until 10) {
                val range = maxPressure - minPressure
                val percentage = range.times(it.div(10f)) + minPressure
                val value = mapValueToVisualRange(percentage, minPressure, maxPressure)
                val xLe = centerX - sqrt(radius.pow(2) - (value / 100f * radius).pow(2))
                val xRi = centerX + sqrt(radius.pow(2) - (value / 100f * radius).pow(2))
                val yLi = centerY - (value / 100f * radius)
                drawLine(
                    color = Color.Black,
                    start = Offset(xLe + 4f, yLi),
                    end = Offset(xRi - 4f, yLi),
                    pathEffect = pathEffect
                )


            }

            // Dibuja la línea horizontal que se ajusta según el valor de la variable
            drawLine(
                color = lineIndicatorColor,
                start = Offset(xLeft, yLine),
                end = Offset(xRight, yLine),
                strokeWidth = strokeLine
            )
            drawCircle(
                color = lineIndicatorColor,
                radius = strokeLine.times(1.3f).dp.toPx(),
                center = Offset(xLeft, yLine)
            )
            drawCircle(
                color = lineIndicatorColor,
                radius = strokeLine.times(1.3f).dp.toPx(),
                center = Offset(xRight, yLine)
            )


            // Dibuja el círculo del TreadCenter
            drawCircle(
                color = centerColor,
                radius = radius.times(0.5f),
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = radius.times(0.15f),
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = radius.times(0.07f),
                center = Offset(centerX + radius.times(0.32f), centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = radius.times(0.07f),
                center = Offset(centerX - radius.times(0.32f), centerY)
            )
            drawCircle(
                color = backgroundColor,
                radius = radius.times(0.07f),
                center = Offset(centerX, centerY - radius.times(0.32f))
            )
            drawCircle(
                color = backgroundColor,
                radius = radius.times(0.07f),
                center = Offset(centerX, centerY + radius.times(0.32f))
            )


        }
    }
}

private fun yLine(value: Float, centerY: Float, radius: Float): Float {
    return centerY - (value / 100f * radius)
}

private fun xRight(value: Float, centerX: Float, radius: Float): Float {
    return centerX - sqrt(radius.pow(2) - (value / 100f * radius).pow(2))
}

private fun xLeft(value: Float, centerX: Float, radius: Float): Float {
    return centerX + sqrt(radius.pow(2) - (value / 100f * radius).pow(2))
}

fun mapValueToVisualRange(value: Float, minValue: Float, maxValue: Float, visualRange:Float = 200f): Float {

    return ((value - minValue) / (maxValue - minValue)) * visualRange - 100f
}
