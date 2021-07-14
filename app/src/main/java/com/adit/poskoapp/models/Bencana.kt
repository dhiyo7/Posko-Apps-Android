package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bencana(
    @SerializedName ("id") var id : Int? = null,
    @SerializedName ("nama") var nama : String? = null,
    @SerializedName ("foto") var foto : String,
    @SerializedName ("detail") var detail : String? = null,
    @SerializedName ("date") var date : String? = null,
    @SerializedName ("created_at") var created_at : String? = null,
    @SerializedName ("updated_at") var updated_at : String? = null,
) : Parcelable
