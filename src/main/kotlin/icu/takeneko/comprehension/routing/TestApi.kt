package icu.takeneko.comprehension.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.testApi(){
    routing {
        route("api") {
            route("/v1") {
                route("/test") {
                    get("/commit") {

                    }
                    get("/answer") {

                    }
                }
            }
        }
    }
}