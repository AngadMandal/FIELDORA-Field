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
import com.example.ui.theme.FieldoraSuccess
import com.example.ui.theme.FieldoraWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMapScreen(
    viewModel: FieldoraViewModel,
    onBack: () -> Unit
) {
    val employeeLocations by viewModel.employeeLocations.collectAsState()
    val routeHistory by viewModel.routeHistory.collectAsState()
    val geofences by viewModel.geofences.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maps, GPS & Field Tracking Pro", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Live Radar") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Route History") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Geofences") })
            }

            when (selectedTab) {
                0 -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Radar, contentDescription = null, tint = FieldoraPrimary, modifier = Modifier.size(48.dp))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Active Field Force GPS Radar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Last updated: Just now • Configured interval: 5m", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        item {
                            Text(text = "Active Field Employees", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        items(employeeLocations) { emp ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = emp.employeeName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Badge(containerColor = if (emp.status.name == "LIVE") FieldoraSuccess else FieldoraWarning) {
                                            Text(emp.status.name, color = androidx.compose.ui.graphics.Color.White, fontSize = 10.sp)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Current Activity: ${emp.currentTask}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Coordinates: ${emp.latitude}, ${emp.longitude} • Updated: ${emp.lastUpdated}", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Verification: Location Verified (Within radius)", fontSize = 11.sp, color = FieldoraSuccess, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
                1 -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(text = "Today's Field Route History", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        items(routeHistory) { point ->
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
                                        Text(text = point.locationName, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(text = "Activity: ${point.activityType} • Lat: ${point.latitude}, Lng: ${point.longitude}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Badge { Text(point.time, fontSize = 10.sp) }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(text = "Configured Geofence Zones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        items(geofences) { geo ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = geo.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Badge(containerColor = FieldoraSuccess) { Text(geo.status, color = androidx.compose.ui.graphics.Color.White, fontSize = 10.sp) }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = "Type: ${geo.type} • Radius: ${geo.radiusMeters}m", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(text = "Coordinates: ${geo.latitude}, ${geo.longitude}", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
