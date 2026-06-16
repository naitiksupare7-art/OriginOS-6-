package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.viewmodel.LauncherViewModel
import kotlinx.coroutines.launch

@Composable
fun LauncherScreen(viewModel: LauncherViewModel) {
    val apps by viewModel.installedApps.collectAsState()
    val context = LocalContext.current
    
    val dockApps = apps.take(4)
    val homeApps = if (apps.size > 4) apps.subList(4, apps.size.coerceAtMost(12)) else emptyList() // Room for widgets + 8 apps

    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    // Abstract organic wallpaper
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2B5876), Color(0xFF4E4376)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                HomeScreen(
                    context = context,
                    apps = homeApps,
                    dockApps = dockApps
                )
            } else {
                AppDrawer(
                    context = context,
                    apps = apps,
                    onBack = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
            }
        }
    }
}
