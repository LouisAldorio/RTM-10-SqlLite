package com.example.rtm10sqlite.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var id : Int = 0,
    var nama : String = "",
    var no_hp : String = "",
    var email : String = ""
)  : Parcelable