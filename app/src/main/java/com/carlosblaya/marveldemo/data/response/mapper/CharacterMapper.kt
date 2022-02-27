package com.carlosblaya.marveldemo.data.response.mapper

import com.carlosblaya.marveldemo.data.database.entities.CharacterDB
import com.carlosblaya.marveldemo.data.response.CharacterResponse
import com.carlosblaya.marveldemo.data.response.CharacterResultResponse
import com.carlosblaya.marveldemo.domain.model.Character

class CharacterMapper {

    fun toCharacterList(json: List<CharacterResponse>?): List<Character> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toCharacter(it) }
            } else {
                emptyList()
            }
        }
    }

    fun toCharacterListDB(json: List<CharacterResponse>?): List<CharacterDB> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toCharacterDB(it) }
            } else {
                emptyList()
            }
        }
    }

    /**
     * Character Detail Service gives a list of a single character, so we get one character only with single()
     */
    fun toSingleCharacterList(json: CharacterResultResponse): Character {
        with(json) {
            return toCharacter(this.dataResponse.results.single())
        }
    }

    fun toSingleCharacterDBList(json: CharacterResultResponse): CharacterDB {
        with(json) {
            return toCharacterDB(this.dataResponse.results.single())
        }
    }

    fun toCharacter(json: CharacterResponse): Character {
        with(json) {
            return Character(
                id = id,
                name = name,
                description = if(description.isEmpty()) "No description available" else description,
                thumbnail = generateThumbnailUrl(thumbnailResponse.path,thumbnailResponse.extension)
            )
        }
    }
    fun toCharacterFromDB(characterDB:CharacterDB): Character {
        with(characterDB) {
            return Character(
                id = id,
                name = name,
                description = if(description.isEmpty()) "No description available" else description,
                thumbnail = thumbnail
            )
        }
    }

    fun toCharacterDB(json: CharacterResponse): CharacterDB {
        with(json) {
            return CharacterDB(
                id = id,
                name = name,
                description = if(description.isEmpty()) "No description available" else description,
                thumbnail = generateThumbnailUrl(thumbnailResponse.path,thumbnailResponse.extension)
            )
        }
    }

    private fun generateThumbnailUrl(path: String, extension: String): String = "$path.$extension"

}