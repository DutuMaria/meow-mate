package com.example.meowmate.data.repository


import com.example.meowmate.data.local.CatsDao
import com.example.meowmate.data.remote.dto.CatDto
import com.example.meowmate.data.remote.TheCatApi
import com.example.meowmate.data.remote.dto.toDomain
import com.example.meowmate.data.remote.dto.toEntity
import com.example.meowmate.domain.model.Breed
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

    override fun getCats(forceRefresh: Boolean, query: String?): Flow<Result<List<CatItem>>> =
        flow {
            try {
                val list = api.getImages(apiKey = apiKeyProvider(), limit = 20)
                emit(Result.success(list.map { it.toDomainModel() }))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }

    private fun CatDto.toDomainModel(): CatItem = CatItem(
        imageId = id,
        imageUrl = url,
        breed = breeds?.firstOrNull()?.let { dto ->
            Breed(
                id = dto.id,
                name = dto.name,
                origin = dto.origin ?: "",
                temperament = dto.temperament ?: "",
                lifeSpan = dto.lifeSpan ?: "",
                intelligence = dto.intelligence ?: 0,
                affectionLevel = dto.affectionLevel ?: 0,
                childFriendly = dto.childFriendly ?: 0,
                socialNeeds = dto.socialNeeds ?: 0,
                wikipediaUrl = dto.wikipediaUrl ?: "",
                vetstreetUrl = dto.vetstreetUrl ?: "",
                description = dto.description ?: ""
            )
        }
    )

    override suspend fun getCatByImageId(id: String): Result<CatItem> = try {
        dao.getById(id)?.toDomain()?.let { Result.success(it) }
            ?: run {
                val remote = api.getImageById(apiKeyProvider(), id)
                val entity = remote.toEntity().also { dao.upsertAll(listOf(it)) }
                Result.success(entity.toDomain())
            }
    } catch (e: Exception) {
        Result.failure(e)
    }
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
