package com.example.yogaappadmin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "yoga_classes")
data class YogaClass(
    @PrimaryKey(autoGenerate = true)
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: Int = 0,

    @get:PropertyName("dayOfWeek")
    @set:PropertyName("dayOfWeek")
    var dayOfWeek: String = "",

    @get:PropertyName("time")
    @set:PropertyName("time")
    var time: String = "",

    @get:PropertyName("teacher")
    @set:PropertyName("teacher")
    var teacher: String = "",

    @get:PropertyName("capacity")
    @set:PropertyName("capacity")
    var capacity: Int = 0,

    @get:PropertyName("duration")
    @set:PropertyName("duration")
    var duration: Int = 0,

    @get:PropertyName("price")
    @set:PropertyName("price")
    var price: Double = 0.0,

    @get:PropertyName("type")
    @set:PropertyName("type")
    var type: String = "",

    @get:PropertyName("description")
    @set:PropertyName("description")
    var description: String? = null
) {
    // No-argument constructor for Firestore
    constructor() : this(0, "", "", "", 0, 0, 0.0, "", null)
}