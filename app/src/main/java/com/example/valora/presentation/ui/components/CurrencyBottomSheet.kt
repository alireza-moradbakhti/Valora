package com.example.valora.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.valora.presentation.ui.theme.glassmorphism

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheet(
    availableCurrencies: List<String>,
    onCurrencySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredCurrencies = remember(searchQuery, availableCurrencies) {
        if (searchQuery.isEmpty()) {
            availableCurrencies
        } else {
            availableCurrencies.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.Transparent,
        // Ensure the sheet expands fully to show the content correctly
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp) // Add padding to reveal the rounded corners
                .glassmorphism(
                    cornerRadius = 24.dp, // Apply a corner radius
                    blurRadius = 22.dp,   // Increase blur for a bolder effect
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.97f) // Make background more opaque
                )
                .padding(16.dp)
        ) {
            // Header with search bar and close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    shape = RoundedCornerShape(16.dp),
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search currency...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of currencies
            LazyColumn {
                items(filteredCurrencies) { currency ->
                    Text(
                        text = currency,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(currency) }
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}
