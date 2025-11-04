package com.example.meowmate.domain.repository

import com.example.meowmate.domain.model.CatItem

interface CatsRepository {
    suspend fun getCats(query: String = ""): List<CatItem>

    suspend fun cachedCats(query: String = ""): List<CatItem>
    suspend fun getCatByImageId(id: String): Result<CatItem>
}