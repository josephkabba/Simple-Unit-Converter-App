package com.example.simpleunitconverterapp.presentation.screens


import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.ULocale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpleunitconverterapp.domain.model.ConversionResult
import com.example.simpleunitconverterapp.domain.model.UnitType
import com.example.simpleunitconverterapp.presentation.composables.UnitDropdown
import com.example.simpleunitconverterapp.presentation.viewmodel.ConverterViewModel
import java.text.DecimalFormat

@Composable
fun ConverterScreen(viewModel: ConverterViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Unit Type Selection
        UnitTypeSelector(
            selectedUnitType = uiState.selectedUnitType,
            onUnitTypeSelected = viewModel::onUnitTypeSelected
        )

        Spacer(modifier = Modifier.height(24.dp))

        // From/To Unit Selection with Swap Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UnitDropdown(
                label = "From",
                items = uiState.selectedUnitType.units,
                selectedItem = uiState.selectedFromUnit,
                onItemSelected = viewModel::onFromUnitSelected,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = viewModel::swapUnits,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Swap Units"
                )
            }

            UnitDropdown(
                label = "To",
                items = uiState.selectedUnitType.units,
                selectedItem = uiState.selectedToUnit,
                onItemSelected = viewModel::onToUnitSelected,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Input Field
        OutlinedTextField(
            value = uiState.inputValue,
            onValueChange = viewModel::onInputValueChanged,
            label = { Text("Enter Value") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Convert Button
        Button(
            onClick = viewModel::convert,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result Display
        uiState.conversionResult?.let { result ->
            ResultCard(result)
        }

        // Error Message
        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun UnitTypeSelector(
    selectedUnitType: UnitType,
    onUnitTypeSelected: (UnitType) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Select Unit Type",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UnitType.values().forEach { unitType ->
                FilterChip(
                    selected = selectedUnitType == unitType,
                    onClick = { onUnitTypeSelected(unitType) },
                    label = { Text(getUnitTypeName(unitType)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ResultCard(result: ConversionResult) {
    val formatter = DecimalFormat("#,##0.####")
    val measureFormat = MeasureFormat.getInstance(
        ULocale.getDefault(),
        MeasureFormat.FormatWidth.SHORT
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Result",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "${formatter.format(result.originalValue)} ${
                    measureFormat.format(Measure(1.0, result.fromUnit))
                        .replace("1", "")
                        .trim()
                }",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "=",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = "${formatter.format(result.convertedValue)} ${
                    measureFormat.format(Measure(1.0, result.toUnit))
                        .replace("1", "")
                        .trim()
                }",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
    }
}

// Helper function to get unit type name
private fun getUnitTypeName(unitType: UnitType): String {
    return when (unitType) {
        is UnitType.Temperature -> "Temperature"
        is UnitType.Length -> "Length"
        is UnitType.Mass -> "Mass"
    }
}