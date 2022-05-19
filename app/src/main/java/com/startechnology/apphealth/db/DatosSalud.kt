package com.startechnology.apphealth.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "datos_salud")
class DatosSalud (
    val sistolica: Int,
    val diastolica: Int,
    val pulso: Int,
    val fecha: String,
    val mes: String,
    val nota: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
): Serializable