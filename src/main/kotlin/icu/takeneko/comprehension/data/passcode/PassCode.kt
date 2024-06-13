package icu.takeneko.comprehension.data.passcode

import kotlinx.serialization.Serializable

@Serializable
data class PassCode(
    val passcode: String,
    val expireTime: Long,
    val answerTimeLimit: Long,
    val paperId:String
)
