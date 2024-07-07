package me.farajist.bmr.routes

import com.natpryce.hamkrest.assertion.assertThat
import me.farajist.FitApp
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class CalculateBMRRouteTest {

    private val client = FitApp()

    @Test
    fun `bmr is calculated when given the required input`() {
        val response = client(Request(Method.POST, "/bmr").body("""
            {
                "age": 45,
                "weight": "60.0",
                "height": "165",
                "gender": "FEMALE"
            }
        """.trimIndent()))
        assertThat(response, hasStatus(Status.OK))
        assertThat(response, hasBody("""{"value":1245.25}""".trimIndent()))
    }

    @Test
    fun `bad request when given the wrong input`() {
        assertThat(client(Request(Method.POST, "/bmr").body("")), hasStatus(Status.BAD_REQUEST))
    }
}