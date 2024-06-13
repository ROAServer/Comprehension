package icu.takeneko.comprehension.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.passcodeApi() {
    routing {
        route("api") {
            route("/v1") {
                route("/passcode") {
                    get("/validate") {
                        val passcode = call.requestParam("passcode") ?: return@get
                        return@get call.respond(passcode == "AAAAAA123456")
                    }
                    get("/info") {

                    }
                    get("/start") {

                    }
                    get("/terminate") {

                    }
                }
            }
        }
    }
}

