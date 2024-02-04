package com.blipblipcode.pruebasdegraficos.ui.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TreadShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val topRegion = size.height.times(0.15f)
        val bottomRegion = size.height.times(0.85f)

        val p0 = Offset(0f, topRegion)
        val p1 = Offset(size.width.div(2f), 0f)
        val p2 = Offset(size.width, topRegion)
        val p3 = Offset(size.width, size.height.times(0.85f))
        val p4 = Offset(size.width.div(2f), size.height)
        val p5 = Offset(0f, bottomRegion)

        val path = Path().apply {
            moveTo(p0.x, p0.y)
            cubicTo(
                p0.x, p0.y,
                0f, 0f,
                p1.x, p1.y
            )
            cubicTo(
                p1.x, p1.y,
                size.width, 0f,
                p2.x, p2.y
            )
            lineTo(p3.x, p3.y)
            cubicTo(
                p3.x, p3.y,
                size.width, size.height,
                p4.x, p4.y
            )
            cubicTo(
                p4.x, p4.y,
                0f, size.height,
                p5.x, p5.y
            )
            close()
        }
        return Outline.Generic(path)
    }
}