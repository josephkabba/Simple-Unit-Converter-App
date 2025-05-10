package com.example.simpleunitconverterapp.domain.model

import android.icu.util.MeasureUnit

data class ConversionRequest(
    val unitType: UnitType,
    val fromUnit: MeasureUnit,
    val toUnit: MeasureUnit,
    val value: Double
)