package icu.takeneko.comprehension.routing

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    routing {
        singlePageApplication {
            vue("dist")
            useResources = true
            defaultPage = "index.html"
        }
        route("api") {
            route("/v1") {
                get {
                    call.respond("Comprehension API v1")
                }
                route("/passcode") {
                    get("/info") {

                    }
                    get("/start"){

                    }
                    get("/terminate") {

                    }
                }
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