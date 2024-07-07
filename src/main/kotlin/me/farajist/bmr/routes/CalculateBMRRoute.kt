package me.farajist.bmr.routes

import me.farajist.bmr.models.BMRData
import me.farajist.bmr.models.BMRGender
import me.farajist.bmr.models.BMRResult
import me.farajist.bmr.models.bmrDataLens
import me.farajist.bmr.models.bmrResultLens
import me.farajist.bmr.services.BMRCalculator
import org.http4k.contract.ContractRoute
import org.http4k.contract.meta
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import java.math.BigDecimal




fun calculateBMRRoute(): ContractRoute {

    val spec = "/bmr" meta {
        summary = "calculate base metabolic rate"
        receiving(bmrDataLens to BMRData(weight = BigDecimal("60.0"), height = BigDecimal("165"), age = 45, gender = BMRGender.FEMALE))
        returning(OK, bmrResultLens to BMRResult(value = BigDecimal("1245.25")))
    } bindContract POST

    val handleCalculateBMR: HttpHandler = { request: Request ->
        val received: BMRData = bmrDataLens(request)
        val result = BMRResult(BMRCalculator.calculate(received))
        Response(OK).with(bmrResultLens of result)
    }

    return spec to handleCalculateBMR
}