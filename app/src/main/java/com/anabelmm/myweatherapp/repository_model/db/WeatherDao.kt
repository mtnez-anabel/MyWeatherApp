package com.anabelmm.myweatherapp.repository_model.db

import androidx.room.*

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM curr_conditions_table")
    suspend fun getWeatherData(): WeatherRelations


    @Transaction
    suspend fun updateData(
        currCondOld: CurrentConditionsEntity,
        each5DaysListOld: List<Each5DaysEntity>,
        each12HoursListOld: List<Each12HoursEntity>,
        currCondNew: CurrentConditionsEntity,
        each5DaysListNew: List<Each5DaysEntity>,
        each12HoursListNew: List<Each12HoursEntity>
    ) {
        deleteData(currCondOld, each5DaysListOld, each12HoursListOld)
        insertData(currCondNew, each5DaysListNew, each12HoursListNew)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(
        currCond: CurrentConditionsEntity,
        each5DaysList: List<Each5DaysEntity>,
        each12HoursList: List<Each12HoursEntity>
    ) {
        insertCurrCond(currCond)
        insertListEach5Days(each5DaysList)
        insertListEach12Hours(each12HoursList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrCond(currCond: CurrentConditionsEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEach5Days(each5DAysList: List<Each5DaysEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEach12Hours(each12HoursList: List<Each12HoursEntity>)

    @Transaction
    @Delete
    suspend fun deleteData(
        currCond: CurrentConditionsEntity,
        each5DaysList: List<Each5DaysEntity>,
        each12HoursList: List<Each12HoursEntity>
    ) {
        deleteCurrCond(currCond)
        deleteListEach5Days(each5DaysList)
        deleteListEach12Hours(each12HoursList)
    }

    @Transaction
    @Delete
    suspend fun deleteCurrCond(currCond: CurrentConditionsEntity)

    @Transaction
    @Delete
    suspend fun deleteListEach5Days(each5DaysList: List<Each5DaysEntity>)

    @Delete
    suspend fun deleteListEach12Hours(each12HoursList: List<Each12HoursEntity>)

    @Query("SELECT COUNT(*) FROM curr_conditions_table")
    fun getCount(): Int
}