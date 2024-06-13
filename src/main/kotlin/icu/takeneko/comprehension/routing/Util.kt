package icu.takeneko.comprehension.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.requestParam(param: String): String? {
    val result = parameters[param]
    if (result == null) {
        respondText("", status = HttpStatusCode.BadRequest)
        return null
    }
    return result
}