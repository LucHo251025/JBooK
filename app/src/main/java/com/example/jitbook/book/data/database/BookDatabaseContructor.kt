//package com.example.jitbook.book.data.database
//
//import android.content.Context
//import androidx.room.Room
//
//object BookDatabaseConstructor {
//    @Volatile
//    private var INSTANCE: FavoriteBookDatabase? = null
//
//    fun getInstance(context: Context): FavoriteBookDatabase {
//        return INSTANCE ?: synchronized(this) {
//            INSTANCE ?: Room.databaseBuilder(
//                context.applicationContext,
//                FavoriteBookDatabase::class.java,
//                FavoriteBookDatabase.DB_NAME
//            ).build().also { INSTANCE = it }
//        }
//    }
//}
