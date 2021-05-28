package com.example.rtm10sqlite.sharePref

import android.content.Context
import android.content.SharedPreferences

class FirstRunSharePref(context: Context) {
    
    private val keyPref = "FIRST_RUN"

    private var mySharePref : SharedPreferences =
            context.getSharedPreferences("SharePrefFile", Context.MODE_PRIVATE)

    var firstRun : Boolean
        get() = mySharePref.getBoolean(keyPref, true)
        set(value) { mySharePref.edit().putBoolean(keyPref,value).commit() }
}