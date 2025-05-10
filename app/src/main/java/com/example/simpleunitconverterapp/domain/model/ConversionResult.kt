package com.example.simpleunitconverterapp.domain.model

import android.icu.util.MeasureUnit

data class ConversionResult(
    val originalValue: Double,
    val convertedValue: Double,
    val fromUnit: MeasureUnit,
    val toUnit: MeasureUnit,
    val unitType: UnitType
)