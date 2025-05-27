//package com.example.jitbook.book.data.database
//
//import androidx.room.TypeConverter
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.decodeFromString
//
//object StringListTypeConverter {
//    @TypeConverter
//    fun fromString(value: String): List<String> {
//        return Json.decodeFromString(value)
//    }
//
//    @TypeConverter
//    fun fromList(list: List<String>): String {
//        return Json.encodeToString(list)
//    }
//}