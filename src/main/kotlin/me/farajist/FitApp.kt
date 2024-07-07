package me.farajist

import me.farajist.bmr.BMRApi
import me.farajist.routes.ExampleContractRoute
import org.http4k.contract.bind
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.contract.security.ApiKeySecurity
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.RequestContexts
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun FitApp(): HttpHandler {
    val contexts = RequestContexts()

    return ServerFilters.InitialiseRequestContext(contexts)
        .then(HandleErrors())
        .then(routes(
            "/ping" bind GET to {
                Response(OK).body("pong")
            },

            BMRApi(),

            "/contract/api/v1" bind contract {
                renderer = OpenApi3(ApiInfo("FitApp API", "v1.0"))

                // Return Swagger API definition under /contract/api/v1/swagger.json
                descriptionPath = "/swagger.json"

                // You can use security filter tio protect routes
                security = ApiKeySecurity(Query.int().required("api"), { it == 42 }) // Allow only requests with &api=42

                // Add contract routes
                routes += ExampleContractRoute()
            }
        ))
}


fun main() {
    val server = FitApp().asServer(Undertow(9000)).start()
    println("Server started on " + server.port())
}

private fun HandleErrors() = ServerFilters.CatchLensFailure { failure ->
    Response(BAD_REQUEST).with(
        Body.string(APPLICATION_JSON)
            .toLens() of "{\"message\":${failure.cause?.message.orEmpty()}"
    )
}
