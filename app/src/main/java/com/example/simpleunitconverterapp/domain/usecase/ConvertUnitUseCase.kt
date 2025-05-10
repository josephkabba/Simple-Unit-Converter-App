package com.example.simpleunitconverterapp.domain.usecase

import com.example.simpleunitconverterapp.domain.model.ConversionRequest
import com.example.simpleunitconverterapp.domain.model.ConversionResult
import com.example.simpleunitconverterapp.domain.repository.ConversionRepository

class ConvertUnitUseCase (
    private val repository: ConversionRepository
) {
    suspend operator fun invoke(request: ConversionRequest): ConversionResult {
        return repository.convertUnit(request)
    }
}