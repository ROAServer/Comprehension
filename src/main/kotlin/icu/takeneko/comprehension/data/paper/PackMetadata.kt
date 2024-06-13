package icu.takeneko.comprehension.data.paper

import kotlinx.serialization.Serializable

@Serializable
data class PackMetadata(
    val id: String,
    val title: String,
    val version: String,
    val answerTimeLimit:String,
    val dataForm: Map<String,String>,
    val questions: Map<String, String>,

)

@Serializable
data class DataFormItem(
    val name: String,
    val hint: String,
)

typealias AssetIndex = Map<String, String>


