package com.example.meowmate.domain.repository

import com.example.meowmate.domain.model.CatItem
import kotlinx.coroutines.flow.Flow

interface CatsRepository {
    fun getCats(forceRefresh: Boolean, query: String?): Flow<Result<List<CatItem>>>
    suspend fun getCatByImageId(id: String): Result<CatItem>
}