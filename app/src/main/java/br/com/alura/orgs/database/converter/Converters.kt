package br.com.alura.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun deDouble(valor: Double?): BigDecimal {
        return valor?.let {
            //transformando para string o valor para evitar valores bizarros
            BigDecimal(valor.toString())
        } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun deBigDecimalParaDouble(valor: BigDecimal?): Double? {
        return valor?.let { valor.toDouble() }
    }
}