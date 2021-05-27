package com.example.rtm10sqlite.ROOM

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class),version = 1)
abstract class RoomHelper : RoomDatabase(){
    abstract fun userDao() : UserDao
}