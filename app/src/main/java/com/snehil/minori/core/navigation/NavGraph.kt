package com.snehil.minori.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snehil.minori.mainui.authentication.forgetpasswordscreen.ui.ForgotPasswordScreen
import com.snehil.minori.mainui.authentication.loginscreen.ui.LoginScreen
import com.snehil.minori.mainui.authentication.signupscreen.ui.SignupScreen
import com.snehil.minori.mainui.dealsofthedayscreen.ui.DealsOfTheDayScreen
import com.snehil.minori.mainui.homescreen.ui.HomeScreen
import com.snehil.minori.mainui.onboardingscreen.ui.OnboardingScreen
import com.snehil.minori.mainui.profilescreen.ui.ProfileScreen
import com.snehil.minori.mainui.splashscreen.ui.SplashScreen
import com.snehil.minori.mainui.trendingproductscreen.ui.TrendingProductScreen
import com.snehil.minori.mainui.artisanspotlightscreen.ui.ArtisanSpotlightScreen
import com.snehil.minori.mainui.newinstorescreen.ui.NewInStoreScreen
import com.snehil.minori.mainui.potterypromoscreen.ui.PotteryPromoScreen
import com.snehil.minori.mainui.newarrivalsscreen.ui.NewArrivalsScreen
import com.snehil.minori.mainui.specialoffersscreen.ui.SpecialOffersScreen
import com.snehil.minori.mainui.ceramicscreen.ui.CeramicScreen
import com.snehil.minori.mainui.paintingscreen.ui.PaintingScreen
import com.snehil.minori.mainui.fineartsscreen.ui.FineArtsScreen
import com.snehil.minori.mainui.wishlistscreen.ui.WishlistScreen
import com.snehil.minori.mainui.cartscreen.ui.CartScreen
import com.snehil.minori.mainui.addressdetailscreen.ui.AddressDetailsScreen
import com.snehil.minori.mainui.orderpreviewscreen.ui.OrderPreviewScreen
import com.snehil.minori.mainui.paymentscreen.ui.PaymentScreen
import com.snehil.minori.mainui.productdetailscreen.ui.ProductDetailScreen
import com.snehil.minori.mainui.searchscreen.ui.SearchScreen
import com.snehil.minori.mainui.settingsscreen.ui.SettingsScreen
import androidx.navigation.navArgument
import androidx.navigation.NavType


@Composable
fun NavGraph(navController: NavHostController) {
    val onProductClick: (String, String) -> Unit = { id, type ->
        navController.navigate(Screen.ProductDetail.createRoute(id, type))
    }
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.ForgotPassword.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewAllTrending = {
                    navController.navigate(Screen.TrendingProduct.route)
                },
                onViewAllDeals = {
                    navController.navigate(Screen.DealsOfTheDay.route)
                },
                onViewArtisanSpotlight = {
                    navController.navigate(Screen.ArtisanSpotlight.route)
                },
                onViewNewInStore = {
                    navController.navigate(Screen.NewInStore.route)
                },
                onViewPotteryPromo = {
                    navController.navigate(Screen.PotteryPromo.route)
                },
                onViewNewArrivals = {
                    navController.navigate(Screen.NewArrivals.route)
                },
                onViewSpecialOffers = {
                    navController.navigate(Screen.SpecialOffers.route)
                },
                onViewCeramics = {
                    navController.navigate(Screen.Ceramics.route)
                },
                onViewPaintings = {
                    navController.navigate(Screen.Paintings.route)
                },
                onViewFineArts = {
                    navController.navigate(Screen.FineArts.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onViewSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onViewSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProductDetail = onProductClick
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.TrendingProduct.route) {
            TrendingProductScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.DealsOfTheDay.route) {
            DealsOfTheDayScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.ArtisanSpotlight.route) {
            ArtisanSpotlightScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.NewInStore.route) {
            NewInStoreScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.PotteryPromo.route) {
            PotteryPromoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.NewArrivals.route) {
            NewArrivalsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.SpecialOffers.route) {
            SpecialOffersScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.Ceramics.route) {
            CeramicScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.Paintings.route) {
            PaintingScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.FineArts.route) {
            FineArtsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.Wishlist.route) {
            WishlistScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.Cart.route) {
            CartScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToAddressDetails = {
                    navController.navigate(Screen.AddressDetails.route)
                },
                onProductClick = onProductClick
            )
        }
        composable(route = Screen.AddressDetails.route) {
            AddressDetailsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToOrderPreview = {
                    navController.navigate(Screen.OrderPreview.route)
                }
            )
        }
        composable(route = Screen.OrderPreview.route) {
            OrderPreviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                }
            )
        }
        composable(route = Screen.Payment.route) {
            PaymentScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.StringType },
                navArgument("productType") { type = NavType.StringType }
            )
        ) {
            ProductDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onViewCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToAddressDetails = {
                    navController.navigate(Screen.AddressDetails.route)
                }
            )
        }
    }
}

