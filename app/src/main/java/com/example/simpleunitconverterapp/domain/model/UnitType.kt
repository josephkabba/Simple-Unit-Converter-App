package com.example.simpleunitconverterapp.domain.model
import android.icu.util.MeasureUnit

sealed class UnitType {
    data object Temperature : UnitType() {
        override val units = listOf(
            MeasureUnit.CELSIUS,
            MeasureUnit.FAHRENHEIT,
            MeasureUnit.KELVIN
        )
    }

    data object Length : UnitType() {
        override val units = listOf(
            MeasureUnit.METER,
            MeasureUnit.FOOT,
            MeasureUnit.INCH
        )
    }

    data object Mass : UnitType() {
        override val units = listOf(
            MeasureUnit.KILOGRAM,
            MeasureUnit.POUND,
            MeasureUnit.OUNCE
        )
    }

    abstract val units: List<MeasureUnit>

    companion object {
        fun values() = listOf(Temperature, Length, Mass)
    }
}