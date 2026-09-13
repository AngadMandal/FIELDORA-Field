package com.example.fieldora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fieldora.data.UserRole
import com.example.fieldora.viewmodel.FieldoraViewModel
import com.example.ui.theme.FieldoraPrimary
import com.example.ui.theme.FieldoraSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: FieldoraViewModel,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("rahul.sharma@fieldora.com") }
    var password by remember { mutableStateOf("********") }
    var selectedRole by remember { mutableStateOf(UserRole.FIELD_EXECUTIVE) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo / Branding
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = FieldoraPrimary,
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("F", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "FIELDORA",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = FieldoraSecondary
            )
            Text(
                text = "Plan. Visit. Sell. Grow.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Work Email / Employee ID") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Role Selector for Demonstration & RBAC testing
            Text(
                text = "Select Simulation Role:",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    UserRole.FIELD_EXECUTIVE to "Field Exec",
                    UserRole.MANAGER to "Manager",
                    UserRole.SUPER_ADMIN to "Super Admin"
                ).forEach { (role, label) ->
                    FilterChip(
                        selected = selectedRole == role,
                        onClick = { selectedRole = role },
                        label = { Text(label, fontSize = 11.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.switchRole(selectedRole)
                    onLoginSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FieldoraPrimary)
            ) {
                Text("Secure Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
