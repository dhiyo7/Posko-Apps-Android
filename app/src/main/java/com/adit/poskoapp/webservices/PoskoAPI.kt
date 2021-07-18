package com.adit.poskoapp.webservices

import com.adit.poskoapp.models.*
import com.adit.poskoapp.utils.PoskoUtils
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class PoskoAPI {
    companion object {
        private var retrofit: Retrofit? = null
        private var okHttp: OkHttpClient? =
            OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun instance(): PengaduanAPIService = getClient().create(PengaduanAPIService::class.java)

        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(PoskoUtils.API_ENDPOINT).client(okHttp)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            } else {
                retrofit!!
            }
        }
    }
}

interface PengaduanAPIService {
    /*API Endpoint Untuk Register dan Login*/
    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("username") username: String? = null,
        @Field("password") password: String? = null
    ): Call<WrappedResponse<User>>

    /*API Untuk CRUD Bencana*/
    @GET("api/bencana")
    fun getBencana(): Call<WrappedListResponse<Bencana>>

    @Multipart
    @POST("api/bencana")
    fun postBencana(
        @Header("Authorization") token: String,
        @Part("nama") name: RequestBody,
        @Part foto: MultipartBody.Part,
        @Part("detail") detail: RequestBody,
        @Part("date") date: RequestBody,
    ): Call<WrappedResponse<Bencana>>

    @Multipart
    @POST("api/bencana/{id}")
    fun editBencana(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("nama") name: RequestBody,
        @Part foto: MultipartBody.Part,
        @Part("detail") detail: RequestBody,
        @Part("date") date: RequestBody,
        @Part("_method") method : RequestBody
    ): Call<WrappedResponse<Bencana>>

    @Multipart
    @POST("api/bencana/{id}")
    fun editBencanaTanpaFoto(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("nama") name: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("date") date: RequestBody,
        @Part("_method") method : RequestBody
    ): Call<WrappedResponse<Bencana>>


    @DELETE("api/bencana/{id}")
    fun deleteBencana(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<WrappedResponse<Bencana>>

    /*API untuk CRUD posko*/
    @GET("api/posko")
    fun getPosko(): Call<WrappedListResponse<Posko>>

    @FormUrlEncoded
    @POST("api/posko")
    fun postPosko(
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("jumlah_pengungsi") jumlah_pengungsi : String,
        @Field("kontak_hp") kontak_hp : String,
        @Field("lokasi") lokasi : String,
        @Field("latitude") latitude : String,
        @Field("longitude") longitude : String,

    ): Call<WrappedResponse<Posko>>

    @FormUrlEncoded
    @PUT("api/posko/{id}")
    fun editPosko(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("nama") name: String,
        @Field("jumlah_pengungsi") jumlah_pengungsi : String,
        @Field("kontak_hp") kontak_hp : String,
        @Field("lokasi") lokasi : String,
        @Field("latitude") latitude : String,
        @Field("longitude") longitude : String,
    ): Call<WrappedResponse<Posko>>

    @DELETE("api/posko/{id}")
    fun deletePosko(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<WrappedResponse<Posko>>

    @GET("api/donatur")
    fun getDonatur() : Call<WrappedListResponse<Donatur>>

    @FormUrlEncoded
    @POST("api/donatur")
    fun postDonatur(
        @Header("Authorization") token: String?,
        @Field("nama") nama : String,
        @Field("posko_penerima") posko_penerima : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("alamat") alamat : String,
        @Field("tanggal") tanggal : String,
    ) : Call<WrappedResponse<Donatur>>

    @FormUrlEncoded
    @PUT("api/donatur/{id}")
    fun putDonatur(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Field("nama") nama : String,
        @Field("posko_penerima") posko_penerima : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("alamat") alamat : String,
        @Field("tanggal") tanggal : String,
    ) : Call<WrappedResponse<Donatur>>

    @DELETE("api/donatur/{id}")
    fun deleteDonatur(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Donatur>>



    @GET("api/penerimaan-logistik")
    fun getPenerimaanLogistik() : Call<WrappedListResponse<PenerimaanLogistik>>

    @Multipart
    @POST("api/penerimaan-logistik")
    fun postPenerimaanLogistik(
        @Header("Authorization") token: String?,
        @Part("pengirim") pengirim : RequestBody?,
        @Part("id_posko") id_posko : RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part
    ) : Call<WrappedResponse<PenerimaanLogistik>>

    @Multipart
    @POST("api/penerimaan-logistik/{id}")
    fun putPenerimaanLogistik(
        @Header("Authorization") token: String?,
        @Path("id") id : Int,
        @Part("pengirim") pengirim : RequestBody?,
        @Part("id_posko") id_posko : RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part,
        @Part("_method") method : RequestBody,
    ) : Call<WrappedResponse<PenerimaanLogistik>>

    @Multipart
    @POST("api/penerimaan-logistik/{id}")
    fun putPenerimaanLogistikTanpaFoto(
        @Header("Authorization") token: String?,
        @Path("id") id : Int,
        @Part("pengirim") pengirim : RequestBody?,
        @Part("id_posko") id_posko : RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part("_method") method : RequestBody,
    ) : Call<WrappedResponse<PenerimaanLogistik>>

    @DELETE("api/penerimaan-logistik/{id}")
    fun deletePenerimaanLogistik(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<PenerimaanLogistik>>

    @GET("api/logistik-masuk")
    fun getLogistikMasuk(@Header("Authorization") token: String?) : Call<WrappedListResponse<LogistikMasuk>>

    @Multipart
    @POST("api/logistik-masuk")
    fun postLogistikMasuk(
        @Header("Authorization") token: String,
        @Part("jenis_kebutuhan") jenis_kebutuhan: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("jumlah") jumlah: RequestBody,
        @Part("pengirim") pengirim : RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("status") status: RequestBody,
        @Part("satuan") satuan: RequestBody,
        @Part foto : MultipartBody.Part,
    ) : Call<WrappedResponse<LogistikMasuk>>

    @Multipart
    @POST("api/logistik-masuk/{id}")
    fun putLogistikMasuk(
        @Header("Authorization") token: String,
        @Path("id") id : Int,
        @Part("jenis_kebutuhan") jenis_kebutuhan: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("jumlah") jumlah: RequestBody,
        @Part("pengirim") pengirim : RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("status") status: RequestBody,
        @Part("satuan") satuan: RequestBody,
        @Part foto : MultipartBody.Part,
        @Part("_method") method : RequestBody
    ) : Call<WrappedResponse<LogistikMasuk>>

    @Multipart
    @POST("api/logistik-masuk/{id}")
    fun putLogistikMasukTanpaFoto(
        @Header("Authorization") token: String,
        @Path("id") id : Int,
        @Part("jenis_kebutuhan") jenis_kebutuhan: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part("jumlah") jumlah: RequestBody,
        @Part("pengirim") pengirim : RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("status") status: RequestBody,
        @Part("satuan") satuan: RequestBody,
        @Part("_method") method : RequestBody
    ) : Call<WrappedResponse<LogistikMasuk>>

    @DELETE("api/logistik-masuk/{id}")
    fun deleteLogistikMasuk(
        @Header("Authorization") token : String,
        @Path("id") id : Int,
    ) : Call<WrappedResponse<LogistikMasuk>>

    @GET("api/logistik-keluar")
    fun getLogistikKeluar(@Header("Authorization") token: String?) : Call<WrappedListResponse<LogistikKeluar>>

    @FormUrlEncoded
    @POST("api/logistik-keluar")
    fun postLogistikKeluar(
        @Header("Authorization") token:String,
        @Field("jenis_kebutuhan") jenis_kebutuhan: String,
        @Field("keterangan") keterangan: String,
        @Field("jumlah") jumlah: String,
        @Field("id_posko_penerima") id_posko_penerima: String,
        @Field("status") status: String,
        @Field("tanggal") tanggal: String,
        @Field("satuan") satuan: String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @FormUrlEncoded
    @PUT("api/logistik-keluar/{id}")
    fun putLogistikKeluar(
        @Header("Authorization") token:String,
        @Path("id") id : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan: String,
        @Field("keterangan") keterangan: String,
        @Field("jumlah") jumlah: String,
        @Field("id_posko_penerima") id_posko_penerima: String,
        @Field("status") status: String,
        @Field("tanggal") tanggal: String,
        @Field("satuan") satuan: String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @DELETE("api/logistik-keluar/{id}")
    fun deleteLogistikKeluar(
        @Header("Authorization") token:String,
        @Path("id") id : String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @GET("api/petugas-posko")
    fun getPetugasPosko(@Header("Authorization") token: String?) : Call<WrappedListResponse<Petugas>>

    @FormUrlEncoded
    @POST("api/petugas-posko")
    fun createPetugasPosko(
        @Header("Authorization") token: String?,
        @Field("nama") nama : String,
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("konfirmasi_password") konfirmasi_password : String,
        @Field("level") level : String,
        @Field("id_posko") id_posko : String
    ) : Call<WrappedResponse<Petugas>>

    @FormUrlEncoded
    @PUT("api/petugas-posko/{id}")
    fun updatePetugasPosko(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Field("nama") nama : String,
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("konfirmasi_password") konfirmasi_password : String,
        @Field("level") level : String,
        @Field("id_posko") id_posko : String
    ) : Call<WrappedResponse<Petugas>>

    @DELETE("api/petugas-posko/{id}")
    fun deletePetugasPosko(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Petugas>>


    @GET("api/kebutuhan-logistik")
    fun getKebutuhan(
        @Header("Authorization") token : String
    ) : Call<WrappedListResponse<KebutuhanLogistik>>

    @FormUrlEncoded
    @POST("api/kebutuhan-logistik")
    fun postKebutuhan(
        @Header("Authorization") token: String?,
        @Field("id_posko") id_posko : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("status") status : String,
        @Field("tanggal") tanggal : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<KebutuhanLogistik>>

    @FormUrlEncoded
    @PUT("api/kebutuhan-logistik/{id}")
    fun putKebutuhan(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Field("id_posko") id_posko : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("status") status : String,
        @Field("tanggal") tanggal : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<KebutuhanLogistik>>

    @DELETE("api/kebutuhan-logistik/{id}")
    fun deleteKebutuhan(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<KebutuhanLogistik>>

    @GET("api/penyaluran")
    fun getPenyaluran(
        @Header("Authorization") token : String,
    ) : Call<WrappedListResponse<Penyaluran>>

    @Multipart
    @POST("api/penyaluran")
    fun postPenyaluran(
        @Header("Authorization") token: String?,
        @Part("nama_penerima") nama_penerima: RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("alamat") alamat : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part
    ) : Call<WrappedResponse<Penyaluran>>

    @Multipart
    @POST("api/penyaluran/{id}")
    fun putPenyaluran(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Part("nama_penerima") nama_penerima: RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("alamat") alamat : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part,
        @Part("_method") method : RequestBody,
    ) : Call<WrappedResponse<Penyaluran>>

    @Multipart
    @POST("api/penyaluran/{id}")
    fun putPenyaluranTanpaFoto(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Part("nama_penerima") nama_penerima: RequestBody,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("alamat") alamat : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part("_method") method : RequestBody,
    ) : Call<WrappedResponse<Penyaluran>>

    @DELETE("api/penyaluran/{id}")
    fun deletePenyaluran(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Penyaluran>>

}


/*Untuk membungkus response dari backend*/
data class WrappedResponse<T>(
    @SerializedName("message") var message: String?,
    @SerializedName("status") var status: Int?,
    @SerializedName("data") var data: T?
) {
    constructor() : this(null, null, null)
}

data class WrappedListResponse<T>(
    @SerializedName("message") var message: String?,
    @SerializedName("status") var status: Int?,
    @SerializedName("data") var data: List<T>
) {
    constructor() : this(null, null, listOf())
}
