package com.startechnology.apphealth.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatosSaludDAO {

    @Query("SELECT * FROM datos_salud")
    fun getAll(): LiveData<List<DatosSalud>>

    @Query("SELECT * FROM datos_salud WHERE id = :id")
    fun get(id:Int): LiveData<DatosSalud>

    @Insert
    fun insertAll(vararg infoSalud: DatosSalud)

    @Update
    fun update(infoSalud: DatosSalud)

    @Query("SELECT * FROM datos_salud WHERE id = (SELECT MAX(id) FROM datos_salud)")
    fun getLast(): LiveData<DatosSalud>

    @Query("SELECT * FROM datos_salud WHERE mes = :mes")
    fun getAllByMes(mes:String): LiveData<List<DatosSalud>>
}