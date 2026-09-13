package com.example.fieldora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fieldora.ui.navigation.FieldoraNavGraph
import com.example.fieldora.viewmodel.FieldoraViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: FieldoraViewModel = viewModel()
                FieldoraNavGraph(viewModel = viewModel)
            }
        }
    }
}
