package icu.takeneko.comprehension.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.intrinsicsApi(){
    routing {
        route("api") {
            route("/v1") {
                route("/intrinsics") {
                    route("/passcode"){
                        get("/create") {

                        }
                        get("/delete") {

                        }
                        post("/results"){

                        }
                    }
                    post("/upload"){

                    }
                    post("/list") {

                    }
                    delete("/delete") {

                    }
                    get("/reload") {

                    }
                }
            }
        }
    }
}