package icu.takeneko.comprehension.data.paper

import icu.takeneko.comprehension.data.ResourceLocation
import kotlinx.serialization.Serializable

@Serializable
data class SingleChoiceQuestion(
    val title: String,
    val assetRef: List<ResourceLocation> = listOf(),
    val choices: Map<String, String>,
    val answer: String
)

@Serializable
data class TrueOrFalseQuestion(
    val title: String,
    val assetRef: List<ResourceLocation> = listOf(),
    val choices: List<String>,
    val answer: Boolean
)

@Serializable
data class FillBlankQuestion(
    val title: String,
    val assetRef: List<ResourceLocation> = listOf(),
    val parts: List<String>
)

@Serializable
data class MultiLineQuestion(
    val title: String,
    val description: String,
    val assetRef: List<ResourceLocation> = listOf()
)

object QuestionRegistry{
    val questionTypes = mapOf(
        "single_choice" to SingleChoiceQuestion::class.java,
        "true_or_false" to TrueOrFalseQuestion::class.java,
        "fill_blank" to FillBlankQuestion::class.java,
        "multi_line" to MultiLineQuestion::class.java
    )
}