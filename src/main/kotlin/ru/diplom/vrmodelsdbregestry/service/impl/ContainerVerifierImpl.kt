package ru.diplom.vrmodelsdbregestry.service.impl

import org.springframework.stereotype.Service
import ru.diplom.vrmodelsdbregestry.service.ContainerVerifier
import ru.diplom.vrmodelsdbregestry.verification.VerificationChain
import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

@Service
class ContainerVerifierImpl(
    private val verificationChain: List<VerificationChain>
) : ContainerVerifier {
    override fun verifyContainer(
        fileName: String,
        containerBytes: ByteArray,
    ) {
        var verificationContext = VerificationContext(
            fileName = fileName,
            bytes = containerBytes
        )
        verificationChain.forEach { check ->
            verificationContext = check.verify(verificationContext)
        }
    }
}