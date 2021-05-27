package com.example.rtm10sqlite.ROOM

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg user: User)

    @Query("DELETE FROM user ")
    fun DeleteAll()

    @Query("DELETE FROM user WHERE id = :id")
    fun DeleteById(id : Int)

    @Query("SELECT * FROM user")
    fun getAllData() : List<User>

//    @Query("UPDATE user SET status = :status WHERE id = :id")
//    fun UpdateTodoStatus(id: Int,status: Int)
}