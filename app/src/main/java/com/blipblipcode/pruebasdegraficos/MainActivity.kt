package com.blipblipcode.pruebasdegraficos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.blipblipcode.pruebasdegraficos.ui.graphics.CircularTread
import com.blipblipcode.pruebasdegraficos.ui.graphics.DonutTread
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

                    val context = LocalContext.current

                    Column( modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween) {

                        CircularTread(
                            pressureValue = 80f,
                            minPressure = 10f,
                            maxPressure = 100f,
                            chartSize = 100.dp
                        ) {
                            Toast.makeText(context, "CircularTread", Toast.LENGTH_SHORT).show()
                        }

                        VerticalTread(
                            pressureValue = 60f,
                            minPressure = 0f,
                            maxPressure = 100f,
                            backgroundColor = Color.White,
                            chartSize = DpSize(width = 50.dp, height = 150.dp)
                        ) {
                            Toast.makeText(context, "VerticalTread", Toast.LENGTH_SHORT).show()
                        }


                        DonutTread(
                            pressureValue = 99f,
                            minPressure = 0f,
                            maxPressure = 100f,
                            backgroundColor = Color.White,
                            donutColorIndicator = Color.Magenta,
                            circularColor = Color.Red,
                            chartSize = 200.dp
                        ) {
                            Toast.makeText(context, "DonutTread", Toast.LENGTH_SHORT).show()
                        }
                    }




                }
            }
        }
    }
}




