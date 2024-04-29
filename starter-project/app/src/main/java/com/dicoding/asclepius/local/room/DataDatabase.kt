package com.dicoding.asclepius.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.local.entity.DataEntity

@Database(entities = [DataEntity::class], version = 1)
abstract class DataDatabase : RoomDatabase(){
    abstract fun dataDao(): DataDao
    companion object{
        @Volatile
        private var INSTANCE: DataDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context): DataDatabase{
            if (INSTANCE == null) {
                synchronized(DataDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataDatabase::class.java, "database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as DataDatabase
        }
    }
}