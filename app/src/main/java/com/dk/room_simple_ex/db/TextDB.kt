package com.dk.room_simple_ex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dk.room_simple_ex.db.dao.TextDao
import com.dk.room_simple_ex.db.dao.TextDao2
import com.dk.room_simple_ex.db.entity.TextEntity
import com.dk.room_simple_ex.db.entity.TextEntity2

@Database(
    entities = [TextEntity::class, TextEntity2::class],
    version = 3)
abstract class TextDB : RoomDatabase() {

    abstract fun textDao() : TextDao
    abstract fun textDao2() : TextDao2

    companion object{

        @Volatile
        private var INSTANCE : TextDB? = null

        fun getDatabase(context: Context) : TextDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TextDB::class.java,
                    "text_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        }
    }