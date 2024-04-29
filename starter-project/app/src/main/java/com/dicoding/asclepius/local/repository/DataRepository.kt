package com.dicoding.asclepius.local.repository

import android.app.Application
import com.dicoding.asclepius.local.entity.DataEntity
import com.dicoding.asclepius.local.room.DataDao
import com.dicoding.asclepius.local.room.DataDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DataRepository(application: Application) {
    private val dataDao : DataDao
    private val executor : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DataDatabase.getDatabase(application)
        dataDao = db.dataDao()
    }

    fun addData(data: DataEntity) {
        executor.execute { dataDao.insert(data) }
    }
}