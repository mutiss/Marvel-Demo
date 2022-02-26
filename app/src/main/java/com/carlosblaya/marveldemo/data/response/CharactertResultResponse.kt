package com.carlosblaya.marveldemo.data.response

import com.google.gson.annotations.SerializedName

data class CharacterResultResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("attributionText") val attributionText: String,
    @SerializedName("data") val dataResponse: CharacterDataContainerResponse
)

data class CharacterDataContainerResponse(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<CharacterResponse>
)

data class CharacterResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("resourceUri") val resourceUri: String,
    @SerializedName("thumbnail") val thumbnailResponse: ThumbnailResponse,
    @SerializedName("coics") val listComics: List<ComicResponse>
)

data class ThumbnailResponse(val path: String, val extension: String)

data class ComicResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = "",
    @SerializedName("thumbnail") val thumbnailResponse: ThumbnailResponse
)
