package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.model.AppInfo
import com.example.ui.components.AppIcon
import com.example.ui.components.ClockWidget
import com.example.ui.components.SystemInfoWidget
import com.example.ui.components.WeatherWidget
import android.content.Context

@Composable
fun HomeScreen(
    context: Context,
    apps: List<AppInfo>,
    dockApps: List<AppInfo>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Widgets area (OriginOS style cluster)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ClockWidget(modifier = Modifier.weight(1f).aspectRatio(1f))
            Column(
                modifier = Modifier.weight(1f).aspectRatio(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherWidget(modifier = Modifier.weight(1f).fillMaxWidth())
                SystemInfoWidget(modifier = Modifier.weight(1f).fillMaxWidth())
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Home apps Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(apps) { app ->
                AppIcon(
                    app = app,
                    onClick = {
                        try {
                            app.intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(app.intent)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                )
            }
        }

        // Dock
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.2f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                dockApps.forEach { app ->
                    AppIcon(
                        app = app,
                        showLabel = false,
                        onClick = {
                            try {
                                app.intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(app.intent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    )
                }
            }
        }
    }
}
