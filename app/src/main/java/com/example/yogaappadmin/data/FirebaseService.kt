package com.example.yogaappadmin.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService {

    private val firestore = FirebaseFirestore.getInstance()
    private val yogaCollection = firestore.collection("yoga_classes")

    // Upload dữ liệu từ SQLite lên Firestore
    suspend fun syncWithFirestore(yogaClasses: List<YogaClass>) {
        try {
            // Lấy tất cả các lớp học từ Firestore
            val firebaseClasses = getAllFromFirestore()

            // Xóa các lớp học trên Firestore mà không còn tồn tại trong SQLite
            val classesToDelete = firebaseClasses.filter { firebaseClass ->
                yogaClasses.none { it.id == firebaseClass.id }
            }
            for (classToDelete in classesToDelete) {
                deleteFromFirestore(classToDelete.id)
            }

            // Đồng bộ hoặc thêm mới các lớp học từ SQLite lên Firestore
            for (yogaClass in yogaClasses) {
                if (yogaClass.id != 0) {
                    yogaCollection.document(yogaClass.id.toString()).set(yogaClass).await()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }


    // Lấy tất cả dữ liệu từ Firestore về
    suspend fun getAllFromFirestore(): List<YogaClass> {
        return try {
            val result = yogaCollection.get().await()
            result.documents.mapNotNull { document ->
                try {
                    YogaClass(
                        id = document.id.toIntOrNull() ?: 0,
                        dayOfWeek = document.getString("dayOfWeek") ?: "",
                        time = document.getString("time") ?: "",
                        teacher = document.getString("teacher") ?: "",
                        capacity = document.get("capacity")?.toString()?.toIntOrNull() ?: 0,
                        duration = document.get("duration")?.toString()?.toIntOrNull() ?: 0,
                        price = document.get("price")?.toString()?.toDoubleOrNull() ?: 0.0,
                        type = document.getString("type") ?: "",
                        description = document.getString("description")
                    )
                } catch (e: Exception) {
                    println("Error converting document: ${e.message}")
                    null
                }
            }
        } catch (e: Exception) {
            println("Error fetching from Firestore: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteFromFirestore(id: Int) {
        try {
            yogaCollection.document(id.toString()).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun updateYogaClass(yogaClass: YogaClass) {
        try {
            yogaCollection.document(yogaClass.id.toString()).set(yogaClass).await()
        } catch (e: Exception) {
            throw e
        }
    }
}
