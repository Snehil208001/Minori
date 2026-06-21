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
}
