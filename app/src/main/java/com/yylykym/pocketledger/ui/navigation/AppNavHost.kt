package com.yylykym.pocketledger.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yylykym.pocketledger.ui.home.HomeScreen
import com.yylykym.pocketledger.ui.transaction.AddTransactionScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onAddTransactionClick = {
                    navController.navigate("add_transaction")
                }
            )
        }

        composable("add_transaction") {
            AddTransactionScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}