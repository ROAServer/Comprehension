package icu.takeneko.comprehension.data.passcode

import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
    val passcode: String,
    val startTime: Long,
    val finishTime: Long,
    val paperId: String,
    val dataForm: Map<String, String> = mapOf(),
    val answers: Map<String, String> = mapOf()
)