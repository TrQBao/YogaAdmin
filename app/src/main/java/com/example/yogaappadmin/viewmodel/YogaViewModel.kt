package com.example.yogaappadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yogaappadmin.data.FirebaseService
import com.example.yogaappadmin.data.YogaClass
import com.example.yogaappadmin.data.YogaDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class YogaViewModel(private val yogaDao: YogaDao, private val firebaseService: FirebaseService) : ViewModel() {
    val allYogaClasses: Flow<List<YogaClass>> = yogaDao.getAllYogaClasses()
    // Lấy tất cả lớp học từ SQLite (lắng nghe thay đổi từ Flow)
    fun getAllYogaClasses(onResult: (List<YogaClass>) -> Unit) {
        viewModelScope.launch {
            yogaDao.getAllYogaClasses().collect { classes ->
                onResult(classes)
            }
        }
    }

    // Thêm lớp học vào SQLite và đồng bộ với Firestore
    fun insertYogaClass(yogaClass: YogaClass) {
        viewModelScope.launch {
            // Thêm lớp học vào SQLite và lấy ID sinh ra
            val newId = yogaDao.insertYogaClass(yogaClass)
            val updatedClass = yogaClass.copy(id = newId.toInt())  // Cập nhật ID mới cho YogaClass

            // Đồng bộ với Firestore
//            firebaseService.syncWithFirestore(listOf(updatedClass))
        }
    }

    fun updateYogaClass(yogaClass: YogaClass) {
        viewModelScope.launch {
            try {
                println("Updating class: $yogaClass")  // Kiểm tra xem dữ liệu đúng không
                yogaDao.updateYogaClass(yogaClass)
//                firebaseService.syncWithFirestore(listOf(yogaClass)) // Đồng bộ với Firestore
                println("Update completed!")
            } catch (e: Exception) {
                println("Error updating class: ${e.message}")
            }
        }
    }


    // Xóa lớp học từ SQLite và Firestore
    fun deleteYogaClass(id: Int) {
        viewModelScope.launch {
            yogaDao.deleteYogaClass(id)
            // Xóa từ Firestore
//            firebaseService.deleteFromFirestore(id)
        }
    }

    // Đồng bộ dữ liệu từ SQLite lên Firestore
    fun syncWithFirestore() {
        viewModelScope.launch {
            val classes = yogaDao.getAllYogaClasses().first() // Lấy danh sách lớp học chỉ khi nhấn nút
            firebaseService.syncWithFirestore(classes)
        }
    }

    // Đồng bộ dữ liệu từ Firestore về SQLite
    fun syncFromFirestoreToLocal() {
        viewModelScope.launch {
            try {
                val firebaseClasses = firebaseService.getAllFromFirestore()
                for (yogaClass in firebaseClasses) {
                    yogaDao.insertYogaClass(yogaClass) // Lưu vào SQLite
                }
            } catch (e: Exception) {
                // Xử lý lỗi
                println("Error syncing from Firestore: ${e.message}")
            }
        }
    }
}