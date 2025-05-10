package com.example.simpleunitconverterapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleunitconverterapp.domain.usecase.ConvertUnitUseCase

class ConverterViewModelFactory(
    private val convertUnitUseCase: ConvertUnitUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            return ConverterViewModel(convertUnitUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}