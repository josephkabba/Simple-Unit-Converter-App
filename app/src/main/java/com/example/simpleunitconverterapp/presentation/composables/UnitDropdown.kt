package com.example.simpleunitconverterapp.presentation.composables

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.icu.util.ULocale
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(
    label: String,
    items: List<MeasureUnit>,
    selectedItem: MeasureUnit,
    onItemSelected: (MeasureUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val measureFormat = MeasureFormat.getInstance(
        ULocale.getDefault(),
        MeasureFormat.FormatWidth.WIDE
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = getUnitDisplayName(selectedItem),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(getUnitDisplayName(unit)) },
                    onClick = {
                        onItemSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

private fun getUnitDisplayName(unit: MeasureUnit): String {
    val measureFormat = MeasureFormat.getInstance(
        ULocale.getDefault(),
        MeasureFormat.FormatWidth.WIDE
    )

    val formatted = measureFormat.format(Measure(1.0, unit))
    return formatted.replace("1", "").trim()
}