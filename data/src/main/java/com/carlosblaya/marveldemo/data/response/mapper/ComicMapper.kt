package com.carlosblaya.marveldemo.data.response.mapper

import com.carlosblaya.marveldemo.data.response.ComicResponse
import com.carlosblaya.marveldemo.domain.model.Comic

class ComicMapper {
    fun toComicList(json: List<ComicResponse>?): List<Comic> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toComic(it) }
            } else {
                emptyList()
            }
        }
    }

    fun toComic(json: ComicResponse): Comic {
        with(json) {
            return Comic(
                id = id,
                name = title,
                thumbnail = generateThumbnailUrl(image.path,image.extension)
            )
        }
    }

    private fun generateThumbnailUrl(path: String, extension: String): String = "$path.$extension"

}