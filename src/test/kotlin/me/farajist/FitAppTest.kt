package me.farajist

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FitAppTest {

    private val client = FitApp()
    @Test
    fun `Ping test`() {
        assertEquals(Response(OK).body("pong"), client(Request(GET, "/ping")))
    }

}
