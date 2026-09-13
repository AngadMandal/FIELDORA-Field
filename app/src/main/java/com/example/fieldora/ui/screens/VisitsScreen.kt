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
import com.example.fieldora.data.VisitOutcome
import com.example.fieldora.viewmodel.FieldoraViewModel
import com.example.ui.theme.FieldoraPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitsScreen(
    viewModel: FieldoraViewModel,
    onBack: () -> Unit
) {
    val visits by viewModel.visits.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var customerName by remember { mutableStateOf("") }
    var purpose by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Field Visits & Route Tracking", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = FieldoraPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Start Visit", tint = androidx.compose.ui.graphics.Color.White)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(visits) { visit ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = visit.customerName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Badge { Text(visit.outcome.name, fontSize = 10.sp) }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Purpose: ${visit.purpose}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Notes: ${visit.notes}", fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Time: ${visit.timestamp}", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = FieldoraPrimary)
                            Text(text = "GPS: Verified", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Log Customer Visit") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("Customer Name") })
                        OutlinedTextField(value = purpose, onValueChange = { purpose = it }, label = { Text("Visit Purpose") })
                        OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Outcome Notes") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (customerName.isNotBlank()) {
                            viewModel.addVisit(customerName, purpose, VisitOutcome.SUCCESSFUL, notes)
                            showAddDialog = false
                            customerName = ""
                            purpose = ""
                            notes = ""
                        }
                    }) {
                        Text("Complete Visit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}
