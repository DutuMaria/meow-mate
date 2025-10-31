package com.example.meowmate.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_images")
data class CatImageEntity(
    @PrimaryKey val id: String,
    val url: String,
    val breedId: String?,
    val name: String?,
    val origin: String?,
    val temperament: String?,
    val lifeSpan: String?,
    val intelligence: Int?,
    val affectionLevel: Int?,
    val childFriendly: Int?,
    val socialNeeds: Int?,
    val wikipediaUrl: String?,
    val vetstreetUrl: String?,
    val description: String?
)

