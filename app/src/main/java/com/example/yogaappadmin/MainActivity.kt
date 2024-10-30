package com.example.yogaappadmin

import YogaClassListScreen
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.yogaappadmin.data.FirebaseService
import com.example.yogaappadmin.data.YogaDatabase
import com.example.yogaappadmin.ui.theme.YogaAppAdminTheme
import com.example.yogaappadmin.viewmodel.YogaViewModel
import com.example.yogaappadmin.viewmodel.YogaViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Khởi tạo ViewModel
        val yogaDao = YogaDatabase.getDatabase(applicationContext).yogaDao()
        val firebaseService = FirebaseService()
        val yogaViewModelFactory = YogaViewModelFactory(yogaDao, firebaseService)
        val yogaViewModel = ViewModelProvider(this, yogaViewModelFactory).get(YogaViewModel::class.java)

        setContent {
            YogaAppAdminTheme {
                // Sử dụng Modifier.fillMaxSize() để chiếm toàn bộ chiều cao và chiều rộng
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    YogaClassListScreen(yogaViewModel, Modifier.padding(innerPadding).fillMaxSize())
                }
            }
        }
    }
}


