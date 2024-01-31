package com.blipblipcode.pruebasdegraficos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.blipblipcode.pruebasdegraficos.ui.theme.PruebasDeGraficosTheme
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebasDeGraficosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "value")

                    val value by infiniteTransition.animateFloat(
                        20f,
                        70f,
                        // No offset for the 1st animation
                        infiniteRepeatable(tween(2000), RepeatMode.Reverse), label = ""
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {

                        CircularTread(
                            pressureValue = value,
                            minPressure = 10f,
                            maxPressure = 80f,
                            circularColor = Color.Yellow,
                            lineIndicatorColor = Color.Black,
                            chartSize = 50.dp,
                            centerColor = Color.Green

                        )

                        CircularTread(
                            pressureValue = value,
                            minPressure = 0f,
                            maxPressure = 100f,
                            circularColor = Color.Magenta,
                            lineIndicatorColor = Color.Black,
                            chartSize = 100.dp
                        )
                        CircularTread(
                            pressureValue = value,
                            minPressure = 0f,
                            maxPressure = 200f,
                            circularColor = Color.Red,
                            lineIndicatorColor = Color.Blue,
                            chartSize = 200.dp
                        )
                    }




                }
            }
        }
    }
}


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

private fun mapValueToVisualRange(value: Float, minValue: Float, maxValue: Float): Float {
    val visualRange = 200f
    return ((value - minValue) / (maxValue - minValue)) * visualRange - 100f
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebasDeGraficosTheme {
        Greeting("Android")
    }
}