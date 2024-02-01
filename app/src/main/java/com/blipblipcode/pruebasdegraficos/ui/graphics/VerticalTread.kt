package com.blipblipcode.pruebasdegraficos.ui.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun VerticalTread(pressureValue: Float,
                  minPressure: Float,
                  maxPressure: Float,
                  chartSize: DpSize = DpSize(width =  30.dp, height =  100.dp),
                  strokeLine: Float = 2f,
                  borderColor: Color = Color.Black ,
                  centerColor: Color = Color(147, 165, 182),
                  lineIndicatorColor: Color = Color.Black,
                  backgroundColor: Color = Color.White,
                  backgroundColorIndicator: Color = Color(204, 237, 220)
){
    Box(Modifier.padding(5.dp)) {
        Canvas(modifier = Modifier.size(chartSize)) {
            val regionPlot =size.height.times(0.70f)
            val topRegion = size.height.times(0.15f)
            val BottonRegion = size.height.times(0.85f)

            val p0 = Offset(0f, topRegion)
            val p1 = Offset(size.width.div(2f), 0f)
            val p2 = Offset(size.width, topRegion)
            val p3 = Offset(size.width, size.height. times(0.85f))
            val p4 = Offset(size.width.div(2f), size.height)
            val p5 = Offset(0f, BottonRegion)
             // pinta la linea del borde
            val pathLine = Path().apply {
                moveTo(p0.x, p0.y)
                cubicTo(p0.x, p0.y,
                    0f, 0f,
                    p1.x, p1.y)
                cubicTo(p1.x, p1.y,
                    size.width, 0f,
                    p2.x, p2.y)
                lineTo(p3.x, p3.y)
                cubicTo(p3.x, p3.y,
                    size.width, size.height,
                    p4.x, p4.y)
                cubicTo(p4.x, p4.y,
                    0f, size.height,
                    p5.x, p5.y)
                close()
            }

            drawPath(pathLine, borderColor)

            //pinta el baBackground
            val pathBackground = Path().apply {

                val stroke = strokeLine.dp.toPx()
                moveTo(p0.x.plus(stroke), p0.y.plus(stroke))
                cubicTo(p0.x.plus(stroke), p0.y.plus(stroke),
                    stroke,   stroke,
                    p1.x  , p1.y .plus(stroke))
                cubicTo(p1.x  , p1.y.plus(stroke),
                    size.width.minus(stroke), stroke,
                    p2.x.minus(stroke), p2.y.plus(stroke))
                lineTo(p3.x.minus(stroke), p3.y.minus(stroke))
                cubicTo(p3.x.minus(stroke), p3.y.minus(stroke),
                    size.width.minus(stroke), size.height.minus(stroke),
                    p4.x.minus(stroke), p4.y.minus(stroke))
                cubicTo(p4.x.minus(stroke), p4.y.minus(stroke),
                    stroke, size.height.minus(stroke),
                    p5.x.plus(stroke), p5.y.minus(stroke))
                close()
            }

            drawPath(pathBackground, backgroundColor)

            val range = maxPressure.minus(minPressure)
            val factor = range.div(7f)
            val segment = factor.times(BottonRegion - strokeLine.dp.toPx().times(2f)).div(100f)
            drawRect(backgroundColorIndicator,
                Offset(strokeLine.dp.toPx(), p0.y + strokeLine + segment.times(2f)),
                size = Size(width = size.width - strokeLine.dp.toPx().times(2f), height = segment.times(2f)))

            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)

            for (it in 0 until 7) {

                val percentage = factor.times(it)
                val value = percentage.times(BottonRegion- strokeLine.dp.toPx().times(2f)).div(100f) + p0.y + strokeLine
                val xRi = size.width - strokeLine.dp.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(strokeLine.dp.toPx(), value),
                    end = Offset(xRi, value),
                    pathEffect = pathEffect
                )



            }

            //Diuja la linea de indicación

            val yLine = (regionPlot- (pressureValue - minPressure) /
                    (maxPressure - minPressure) * (regionPlot - strokeLine.dp.toPx().times(2f))).plus(topRegion)
            drawLine(
                color = lineIndicatorColor,
                start = Offset(strokeLine.dp.toPx(), yLine),
                end = Offset(size.width - strokeLine.dp.toPx(), yLine),
                strokeWidth = strokeLine
            )
            val leftTriangle = Path().apply {
                moveTo(0f, yLine.minus(strokeLine.dp.toPx().times(2f)))
                lineTo(strokeLine.dp.toPx().times(4f), yLine)
                lineTo(0f, yLine.plus(strokeLine.dp.toPx().times(2f)))
            }
            drawPath(leftTriangle, lineIndicatorColor)

            val rightTriangle = Path().apply {
                moveTo(size.width, yLine.minus(strokeLine.dp.toPx().times(2f)))
                lineTo( size.width -strokeLine.dp.toPx().times(4f), yLine)
                lineTo(size.width, yLine.plus(strokeLine.dp.toPx().times(2f)))
            }
            drawPath(rightTriangle, lineIndicatorColor)




        }
    }
}

