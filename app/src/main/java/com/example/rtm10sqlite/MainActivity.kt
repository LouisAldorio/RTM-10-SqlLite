package com.example.rtm10sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.room.Room
import com.example.rtm10sqlite.ROOM.RoomHelper
import com.example.rtm10sqlite.data.User
import com.example.rtm10sqlite.sharePref.FirstRunSharePref
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var mysqlitedb : DBHelper? = null

    //ROOM
    var roomHelper : RoomHelper? = null


    var myFirstRunSharePref : FirstRunSharePref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mysqlitedb = DBHelper(this)
        //share pref to mark wheather its first open apps , or not, if first open then preload data
        myFirstRunSharePref = FirstRunSharePref(this)

        //ROOM
        roomHelper = Room.databaseBuilder(
            this,
            RoomHelper::class.java,
            "UserROOM.db"
        ).build()

        //preloading data

        //below rows is used to simulate the transactional model with pre load in first run application.
        //for sqllite
//        mysqlitedb?.deleteAllUser()

        //for ROOM
        DeleteAllFromROOM()
        myFirstRunSharePref?.firstRun = true
        //simulation section end here

        if(myFirstRunSharePref!!.firstRun){
            val secondIntent = Intent(this,PreloadActivity::class.java)
            startActivity(secondIntent)
        }


        btn_submit.setOnClickListener {
            val userTmp : User = User()
            userTmp.nama = edit_text_name.text.toString()
            userTmp.email = edit_text_email.text.toString()
            userTmp.no_hp = edit_text_phone_number.text.toString()

            var result = mysqlitedb?.addUser(userTmp)
            if(result !=- 1L){
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Gagal",Toast.LENGTH_SHORT).show()
            }
            updateAdapter()
            edit_text_name.text.clear()
            edit_text_email.text.clear()
            edit_text_phone_number.text.clear()


            //ROOM
//            val userTmp = com.example.rtm10sqlite.ROOM.User(Random.nextInt())
//            userTmp.nama = edit_text_name.text.toString()
//            userTmp.email = edit_text_email.text.toString()
//            userTmp.phone = edit_text_phone_number.text.toString()
//
//            doAsync {
//                roomHelper?.userDao()?.insertAll(userTmp)
//                uiThread {
//                    updateAdapter()
//                    edit_text_name.text.clear()
//                    edit_text_email.text.clear()
//                    edit_text_phone_number.text.clear()
//                }
//            }
        }


        btn_delete.setOnClickListener {
            var nama = spinner1.selectedItem.toString()
            if(nama!=null || nama !=""){
                doAsync {
                    mysqlitedb?.deleteData(nama)
                    updateAdapter()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    private fun updateAdapter() {
        doAsync {
//            var nameList = mysqlitedb!!.viewAllName().toTypedArray()

            //ROOM
            var nameList = roomHelper?.userDao()?.getAllData()?.toTypedArray()

            uiThread {
                if(spinner1 != null && nameList?.size != 0) {
                    var arrayAdapter = ArrayAdapter(applicationContext,
                        android.R.layout.simple_spinner_dropdown_item, nameList!!)
                    spinner1.adapter = arrayAdapter
                }
            }
        }
    }

    fun DeleteAllFromROOM(){
        doAsync {
            roomHelper!!.userDao().DeleteAll()
        }
    }
}