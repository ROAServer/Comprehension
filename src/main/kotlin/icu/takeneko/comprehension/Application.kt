package icu.takeneko.comprehension

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("Comprehension")
    logger.info("Hello World!")
    val port = (System.getProperty("comprehension.port") ?: "80").toIntOrNull() ?: 80
    val host = System.getProperty("comprehension.host") ?: "0.0.0.0"

    embeddedServer(Netty, port = port, host = host, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(AutoHeadResponse)
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    install(CachingHeaders) {
        val noCache = io.ktor.http.content.CachingOptions(CacheControl.NoCache(CacheControl.Visibility.Public))
        val lazy = io.ktor.http.content.CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60 * 10)) // 10 minutes
        val superLazy = io.ktor.http.content.CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60 * 60)) // 1 hour
        options { _, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.Html -> noCache
                ContentType.Text.CSS -> lazy
                ContentType.Text.JavaScript -> lazy

                ContentType.Image.SVG -> superLazy
                ContentType.Image.JPEG -> superLazy
                ContentType.Image.PNG -> superLazy
                ContentType("image", "fav") -> superLazy
                else -> null
            }
        }
    }
    install(ContentNegotiation) {
        json()
    }
    routing {
        singlePageApplication {
            vue("dist")
            useResources = true
            defaultPage = "index.html"
        }
        // actual API code
        route("api") {
            get("/") {
                call.respondText { "Some API response" }
            }
        }
    }
}

val Application.envKind get() = environment.config.propertyOrNull("ktor.environment")?.getString()
val Application.isDev get() = envKind != null && envKind == "dev"
val Application.isProd get() = envKind != null && envKind != "dev"
