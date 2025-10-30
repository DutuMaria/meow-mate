package com.example.meowmate.domain.usecase

import com.example.meowmate.domain.repository.CatsRepository

class GetCatsUseCase(private val repo: CatsRepository) {
    operator fun invoke(force: Boolean, query: String?) = repo.getCats(force, query)
}