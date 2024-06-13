package icu.takeneko.comprehension.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = ResourceLocation.ResourceLocationSerializer::class)
class ResourceLocation(val namespace: String, val path: String) {
    constructor(complex: String) : this(
        complex.split(":")[0],
        complex.split(":").run { subList(1, this.size).joinToString(":") }
    )

    override fun toString(): String {
        return "$namespace:$path"
    }

    class ResourceLocationSerializer : KSerializer<ResourceLocation> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("icu.takneko.comprehension.data.ResourceLocation", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ResourceLocation {
            return ResourceLocation(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: ResourceLocation) {
            encoder.encodeString(value.toString())
        }

    }
}