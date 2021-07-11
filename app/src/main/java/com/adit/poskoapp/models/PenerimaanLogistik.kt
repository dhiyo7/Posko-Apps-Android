package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PenerimaanLogistik (
    @SerializedName("id_posko") var id_posko : Int,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("keterangan") var keterangan : String,
    @SerializedName("jumlah") var jumlah : Int,
    @SerializedName("tanggal") var tanggal : String
) : Parcelable