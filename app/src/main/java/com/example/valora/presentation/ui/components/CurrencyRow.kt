package com.example.valora.presentation.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A reusable Composable for a single currency row.
 * It contains a clickable text field to open the currency selection sheet
 * and an optional amount input field.
 */
@Composable
fun CurrencyRow(
    currencyCode: String,
    onCurrencyClick: () -> Unit, // Changed from onCurrencyChange
    amount: String? = null,
    onAmountChange: ((String) -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Amount field
        if (amount != null && onAmountChange != null) {
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Clickable text field to open the bottom sheet
        Row(
            modifier = Modifier
                .weight(0.8f)
                .clickable(onClick = onCurrencyClick)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = currencyCode, style = TextStyle(fontSize = 18.sp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select currency")
        }
    }
}

