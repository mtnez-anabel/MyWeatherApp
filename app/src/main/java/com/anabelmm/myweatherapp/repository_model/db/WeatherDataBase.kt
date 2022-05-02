package com.anabelmm.myweatherapp.repository_model.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CurrentConditionsEntity::class, Each5DaysEntity::class, Each12HoursEntity::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDataBase? = null

        // It returns a Singleton WeatherDataBase
        fun getDatabase(context: Context, scope: CoroutineScope): WeatherDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDataBase::class.java,
                    "WeatherRelations"
                )
                    .addCallback(WeatherDataBaseCallback(scope))
                    .build()
                Log.i("!!!!!!!!!!", "its created....................")
                INSTANCE = instance
                // return instance
                instance

            }

        }

    }

    private class WeatherDataBaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.i("!!!!!!!!!!", "after onCreate from callback....................")
            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.getWeatherDao()
                    //It pre-populates the DB when it's created for the first time
                    //to avoid crashes if server fails
                    val manager = DataBaseManager()
                    manager.setToDB(dao, FixedData.fixedData)
                    Log.i("!!!!!!!!!!", "its populated....................")
                }

            }
        }
    }

}