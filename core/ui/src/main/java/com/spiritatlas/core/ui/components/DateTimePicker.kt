package com.spiritatlas.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A comprehensive date and time picker for spiritual profile data.
 * Allows easy navigation through years and precise time selection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    selectedDateTime: LocalDateTime?,
    onDateTimeChange: (LocalDateTime?) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var showPicker by remember { mutableStateOf(false) }
    var tempYear by remember { mutableStateOf(selectedDateTime?.year ?: 1990) }
    var tempMonth by remember { mutableStateOf(selectedDateTime?.monthValue ?: 1) }
    var tempDay by remember { mutableStateOf(selectedDateTime?.dayOfMonth ?: 1) }
    var tempHour by remember { mutableStateOf(selectedDateTime?.hour ?: 12) }
    var tempMinute by remember { mutableStateOf(selectedDateTime?.minute ?: 0) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedDateTime?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) ?: "",
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                TextButton(onClick = { showPicker = !showPicker }) {
                    Text("Select")
                }
            }
        )

        if (showPicker) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Select Date & Time",
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Year selection with easy navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { tempYear -= 10 }) {
                            Text("-10")
                        }
                        TextButton(onClick = { tempYear -= 1 }) {
                            Text("-1")
                        }
                        OutlinedTextField(
                            value = tempYear.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { tempYear = it }
                            },
                            label = { Text("Year") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { tempYear += 1 }) {
                            Text("+1")
                        }
                        TextButton(onClick = { tempYear += 10 }) {
                            Text("+10")
                        }
                    }

                    // Month and Day
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = tempMonth.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { 
                                    if (it in 1..12) tempMonth = it 
                                }
                            },
                            label = { Text("Month") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = tempDay.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { 
                                    if (it in 1..31) tempDay = it 
                                }
                            },
                            label = { Text("Day") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Hour and Minute
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = tempHour.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { 
                                    if (it in 0..23) tempHour = it 
                                }
                            },
                            label = { Text("Hour (0-23)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = tempMinute.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { 
                                    if (it in 0..59) tempMinute = it 
                                }
                            },
                            label = { Text("Minute") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = {
                                onDateTimeChange(null)
                                showPicker = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Clear")
                        }
                        TextButton(
                            onClick = { showPicker = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                try {
                                    val dateTime = LocalDateTime.of(
                                        tempYear, tempMonth, tempDay, tempHour, tempMinute
                                    )
                                    onDateTimeChange(dateTime)
                                    showPicker = false
                                } catch (e: Exception) {
                                    // Handle invalid date
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Set")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DateTimePickerPreview() {
    DateTimePicker(
        selectedDateTime = LocalDateTime.now(),
        onDateTimeChange = { },
        label = "Birth Date & Time"
    )
}
