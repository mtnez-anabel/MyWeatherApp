package com.anabelmm.myweatherapp.repository_model.db

import androidx.room.*

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM curr_conditions_table")
    suspend fun getWeatherData(): WeatherRelations

//    @Transaction
//    @Query("SELECT * FROM curr_conditions_table")
//    suspend fun getList5DaysData(): List<Each5DaysEntity>
//
//    @Transaction
//    @Query("SELECT * FROM curr_conditions_table")
//    suspend fun getList12HoursData(): List<Each12HoursEntity>

    @Transaction
    suspend fun updateData(currCond: CurrentConditionsEntity,
                           each5DaysList: List<Each5DaysEntity>,
                           each12HoursList:  List<Each12HoursEntity>) {
        deleteData(currCond, each5DaysList, each12HoursList)
        insertData(currCond, each5DaysList, each12HoursList)
    }

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(
        currCond: CurrentConditionsEntity,
        each5DaysList: List<Each5DaysEntity>,
        each12HoursList:  List<Each12HoursEntity>
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
    suspend fun deleteData(currCond: CurrentConditionsEntity,
                   each5DaysList: List<Each5DaysEntity>,
                   each12HoursList:  List<Each12HoursEntity>){
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