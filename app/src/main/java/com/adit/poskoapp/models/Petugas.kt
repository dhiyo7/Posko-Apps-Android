package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Petugas(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("username") var username : String? = null,
    @SerializedName("level") var level : String? = null,
    @SerializedName("created_at") var created_at : String? = null,
) : Parcelable