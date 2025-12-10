package com.spiritatlas.core.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*

/**
 * Validation components with inline feedback
 *
 * Features:
 * - Real-time validation
 * - Inline error messages
 * - Success indicators
 * - Accessibility-friendly
 * - Spiritual theming
 */

// ═══════════════════════════════════════════════════════════════════════════════
// VALIDATION STATE
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Field validation state
 */
sealed class ValidationState {
    data object Idle : ValidationState()
    data object Validating : ValidationState()
    data object Valid : ValidationState()
    data class Invalid(val message: String) : ValidationState()
}

/**
 * Validation result for forms
 */
data class ValidationResult(
    val isValid: Boolean,
    val errors: Map<String, String> = emptyMap()
)

// ═══════════════════════════════════════════════════════════════════════════════
// VALIDATED TEXT FIELD
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Text field with built-in validation and error display
 */
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    validationState: ValidationState = ValidationState.Idle,
    placeholder: String? = null,
    helperText: String? = null,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .semantics {
                    if (validationState is ValidationState.Invalid) {
                        error(validationState.message)
                    }
                },
            placeholder = placeholder?.let { { Text(it) } },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = when (validationState) {
                            is ValidationState.Invalid -> MaterialTheme.colorScheme.error
                            is ValidationState.Valid -> CosmicBlue
                            else -> MaterialTheme.colorScheme.primary
                        }
                    )
                }
            },
            trailingIcon = {
                ValidationIcon(validationState)
            },
            isError = validationState is ValidationState.Invalid,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = when (validationState) {
                    is ValidationState.Invalid -> MaterialTheme.colorScheme.error
                    is ValidationState.Valid -> CosmicBlue
                    else -> MaterialTheme.colorScheme.primary
                },
                unfocusedBorderColor = when (validationState) {
                    is ValidationState.Invalid -> MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                    is ValidationState.Valid -> CosmicBlue.copy(alpha = 0.5f)
                    else -> MaterialTheme.colorScheme.outline
                },
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = when (validationState) {
                    is ValidationState.Invalid -> MaterialTheme.colorScheme.error
                    is ValidationState.Valid -> CosmicBlue
                    else -> MaterialTheme.colorScheme.primary
                },
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        // Error message or helper text
        AnimatedVisibility(
            visible = validationState is ValidationState.Invalid || helperText != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            when (validationState) {
                is ValidationState.Invalid -> {
                    ErrorMessage(validationState.message)
                }
                else -> {
                    helperText?.let {
                        HelperMessage(it)
                    }
                }
            }
        }
    }
}

/**
 * Validation icon shown in trailing position
 */
@Composable
private fun ValidationIcon(
    validationState: ValidationState,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = validationState,
        transitionSpec = {
            fadeIn() + scaleIn() togetherWith fadeOut() + scaleOut()
        },
        label = "validation_icon"
    ) { state ->
        when (state) {
            is ValidationState.Validating -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is ValidationState.Valid -> {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Valid",
                    tint = CosmicBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
            is ValidationState.Invalid -> {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Invalid",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )
            }
            else -> {
                // No icon for idle state
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR AND HELPER MESSAGES
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Error message display
 */
@Composable
private fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(start = 16.dp, top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(14.dp)
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            lineHeight = 16.sp
        )
    }
}

/**
 * Helper message display
 */
@Composable
private fun HelperMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(start = 16.dp, top = 4.dp),
        lineHeight = 16.sp
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// FORM VALIDATION CARD
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Card showing form validation summary
 */
@Composable
fun ValidationSummaryCard(
    validationResult: ValidationResult,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null
) {
    if (validationResult.isValid) return

    AnimatedVisibility(
        visible = !validationResult.isValid,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(20.dp)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Please correct the following:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error
                    )

                    validationResult.errors.forEach { (field, error) ->
                        Text(
                            text = "• $field: $error",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                onDismiss?.let {
                    IconButton(
                        onClick = it,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// INLINE VALIDATION FEEDBACK
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Inline validation feedback chip
 */
@Composable
fun ValidationFeedbackChip(
    validationState: ValidationState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = validationState is ValidationState.Valid || validationState is ValidationState.Invalid,
        enter = slideInHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        val (icon, color, text) = when (validationState) {
            is ValidationState.Valid -> Triple(Icons.Default.Check, CosmicBlue, "Valid")
            is ValidationState.Invalid -> Triple(Icons.Default.Close, MaterialTheme.colorScheme.error, "Invalid")
            else -> return@AnimatedVisibility
        }

        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = color.copy(alpha = 0.1f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// VALIDATION HELPERS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Common validation rules
 */
object ValidationRules {
    fun required(value: String, fieldName: String = "This field"): ValidationState {
        return if (value.isBlank()) {
            ValidationState.Invalid("$fieldName is required")
        } else {
            ValidationState.Valid
        }
    }

    fun email(value: String): ValidationState {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return when {
            value.isBlank() -> ValidationState.Invalid("Email is required")
            !emailRegex.matches(value) -> ValidationState.Invalid("Please enter a valid email")
            else -> ValidationState.Valid
        }
    }

    fun minLength(value: String, min: Int, fieldName: String = "This field"): ValidationState {
        return when {
            value.isBlank() -> ValidationState.Invalid("$fieldName is required")
            value.length < min -> ValidationState.Invalid("$fieldName must be at least $min characters")
            else -> ValidationState.Valid
        }
    }

    fun maxLength(value: String, max: Int, fieldName: String = "This field"): ValidationState {
        return if (value.length > max) {
            ValidationState.Invalid("$fieldName must be less than $max characters")
        } else {
            ValidationState.Valid
        }
    }

    fun numeric(value: String, fieldName: String = "This field"): ValidationState {
        return when {
            value.isBlank() -> ValidationState.Invalid("$fieldName is required")
            value.toIntOrNull() == null -> ValidationState.Invalid("$fieldName must be a number")
            else -> ValidationState.Valid
        }
    }

    fun dateFormat(value: String): ValidationState {
        // Simple date validation (can be enhanced)
        return if (value.matches("\\d{2}/\\d{2}/\\d{4}".toRegex())) {
            ValidationState.Valid
        } else {
            ValidationState.Invalid("Please use MM/DD/YYYY format")
        }
    }

    fun url(value: String): ValidationState {
        val urlRegex = "^(https?://)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}.*\$".toRegex()
        return when {
            value.isBlank() -> ValidationState.Invalid("URL is required")
            !urlRegex.matches(value) -> ValidationState.Invalid("Please enter a valid URL")
            else -> ValidationState.Valid
        }
    }

    fun birthDate(value: String): ValidationState {
        return when {
            value.isBlank() -> ValidationState.Invalid("Birth date is required")
            !value.matches("\\d{2}/\\d{2}/\\d{4}".toRegex()) -> {
                ValidationState.Invalid("Please use MM/DD/YYYY format")
            }
            else -> {
                // Additional date logic validation could go here
                ValidationState.Valid
            }
        }
    }

    fun time(value: String): ValidationState {
        return when {
            value.isBlank() -> ValidationState.Invalid("Time is required")
            !value.matches("\\d{2}:\\d{2}".toRegex()) -> {
                ValidationState.Invalid("Please use HH:MM format")
            }
            else -> ValidationState.Valid
        }
    }
}

/**
 * Composable function to validate multiple fields
 */
@Composable
fun rememberFormValidation(
    vararg validators: Pair<String, () -> ValidationState>
): ValidationResult {
    val errors = validators.mapNotNull { (field, validator) ->
        val state = validator()
        if (state is ValidationState.Invalid) {
            field to state.message
        } else {
            null
        }
    }.toMap()

    return ValidationResult(
        isValid = errors.isEmpty(),
        errors = errors
    )
}

/**
 * Extension function to validate a state value
 */
fun String.validate(vararg rules: (String) -> ValidationState): ValidationState {
    rules.forEach { rule ->
        val result = rule(this)
        if (result is ValidationState.Invalid) {
            return result
        }
    }
    return ValidationState.Valid
}
