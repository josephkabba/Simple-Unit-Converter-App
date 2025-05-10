package com.example.simpleunitconverterapp.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import android.icu.text.MeasureFormat
import com.example.simpleunitconverterapp.domain.model.ConversionRequest
import com.example.simpleunitconverterapp.domain.model.ConversionResult
import com.example.simpleunitconverterapp.domain.model.UnitType
import com.example.simpleunitconverterapp.domain.repository.ConversionRepository

class ConversionRepositoryImpl: ConversionRepository {
    override suspend fun convertUnit(request: ConversionRequest): ConversionResult {
        val convertedValue = when (request.unitType) {
            is UnitType.Temperature -> convertTemperature(
                request.value,
                request.fromUnit,
                request.toUnit
            )
            is UnitType.Length -> convertLength(
                request.value,
                request.fromUnit,
                request.toUnit
            )
            is UnitType.Mass -> convertMass(
                request.value,
                request.fromUnit,
                request.toUnit
            )

            UnitType.Length -> TODO()
            UnitType.Mass -> TODO()
            UnitType.Temperature -> TODO()
        }

        return ConversionResult(
            originalValue = request.value,
            convertedValue = convertedValue,
            fromUnit = request.fromUnit,
            toUnit = request.toUnit,
            unitType = request.unitType
        )
    }

    private fun convertTemperature(
        value: Double,
        fromUnit: MeasureUnit,
        toUnit: MeasureUnit
    ): Double {
        // Convert to Kelvin first
        val kelvin = when (fromUnit) {
            MeasureUnit.CELSIUS -> value + 273.15
            MeasureUnit.FAHRENHEIT -> (value + 459.67) * 5.0 / 9.0
            MeasureUnit.KELVIN -> value
            else -> throw IllegalArgumentException("Unsupported temperature unit: $fromUnit")
        }

        // Convert from Kelvin to target unit
        return when (toUnit) {
            MeasureUnit.CELSIUS -> kelvin - 273.15
            MeasureUnit.FAHRENHEIT -> kelvin * 9.0 / 5.0 - 459.67
            MeasureUnit.KELVIN -> kelvin
            else -> throw IllegalArgumentException("Unsupported temperature unit: $toUnit")
        }
    }

    private fun convertLength(
        value: Double,
        fromUnit: MeasureUnit,
        toUnit: MeasureUnit
    ): Double {
        // Convert to meters first
        val meters = when (fromUnit) {
            MeasureUnit.METER -> value
            MeasureUnit.FOOT -> value * 0.3048
            MeasureUnit.INCH -> value * 0.0254
            else -> throw IllegalArgumentException("Unsupported length unit: $fromUnit")
        }

        // Convert from meters to target unit
        return when (toUnit) {
            MeasureUnit.METER -> meters
            MeasureUnit.FOOT -> meters / 0.3048
            MeasureUnit.INCH -> meters / 0.0254
            else -> throw IllegalArgumentException("Unsupported length unit: $toUnit")
        }
    }

    private fun convertMass(
        value: Double,
        fromUnit: MeasureUnit,
        toUnit: MeasureUnit
    ): Double {
        // Convert to kilograms first
        val kilograms = when (fromUnit) {
            MeasureUnit.KILOGRAM -> value
            MeasureUnit.POUND -> value * 0.45359237
            MeasureUnit.OUNCE -> value * 0.0283495231
            else -> throw IllegalArgumentException("Unsupported mass unit: $fromUnit")
        }

        // Convert from kilograms to target unit
        return when (toUnit) {
            MeasureUnit.KILOGRAM -> kilograms
            MeasureUnit.POUND -> kilograms / 0.45359237
            MeasureUnit.OUNCE -> kilograms / 0.0283495231
            else -> throw IllegalArgumentException("Unsupported mass unit: $toUnit")
        }
    }

    // Helper method to format for display if needed
    fun formatMeasure(value: Double, unit: MeasureUnit, locale: ULocale = ULocale.getDefault()): String {
        val measure = Measure(value, unit)
        val formatter = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.SHORT)
        return formatter.format(measure)
    }
}