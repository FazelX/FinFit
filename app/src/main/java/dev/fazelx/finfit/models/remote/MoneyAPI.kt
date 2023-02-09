package dev.fazelx.finfit.models.remote

import dev.fazelx.finfit.models.remote.requests.CreateExpenseRequestModel
import dev.fazelx.finfit.models.remote.requests.LoginRequestModel
import dev.fazelx.finfit.models.remote.responses.LoginResponse
import dev.fazelx.finfit.models.remote.requests.SignupRequestModel
import dev.fazelx.finfit.models.remote.responses.CreateExpenseResponseModel
import dev.fazelx.finfit.models.remote.responses.GetExpenseResponseModel
import dev.fazelx.finfit.models.remote.responses.SignupResponseModel
import retrofit2.http.*


interface MoneyAPI {

    @POST("users/login")
    suspend fun login(
        @Body loginRequest: LoginRequestModel
    ): LoginResponse


    @POST("users/")
    suspend fun signup(
        @Body signupRequest: SignupRequestModel
    ): SignupResponseModel


    @POST("tasks")
    suspend fun addTransactionToAPI(
        @Header("Authorization") token : String,
        @Body createExpenseRequestModel: CreateExpenseRequestModel
    ): CreateExpenseResponseModel

    @GET("transactions")
    suspend fun getAllTransactionsFromAPI(
        @Header("Authorization") token : String
    ) : GetExpenseResponseModel

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionFromAPI(
        @Header("Authorization") token : String,
        @Path("id") id : String
    )

    @POST("users/logout")
    suspend fun logout(
        @Header("Authorization") token : String,
    )

    @DELETE("users/me")
    suspend fun deleteUserFromAPI(
        @Header("Authorization") token : String,
    )
}