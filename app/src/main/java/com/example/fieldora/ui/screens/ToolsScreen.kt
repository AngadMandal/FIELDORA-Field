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
fun ToolsScreen(
    viewModel: FieldoraViewModel,
    onBack: () -> Unit
) {
    val expenses by viewModel.expenses.collectAsState()
    var showExpenseDialog by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("Fuel") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Field Tools & Expense Claims", fontWeight = FontWeight.Bold) },
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
                onClick = { showExpenseDialog = true },
                containerColor = FieldoraPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense", tint = androidx.compose.ui.graphics.Color.White)
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
            item {
                Text(text = "Submitted Expense Claims", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(expenses) { exp ->
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
                            Text(text = exp.category, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "INR ${exp.amount}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FieldoraPrimary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = exp.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Date: ${exp.date}", fontSize = 12.sp)
                            Badge { Text(exp.status.name, fontSize = 10.sp) }
                        }
                    }
                }
            }
        }

        if (showExpenseDialog) {
            AlertDialog(
                onDismissRequest = { showExpenseDialog = false },
                title = { Text("Submit Expense Claim") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category (Fuel, Food, Travel)") })
                        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount (INR)") })
                        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description / Receipt Details") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (amount.isNotBlank()) {
                            viewModel.addExpense(category, amount.toDoubleOrNull() ?: 0.0, description)
                            showExpenseDialog = false
                            amount = ""
                            description = ""
                        }
                    }) {
                        Text("Submit Claim")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExpenseDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}
