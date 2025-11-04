package com.example.meowmate.data.local


import androidx.room.*

@Dao
interface CatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CatEntity>)

    @Query("SELECT * FROM cats ORDER BY savedAt DESC")
    suspend fun getAll(): List<CatEntity>

    @Query("""
        SELECT * FROM cats
        WHERE name       LIKE '%' || :q || '%' COLLATE NOCASE
           OR origin     LIKE '%' || :q || '%' COLLATE NOCASE
           OR temperament LIKE '%' || :q || '%' COLLATE NOCASE
        ORDER BY savedAt DESC
    """)
    suspend fun search(q: String): List<CatEntity>

    @Query("SELECT * FROM cats WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): CatEntity?


    @Query("DELETE FROM cats")
    suspend fun clearAll()
}
