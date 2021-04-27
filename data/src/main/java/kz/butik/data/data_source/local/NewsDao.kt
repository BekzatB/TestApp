package kz.butik.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.butik.data.local_models.ArticleLocalModel

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleLocalModel>)

    @Query("SELECT * FROM article_table")
    suspend fun getArticles(): List<ArticleLocalModel>

    @Query("UPDATE article_table SET isFavorite=:isFav WHERE title=:title")
    suspend fun updateArticle(title: String, isFav: Int)

    @Query("SELECT * FROM article_table WHERE title=:title ORDER BY isFavorite DESC LIMIT 1")
    suspend fun getArticle(title: String): ArticleLocalModel
}