package com.snehil.minori.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object TrendingProduct : Screen("trending_products")
    object DealsOfTheDay : Screen("deals_of_the_day")
    object ArtisanSpotlight : Screen("artisan_spotlight")
    object NewInStore : Screen("new_in_store")
    object PotteryPromo : Screen("pottery_promo")
    object NewArrivals : Screen("new_arrivals")
    object SpecialOffers : Screen("special_offers")
    object Ceramics : Screen("ceramics")
    object Paintings : Screen("paintings")
    object FineArts : Screen("fine_arts")
    object Wishlist : Screen("wishlist")
    object Cart : Screen("cart")
    object AddressDetails : Screen("address_details")
    object OrderPreview : Screen("order_preview")
    object Payment : Screen("payment")
    object Search : Screen("search")
    object Settings : Screen("settings")
    object ProductDetail : Screen("product_detail/{productId}/{productType}") {
        fun createRoute(productId: String, productType: String) = "product_detail/$productId/$productType"
    }
}

