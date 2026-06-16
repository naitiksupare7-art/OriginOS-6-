package com.example.viewmodel

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherViewModel : ViewModel() {
    private val _installedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val installedApps: StateFlow<List<AppInfo>> = _installedApps.asStateFlow()

    fun loadApps(context: Context) {
        viewModelScope.launch {
            val apps = withContext(Dispatchers.IO) {
                val pm = context.packageManager
                val intent = Intent(Intent.ACTION_MAIN, null).apply {
                    addCategory(Intent.CATEGORY_LAUNCHER)
                }
                
                val resolveInfos = pm.queryIntentActivities(intent, 0)
                resolveInfos.mapNotNull { resolveInfo ->
                    try {
                        val packageName = resolveInfo.activityInfo.packageName
                        val label = resolveInfo.loadLabel(pm).toString()
                        val icon = resolveInfo.loadIcon(pm)
                        val launchIntent = pm.getLaunchIntentForPackage(packageName)
                        
                        if (launchIntent != null) {
                            AppInfo(label, packageName, icon, launchIntent)
                        } else null
                    } catch (e: Exception) {
                        Log.e("LauncherVM", "Error loading app info", e)
                        null
                    }
                }.sortedBy { it.label.lowercase() }
            }
            _installedApps.value = apps
        }
    }
}
