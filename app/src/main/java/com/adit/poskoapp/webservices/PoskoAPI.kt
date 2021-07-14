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

    @FormUrlEncoded
    @POST("api/register")
    fun register(
        @Field("name") name: String? = null,
        @Field("email") email: String? = null,
        @Field("password") password: String? = null,
        @Field("confirm_password") confirm_password: String? = null
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

    @GET("api/penerimaan-logistik")
    fun getPenerimaanLogistik() : Call<WrappedListResponse<PenerimaanLogistik>>

    @GET("api/logistik-masuk")
    fun getLogistikMasuk(@Header("Authorization") token: String?) : Call<WrappedListResponse<LogistikMasuk>>

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
