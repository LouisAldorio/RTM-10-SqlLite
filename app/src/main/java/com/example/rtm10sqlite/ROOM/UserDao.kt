package com.example.rtm10sqlite.ROOM

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg user: User)

    @Insert
    fun Insert(user: User)

    @Query("DELETE FROM user ")
    fun DeleteAll()

    @Query("DELETE FROM user WHERE id = :id")
    fun DeleteById(id : Int)

    @Query("SELECT * FROM user")
    fun getAllData() : List<User>

//    @Query("UPDATE user SET status = :status WHERE id = :id")
//    fun UpdateTodoStatus(id: Int,status: Int)

    //transaction
    @Transaction
    fun BulkInsert(userList : List<User>){
        for(item in userList){
            Insert(item)
        }
    }
}