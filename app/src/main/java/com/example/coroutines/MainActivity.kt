package com.example.coroutines


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.coroutines.ui.theme.CoroutinesTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    fibonacciSeries()

                }
            }
        }
    }
}

@Composable
fun fibonacciSeries() {
    var fibonacciList by remember {
        mutableStateOf(listOf<Int>())
    }
    val scope = rememberCoroutineScope()

    Column {
        Button(onClick = {
            scope.launch {
                fibonacciList = generateFibonacciSeriesAsync(10)
            }
        }) {
            Text(text = "Generate Fibonacci Series")
        }

        Text(text = "Fibonacci Series: ${fibonacciList.joinToString(", ")}")
    }
}

suspend fun generateFibonacciSeriesAsync(n: Int): List<Int> = coroutineScope {
    val deferredResult = async {
        generateFibonacciSeries(n)
    }
    deferredResult.await()
}

suspend fun generateFibonacciSeries(n: Int): List<Int> {
    val series = mutableListOf(0, 1)

    if (n < 2) return series

    for (i in 2 until n) {
        val next = series[i - 1] + series[i - 2]
        series.add(next)
        delay(1000L)
    }

    return series
}

