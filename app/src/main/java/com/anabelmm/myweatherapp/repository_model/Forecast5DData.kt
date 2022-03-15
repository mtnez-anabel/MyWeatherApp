import com.google.gson.annotations.SerializedName

data class Forecast5DData(
    @SerializedName("DailyForecasts") val list: List<Days>
)
data class Days(
    @SerializedName("Temperature") val temperature: TemperatureByDay,
    @SerializedName("Day") val day : Day,
    @SerializedName("Night") val night: Night
)
data class TemperatureByDay(
    @SerializedName("Minimum") val minimum : Minimum,
    @SerializedName("Maximum") val maximum : Maximum
)
data class Minimum(
    @SerializedName("Value") val minValue : Double
)
data class Maximum(
    @SerializedName("Value") val maxValue : Double
)
data class Day(
    @SerializedName("Icon") val iconDay: Int,
    @SerializedName("IconPhrase") val dayPhrase : String
)
data class Night(
    @SerializedName("Icon") val iconNight: Int,
    @SerializedName("IconPhrase") val nightPhrase : String
)
