package com.adit.poskoapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogistikMasuk(
    @SerializedName("id") var id : String? = null,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String? = null,
    @SerializedName("keterangan") var keterangan : String? = null,
    @SerializedName("jumlah") var jumlah : String? = null,
    @SerializedName("pengirim") var pengirim : String? = null,
    @SerializedName("posko_penerima") var posko_penerima : String? = null,
    @SerializedName("status") var status : String? = null,
    @SerializedName("satuan") var satuan : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("foto") var foto : String? = null,
    @SerializedName("created_at") var created_at : String? = null,
    @SerializedName("updated_at") var updated_at : String? = null,
) : Parcelable