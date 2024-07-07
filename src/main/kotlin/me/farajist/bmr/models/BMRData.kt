package me.farajist.bmr.models

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import java.math.BigDecimal

enum class BMRGender {
    MALE,
    FEMALE
}

data class BMRData(val weight: BigDecimal, val height: BigDecimal, val age: Int, val gender: BMRGender)
val bmrDataLens = Body.auto<BMRData>().toLens()