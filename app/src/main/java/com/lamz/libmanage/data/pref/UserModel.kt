package com.lamz.libmanage.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val user: String,
    val role : String,
    val isLogin: Boolean = false
) : Parcelable