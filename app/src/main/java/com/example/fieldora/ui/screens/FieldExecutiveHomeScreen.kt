package com.example.fieldora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldora.viewmodel.FieldoraViewModel
import com.example.ui.theme.FieldoraPrimary
import com.example.ui.theme.FieldoraSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldExecutiveHomeScreen(
    viewModel: FieldoraViewModel,
    onNavigateTo: (String) -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val isCheckedIn by viewModel.isCheckedIn.collectAsState()
    val checkInTime by viewModel.checkInTime.collectAsState()
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = "FIELDORA Mobile", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "Good Morning, ${currentUser.name.split(" ")[0]}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateTo("notifications") }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = { onNavigateTo("ai") }) {
                        Icon(Icons.Default.SmartToy, contentDescription = "FIELDORA AI", tint = FieldoraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Tasks") },
                    label = { Text("Tasks") },
                    selected = false,
                    onClick = { onNavigateTo("tasks") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.People, contentDescription = "CRM") },
                    label = { Text("CRM") },
                    selected = false,
                    onClick = { onNavigateTo("customers") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Receipt, contentDescription = "Expenses") },
                    label = { Text("Expenses") },
                    selected = false,
                    onClick = { onNavigateTo("tools") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                    label = { Text("More") },
                    selected = false,
                    onClick = { onNavigateTo("admin_dashboard") }
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
            // Check-in Widget Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isCheckedIn) FieldoraSuccess.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isCheckedIn) "Checked In at $checkInTime" else "Not Checked In Today",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                );
                                Text(
                                    text = if (isCheckedIn) "GPS Active • Geofence Verified" else "Tap below to start shift & GPS tracking",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(if (isCheckedIn) FieldoraSuccess else Color.Gray)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.toggleCheckIn() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCheckedIn) Color.Red else FieldoraPrimary
                            )
                        ) {
                            Icon(
                                imageVector = if (isCheckedIn) Icons.Default.ExitToApp else Icons.Default.Login,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCheckedIn) "CHECK OUT" else "CHECK IN NOW",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Today's Target Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Today's Target & Progress", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TargetMetricBox("Visits", "8 / 10")
                            TargetMetricBox("Leads", "5 / 8")
                            TargetMetricBox("Follow-ups", "4 / 5")
                            TargetMetricBox("Conv.", "2 / 3")
                        }
                    }
                }
            }

            // Quick Actions Grid
            item {
                Text(text = "Quick Actions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickActionButton(icon = Icons.Default.PersonAdd, label = "New Lead") { onNavigateTo("leads") }
                    QuickActionButton(icon = Icons.Default.Store, label = "Visit", tint = FieldoraPrimary) { onNavigateTo("visits") }
                    QuickActionButton(icon = Icons.Default.People, label = "Customer") { onNavigateTo("customers") }
                    QuickActionButton(icon = Icons.Default.ReceiptLong, label = "Expense") { onNavigateTo("tools") }
                }
            }

            // Today's Tasks
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Today's Tasks", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    TextButton(onClick = { onNavigateTo("tasks") }) {
                        Text("View All")
                    }
                }
            }

            items(tasks.take(2)) { task ->
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
                            Text(text = task.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = task.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Badge {
                            Text(text = task.priority.name, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TargetMetricBox(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FieldoraPrimary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun QuickActionButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: Color = MaterialTheme.colorScheme.primary, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(tint.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = tint)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}
