package com.spiritatlas.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Consolidated state for DateTimePicker to minimize recompositions.
 * Single state object instead of 5 separate states.
 */
@Immutable
private data class DateTimePickerState(
    val year: Int = 1990,
    val month: Int = 1,
    val day: Int = 1,
    val hour: Int = 12,
    val minute: Int = 0
) {
    fun toLocalDateTime(): LocalDateTime? = try {
        LocalDateTime.of(year, month, day, hour, minute)
    } catch (e: Exception) {
        null
    }

    companion object {
        fun fromLocalDateTime(dateTime: LocalDateTime?): DateTimePickerState {
            return dateTime?.let {
                DateTimePickerState(
                    year = it.year,
                    month = it.monthValue,
                    day = it.dayOfMonth,
                    hour = it.hour,
                    minute = it.minute
                )
            } ?: DateTimePickerState()
        }
    }
}

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
    var pickerState by remember(selectedDateTime) {
        mutableStateOf(DateTimePickerState.fromLocalDateTime(selectedDateTime))
    }

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
                        TextButton(onClick = { pickerState = pickerState.copy(year = pickerState.year - 10) }) {
                            Text("-10")
                        }
                        TextButton(onClick = { pickerState = pickerState.copy(year = pickerState.year - 1) }) {
                            Text("-1")
                        }
                        OutlinedTextField(
                            value = pickerState.year.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let { pickerState = pickerState.copy(year = it) }
                            },
                            label = { Text("Year") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { pickerState = pickerState.copy(year = pickerState.year + 1) }) {
                            Text("+1")
                        }
                        TextButton(onClick = { pickerState = pickerState.copy(year = pickerState.year + 10) }) {
                            Text("+10")
                        }
                    }

                    // Month and Day
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = pickerState.month.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let {
                                    if (it in 1..12) pickerState = pickerState.copy(month = it)
                                }
                            },
                            label = { Text("Month") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = pickerState.day.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let {
                                    if (it in 1..31) pickerState = pickerState.copy(day = it)
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
                            value = pickerState.hour.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let {
                                    if (it in 0..23) pickerState = pickerState.copy(hour = it)
                                }
                            },
                            label = { Text("Hour (0-23)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = pickerState.minute.toString(),
                            onValueChange = { newValue ->
                                newValue.toIntOrNull()?.let {
                                    if (it in 0..59) pickerState = pickerState.copy(minute = it)
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
                                pickerState.toLocalDateTime()?.let { dateTime ->
                                    onDateTimeChange(dateTime)
                                    showPicker = false
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
