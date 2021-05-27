package com.example.rtm10sqlite.ROOM

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nama") var nama: String = "",
    @ColumnInfo(name = "no_hp") var phone: String = "",
    @ColumnInfo(name = "email") var email: String = ""
)