package com.snehil.minori.mainui.homescreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.snehil.minori.core.base.BaseViewModel
import com.snehil.minori.domain.model.Category
import com.snehil.minori.domain.model.Product
import com.snehil.minori.domain.model.Banner
import com.snehil.minori.domain.model.Deal
import com.snehil.minori.domain.usecase.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val banners: List<Banner> = emptyList(),
    val deals: List<Deal> = emptyList(),
    val selectedCategoryId: Int = 1,
    val searchQuery: String = ""
)

sealed interface HomeEffect {
    data class NavigateToProductDetails(val productId: String) : HomeEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : BaseViewModel<HomeState, HomeEffect>() {

    override val initialState: HomeState = HomeState()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            getHomeDataUseCase()
                .onStart { setLoading(true); setError(null) }
                .catch { exception ->
                    setLoading(false)
                    setError(exception.message ?: "Failed to load home feed")
                }
                .collect { homeData ->
                    setLoading(false)
                    updateState {
                        it.copy(
                            categories = homeData.categories,
                            products = homeData.products,
                            banners = homeData.banners,
                            deals = homeData.deals
                        )
                    }
                }
        }
    }

    fun onCategorySelected(categoryId: Int) {
        updateState { it.copy(selectedCategoryId = categoryId) }
    }

    fun onSearchQueryChanged(query: String) {
        updateState { it.copy(searchQuery = query) }
    }
}
