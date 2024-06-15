package icu.takeneko.comprehension.data.paper

import icu.takeneko.comprehension.data.ResourceLocation
import icu.takeneko.comprehension.data.json
import icu.takeneko.comprehension.util.tryDeserialize
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.*

object PaperManager {
    private val packs = mutableMapOf<String, PackContainer>()
    private val packsPath = Path("./ComprehensionData/Packs")
    private val logger = LoggerFactory.getLogger("Comprehension/PaperManager")

    fun init() {
        if (!packsPath.exists()) {
            packsPath.createDirectories()
        }
        packsPath.listDirectoryEntries()
            .filter { it.isDirectory() || it.extension == "zip" }
            .forEach {
                logger.info("Loading pack $it")
                if (it.isDirectory()) {
                    loadDirectoryPack(it)
                } else {
                    loadZipPack(it)
                }
            }
        logger.info("Loaded ${packs.size} paper pack${if (packs.size == 1) "" else "s"}")
    }

    private fun loadDirectoryPack(directory: Path) {
        val metadataPath = directory.resolve("metadata.json")
        val assetIndexPath = directory.resolve("asset_index.json")
        val metadata = metadataPath.inputStream().use { tryDeserialize<PackMetadata>(it) } ?: run {
            logger.error("$directory is not a valid Comprehension PaperPack")
            return
        }
        val assetIndex = assetIndexPath.inputStream().use { tryDeserialize<AssetIndex>(it) } ?: run {
            logger.error("$directory is not a valid Comprehension PaperPack")
            return
        }
        val assets: MutableMap<String, ByteArray> = mutableMapOf()
        assetIndex.forEach { t, u ->
            val assetPath = directory.resolve("assets").resolve(u)
            if (assetPath.notExists()) {
                logger.warn("Cannot load asset $t in pack ${metadata.id}($directory), it does not exist.")
                return@forEach
            }
            assets[t] = assetPath.readBytes()
        }
        val questions: MutableMap<String, Any> = mutableMapOf()
        metadata.questions.forEach { t, u ->
            val s = QuestionRegistry.questionSerializers[u] ?: run {
                logger.error("Cannot find deserializer for question type $u in pack ${metadata.id}($directory).")
                return@forEach
            }
            val questionPath = directory.resolve("questions").resolve("$t.json")
            if (questionPath.notExists()) {
                logger.warn("Cannot load question $t in pack ${metadata.id}($directory), it does not exist.")
                return@forEach
            }
            val obj = try {
                json.decodeFromString(s, questionPath.readText())
            } catch (e: Exception) {
                logger.warn(
                    "Cannot load question $t in pack ${metadata.id}($directory), deserialization failed with an exception.",
                    e
                )
                return@forEach
            }
            questions[t] = obj
        }
        val packContainer = PackContainer(metadata).apply {
            this.questions += questions
            this.assets += assets
        }
        packs[packContainer.metadata.id] = packContainer
    }

    fun loadZipPack(directory: Path) {

    }

    fun getFirstQuestion(paperId: String): ResourceLocation? {
        if (paperId !in paperId) return null
        return ResourceLocation(paperId, packs[paperId]!!.questions.keys.firstOrNull() ?: return null)
    }
}