package ru.diplom.vrmodelsdbregestry.verification.chain

import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext


open class FileNameVerification : VerificationChain {
    override fun verify(context: VerificationContext) {
        check(context.fileName.matches(FILE_NAME_PATTERN)) {
            "Недопустимое имя файла или имя не передано: ${context.fileName}"
        }
    }

    companion object {
        private val FILE_NAME_PATTERN = Regex("^[A-z0-9\\-_]*\\.zip\$")
    }
}