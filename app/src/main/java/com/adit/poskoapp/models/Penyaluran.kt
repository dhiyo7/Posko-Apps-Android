package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Penyaluran (
    @SerializedName("id") var id : String,
    @SerializedName("nama_penerima") var nama_penerima : String,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("jumlah") var jumlah : String,
    @SerializedName("satuan") var satuan : String,
    @SerializedName("status") var status : String,
    @SerializedName("keterangan") var keterangan : String,
    @SerializedName("alamat") var alamat : String,
    @SerializedName("foto") var foto : String,
    @SerializedName("tanggal") var tanggal : String,
    @SerializedName("created_at") var created_at : String,
    @SerializedName("updated_at") var updated_at : String,
) : Parcelable