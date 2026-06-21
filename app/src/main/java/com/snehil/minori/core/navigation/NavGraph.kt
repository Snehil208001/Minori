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

@Composable
fun NavGraph(navController: NavHostController) {
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
                }
            )
        }
        composable(route = Screen.DealsOfTheDay.route) {
            DealsOfTheDayScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(route = Screen.ArtisanSpotlight.route) {
            ArtisanSpotlightScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(route = Screen.NewInStore.route) {
            NewInStoreScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(route = Screen.PotteryPromo.route) {
            PotteryPromoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(route = Screen.NewArrivals.route) {
            NewArrivalsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
    }
}

