package me.farajist.bmr.services

import me.farajist.bmr.models.BMRData
import me.farajist.bmr.models.BMRGender
import java.math.BigDecimal

object BMRCalculator {

    fun calculate(data: BMRData): BigDecimal {
        val result = data.weight.times(BigDecimal(10)) + data.height.times(BigDecimal(6.25)) - data.age.times(5).toBigDecimal()
        return if (data.gender === BMRGender.MALE) result.plus(BigDecimal(5)) else result.minus(BigDecimal(161))
    }
}