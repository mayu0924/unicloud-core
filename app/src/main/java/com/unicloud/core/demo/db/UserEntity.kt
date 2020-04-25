package com.unicloud.core.demo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * unicloud-core
 * ---------------------------------
 * created by mayu
 * on 2020/4/25
 */
@Entity(tableName = "user_table")
class UserEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sex") val sex: String
){
    override fun toString(): String {
        return "UserEntity(id=$id, name='$name', sex='$sex')"
    }
}