package icu.takeneko.comprehension.data.paper

class PackContainer(val metadata: PackMetadata) {
    val questions: MutableMap<String, Any> = mutableMapOf()
    val assets: MutableMap<String, ByteArray> = mutableMapOf()

    fun <T> getQuestion(id: String, type: Class<out T>): T? {
        return questions[id] as T?
    }

    fun getAsset(id: String): ByteArray? {
        return assets[id]
    }

}