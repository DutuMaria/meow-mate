package com.example.meowmate.domain.model

data class Breed(
    val id: String?,
    val name: String,
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