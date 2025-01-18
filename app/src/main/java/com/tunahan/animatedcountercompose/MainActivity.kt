package com.tunahan.animatedcountercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tunahan.animatedcountercompose.ui.theme.AnimatedCounterComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimatedCounterComposeTheme {
                AnimatedCounter()
            }
        }
    }
}

@Composable
fun AnimatedCounter() {
    var count by remember { mutableIntStateOf(0) }
    var oldCount by remember { mutableIntStateOf(count) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }

    SideEffect {
        oldCount = count
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            val countString = count.toString()
            val oldCountString = oldCount.toString()
            for (i in countString.indices) {
                val oldChar = oldCountString.getOrNull(i)
                val newChar = countString[i]
                val char = if (oldChar == newChar) oldCountString[i] else countString[i]

                AnimatedContent(
                    targetState = char,
                    transitionSpec = {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    }, label = ""
                ) { animatedChar ->
                    Text(
                        text = animatedChar.toString(),
                        softWrap = false,
                        fontSize = 80.sp
                    )
                }
            }
        }
    }
}