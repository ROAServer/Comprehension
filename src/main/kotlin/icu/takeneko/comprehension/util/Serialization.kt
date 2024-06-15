package icu.takeneko.comprehension.util

import icu.takeneko.comprehension.data.json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

inline fun <reified T> tryDeserialize(input:String): T? {
    return try {
        json.decodeFromString<T>(input)
    }catch (e:Exception){
        return null
    }
}

inline fun <reified T> tryDeserialize(input:InputStream): T? {
    return try {
        json.decodeFromStream<T>(input)
    }catch (e:Exception){
        return null
    }
}