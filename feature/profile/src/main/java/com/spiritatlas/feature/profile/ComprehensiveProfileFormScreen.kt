package com.spiritatlas.feature.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ayurveda.AyurvedaCalculator
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveProfileFormScreen(
    onDone: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // Basic info
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var birthDate by remember { mutableStateOf<LocalDate?>(null) }

    // Parents
    var motherBirthDate by remember { mutableStateOf<LocalDate?>(null) }
    var fatherBirthDate by remember { mutableStateOf<LocalDate?>(null) }

    // Relationship milestone
    var firstKissDate by remember { mutableStateOf<LocalDate?>(null) }

    // Ayurveda simple Q&A
    var ayurvedaAnswers by remember { mutableStateOf(mapOf<String, Int>()) }
    var ayurvedaProfile by remember { mutableStateOf<com.spiritatlas.core.ayurveda.DoshaProfile?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Comprehensive Profile Setup") })
        },
        bottomBar = {
            BottomAppBar(actions = {
                Spacer(Modifier.weight(1f))
                Button(onClick = {
                    ayurvedaProfile = AyurvedaCalculator.calculate(ayurvedaAnswers)
                    // TODO: Save all fields via viewModel
                    onDone()
                }) { Text("Save & Continue") }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExpandableSection(title = "Basic Information") {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                DateInput(label = "Birth Date", date = birthDate, onDate = { birthDate = it })
            }

            ExpandableSection(title = "Parents' Birth Dates") {
                DateInput(label = "Mother's Birth Date", date = motherBirthDate, onDate = { motherBirthDate = it })
                DateInput(label = "Father's Birth Date", date = fatherBirthDate, onDate = { fatherBirthDate = it })
            }

            ExpandableSection(title = "Relationship Milestones") {
                DateInput(label = "First Kiss Date", date = firstKissDate, onDate = { firstKissDate = it })
            }

            ExpandableSection(title = "Ayurveda (Dosha) Questionnaire") {
                Text("Answer quick questions (0=Vata, 1=Kapha, 2=Pitta)")
                AyurvedaQuestion(id = "sleep", prompt = "Sleep pattern (light 0 / average 1 / intense 2)", onAnswer = {
                    ayurvedaAnswers = ayurvedaAnswers + ("sleep" to it)
                })
                AyurvedaQuestion(id = "appetite", prompt = "Appetite (irregular 0 / stable 1 / strong 2)", onAnswer = {
                    ayurvedaAnswers = ayurvedaAnswers + ("appetite" to it)
                })
                AyurvedaQuestion(id = "temperature", prompt = "Preferred temperature (warm 0 / temperate 1 / cool 2)", onAnswer = {
                    ayurvedaAnswers = ayurvedaAnswers + ("temperature" to it)
                })

                ayurvedaProfile?.let { profile ->
                    Text("Ayurveda Result: V=${profile.vata}, P=${profile.pitta}, K=${profile.kapha}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AyurvedaQuestion(
    id: String,
    prompt: String,
    onAnswer: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selection by remember { mutableStateOf<String?>(null) }
    val options = listOf("0", "1", "2")

    Column(Modifier.fillMaxWidth()) {
        Text(prompt, style = MaterialTheme.typography.bodyMedium)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selection ?: "Select (0/1/2)",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                label = { Text("Answer") }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { opt ->
                    DropdownMenuItem(text = { Text(opt) }, onClick = {
                        selection = opt
                        expanded = false
                        onAnswer(opt.toInt())
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateInput(
    label: String,
    date: LocalDate?,
    onDate: (LocalDate) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        OutlinedTextField(
            value = date?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                TextButton(onClick = { showPicker = true }) { Text("Pick") }
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (showPicker) {
            DatePickerDialog(onDismissRequest = { showPicker = false }, confirmButton = {
                // handled via state below
            }) {
                val datePickerState = rememberDatePickerState()
                DatePicker(state = datePickerState)
                LaunchedEffect(datePickerState.selectedDateMillis) {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val ld = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                        onDate(ld)
                        showPicker = false
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(true) }
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Collapse" else "Expand")
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    content()
                }
            }
        }
    }
}
