package com.example.mymobileapp.DataBase
import kotlinx.serialization.Serializable

@Serializable
data class CarInfo (
    val id: Int = -1,
    val model: String? = null,
    val manufacturer: String? = null,
    val price_per_day: Int? = null,
    val transmission: String? = null,
    val type_of_fuel: String? = null,
)