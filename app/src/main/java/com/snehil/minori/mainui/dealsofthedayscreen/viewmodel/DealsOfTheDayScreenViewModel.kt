package com.snehil.minori.mainui.dealsofthedayscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.R
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.mainui.dealsofthedayscreen.model.Deal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DealsState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedSort: String = "Popularity",
    val deals: List<Deal> = emptyList(),
    val wishlistedIds: Set<Int> = emptySet(),
    val secondsRemaining: Int = 82520, // 22h 55m 20s
    val categories: List<String> = listOf("All", "Clayware", "Furniture", "Decor", "Textiles", "Accessories"),
    val sortOptions: List<String> = listOf("Popularity", "Price: Low to High", "Price: High to Low")
)

sealed class DealsEffect {
    object NavigateBack : DealsEffect()
}

@HiltViewModel
class DealsOfTheDayScreenViewModel @Inject constructor() : BaseViewModel<DealsState, DealsEffect>() {

    private val allDeals = listOf(
        Deal(1, "Handmade Clay Pitcher", "Warm Sage-wash handthrown rustic clay water pitcher.", 999, 1999, "50% OFF", 4.7f, 85, R.drawable.clay_pitcher, "Clayware"),
        Deal(2, "Stained Amber Lantern", "Amber warm gradient stained glass lantern frame.", 1399, 2799, "50% OFF", 4.8f, 54, R.drawable.glass_carafe, "Decor"),
        Deal(3, "Teak Wooden Coaster Set", "Set of 6 organic hand-sanded teak wood coasters.", 599, 1199, "50% OFF", 4.6f, 38, R.drawable.oak_chest, "Furniture"),
        Deal(4, "Bohemian Macrame Hanger", "Woven cream plant hanger with wooden bead accents.", 499, 999, "50% OFF", 4.9f, 210, R.drawable.macrame_hanger, "Decor"),
        Deal(5, "Earthy Glazed Clay Plate", "Speckled ceramic dining serving plate.", 399, 799, "50% OFF", 4.7f, 93, R.drawable.clay_plate, "Clayware"),
        Deal(6, "Tasseled Cotton Hammock", "Relaxing woven rope organic white cream hammock.", 1999, 3999, "50% OFF", 4.8f, 76, R.drawable.wool_rug, "Textiles"),
        Deal(7, "Earthy Clay Tea Mug", "Stoneware tea/coffee cup with sturdy flat base.", 299, 599, "50% OFF", 4.5f, 142, R.drawable.ceramic_mug, "Clayware"),
        Deal(8, "Fringed Linen Pillow", "Woven beige soft linen cushion cover with zip.", 449, 899, "50% OFF", 4.6f, 68, R.drawable.linen_pillow, "Textiles"),
        Deal(9, "Braided Seagrass Basket", "Woven storage basket with carry handles.", 699, 1399, "50% OFF", 4.8f, 122, R.drawable.woven_basket, "Accessories"),
        Deal(10, "Beeswax Scented Candle", "Hand-poured candle in reusable rustic clay pot.", 549, 1099, "50% OFF", 4.9f, 310, R.drawable.boho_candle, "Decor")
    )

    override val initialState: DealsState = DealsState(deals = allDeals)

    private var timerJob: kotlinx.coroutines.Job? = null

    init {
        startTimer()
        filterAndSortDeals()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (currentState.secondsRemaining > 0) {
                delay(1000)
                updateState { it.copy(secondsRemaining = it.secondsRemaining - 1) }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        updateState { it.copy(searchQuery = query) }
        filterAndSortDeals()
    }

    fun updateCategory(category: String) {
        updateState { it.copy(selectedCategory = category) }
        filterAndSortDeals()
    }

    fun updateSort(sort: String) {
        updateState { it.copy(selectedSort = sort) }
        filterAndSortDeals()
    }

    fun toggleWishlist(dealId: Int) {
        updateState { state ->
            val updated = state.wishlistedIds.toMutableSet()
            if (updated.contains(dealId)) {
                updated.remove(dealId)
            } else {
                updated.add(dealId)
            }
            state.copy(wishlistedIds = updated)
        }
    }

    fun getFormattedTime(): String {
        val total = currentState.secondsRemaining
        if (total <= 0) return "Deals ended"
        val h = total / 3600
        val m = (total % 3600) / 60
        val s = total % 60
        return String.format("Deals end in: %02dh %02dm %02ds", h, m, s)
    }

    private fun filterAndSortDeals() {
        val query = currentState.searchQuery.lowercase().trim()
        val category = currentState.selectedCategory
        val sort = currentState.selectedSort

        var result = allDeals

        // Category Filter
        if (category != "All") {
            result = result.filter { it.category == category }
        }

        // Search Filter
        if (query.isNotEmpty()) {
            result = result.filter {
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query)
            }
        }

        // Sorting
        result = when (sort) {
            "Price: Low to High" -> result.sortedBy { it.price }
            "Price: High to Low" -> result.sortedByDescending { it.price }
            "Popularity" -> result.sortedByDescending { it.rating }
            else -> result
        }

        updateState { it.copy(deals = result) }
    }

    fun goBack() {
        emitEffect(DealsEffect.NavigateBack)
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}
