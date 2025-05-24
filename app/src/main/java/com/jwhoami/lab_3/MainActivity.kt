package com.jwhoami.lab_3

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jwhoami.lab_3.ui.theme.Lab3Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    var guess by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    var randomNumber by remember { mutableIntStateOf(Random.nextInt(1, 101)) }
    var attemptsLeft by remember { mutableIntStateOf(3) }
    var gameOver by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¡Guess the Number!",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            "The number is between 1 and 100...",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = guess,
            onValueChange = { guess = it },
            label = { Text("") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Attempts left: $attemptsLeft",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button (
            onClick = {
                val userGuess = guess.toIntOrNull()
                if (userGuess == null) {
                    resultMessage = "Please enter a valid number."
                } else {
                    resultMessage = when {
                        userGuess < randomNumber -> "Too low!"
                        userGuess > randomNumber -> "Too high!"
                        else -> {
                            gameOver = true
                            "Correct! New number generated."
                        }
                    }
                }

                attemptsLeft--
                if (attemptsLeft <= 0) {
                    resultMessage = "Game over! The number was $randomNumber."
                    gameOver = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            enabled = !gameOver
        ) {
            Text(
                text = "¡Guess It!"
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedButton (
            onClick = {
                guess = ""
                resultMessage = ""
                randomNumber = Random.nextInt(1, 101)
                attemptsLeft = 3
                gameOver = false
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "¡Try again!"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resultMessage.isNotEmpty()) {
            Text(
                text = resultMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    Lab3Theme {
        MainView()
    }
}
