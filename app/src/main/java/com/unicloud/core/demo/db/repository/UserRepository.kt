package com.unicloud.core.demo.db.repository

import com.unicloud.core.demo.db.UserEntity
import com.unicloud.core.demo.db.dao.UserDao

/**
 * unicloud-core
 * ---------------------------------
 * created by mayu
 * on 2020/4/25
 */
class UserRepository(private var userDao: UserDao) {

    suspend fun insert(userEntity: UserEntity) = userDao.insertUser(userEntity)

    suspend fun deleteAll() = userDao.deleteAll()

    fun queryAll() = userDao.queryAllUser()
}