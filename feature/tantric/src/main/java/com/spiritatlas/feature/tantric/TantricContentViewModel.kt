package com.spiritatlas.feature.tantric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.tantric.*
import com.spiritatlas.domain.repository.TantricContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TantricContentViewModel @Inject constructor(
    private val tantricContentRepository: TantricContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TantricContentUiState>(TantricContentUiState.Loading)
    val uiState: StateFlow<TantricContentUiState> = _uiState.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    init {
        loadAllContent()
        loadFavorites()
    }

    fun loadContentForType(type: com.spiritatlas.feature.tantric.TantricContentType) {
        viewModelScope.launch {
            _uiState.value = TantricContentUiState.Loading
            
            tantricContentRepository.getTantricContentByType(type.toDbType())
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = TantricContentUiState.Loading
                        }
                        is Result.Success -> {
                            val currentState = _uiState.value
                            val allContent = if (currentState is TantricContentUiState.Success) {
                currentState.content.filter { it.contentType.toUiType() != type } + result.data
                            } else {
                                result.data
                            }
                            
                            _uiState.value = TantricContentUiState.Success(
                                content = allContent,
                                selectedContent = (currentState as? TantricContentUiState.Success)?.selectedContent
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = TantricContentUiState.Error(
                                message = result.exception.message ?: "Unknown error occurred"
                            )
                        }
                    }
                }
        }
    }

    fun loadAllContent() {
        viewModelScope.launch {
            _uiState.value = TantricContentUiState.Loading
            
            tantricContentRepository.getAllTantricContent()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = TantricContentUiState.Loading
                        }
                        is Result.Success -> {
                            _uiState.value = TantricContentUiState.Success(
                                content = result.data,
                                selectedContent = null
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = TantricContentUiState.Error(
                                message = result.exception.message ?: "Failed to load content"
                            )
                        }
                    }
                }
        }
    }

    fun refreshContent() {
        viewModelScope.launch {
            tantricContentRepository.refreshTantricContent()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            // Keep current state during refresh
                        }
                        is Result.Success -> {
                            loadAllContent()
                        }
                        is Result.Error -> {
                            _uiState.value = TantricContentUiState.Error(
                                message = "Failed to refresh: ${result.exception.message}"
                            )
                        }
                    }
                }
        }
    }

    fun selectContent(content: TantricContent) {
        val currentState = _uiState.value
        if (currentState is TantricContentUiState.Success) {
            _uiState.value = currentState.copy(selectedContent = content)
        }
    }

    fun clearSelectedContent() {
        val currentState = _uiState.value
        if (currentState is TantricContentUiState.Success) {
            _uiState.value = currentState.copy(selectedContent = null)
        }
    }

    fun toggleFavorite(content: TantricContent) {
        viewModelScope.launch {
            val currentFavorites = _favorites.value.toMutableSet()
            
            if (currentFavorites.contains(content.id)) {
                currentFavorites.remove(content.id)
                tantricContentRepository.removeFromFavorites(content.id)
            } else {
                currentFavorites.add(content.id)
                tantricContentRepository.addToFavorites(content.id)
            }
            
            _favorites.value = currentFavorites
        }
    }

    fun searchContent(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadAllContent()
                return@launch
            }
            
            tantricContentRepository.searchTantricContent(query)
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = TantricContentUiState.Loading
                        }
                        is Result.Success -> {
                            _uiState.value = TantricContentUiState.Success(
                                content = result.data,
                                selectedContent = null
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = TantricContentUiState.Error(
                                message = "Search failed: ${result.exception.message}"
                            )
                        }
                    }
                }
        }
    }

    fun getPersonalizedRecommendations() {
        viewModelScope.launch {
            tantricContentRepository.getPersonalizedRecommendations()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = TantricContentUiState.Loading
                        }
                        is Result.Success -> {
                            _uiState.value = TantricContentUiState.Success(
                                content = result.data,
                                selectedContent = null
                            )
                        }
                        is Result.Error -> {
                            _uiState.value = TantricContentUiState.Error(
                                message = "Failed to get recommendations: ${result.exception.message}"
                            )
                        }
                    }
                }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            tantricContentRepository.getFavorites()
                .collect { favorites ->
                    _favorites.value = favorites.toSet()
                }
        }
    }
}

sealed class TantricContentUiState {
    object Loading : TantricContentUiState()
    
    data class Success(
        val content: List<TantricContent>,
        val selectedContent: TantricContent? = null
    ) : TantricContentUiState()
    
    data class Error(
        val message: String
    ) : TantricContentUiState()
}

// Extension function to convert UI enum to domain enum
private fun com.spiritatlas.feature.tantric.TantricContentType.toDbType(): com.spiritatlas.domain.tantric.TantricContentType {
    return when (this) {
        com.spiritatlas.feature.tantric.TantricContentType.TANTRIC_PRACTICES -> com.spiritatlas.domain.tantric.TantricContentType.TANTRIC_PRACTICES
        com.spiritatlas.feature.tantric.TantricContentType.KAMA_SUTRA -> com.spiritatlas.domain.tantric.TantricContentType.KAMA_SUTRA
        com.spiritatlas.feature.tantric.TantricContentType.ROBERT_GREENE -> com.spiritatlas.domain.tantric.TantricContentType.ROBERT_GREENE
        com.spiritatlas.feature.tantric.TantricContentType.COMPATIBILITY -> com.spiritatlas.domain.tantric.TantricContentType.COMPATIBILITY
    }
}
