package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    @SerializedName("id") var id : String? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("username") var username : String? = null,
    @SerializedName("level") var level : String? = null,
    @SerializedName("id_posko") var id_posko : Int? = null,
//    @SerializedName("email") var email : String? = null,
    @SerializedName("password") var password : String? = null,
    @SerializedName("token") var token : String? = null,
    @SerializedName("created_at") var created_at : String? = null,
    @SerializedName("updated_at") var updated_at : String? = null,

) : Parcelable