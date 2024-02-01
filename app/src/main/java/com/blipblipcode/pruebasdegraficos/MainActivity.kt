package com.blipblipcode.pruebasdegraficos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.blipblipcode.pruebasdegraficos.ui.graphics.CircularTread
import com.blipblipcode.pruebasdegraficos.ui.graphics.VerticalTread
import com.blipblipcode.pruebasdegraficos.ui.theme.PruebasDeGraficosTheme

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
                        0f,
                        100f,
                        infiniteRepeatable(tween(2000), RepeatMode.Reverse), label = ""
                    )

                    val value2 by animateFloatAsState(
                        targetValue = 80f,
                        animationSpec = tween(durationMillis = 2000, easing = LinearEasing, delayMillis = 200),
                        label = "value2"
                    )

                    Column( modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {

                        CircularTread(pressureValue = value, minPressure = 10f, maxPressure =100f, chartSize = 200.dp )

                        CircularTread(pressureValue = value2, minPressure = 10f, maxPressure =100f, chartSize = 50.dp )

                        VerticalTread(pressureValue = value2,
                            minPressure = 0f,
                            maxPressure = 100f,
                            backgroundColor = Color.White,
                            chartSize = DpSize(width = 100.dp, height = 300.dp))


                        VerticalTread(pressureValue = value,
                            minPressure = 0f,
                            maxPressure = 100f,
                            backgroundColor = Color.White,
                            chartSize = DpSize(width = 100.dp, height = 300.dp))
                    }




                }
            }
        }
    }
}




