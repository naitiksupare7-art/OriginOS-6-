package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.LauncherScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.LauncherViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.loadApps(this)
        
        setContent {
            MyApplicationTheme {
                LauncherScreen(viewModel = viewModel)
            }
        }
    }

    override fun onBackPressed() {
        // Do nothing to prevent exiting launcher
    }
}
