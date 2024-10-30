package com.example.yogaappadmin.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SyncConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, hasInternet: Boolean) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = if (hasInternet) "Sync Confirmation" else "No Internet Connection") },
        text = {
            Text(
                if (hasInternet)
                    "Are you sure you want to sync with Firestore?"
                else
                    "Please check your internet connection and try again."
            )
        },
        confirmButton = {
            if (hasInternet) {
                TextButton(onClick = onConfirm) {
                    Text("Yes")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (hasInternet) "No" else "OK")
            }
        }
    )
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}