package com.example.fieldora.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fieldora.data.UserRole
import com.example.fieldora.ui.screens.*
import com.example.fieldora.viewmodel.FieldoraViewModel

@Composable
fun FieldoraNavGraph(viewModel: FieldoraViewModel) {
    val navController = rememberNavController()
    val currentUser by viewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(viewModel = viewModel) {
                if (currentUser.role == UserRole.SUPER_ADMIN || currentUser.role == UserRole.ADMIN || currentUser.role == UserRole.MANAGER) {
                    navController.navigate("admin_dashboard") { popUpTo("login") { inclusive = true } }
                } else {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                }
            }
        }
        composable("home") {
            FieldExecutiveHomeScreen(
                viewModel = viewModel,
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable("admin_dashboard") {
            AdminDashboardScreen(
                viewModel = viewModel,
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable("customers") {
            CustomersScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("leads") {
            LeadsScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("visits") {
            VisitsScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("live_map") {
            LiveMapScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("campaigns") {
            CampaignsScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("tasks") {
            TasksScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("tools") {
            ToolsScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("ai") {
            AiAssistantScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("admin_tools") {
            AdminToolsScreen(viewModel = viewModel) { navController.popBackStack() }
        }
        composable("notifications") {
            CommunicationScreen(viewModel = viewModel) { navController.popBackStack() }
        }
    }
}
