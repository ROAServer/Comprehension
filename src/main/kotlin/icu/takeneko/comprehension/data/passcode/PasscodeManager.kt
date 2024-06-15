package icu.takeneko.comprehension.data.passcode

import icu.takeneko.comprehension.config
import icu.takeneko.comprehension.data.ResourceLocation
import icu.takeneko.comprehension.data.json
import icu.takeneko.comprehension.data.paper.PaperManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromStream
import kotlin.io.path.*

object PasscodeManager {

    private val passcodes = mutableMapOf<String, PassCode>()
    private val testResults = mutableMapOf<String, TestResult>()
    private val passcodesPath = Path("./ComprehensionData/Passcode")
    private val passcodeData = passcodesPath.resolve("Data")
    private val passcodeResult = passcodesPath.resolve("Result")

    fun init() {
        if (!passcodeData.exists()) {
            passcodeData.createDirectories()
        }
        if (!passcodeResult.exists()) {
            passcodeResult.createDirectories()
        }
        read()
    }

    fun flush() {
        passcodes.values.forEach {
            passcodeData.resolve("${it.passcode}.json").apply {
                deleteIfExists()
                createFile()
                this.writeText(json.encodeToString(it))
            }
        }
        testResults.values.forEach {
            passcodeResult.resolve("${it.passcode}.json").apply {
                deleteIfExists()
                createFile()
                this.writeText(json.encodeToString(it))
            }
        }
    }

    fun read() {
        (passcodeData.toFile().listFiles() ?: return)
            .filter { it.extension == "json" }
            .forEach {
                val pc = try {
                    json.decodeFromStream<PassCode>(it.inputStream())
                } catch (e: Exception) {
                    e.printStackTrace()
                    it.toPath().deleteIfExists()
                    return@forEach
                }
                passcodes[pc.passcode] = pc
            }
        (passcodeResult.toFile().listFiles() ?: return)
            .filter { it.extension == "json" }
            .forEach {
                val pc = try {
                    json.decodeFromStream<TestResult>(it.inputStream())
                } catch (e: Exception) {
                    e.printStackTrace()
                    it.toPath().deleteIfExists()
                    return@forEach
                }
                testResults[pc.passcode] = pc
            }
        testResults
            .filter { it.key !in passcodes }
            .forEach {
                testResults.remove(it.key)
            }
        passcodes
            .filter { it.key !in testResults }
            .map { it.value }
            .forEach {
                it.started = false
                it.hasTestResult = false
            }
        flush()
    }

    fun getProgress(passCode: String): ResourceLocation? {
        val result = passcodes[passCode] ?: return null
        if (!validatePasscodeStatus(passCode)) return null
        val testResult = testResults[passCode] ?: return null
        return testResult.progress
    }

    fun setProgress(passCode: String, location: ResourceLocation): Boolean {
        val result = passcodes[passCode] ?: return false
        if (!validatePasscodeStatus(passCode)) return false
        val testResult = testResults[passCode] ?: return false
        testResult.progress = location
        flush()
        return false
    }

    private fun validatePasscodeStatus(passCode: String): Boolean {
        val result = passcodes[passCode] ?: return false
        if (result.started && !result.hasTestResult) {
            result.hasTestResult = false
            result.started = false
            testResults.remove(passCode)
            flush()
            return false
        }
        if (result.started && result.hasTestResult && passCode !in testResults) {
            result.hasTestResult = false
            result.started = false
            testResults.remove(passCode)
            return false
        }
        return true
    }

    fun requestRestart(passCode: String): Boolean {
        if (passCode !in passcodes) return false
        passcodes[passCode]?.hasTestResult = false
        passcodes[passCode]?.started = false
        testResults.remove(passCode)
        return true
    }

    fun finish(passCode: String): Boolean {
        if (passCode !in passcodes) return false
        if (passCode !in testResults) return false
        validatePasscodeStatus(passCode)
        passcodes[passCode]!!.apply {
            started = true
            hasTestResult = true
            testResults[passCode]!!.apply {
                this.finishTime = System.currentTimeMillis()
            }
        }
        flush()
        return true
    }

    fun start(passCode: String): Boolean {
        if (passCode !in passcodes) return false
        if (passCode !in testResults) return false
        validatePasscodeStatus(passCode)
        passcodes[passCode]!!.apply {
            started = true
            hasTestResult = true
            testResults[passCode] = TestResult(
                passcode = passCode,
                startTime = System.currentTimeMillis(),
                paperId = this.paperId,
                progress = PaperManager.getFirstQuestion(this.paperId) ?: return false
            )
        }
        return true
    }

    fun deletePasscode(passCode: String): Boolean {
        return passcodes.remove(passCode) != null
    }

    fun isStarted(passCode: String): Boolean {
        val result = passcodes[passCode] ?: return false
        return result.started
    }

    fun validate(passCode: String): Boolean {
        return passCode in passcodes
    }

    fun create(
        passCode: String,
        expireTime: Long = System.currentTimeMillis() + config.passcodeDefaultExpireTime,
        associatePaperId: String
    ) {
        val pc = PassCode(
            passCode,
            expireTime,
            associatePaperId,
        )
        passcodes[passCode] = pc
    }

    fun info(passcode: String): PassCode? {
        if (passcode in passcodes){
            validatePasscodeStatus(passcode)
            return passcodes[passcode]!!
        }
        return null
    }
}