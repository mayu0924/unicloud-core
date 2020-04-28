package com.unicloud.core.demo.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.unicloud.core.demo.db.UserEntity

/**
 * unicloud-core
 * ---------------------------------
 * created by mayu
 * on 2020/4/25
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    fun queryAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user_table")
    fun queryAllUsers(): List<UserEntity>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table WHERE name = :name")
    fun queryByName(name: String): LiveData<List<UserEntity>>
}