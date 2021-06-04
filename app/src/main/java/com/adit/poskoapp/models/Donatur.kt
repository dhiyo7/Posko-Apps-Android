package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donatur(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String? = null,
    @SerializedName("keterangan") var keterangan : String? = null,
    @SerializedName("alamat") var alamat : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
) : Parcelable
