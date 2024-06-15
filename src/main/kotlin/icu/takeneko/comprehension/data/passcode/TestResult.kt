package icu.takeneko.comprehension.data.passcode

import icu.takeneko.comprehension.data.ResourceLocation
import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
    val passcode: String,
    val startTime: Long,
    var finishTime: Long = -1,
    val paperId: String,
    val dataForm: Map<String, String> = mapOf(),
    val answers: Map<String, String> = mapOf(),
    var progress: ResourceLocation
)