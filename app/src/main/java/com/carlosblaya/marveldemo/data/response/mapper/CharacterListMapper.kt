package com.carlosblaya.marveldemo.data.response.mapper

import com.carlosblaya.marveldemo.data.response.CharacterResponse
import com.carlosblaya.marveldemo.domain.model.Character

class CharacterListMapper {

    fun toCharacterList(json: List<CharacterResponse>?): List<Character> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toCharacter(it) }
            } else {
                emptyList()
            }
        }
    }

    private fun toCharacter(json: CharacterResponse): Character {
        with(json) {
            return Character(
                id = id,
                name = name,
                description = description,
                thumbnail = generateThumbnailUrl(thumbnailResponse.path,thumbnailResponse.extension)
            )
        }
    }

    private fun generateThumbnailUrl(path: String, extension: String): String = "$path.$extension"

}