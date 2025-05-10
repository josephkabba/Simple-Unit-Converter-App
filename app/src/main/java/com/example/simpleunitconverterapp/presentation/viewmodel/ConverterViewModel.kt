package com.example.simpleunitconverterapp.presentation.viewmodel

import com.example.simpleunitconverterapp.domain.usecase.ConvertUnitUseCase
import android.icu.util.MeasureUnit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleunitconverterapp.domain.model.ConversionRequest
import com.example.simpleunitconverterapp.domain.model.ConversionResult
import com.example.simpleunitconverterapp.domain.model.UnitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val convertUnitUseCase: ConvertUnitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    fun onUnitTypeSelected(unitType: UnitType) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedUnitType = unitType,
                selectedFromUnit = unitType.units.first(),
                selectedToUnit = unitType.units[1],
                inputValue = "",
                conversionResult = null,
                error = null
            )
        }
    }

    fun onFromUnitSelected(unit: MeasureUnit) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedFromUnit = unit,
                conversionResult = null,
                error = null
            )
        }
    }

    fun onToUnitSelected(unit: MeasureUnit) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedToUnit = unit,
                conversionResult = null,
                error = null
            )
        }
    }

    fun onInputValueChanged(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.update { currentState ->
                currentState.copy(
                    inputValue = value,
                    conversionResult = null,
                    error = null
                )
            }
        }
    }

    fun swapUnits() {
        val currentState = _uiState.value
        _uiState.update {
            it.copy(
                selectedFromUnit = currentState.selectedToUnit,
                selectedToUnit = currentState.selectedFromUnit,
                conversionResult = null,
                error = null
            )
        }
    }

    fun convert() {
        val currentState = _uiState.value
        val inputValue = currentState.inputValue.toDoubleOrNull()

        when {
            currentState.inputValue.isEmpty() -> {
                _uiState.update {
                    it.copy(error = "Please enter a value")
                }
            }
            inputValue == null -> {
                _uiState.update {
                    it.copy(error = "Invalid number format")
                }
            }
            else -> {
                viewModelScope.launch {
                    try {
                        val request = ConversionRequest(
                            unitType = currentState.selectedUnitType,
                            fromUnit = currentState.selectedFromUnit,
                            toUnit = currentState.selectedToUnit,
                            value = inputValue
                        )

                        val result = convertUnitUseCase(request)

                        _uiState.update {
                            it.copy(conversionResult = result, error = null)
                        }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(error = e.message ?: "Unknown error occurred")
                        }
                    }
                }
            }
        }
    }
}

data class ConverterUiState(
    val selectedUnitType: UnitType = UnitType.Temperature,
    val selectedFromUnit: MeasureUnit = UnitType.Temperature.units.first(),
    val selectedToUnit: MeasureUnit = UnitType.Temperature.units[1],
    val inputValue: String = "",
    val conversionResult: ConversionResult? = null,
    val error: String? = null
)