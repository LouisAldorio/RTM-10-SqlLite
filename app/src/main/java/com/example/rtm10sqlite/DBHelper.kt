package com.example.rtm10sqlite

import DB.UserDB
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rtm10sqlite.data.User

class DBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mysqlitedbex.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_USER_TABLE = ("CREATE TABLE ${UserDB.userTable.TABLE_USER} " +
                "(${UserDB.userTable.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${UserDB.userTable.COLUMN_NAME} TEXT," +
                "${UserDB.userTable.COLUMN_EMAIL} TEXT," +
                "${UserDB.userTable.COLUMN_PHONE} TEXT)")
        db!!.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(
            "DROP TABLE IF EXISTS " +
                    "${UserDB.userTable.TABLE_USER}"
        )
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(UserDB.userTable.COLUMN_NAME, user.nama)
            put(UserDB.userTable.COLUMN_EMAIL, user.email)
            put(UserDB.userTable.COLUMN_PHONE, user.no_hp)
        }
        val success = db.insert(
            UserDB.userTable.TABLE_USER,
            null, contentValues
        )
        db.close()
        return success
    }

    fun viewAllName(): List<String> {
        val nameList = ArrayList<String>()
        val SELECT_NAME = "SELECT ${UserDB.userTable.COLUMN_NAME} " +
                "FROM ${UserDB.userTable.TABLE_USER}"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(SELECT_NAME, null)
        } catch (e: SQLException) {
            return ArrayList()
        }

        var userNama: String = ""
        if (cursor.moveToFirst()) {
            do {
                userNama = cursor.getString(
                    cursor.getColumnIndex(UserDB.userTable.COLUMN_NAME)
                )
                nameList.add(userNama)
            } while (cursor.moveToNext())
        }
        return nameList
    }

    fun deleteData(nama: String){
        val db = this.writableDatabase
        val selection = "${UserDB.userTable.COLUMN_NAME} = ?"
        val selectionArgs = arrayOf(nama)
        db.delete(UserDB.userTable.TABLE_USER,selection,selectionArgs)
    }

    fun deleteAllUser(){
        var db = this.writableDatabase
        db.delete(UserDB.userTable.TABLE_USER, "",null)
    }


    //transaction model
    fun beginUserTransaction(){
        this.writableDatabase.beginTransaction()
    }
    fun successUserTransaction(){
        this.writableDatabase.setTransactionSuccessful()
    }
    fun endUserTransaction(){
        this.writableDatabase.endTransaction()
    }
    fun addUserTransaction(user : User){
        val sqlString = "INSERT INTO ${UserDB.userTable.TABLE_USER} " +
                "(${UserDB.userTable.COLUMN_ID}" +
                ",${UserDB.userTable.COLUMN_NAME}" +
                ",${UserDB.userTable.COLUMN_EMAIL}" +
                ",${UserDB.userTable.COLUMN_PHONE}) VALUES (?,?,?,?)"
        val myStatement = this.writableDatabase.compileStatement(sqlString)

        myStatement.bindLong(1,user.id.toLong())
        myStatement.bindString(2,user.nama)
        myStatement.bindString(3,user.email)
        myStatement.bindString(4,user.no_hp)

        myStatement.execute()

        myStatement.clearBindings()
    }
}