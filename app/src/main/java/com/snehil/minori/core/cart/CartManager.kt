package com.snehil.minori.core.cart

import com.snehil.minori.domain.model.WishlistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

data class CartItem(
    val product: WishlistItem,
    val quantity: Int
)

@Singleton
class CartManager @Inject constructor() {
    private val _cart = MutableStateFlow<Map<String, CartItem>>(emptyMap())
    val cartFlow: StateFlow<List<CartItem>> = MutableStateFlow<List<CartItem>>(emptyList()).apply {
        // backing field helper
    }

    private val _cartList = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartList.asStateFlow()

    fun getCartCount(): Int {
        return _cartList.value.sumOf { it.quantity }
    }

    fun getCartTotal(): Double {
        return _cartList.value.sumOf { it.product.price * it.quantity }
    }

    fun addToCart(item: WishlistItem, quantity: Int = 1) {
        synchronized(this) {
            val currentMap = _cart.value.toMutableMap()
            val existing = currentMap[item.id]
            if (existing != null) {
                currentMap[item.id] = existing.copy(quantity = existing.quantity + quantity)
            } else {
                currentMap[item.id] = CartItem(item, quantity)
            }
            _cart.value = currentMap
            _cartList.value = currentMap.values.toList()
        }
    }

    fun removeFromCart(itemId: String) {
        synchronized(this) {
            val currentMap = _cart.value.toMutableMap()
            currentMap.remove(itemId)
            _cart.value = currentMap
            _cartList.value = currentMap.values.toList()
        }
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        synchronized(this) {
            val currentMap = _cart.value.toMutableMap()
            val existing = currentMap[itemId]
            if (existing != null) {
                if (quantity <= 0) {
                    currentMap.remove(itemId)
                } else {
                    currentMap[itemId] = existing.copy(quantity = quantity)
                }
                _cart.value = currentMap
                _cartList.value = currentMap.values.toList()
            }
        }
    }

    fun clearCart() {
        synchronized(this) {
            _cart.value = emptyMap()
            _cartList.value = emptyList()
        }
    }
}
