package com.anabelmm.myweatherapp.repository_model.db

import androidx.room.*
import com.anabelmm.myweatherapp.repository_model.DataManagerAPIClient


data class WeatherRelations(
   @Embedded val currCond : CurrentConditionsEntity,
   @Relation(
       parentColumn = "observationDateTime",
       entityColumn = "momentObservation5D",
       entity = Each5DaysEntity::class
   )
   val list5D: List<Each5DaysEntity>,
   @Relation(
       parentColumn = "observationDateTime",
       entityColumn = "momentObservation12H",
       entity = Each12HoursEntity::class
   )
   val list12H: List<Each12HoursEntity>
)

@Entity(tableName = "curr_conditions_table")
data class CurrentConditionsEntity(
    @PrimaryKey val observationDateTime: String,
    val currWeatherPhrase: String,
    val isDayTime: Boolean,
    val currTemperature: Double,
    val realFeelTemperature: Double,
)
@Entity(tableName = "each5days_table")
data class Each5DaysEntity(
    @PrimaryKey val idDay: Int,
    val momentObservation5D: String,
    val minValue: Double,
    val maxValue: Double,
    val iconDay: Int,
    val dayPhrase: String,
    val iconNight: Int,
    val nightPhrase: String
)
@Entity(tableName = "each12hours_table")
data class Each12HoursEntity(
    @PrimaryKey val idHour: Int,
    val momentObservation12H: String,
    val epochDateTime: Long,
    val weatherIcon: Int,
    val hourlyTempValue: Double
)

