package com.carlosblaya.marveldemo.data.network.services

import com.carlosblaya.marveldemo.data.response.CharacterResponse
import com.carlosblaya.marveldemo.data.response.CharacterResultResponse
import com.carlosblaya.marveldemo.data.response.ComicDataWrapperResponse
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.util.Konsts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiInterface {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apikey: String = Konsts.PUBLIC_KEY,
        @Query("ts") ts: String = Konsts.timeStamp,
        @Query("hash") hash: String = Konsts.hash(),
        @Query("offset") offset: Int,
        @Query("nameStartsWith") name: String?
    ): CharacterResultResponse

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") id: Long,
        @Query("apikey") apikey: String = Konsts.PUBLIC_KEY,
        @Query("ts") ts: String = Konsts.timeStamp,
        @Query("hash") hash: String = Konsts.hash(),
    ): CharacterResultResponse

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComicsCharacter(
        @Path("characterId") id: Long,
        @Query("offset") offset: Int,
        @Query("apikey") apikey: String = Konsts.PUBLIC_KEY,
        @Query("ts") ts: String = Konsts.timeStamp,
        @Query("hash") hash: String = Konsts.hash(),
        @Query("format") format: String = "comic",
        @Query("formatType") formatType: String = "comic"
    ): ComicDataWrapperResponse


}