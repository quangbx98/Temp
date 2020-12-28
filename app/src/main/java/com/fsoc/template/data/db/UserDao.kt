package com.fsoc.template.data.db

import androidx.room.*
import com.fsoc.template.data.db.entity.UserEntity
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity")
    fun getAll(): Flowable<List<UserEntity>?>

    @Query("SELECT * FROM userentity WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flowable<List<UserEntity>?>

//    @Query("SELECT * FROM user WHERE username LIKE :username AND " + " LIMIT 1")
//    fun findByName(username: String): Observable<UserEntity?>

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)


    /*
    *
    * @Query("Select * from news_articles")
    fun getAllArticles(): Flowable<List<NewsPublisherData>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllArticles(articles: List<NewsPublisherData>)

    @Query("DELETE FROM news_articles")
    fun clear()

    * */

    /*
    *  @Query("SELECT * FROM album_table WHERE id = :id")
    override fun select(id: Long): Flowable<AlbumData.Album>

    @Query("SELECT * FROM album_table ORDER BY id")
    override fun selectAllPaged(): DataSource.Factory<Int, AlbumData.Album>

    @Query("DELETE FROM album_table")
    override fun truncate()

    @Transaction
    fun replace(albums: List<AlbumData.Album>) {
        val firstId: Long = albums.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = albums.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(albums)
    }

    @Query("DELETE FROM album_table WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)
    *
    * */
}