package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PenerimaanLogistik (
    @SerializedName("id") var id : Int,
    @SerializedName("pengirim") var pengirim : String,
    @SerializedName("id_posko") var id_posko : Int,
    @SerializedName("posko_penerima") var posko_penerima : String,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("keterangan") var keterangan : String,
    @SerializedName("jumlah") var jumlah : Int,
    @SerializedName("satuan") var satuan : String,
    @SerializedName("tanggal") var tanggal : String,
    @SerializedName("status") var status : String,
    @SerializedName("foto") var foto : String,
) : Parcelable