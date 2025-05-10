package com.example.simpleunitconverterapp.domain.repository

import com.example.simpleunitconverterapp.domain.model.ConversionRequest
import com.example.simpleunitconverterapp.domain.model.ConversionResult

interface ConversionRepository {
    suspend fun convertUnit(request: ConversionRequest): ConversionResult
}