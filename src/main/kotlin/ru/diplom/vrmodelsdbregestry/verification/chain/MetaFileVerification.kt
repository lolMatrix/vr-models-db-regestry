package ru.diplom.vrmodelsdbregestry.verification.chain

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext
import ru.diplom.vrmodelsdbregestry.verification.dto.PositioningInstruction
import java.io.ByteArrayInputStream
import java.util.zip.ZipInputStream

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
class MetaFileVerification : VerificationChain {
    override fun verify(context: VerificationContext) = context.apply {
        ZipInputStream(ByteArrayInputStream(bytes)).use {
            var entry = it.nextEntry
            while (entry != null) {
                if (!entry.isDirectory && !entry.name.contains("__MACOSX")) {
                    when  {
                        entry.name.contains(".json") -> {
                            val objectMapper = jacksonMapperBuilder()
                                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .build()
                            positioningInstruction = objectMapper
                                .readValue<PositioningInstruction>(it.readBytes())
                        }
                        entry.name.contains(".mtl") || entry.name.contains(".obj") -> filesList += entry.name
                    }
                }
                entry = it.nextEntry
            }
            it.closeEntry()
        }
        check(positioningInstruction != null) {
            "Не представлен файл метаинформации"
        }
    }
}
