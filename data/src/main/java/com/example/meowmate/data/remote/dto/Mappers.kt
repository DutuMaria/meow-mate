package com.example.meowmate.data.remote.dto

import com.example.meowmate.data.local.CatImageEntity
import com.example.meowmate.domain.model.Breed
import com.example.meowmate.domain.model.CatItem

internal fun BreedDto.toDomain() = Breed(
    id, name, origin, temperament, lifeSpan,
    intelligence, affectionLevel, childFriendly, socialNeeds,
    wikipediaUrl, vetstreetUrl, description
)

internal fun CatImageDto.toEntity(): CatImageEntity {
    val b = breeds?.firstOrNull()
    return CatImageEntity(
        id = id,
        url = url,
        breedId = b?.id,
        name = b?.name,
        origin = b?.origin,
        temperament = b?.temperament,
        lifeSpan = b?.lifeSpan,
        intelligence = b?.intelligence,
        affectionLevel = b?.affectionLevel,
        childFriendly = b?.childFriendly,
        socialNeeds = b?.socialNeeds,
        wikipediaUrl = b?.wikipediaUrl,
        vetstreetUrl = b?.vetstreetUrl,
        description = b?.description
    )
}

internal fun CatImageEntity.toDomain() = CatItem(
    imageId = id,
    imageUrl = url,
    breed = Breed(
        id = breedId ?: "",
        name = name ?: "Unknown",
        origin = origin,
        temperament = temperament,
        lifeSpan = lifeSpan,
        intelligence = intelligence,
        affectionLevel = affectionLevel,
        childFriendly = childFriendly,
        socialNeeds = socialNeeds,
        wikipediaUrl = wikipediaUrl,
        vetstreetUrl = vetstreetUrl,
        description = description
    )
)
