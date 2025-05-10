package com.example.simpleunitconverterapp.data

import android.icu.util.MeasureUnit
import com.example.simpleunitconverterapp.domain.model.ConversionRequest
import com.example.simpleunitconverterapp.domain.model.UnitType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ConversionRepositoryImplTest {
    private lateinit var repository: ConversionRepositoryImpl

    @Before
    fun setup() {
        repository = ConversionRepositoryImpl()
    }

    @Test
    fun `convert Celsius to Fahrenheit returns correct value`() = runBlocking {
        val request = ConversionRequest(
            unitType = UnitType.Temperature,
            fromUnit = MeasureUnit.CELSIUS,
            toUnit = MeasureUnit.FAHRENHEIT,
            value = 0.0
        )

        val result = repository.convertUnit(request)

        assertEquals(32.0, result.convertedValue, 0.001)
    }

    @Test
    fun `convert Meters to Feet returns correct value`() = runBlocking {
        val request = ConversionRequest(
            unitType = UnitType.Length,
            fromUnit = MeasureUnit.METER,
            toUnit = MeasureUnit.FOOT,
            value = 1.0
        )

        val result = repository.convertUnit(request)

        assertEquals(3.28084, result.convertedValue, 0.001)
    }

    @Test
    fun `convert Kilograms to Pounds returns correct value`() = runBlocking {
        val request = ConversionRequest(
            unitType = UnitType.Mass,
            fromUnit = MeasureUnit.KILOGRAM,
            toUnit = MeasureUnit.POUND,
            value = 1.0
        )

        val result = repository.convertUnit(request)

        assertEquals(2.20462, result.convertedValue, 0.001)
    }
}