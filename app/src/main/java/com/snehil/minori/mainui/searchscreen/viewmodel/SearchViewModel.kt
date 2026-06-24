package com.snehil.minori.mainui.searchscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.core.common.ProductRegistry
import com.snehil.minori.domain.model.WishlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val filteredProducts: List<WishlistItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val popularSearches: List<String> = emptyList()
)

sealed interface SearchEffect {
    object NavigateBack : SearchEffect
    data class NavigateToProductDetail(val productId: String, val productType: String) : SearchEffect
}

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel<SearchState, SearchEffect>() {

    override val initialState: SearchState = SearchState(
        categories = listOf("All", "Ceramics", "Paintings", "Sculpture", "Furniture", "Bags"),
        popularSearches = listOf("Bowl", "Vase", "Rug", "Candle", "Workshop", "Bronze"),
        filteredProducts = ProductRegistry.getAllProducts()
    )

    private val queryFlow = MutableStateFlow("")
    private val categoryFlow = MutableStateFlow("All")

    init {
        viewModelScope.launch {
            combine(queryFlow, categoryFlow) { query, category ->
                val allProducts = ProductRegistry.getAllProducts()
                var results = allProducts

                if (category != "All") {
                    results = results.filter {
                        val catLabel = it.categoryLabel ?: it.type
                        catLabel.contains(category, ignoreCase = true)
                    }
                }

                if (query.trim().isNotEmpty()) {
                    results = results.filter {
                        it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        (it.categoryLabel ?: "").contains(query, ignoreCase = true)
                    }
                }

                results
            }.collect { results ->
                updateState {
                    it.copy(
                        searchQuery = queryFlow.value,
                        selectedCategory = categoryFlow.value,
                        filteredProducts = results
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        queryFlow.value = query
    }

    fun selectCategory(category: String) {
        categoryFlow.value = category
    }

    fun clearSearch() {
        queryFlow.value = ""
        categoryFlow.value = "All"
    }

    fun onProductClick(productId: String, productType: String) {
        emitEffect(SearchEffect.NavigateToProductDetail(productId, productType))
    }

    fun goBack() {
        emitEffect(SearchEffect.NavigateBack)
    }
}
