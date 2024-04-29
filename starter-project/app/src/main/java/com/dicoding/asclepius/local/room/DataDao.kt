package com.dicoding.asclepius.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dicoding.asclepius.local.entity.DataEntity
@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite : DataEntity)
}