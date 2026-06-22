package com.snehil.minori.core.wishlist

import com.snehil.minori.domain.model.WishlistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistManager @Inject constructor() {
    private val _wishlist = MutableStateFlow<Map<String, WishlistItem>>(emptyMap())
    val wishlist: StateFlow<List<WishlistItem>> = MutableStateFlow<List<WishlistItem>>(emptyList()).apply {
        // We will map the map values to list reactively
    }

    // Since Kotlin doesn't allow direct map inside constructor assignment easily without a scope or using a standard StateFlow wrapper,
    // let's define it properly using a backing field or transforming it in the viewmodels/flow.
    // Actually, we can just map it on the fly or keep a separate list StateFlow and update both together!
    // Keeping both together is extremely simple and fast:
    private val _wishlistList = MutableStateFlow<List<WishlistItem>>(emptyList())
    val wishlistFlow: StateFlow<List<WishlistItem>> = _wishlistList.asStateFlow()

    fun isWishlisted(id: String): Boolean {
        return _wishlist.value.containsKey(id)
    }

    fun toggleWishlist(item: WishlistItem) {
        synchronized(this) {
            val currentMap = _wishlist.value.toMutableMap()
            if (currentMap.containsKey(item.id)) {
                currentMap.remove(item.id)
            } else {
                currentMap[item.id] = item
            }
            _wishlist.value = currentMap
            _wishlistList.value = currentMap.values.toList()
        }
    }
}
