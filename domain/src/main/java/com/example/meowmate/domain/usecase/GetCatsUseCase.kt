package com.example.meowmate.domain.usecase

import com.example.meowmate.domain.model.CatItem
import com.example.meowmate.domain.repository.CatsRepository

class GetCatsUseCase(private val repo: CatsRepository) {
    suspend operator fun invoke(query: String = ""): List<CatItem> {
        return repo.getCats(query)
    }
}