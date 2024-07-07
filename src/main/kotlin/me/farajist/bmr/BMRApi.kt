package me.farajist.bmr

import me.farajist.bmr.routes.calculateBMRRoute
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.OpenAPIJackson
import org.http4k.contract.openapi.v3.OpenApi3

fun BMRApi() = contract {
    renderer = OpenApi3(ApiInfo("BMR service", "v1.0"), OpenAPIJackson)
    descriptionPath = "bmr"
    routes += listOf(calculateBMRRoute())
}