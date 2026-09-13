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
fun LeadsScreen(
    viewModel: FieldoraViewModel,
    onBack: () -> Unit
) {
    val leads by viewModel.leads.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var customerName by remember { mutableStateOf("") }
    var product by remember { mutableStateOf("") }
    var expectedValue by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lead Pipeline & Sales", fontWeight = FontWeight.Bold) },
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
                Icon(Icons.Default.Add, contentDescription = "Add Lead", tint = androidx.compose.ui.graphics.Color.White)
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
            items(leads) { lead ->
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
                            Text(text = lead.customerName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Badge { Text(lead.stage.name, fontSize = 10.sp) }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Product: ${lead.product}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Expected: INR ${lead.expectedValue.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = FieldoraPrimary)
                            Text(text = "Owner: ${lead.owner}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Create New Sales Lead") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = customerName, onValueChange = { customerName = it }, label = { Text("Customer Name") })
                        OutlinedTextField(value = product, onValueChange = { product = it }, label = { Text("Product / Service") })
                        OutlinedTextField(value = expectedValue, onValueChange = { expectedValue = it }, label = { Text("Expected Value (INR)") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (customerName.isNotBlank()) {
                            viewModel.addLead(customerName, product, expectedValue.toDoubleOrNull() ?: 50000.0)
                            showAddDialog = false
                            customerName = ""
                            product = ""
                            expectedValue = ""
                        }
                    }) {
                        Text("Save Lead")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}
