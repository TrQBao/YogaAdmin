package com.example.yogaappadmin.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface YogaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYogaClass(yogaClass: YogaClass): Long

    @Query("SELECT * FROM yoga_classes")
    fun getAllYogaClasses(): Flow<List<YogaClass>>

    @Query("DELETE FROM yoga_classes WHERE id = :id")
    suspend fun deleteYogaClass(id: Int): Int

    @Update
    suspend fun updateYogaClass(yogaClass: YogaClass): Int
}