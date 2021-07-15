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
        @Field("foto") foto: String,
        @Field("detail") detail: String,
        @Field("date") date: String,
    ): Call<WrappedResponse<Posko>>

    @FormUrlEncoded
    @PUT("api/posko/{id}")
    fun editPosko(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("foto") foto: String,
        @Field("detail") detail: String,
        @Field("date") date: String,
    ): Call<WrappedResponse<Bencana>>

    @DELETE("api/posko/{id}")
    fun deletePosko(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Call<WrappedResponse<Bencana>>

    @GET("api/donatur")
    fun getDonatur() : Call<WrappedListResponse<Donatur>>

    @FormUrlEncoded
    @POST("api/donatur")
    fun postDonatur(
        @Header("Authorization") token: String?,
        @Field("nama") nama : String,
        @Field("id_posko") id_posko : String,
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
        @Field("id_posko") id_posko : String,
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
        @Part("posko_penerima") id_posko: RequestBody,
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
        @Part("posko_penerima") id_posko: RequestBody,
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
        @Part("posko_penerima") id_posko: RequestBody,
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
    ) : Call<WrappedResponse<KebutuhanLogistik>>

    @DELETE("api/kebutuhan-logistik/{id}")
    fun deleteKebutuhan(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<KebutuhanLogistik>>


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
