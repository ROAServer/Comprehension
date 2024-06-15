package icu.takeneko.comprehension.data

import io.ktor.util.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlin.io.path.*

@Serializable
data class Config(
    val passcodeDefaultExpireTime: Long = 3600 * 1000,
    val apiKey: String = generateNonce() + generateNonce()
)

val json = Json {
    encodeDefaults = true
    prettyPrint = true
}

@OptIn(ExperimentalSerializationApi::class)
fun loadConfig(): Config {
    Path("./ComprehensionData/config.json").apply {
        if (notExists()) {
            return Config().also(::writeConfig)
        }
        return try {
            inputStream().use { json.decodeFromStream<Config>(it).also(::writeConfig) }
        } catch (e: Exception) {
            Config().also(::writeConfig)
        }
    }
}

fun writeConfig(config: Config) {
    Path("./ComprehensionData/config.json").apply {
        deleteIfExists()
        createFile()
        writeText(json.encodeToString(config))
    }
}
