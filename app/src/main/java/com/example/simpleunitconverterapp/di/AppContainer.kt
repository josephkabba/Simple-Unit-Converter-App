package com.example.simpleunitconverterapp.di

import android.content.Context
import com.example.simpleunitconverterapp.data.ConversionRepositoryImpl
import com.example.simpleunitconverterapp.domain.repository.ConversionRepository
import com.example.simpleunitconverterapp.domain.usecase.ConvertUnitUseCase
import com.example.simpleunitconverterapp.presentation.viewmodel.ConverterViewModelFactory

class AppContainer(private val applicationContext: Context) {

    // Repository
    private val conversionRepository: ConversionRepository by lazy {
        ConversionRepositoryImpl()
    }

    // Use Case
    private val convertUnitUseCase by lazy {
        ConvertUnitUseCase(conversionRepository)
    }

    // ViewModel Factory
    val converterViewModelFactory: ConverterViewModelFactory by lazy {
        ConverterViewModelFactory(convertUnitUseCase)
    }
}