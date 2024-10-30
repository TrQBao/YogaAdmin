package com.example.yogaappadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yogaappadmin.data.FirebaseService
import com.example.yogaappadmin.data.YogaDao

class YogaViewModelFactory(private val yogaDao: YogaDao, private val firebaseService: FirebaseService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YogaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YogaViewModel(yogaDao, firebaseService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}