package me.farajist.bmr.models

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import java.math.BigDecimal

data class BMRResult(val value: BigDecimal)
val bmrResultLens = Body.auto<BMRResult>().toLens()