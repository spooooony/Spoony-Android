package com.spoony.spoony.data.service

import com.spoony.spoony.core.network.BaseResponse
import com.spoony.spoony.data.dto.request.SignInRequestDto
import com.spoony.spoony.data.dto.request.SignUpRequestDto
import com.spoony.spoony.data.dto.response.SignInResponseDto
import com.spoony.spoony.data.dto.response.SignUpResponseDto
import com.spoony.spoony.data.dto.response.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun signIn(
        @Header("Authorization") authorization: String,
        @Body signInRequestDto: SignInRequestDto
    ): BaseResponse<SignInResponseDto>

    @POST("/api/v1/auth/signup")
    suspend fun signUp(
        @Header("Authorization") authorization: String,
        @Body signUpRequestDto: SignUpRequestDto
    ): BaseResponse<SignUpResponseDto>

    @POST("/api/v1/auth/logout")
    suspend fun signOut(
        @Header("Authorization") authorization: String
    ): BaseResponse<Unit>

    @POST("/api/v1/auth/withdraw")
    suspend fun withDraw(
        @Header("Authorization") authorization: String
    ): BaseResponse<Unit>

    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String
    ): BaseResponse<TokenResponseDto>
}
