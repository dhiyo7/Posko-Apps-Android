package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Posko(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("jumlah_pengungsi") var jumlah_pengungsi : String? = null,
    @SerializedName("kontak_hp") var kontak_hp : String? = null,
    @SerializedName("lokasi") var lokasi : String? = null,
    @SerializedName("longitude") var longitude : String? = null,
    @SerializedName("latitude") var latitude : String? = null,
    @SerializedName("created_at") var created_at : String? = null,
): Parcelable {
    override fun toString(): String {
        return nama!!
    }
}