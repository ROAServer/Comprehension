package icu.takeneko.comprehension.routing

import icu.takeneko.comprehension.data.passcode.PasscodeManager
import io.ktor.http.*
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
                        return@get call.respond(PasscodeManager.validate(passcode))
                    }
                    get("/info") {
                        val passcode = call.requestParam("passcode") ?: return@get
                        call.respond(
                            PasscodeManager.info(passcode) ?: return@get call.respondText(
                                "",
                                status = HttpStatusCode.NotFound
                            )
                        )
                    }
                    get("/start") {
                        val passcode = call.requestParam("passcode") ?: return@get
                        call.respond(PasscodeManager.start(passcode))
                    }
                    get("/finish") {
                        val passcode = call.requestParam("passcode") ?: return@get
                        call.respond(PasscodeManager.finish(passcode))
                    }
                }
            }
        }
    }
}

