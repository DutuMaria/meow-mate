package com.example.meowmate.data


import com.example.meowmate.data.local.CatsDao
import com.example.meowmate.data.remote.TheCatApi
import com.example.meowmate.domain.model.CatItem
import com.example.meowmate.domain.repository.CatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: TheCatApi,
    private val dao: CatsDao,
    private val apiKeyProvider: () -> String? = { null }
) : CatsRepository {

    override fun getCats(forceRefresh: Boolean, query: String?): Flow<Result<List<CatItem>>> = flow {
        emit(Result.success(dao.getAll().map { it.toDomain() }.filterQuery(query)))
        try {
            if (forceRefresh || dao.getAll().isEmpty()) {
                val remote = api.getImages(apiKeyProvider(), limit = 40)
                val entities = remote.map { it.toEntity() }
                dao.clear()
                dao.upsertAll(entities)
            }
            val cached = dao.getAll().map { it.toDomain() }.filterQuery(query)
            if (cached.isEmpty()) emit(Result.failure(IllegalStateException("No cats found")))
            else emit(Result.success(cached))
        } catch (e: Exception) {
            val cached = dao.getAll().map { it.toDomain() }.filterQuery(query)
            if (cached.isNotEmpty()) emit(Result.success(cached))
            emit(Result.failure(e))
        }
    }

    override suspend fun getCatByImageId(id: String): Result<CatItem> = try {
        dao.getById(id)?.toDomain()?.let { Result.success(it) }
            ?: run {
                val remote = api.getImageById(apiKeyProvider(), id)
                val entity = remote.toEntity().also { dao.upsertAll(listOf(it)) }
                Result.success(entity.toDomain())
            }
    } catch (e: Exception) { Result.failure(e) }
}

private fun List<CatItem>.filterQuery(q: String?): List<CatItem> {
    if (q.isNullOrBlank()) return this
    val s = q.trim().lowercase()
    return filter { item ->
        val b = item.breed
        listOf(item.imageId, b?.name, b?.origin, b?.temperament, b?.description)
            .any { it?.lowercase()?.contains(s) == true }
    }
}
