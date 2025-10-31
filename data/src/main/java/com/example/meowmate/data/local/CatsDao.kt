package com.example.meowmate.data.local


import androidx.room.*

@Dao
interface CatsDao {
    @Query("SELECT * FROM cat_images ORDER BY name ASC")
    suspend fun getAll(): List<CatImageEntity>

    @Query("SELECT * FROM cat_images WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): CatImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<CatImageEntity>)

    @Query("DELETE FROM cat_images")
    suspend fun clear()
}
