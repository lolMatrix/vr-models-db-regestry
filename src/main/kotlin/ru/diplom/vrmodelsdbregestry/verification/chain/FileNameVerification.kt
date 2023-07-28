package ru.diplom.vrmodelsdbregestry.verification.chain

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class FileNameVerification : VerificationChain {
    override fun verify(context: VerificationContext) = context.apply {
        check(context.fileName.matches(FILE_NAME_PATTERN)) {
            "Недопустимое имя файла или имя не передано: ${context.fileName}"
        }
    }

    companion object {
        private val FILE_NAME_PATTERN = Regex("^[A-z0-9\\-_]*\\.zip\$")
    }
}