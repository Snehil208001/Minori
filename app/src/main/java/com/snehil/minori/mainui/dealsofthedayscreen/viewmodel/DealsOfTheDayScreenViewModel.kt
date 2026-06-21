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
        Deal(1, "Earthy Ceramic Bowl", "Hand-thrown clay bowl with natural white glaze.", 1250, 2500, "50% OFF", 4.7f, 154, R.drawable.ceramic_bowl, "Clayware"),
        Deal(2, "Terracotta Clay Pitcher", "Traditional rustic pitcher for fresh water or decor.", 1600, 3200, "50% OFF", 4.9f, 89, R.drawable.clay_pitcher, "Clayware"),
        Deal(3, "Glass Vase / Carafe", "Blown decanter in warm amber gradient shade.", 2650, 5300, "50% OFF", 4.5f, 43, R.drawable.glass_carafe, "Decor"),
        Deal(4, "Oak Carved Chest", "Handcrafted organic frame drawer with brass keys.", 5700, 9500, "40% OFF", 4.8f, 21, R.drawable.oak_chest, "Furniture"),
        Deal(5, "Handwoven Seagrass Basket", "Seagrass round storage basket with sturdy side handles.", 1050, 2100, "50% OFF", 4.6f, 78, R.drawable.woven_basket, "Accessories"),
        Deal(6, "Boho Macrame Hanger", "Organic cotton woven plant hanger with wooden bead accents.", 750, 1500, "50% OFF", 4.7f, 134, R.drawable.macrame_hanger, "Decor"),
        Deal(7, "Hand-glazed Clay Plate", "Speckled clay dinner side plate with raised outer lip.", 600, 1200, "50% OFF", 4.8f, 92, R.drawable.clay_plate, "Clayware"),
        Deal(8, "Textured Linen Pillow", "Earthy linen square pillow cover with invisible zipper.", 1100, 2200, "50% OFF", 4.5f, 57, R.drawable.linen_pillow, "Textiles"),
        Deal(9, "Rattan Lounge Chair", "Minimalist modern handwoven rattan armchair.", 6000, 12000, "50% OFF", 4.6f, 67, R.drawable.rattan_chair, "Furniture"),
        Deal(10, "Speckled Clay Mug", "Cozy speckled mug with flat base and large handle.", 600, 1200, "50% OFF", 4.8f, 320, R.drawable.ceramic_mug, "Clayware")
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
