package com.spiritatlas.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.SpiritAtlasTheme

// Stubs for missing classes and composables

data class SheetOption(val id: String, val label: String, val icon: ImageVector, val isDestructive: Boolean = false)

@Composable
fun OptionsSheet(visible: Boolean, onDismiss: () -> Unit, options: List<SheetOption>, onOptionSelected: (String) -> Unit, title: String) {}

data class FilterOption(val id: String, val label: String, val icon: ImageVector? = null)

@Composable
fun FilterSheet(visible: Boolean, onDismiss: () -> Unit, filterCategories: Map<String, List<FilterOption>>, initialFilters: Set<String>, onFiltersChanged: (Set<String>) -> Unit, onApply: () -> Unit) {}

@Composable
fun ShareSheet(visible: Boolean, onDismiss: () -> Unit, onShareDestination: (String) -> Unit, onCopyLink: () -> Unit, onGenerateQR: () -> Unit, onShareAsImage: () -> Unit) {}

@Composable
fun ConfirmationSheet(visible: Boolean, onDismiss: () -> Unit, title: String, message: String, confirmText: String, onConfirm: () -> Unit, icon: ImageVector) {}

data class SelectableProfile(val id: String, val name: String, val imageUrl: String, val isSelected: Boolean = false)

@Composable
fun ProfileSelectorSheet(visible: Boolean, onDismiss: () -> Unit, profiles: List<SelectableProfile>, onProfileSelected: (String) -> Unit, onAddNew: () -> Unit, title: String) {}

@Composable
fun LanguageAndThemeSheet(visible: Boolean, onDismiss: () -> Unit, currentLanguage: String, availableLanguages: List<String>, onLanguageSelected: (String) -> Unit, currentTheme: String, onThemeSelected: (String) -> Unit) {}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SpiritualBottomSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }

        SpiritualBottomSheet(
            visible = visible,
            onDismiss = { visible = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Spiritual Bottom Sheet", style = MaterialTheme.typography.headlineSmall)
                Text("This is a sample content for the bottom sheet.")
                Button(onClick = { visible = false }) {
                    Text("Close")
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun OptionsSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }
        val options = remember {
            listOf(
                SheetOption(
                    id = "edit",
                    label = "Edit Profile",
                    icon = Icons.Default.Edit
                ),
                SheetOption(
                    id = "share",
                    label = "Share Profile",
                    icon = Icons.Default.Share
                ),
                SheetOption(
                    id = "settings",
                    label = "Settings",
                    icon = Icons.Default.Settings
                ),
                SheetOption(
                    id = "delete",
                    label = "Delete Account",
                    icon = Icons.Default.Delete, 
                    isDestructive = true
                )
            )
        }

        OptionsSheet(
            visible = visible,
            onDismiss = { visible = false },
            options = options,
            onOptionSelected = { /* Handle option */ },
            title = "Profile Options"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FilterSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }
        val filterCategories = remember {
            mapOf(
                "Dosha" to listOf(
                    FilterOption("vata", "Vata", Icons.Default.Air),
                    FilterOption("pitta", "Pitta", Icons.Default.LocalFireDepartment),
                    FilterOption("kapha", "Kapha", Icons.Default.WaterDrop)
                ),
                "Numerology" to listOf(
                    FilterOption("lifepath", "Life Path"),
                    FilterOption("destiny", "Destiny"),
                    FilterOption("soulurge", "Soul Urge")
                )
            )
        }

        FilterSheet(
            visible = visible,
            onDismiss = { visible = false },
            filterCategories = filterCategories,
            initialFilters = setOf("vata", "lifepath"),
            onFiltersChanged = { /* Handle filter change */ },
            onApply = { /* Handle apply */ }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ShareSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }

        ShareSheet(
            visible = visible,
            onDismiss = { visible = false },
            onShareDestination = {},
            onCopyLink = {},
            onGenerateQR = {},
            onShareAsImage = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ConfirmationSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }

        ConfirmationSheet(
            visible = visible,
            onDismiss = { visible = false },
            title = "Delete Profile?",
            message = "This action is irreversible. All your data will be permanently deleted.",
            confirmText = "Delete",
            onConfirm = { visible = false },
            icon = Icons.Default.DeleteForever
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ProfileSelectorSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }
        val profiles = remember {
            listOf(
                SelectableProfile("1", "Jonathan", "https://example.com/jonathan.png"),
                SelectableProfile("2", "Luna", "https://example.com/luna.png", true),
                SelectableProfile("3", "Cosmo", "https://example.com/cosmo.png")
            )
        }

        ProfileSelectorSheet(
            visible = visible,
            onDismiss = { visible = false },
            profiles = profiles,
            onProfileSelected = {},
            onAddNew = {},
            title = "Select a Profile"
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun LanguageAndThemeSheet_Preview() {
    SpiritAtlasTheme {
        var visible by remember { mutableStateOf(true) }

        LanguageAndThemeSheet(
            visible = visible,
            onDismiss = { visible = false },
            currentLanguage = "English",
            availableLanguages = listOf("English", "Español", "Français"),
            onLanguageSelected = {},
            currentTheme = "System Default",
            onThemeSelected = {}
        )
    }
}
