package com.adit.poskoapp.webservices

import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.models.User
import com.adit.poskoapp.utils.PoskoUtils
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
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

    @GET("api/bencana")
    fun getBencana(): Call<WrappedListResponse<Bencana>>

    @FormUrlEncoded
    @POST("api/bencana")
    fun postBencana(
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("foto") foto: String,
        @Field("detail") detail: String,
        @Field("date") date: String,
    ): Call<WrappedResponse<Bencana>>

    @FormUrlEncoded
    @PUT("api/bencana/{id}")
    fun editBencana(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("foto") foto: String,
        @Field("detail") detail: String,
        @Field("date") date: String,
    ): Call<WrappedResponse<Bencana>>

    @DELETE("api/bencana/{id}")
    fun deleteBencana(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): Call<WrappedResponse<Bencana>>
}

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
