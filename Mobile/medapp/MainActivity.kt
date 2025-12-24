package com.hyper.medapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hyper.medapp.ui.theme.MedAppTheme

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val responseMessage by viewModel.responseMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // header logo and avatar
        Header()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = responseMessage,
                    modifier = Modifier.padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Кнопка "Загрузить фото"
            Button(
                onClick = {
                    // Нужно будет дописать логику загрузки анализа
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Загрузить анализ")
            }

            // Запись к врачу
            Button(
                onClick = {
                    viewModel.fetchData()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Записаться к врачу")
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            )
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Логотип приложения
        Image(
            painter = painterResource(id = R.drawable.ic_logo), // Укажите свой файл логотипа
            contentDescription = "Логотип приложения",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(60.dp),
            contentScale = ContentScale.Fit
        )

        // Аватарка пользователя
        Image(
            painter = painterResource(id = R.drawable.ic_avatar_placeholder),
            contentDescription = "user avatar",
            modifier = Modifier
                .padding(end = 16.dp)
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}