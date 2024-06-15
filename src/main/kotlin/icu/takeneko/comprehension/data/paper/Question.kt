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
    val questionSerializers = mapOf(
        "single_choice" to SingleChoiceQuestion.serializer(),
        "true_or_false" to TrueOrFalseQuestion.serializer(),
        "fill_blank" to FillBlankQuestion.serializer(),
        "multi_line" to MultiLineQuestion.serializer()
    )

    val classSerializationStrategies = mapOf(
        SingleChoiceQuestion::class.java to SingleChoiceQuestion.serializer(),
        TrueOrFalseQuestion::class.java to TrueOrFalseQuestion.serializer(),
        FillBlankQuestion::class.java to FillBlankQuestion.serializer(),
        MultiLineQuestion::class.java to MultiLineQuestion.serializer()
    )
}