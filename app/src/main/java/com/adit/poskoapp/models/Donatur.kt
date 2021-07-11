package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donatur(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var nama : String,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("keterangan") var keterangan: String,
    @SerializedName("alamat") var alamat : String,
    @SerializedName("id_posko") var id_posko : String,
    @SerializedName("tanggal") var tanggal : String
) : Parcelable
