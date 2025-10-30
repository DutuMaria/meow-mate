package com.example.meowmate.domain.usecase

import com.example.meowmate.domain.repository.CatsRepository

class GetCatByIdUseCase(private val repo: CatsRepository) {
    suspend operator fun invoke(id: String) = repo.getCatByImageId(id)
}