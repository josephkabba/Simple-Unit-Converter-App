package com.example.simpleunitconverterapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.simpleunitconverterapp.SimpleUnitConverterApplication
import com.example.simpleunitconverterapp.presentation.screens.ConverterScreen
import com.example.simpleunitconverterapp.presentation.theme.UnitConverterTheme
import com.example.simpleunitconverterapp.presentation.viewmodel.ConverterViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ConverterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as SimpleUnitConverterApplication).appContainer
        viewModel = appContainer.converterViewModelFactory.create(ConverterViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConverterScreen(viewModel)
                }
            }
        }
    }
}