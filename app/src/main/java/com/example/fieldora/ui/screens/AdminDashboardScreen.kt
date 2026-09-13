package com.example.fieldora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldora.viewmodel.FieldoraViewModel
import com.example.ui.theme.FieldoraPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: FieldoraViewModel,
    onNavigateTo: (String) -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val visits by viewModel.visits.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FIELDORA Management Console", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                actions = {
                    IconButton(onClick = { onNavigateTo("ai") }) {
                        Icon(Icons.Default.SmartToy, contentDescription = "AI", tint = FieldoraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Overview") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.People, contentDescription = "Team") },
                    label = { Text("Team") },
                    selected = false,
                    onClick = { onNavigateTo("customers") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Map, contentDescription = "Live Map") },
                    label = { Text("Live Map") },
                    selected = false,
                    onClick = { onNavigateTo("visits") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Admin") },
                    selected = false,
                    onClick = { onNavigateTo("admin_tools") }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FieldoraPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Organization Overview", color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Active Field Force: 12 / 15", color = androidx.compose.ui.graphics.Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            StatPill("Visits Today", "48")
                            StatPill("Leads Won", "14")
                            StatPill("Pending Exp.", "${expenses.size}")
                        }
                    }
                }
            }

            item {
                Text(text = "Quick Management Modules", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.People, title = "Customers & CRM") { onNavigateTo("customers") }
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.TrendingUp, title = "Lead Pipeline") { onNavigateTo("leads") }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.Map, title = "Live Map") { onNavigateTo("live_map") }
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.Campaign, title = "Campaigns") { onNavigateTo("campaigns") }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.Assignment, title = "Tasks") { onNavigateTo("tasks") }
                    ModuleButton(modifier = Modifier.weight(1f), icon = Icons.Default.ReceiptLong, title = "Expenses & Forms") { onNavigateTo("tools") }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Recent Team Field Activity", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    TextButton(onClick = { onNavigateTo("visits") }) {
                        Text("View Map")
                    }
                }
            }

            items(visits) { visit ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = visit.customerName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Executive: ${visit.employeeName} • ${visit.purpose}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Badge {
                            Text(text = visit.timestamp, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatPill(label: String, value: String) {
    Column {
        Text(text = value, color = androidx.compose.ui.graphics.Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
    }
}

@Composable
fun ModuleButton(modifier: Modifier = Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = FieldoraPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
        }
    }
}
